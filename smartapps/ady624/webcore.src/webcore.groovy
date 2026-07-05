/*
 *  webCoRE - Community's own Rule Engine - Web Edition for HE
 *
 *  Copyright 2016 Adrian Caramaliu <ady624("at" sign goes here)gmail.com>
 *
 *  webCoRE (MAIN APP)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Last update July 5, 2026 for Hubitat
 */

/*
 * Parent (this file) <-> piston child (webcore-piston.groovy) interface.
 * Piston children are found via findPiston()/getChildApps() and referenced below as
 * piston/chld/t1; the child calls parent.xxx() using the wNNN() wrapper methods defined
 * near the bottom of webcore-piston.groovy.
 *
 * Calls this app makes to a piston child:
 *   piston.get(minimal)                       - fetch full/minimal runtime data for the dashboard
 *   piston.setup(data, chunks)                - save an uploaded/edited piston definition
 *   piston.pausePiston() / piston.resume()    - pause/resume a piston
 *   piston.deletePiston()                     - piston self-cleanup before app.deleteChildApp()
 *   piston.setLocalVariable(name, value)      - set a piston-local variable from the dashboard
 *   piston.proxyEvaluateExpression(...)       - evaluate an expression in the piston's context (IDE)
 *   piston.activity(lastLogTimestamp)         - get activity/log data since a timestamp
 *   piston.execute(data, src)                 - run a piston (external trigger or executePiston())
 *   piston.config(data)                       - initialize a newly created piston
 *   piston.test()/.clickTile()/.setBin()/.setCategory()/.updModified()/.setLoggingLevel()/.clearLogs()
 *                                              - misc dashboard-driven ops (dynamic dispatch via common_Simple)
 *   chld.clearLogsQ()/.clearAllQ()/.clearCache() - periodic/bulk piston cache cleanup (clearChldCaches)
 *   chld.curPState()                          - fetch cached piston metadata (gtMeta fallback)
 *   chld.killSwitchDisable()                  - notify all pistons the global kill switch turned on
 *   chld.updated()                            - force a child's updated() (forced resubscribe)
 *   t1.clearParentCache(meth)                 - tell one piston to refresh its cached copy of gtPdata()
 *   t1.clearGlobalCache(meth)                 - tell one piston to refresh its global variable cache
 *   t1.gtGlobalVarsInUse()                    - ask a piston which global vars it references
 *
 * Calls a piston child makes to this app (parent.xxx()):
 *   parent.isInstalled() / .getWikiUrl() / .getDashboardUrl() / .getWCendpoints()
 *                                              - install state, links, and endpoint config (piston prefs page)
 *   parent.generatePistonName()               - default name for a new piston
 *   parent.pistonUninstalled(id)              - piston deleted outside the dashboard API (e.g. HE Apps list);
 *                                                invalidates the parent's piston-list/metadata/base-result caches
 *   parent.readFuelStream()/.writeFuelStream()/.clearFuelStream()/.writeToFuelStream()
 *                                              - fuel stream storage passthrough
 *   parent.getChildAttributes()/.getChildComparisons()/.getChildVirtCommands()/.getChildVirtDevices()/
 *   .getChildCommands()/.getColors()          - shared reference-data caches, loaded once and reused by all pistons
 *   parent.gtPdata()                          - shared piston state map (enabled/disabled, settings, lifx, etc.)
 *   parent.pCallupdateRunTimeData(rt)         - relay updated runtime data back into the parent's cache
 *   parent.getPushDev()                       - configured push-notification device(s)
 *   parent.executePiston(pistonId, data, selfId) - ask parent to execute a different piston
 *   parent.pausePiston(pistonId, selfId) / .resumePiston(pistonId, selfId) - ask parent to pause/resume another piston
 *   parent.isPisPaused(pistonId)              - ask parent whether another piston is paused
 *   parent.getGStore()                        - global store map
 *   parent.listAvailableDevices(raw)          - devices selected for use with webCoRE
 *   parent.getWData()                         - weather data
 *   parent.listAvailableVariables()           - global variables list
 *
 * Parent (this file) <-> fuel stream child (webcore-fuel-stream.groovy) interface.
 * Fuel stream/graph children are found via findCreateFuel()/getChildApps(name==handleFuelS()),
 * referenced below as result/stream/it/lts (lts is the fixed "webCoRE Long Term Storage" instance
 * returned by gtLTS() - same app type, distinguished by label).
 *
 * Calls this app makes to a fuel stream child:
 *   result.createStream(map)                  - initialize a newly created fuel stream
 *   result.readFuelStream(req)/.writeFuelStream(req)/.clearFuelStream(req)/.updateFuelStream(req)
 *                                              - proxied 1:1 from the parent methods of (almost) the same
 *                                                name that pistons call - see readFuelStream() etc. below
 *   it.getFuelStreams(includeLTS)             - list a canister's streams (listFuelStreams)
 *   stream.listFuelStreamData(id)             - fetch stored data points (api_intf_fuelstreams_get)
 *   graphChild.gforward(data.path)            - forward an HTTP request to a graph (api_forward); found via
 *                                                findPiston() with n=handleFuelS(), so despite the helper's
 *                                                name this is a fuel stream child, not a piston
 *   lts.quantParams(sensorId, attr)/.isStorage(sensorId, attr)/.isQuant(sensorId, attr)
 *                                              - proxied from quantParams()/ltsAvailable()/ltsQuant() below,
 *                                                which regular graph children call on the parent to reach LTS
 *
 * Calls a fuel stream child makes to this app (parent.xxx()):
 *   parent.childAppDuplicationFinished(type, childId) - notify parent a graph was duplicated
 *   parent.resetFuelStreamList()              - invalidate the cached fuel stream list
 *   parent.ltsExists() / .ltsAvailable(id, attr) / .quantParams(id, attr)
 *                                              - ask parent to check/query the LTS child (see proxy above)
 *   parent.listFuelStreams(includeLTS)        - list all fuel streams across canisters
 *   parent.readFuelStream(stream)             - read another stream's data (cross-stream reference)
 *   parent.getWData() / .openWeatherConfig()  - weather data / config (proxied to the storage child, see below)
 *   parent.getWCendpoints()                   - endpoint config
 *   parent.hashPID(id)                        - hash an id the same way pistons are hashed
 *
 * Parent (this file) <-> storage child (webcore-storage.groovy) interface.
 * There is at most one storage child per instance, found/created via getStorageApp(); it exists only
 * to host the $weather device integration.
 *
 * Calls this app makes to the storage child:
 *   storageApp.updateLabel(label)             - keep the storage app's label in sync with this app's name
 *   storageApp.settingsToState(key, value)    - push weather-related settings into the storage app's state
 *   storageApp.startWeather() / .stopWeather() - enable/disable the weather integration
 *   storageApp.getDashboardData()             - weather data for the dashboard
 *   storageApp.getWData()                     - weather data (backs the parent's own getWData(), which fuel
 *                                                stream/piston children call via parent.getWData())
 *   storageApp.listAvailableDevices(raw, offset) - devices available to $weather
 *
 * Calls the storage child makes to this app (parent.xxx()):
 *   parent.isInstalled() / .getWikiUrl()      - install state and wiki link (storage app prefs page)
 *   parent.refreshDevices()                   - notify parent the device list may need refreshing
 *   parent.capabilities()                     - device capability list
 *
 * Browser dashboard (HTML/JS UI) -> this app interface.
 * The web dashboard is not a child app; it's a browser SPA that calls the HTTP paths declared in the
 * mappings{} block below, each backed by a private api_intf_dashboard_*()/api_intf_fuelstreams_*()
 * handler that checks verifySecurityToken()/PIN before doing anything.
 *
 *   /intf/dashboard/load                 api_intf_dashboard_load          - initial load: full base result (see
 *                                                                           api_get_base_result/clearBaseResult above)
 *   /intf/dashboard/devices              api_intf_dashboard_devices       - paged device list
 *   /intf/dashboard/refresh              api_intf_dashboard_refresh       - re-fetch live device attribute values
 *   /intf/dashboard/piston/new           api_intf_dashboard_piston_new    - suggest a name for a new piston
 *   /intf/dashboard/piston/create        api_intf_dashboard_piston_create - create a piston child app
 *   /intf/dashboard/piston/backup        api_intf_dashboard_piston_backup - export one or more pistons as JSON
 *   /intf/dashboard/piston/get           api_intf_dashboard_piston_get    - open a piston (full runtime data)
 *   /intf/dashboard/piston/getDb         api_intf_dashboard_piston_getDb  - fetch the shared capability/command/
 *                                                                           comparison "DB" the IDE needs
 *   /intf/dashboard/piston/set           api_intf_dashboard_piston_set    - save a small piston in one shot
 *   /intf/dashboard/piston/set.start
 *   /intf/dashboard/piston/set.chunk
 *   /intf/dashboard/piston/set.end       api_intf_dashboard_piston_set_{start,chunk,end} - save a large piston,
 *                                                                           uploaded in chunks and reassembled
 *   /intf/dashboard/piston/pause         api_intf_dashboard_piston_pause  - pause button
 *   /intf/dashboard/piston/resume        api_intf_dashboard_piston_resume - resume button
 *   /intf/dashboard/piston/set.bin       api_intf_dashboard_piston_set_bin - move a piston to a different bin
 *   /intf/dashboard/piston/tile          api_intf_dashboard_piston_tile   - dashboard tile tap/click
 *   /intf/dashboard/piston/set.category  api_intf_dashboard_piston_set_category
 *   /intf/dashboard/piston/set.modified  api_intf_dashboard_piston_set_modified
 *   /intf/dashboard/piston/logging       api_intf_dashboard_piston_logging - set a piston's logging level
 *   /intf/dashboard/piston/clear.logs    api_intf_dashboard_piston_clear_logs
 *   /intf/dashboard/piston/delete        api_intf_dashboard_piston_delete - delete a piston (see
 *                                                                           invalidatePistonCaches() above)
 *   /intf/dashboard/piston/evaluate      api_intf_dashboard_piston_evaluate - IDE "evaluate expression" feature
 *   /intf/dashboard/piston/test          api_intf_dashboard_piston_test   - "test" button (dry run)
 *   /intf/dashboard/piston/activity      api_intf_dashboard_piston_activity - poll a piston's live log/activity
 *   /intf/dashboard/variable/set         api_intf_variable_set            - create/update/delete a global or
 *                                                                           piston-local variable
 *   /intf/dashboard/settings/set         api_intf_settings_set            - save Settings page changes
 *   /intf/fuelstreams/list               api_intf_fuelstreams_list        - list fuel streams across canisters
 *   /intf/fuelstreams/get                api_intf_fuelstreams_get         - fetch one stream's data points
 *   /intf/dashboard/presence/create      api_intf_dashboard_presence_create - create a presence sensor device
 *
 * /gforward/:pistonIdOrName (api_forward) is also dashboard-driven - it's how an embedded graph tile/page is
 * rendered - but despite the path name it forwards to a fuel stream child, not a piston (see graphChild above).
 *
 * The remaining mapped paths are NOT called by the HTML dashboard - they're separate external integrations:
 *   /intf/location/entered, /exited, /updated - a companion presence/geofencing app updates a virtual
 *                                                presence device (api_intf_location_*)
 *   /ifttt/:eventName                         - IFTTT webhook trigger (api_ifttt)
 *   /email/:pistonId                          - inbound email trigger (api_email)
 *   /execute/:pistonIdOrName                  - generic external "run this piston" webhook (api_execute)
 *   /global/:varName                          - external read of a global variable's value (api_global)
 *   /tap, /tap/:tapId                         - mapped to "api_tap", but no api_tap() method is defined
 *                                                anywhere in this file; these two paths are currently broken
 */

//file:noinspection GroovySillyAssignment
//file:noinspection GrDeprecatedAPIUsage
//file:noinspection GroovyDoubleNegation
//file:noinspection GroovyUnusedAssignment
//file:noinspection unused
//file:noinspection SpellCheckingInspection
//file:noinspection GroovyFallthrough
//file:noinspection GrMethodMayBeStatic

@Field static final String sVER='v0.3.114.20220203'
@Field static final String sHVER='v0.3.114.20240115_HE'
@Field static final String sHVERSTR='v0.3.114.20240115_HE - July 5, 2026'

static String version(){ return sVER }
static String HEversion(){ return sHVER }

/*** webCoRE DEFINITION	***/

@Field static final String sWC='webCoRE'
@Field static final String sWCD='webcore.co'


private static String handle(){ return sWC }
private static String domain(){ return sWCD }

@Field static final String sPISTN=' Piston'
@Field static final String sWEAT=' Weather'
@Field static final String sSTOR=' Storage'
@Field static final String sFUELS=' Fuel Stream'
@Field static final String sPRES=' Presence Sensor'
private static String handlePistn(){ return sWC+sPISTN }
private static String handleWeat(){ return sWC+sWEAT }
private static String handleStor(){ return sWC+sSTOR }
private static String handleFuelS(){ return sWC+sFUELS }
private static String handlePres(){ return sWC+sPRES }

definition(
	name: handle(),
	namespace: "ady624",
	author: "Adrian Caramaliu",
	description: "${handle()} Automations & Graphs ${sHVERSTR}",
	category: "Convenience",
	singleInstance: false,
	documentationLink:'https://wiki.webcore.co',
	/* icons courtesy of @chauger - thank you */
	iconUrl:gimg('app-CoRE.png'),
	iconX2Url:gimg('app-CoRE@2x.png'),
	iconX3Url:gimg('app-CoRE@3x.png'),
	importUrl: "https://raw.githubusercontent.com/imnotbob/webCoRE/hubitat-patches/smartapps/ady624/webcore.src/webcore.groovy"
)

import java.text.SimpleDateFormat
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.transform.Field
import java.security.MessageDigest
import java.util.concurrent.Semaphore
import java.util.zip.GZIPOutputStream

preferences{
	//UI pages
	page((sNM): "pageMain")
	page((sNM): "pageDisclaimer")
	page((sNM): "pageEngineBlock")
	page((sNM): "pageInitializeDashboard")
	page((sNM): "pageFinishInstall")
	page((sNM): "pageSelectDevices")
	page((sNM): "pageFuelStreams")
	page((sNM): "pageSettings")
	page((sNM): "pageGraphs")
	page((sNM): "pageChangePassword")
	page((sNM): "pageClearTokens")
	page((sNM): "pageRebuildCache")
	page((sNM): "pageResetEndpoint")
	page((sNM): "pageCleanups")
	page((sNM): "pageLogCleanups")
	page((sNM): "pageUberCleanups")
	page((sNM): "pageDumpDashC")
	page((sNM): "pageDumpGlob")
	page((sNM): "pageRemove")
	page((sNM): "graphDuplicationPage")
	page((sNM):sPDPC)
	page((sNM):sPDPEXC)
	page((sNM):sPDPDEV)
}

@CompileStatic
private static Boolean eric(){ return false }
@CompileStatic
private static Boolean eric1(){ return false }
@CompileStatic
private static Boolean graphsOn(){ return true }

//#include ady624.webCoRElib1

/******************************************************************************/
/*** webCoRE CONSTANTS														***/
/******************************************************************************/

@Field static final String sNL=(String)null
@Field static final String sBLK=''
@Field static final String sSPC=' '
@Field static final String sCLN=':'
@Field static final String sDIV='/'
@Field static final String sBOOL='bool'
@Field static final String sAPPJAVA="application/javascript;charset=utf-8"
@Field static final String sCONTENTT='contentType'
@Field static final String sDATA='data'
@Field static final String sSTS='status'
@Field static final String sERR='error'
@Field static final String sINFO='info'
@Field static final String sWARN='warn'
@Field static final String sTRC='trace'
@Field static final String sDBG='debug'
@Field static final String sTIMER='timer'
@Field static final String sNONE='None'
@Field static final String sMINML='Minimal'
@Field static final String sMEDIUM='Medium'
@Field static final String sFULL='Full'
@Field static final String sSUCC="ST_SUCCESS"
@Field static final String sERRID="ERR_INVALID_ID"
@Field static final String sERRTOK="ERR_INVALID_TOKEN"
@Field static final String sERROR="ST_ERROR"
@Field static final String sERRCHUNK="ERR_INVALID_CHUNK"
@Field static final String sERRUNK="ERR_UNKNOWN"
@Field static final String sTXT='text'
@Field static final String sAPPJSON='application/json'
@Field static final String sTIT='title'
@Field static final String sDESC='description'
@Field static final String sREQ='required'
@Field static final String sNM='name'
@Field static final String sVAL='value'
@Field static final String sTYPE='type'
@Field static final String sNOW='now'
@Field static final String sVARIABLE='variable'
@Field static final String sRGB='rgb'
@Field static final String sUTF8='UTF-8'
@Field static final String sID='id'
@Field static final String sA='a'
@Field static final String sB='b'
@Field static final String sC='c'
@Field static final String sD='d'
@Field static final String sE='e'
@Field static final String sG='g'
@Field static final String sH='h'
@Field static final String sI='i'
@Field static final String sS='s'
@Field static final String sL='l'
@Field static final String sM='m'
@Field static final String sN='n'
@Field static final String sO='o'
@Field static final String sP='p'
@Field static final String sR='r'
@Field static final String sT='t'
@Field static final String sV='v'
@Field static final String sX='x'
@Field static final String sZ='z'

@Field static final String sLCLFS='localFuelStreams'

/** m.string */
@CompileStatic
private static String sMs(Map m,String v){ (String)m.get(v) }

/** m.string */
@CompileStatic
private static Map mMs(Map m,String s){ (Map)m.get(s) }

/******************************************************************************/
/*** CONFIGURATION PAGES													***/
/******************************************************************************/

/******************************************************************************/
/*** COMMON PAGES															***/
/******************************************************************************/
def pageMain(){
	//webCoRE Dashboard initialization
	Boolean success=initializeWebCoREEndpoint()
	if(!(Boolean)state.installed){
		return dynamicPage((sNM): "pageMain", (sTIT): sBLK, install: false, uninstall: false, nextPage: "pageInitializeDashboard"){
			section(){
				paragraph "Welcome to "+handle()
				paragraph "You will be guided through a few installation steps that should only take a minute."
			}
			if(success){
				if(!state.oAuthRequired){
					section('Note'){
						paragraph "If you have previously installed webCoRE and are trying to open it, please go back to Apps in the HE console access webCoRE.\r\n\r\nIf you are trying to install another instance of webCoRE then please continue with the steps.", (sREQ): true
					}
				}
				if(mTZ()){
					section(){
						paragraph "It looks like you are ready to go, please tap Next"
					}
				}else{
					section(){
						paragraph "Your location is not correctly setup."
					}
					pageSectionTimeZoneInstructions()
				}
			}else{
				section(){
					paragraph "We'll start by configuring. You need to setup OAuth in the HE console for the webCoRE App."
				}
				pageSectionInstructions()
				section (){
					paragraph "Once you have finished the steps above, tap Next", (sREQ): true
				}
			}
		}
	}
	//webCoRE main page
	dynamicPage((sNM): "pageMain", (sTIT): sBLK, install: true, uninstall: false){
		if((Boolean)gtAS('disabled')==true){
			section(){
				paragraph span("(Disabled) Kill switch is active - all pistons are disabled", sCLRORG), (sREQ): true
			}
		}
		if(!gtSetB('agreement')){
			pageSectionDisclaimer()
		}else{
			section(){
				href "pageEngineBlock", (sTIT): imgTitle("app-CoRE.png", inputTitleStr("Engine block - Cast iron")), (sDESC): sVER+" HE: "+ sHVERSTR, (sREQ): false, state: "complete"
			}

		}

		section(){
			String mPng="dashboard.png"
			if(!(String)state.endpoint){
				href "pageInitializeDashboard", (sTIT): imgTitle(mPng, inputTitleStr("Dashboard")), (sDESC): "Tap to initialize", (sREQ): false, state: "complete"
			}else{
				//trace "*** DO NOT SHARE THIS LINK WITH ANYONE *** Dashboard URL: ${getDashboardInitUrl()}"
				href sBLK, (sTIT): imgTitle(mPng, inputTitleStr("Open Dashboard")), style: "external", url: getDashboardInitUrl(), (sDESC): "Tap to open", (sREQ): false
				href sBLK, (sTIT): imgTitle("browser-reg.png", inputTitleStr("Register a browser")), style: "external", url: getDashboardInitUrl(true), (sDESC): "Tap to open", (sREQ): false
			}
		}

		section(){
			href "pageSettings", (sTIT): imgTitle("settings.png", inputTitleStr("Settings")), (sREQ): false, state: "complete"
		}

		if(graphsOn()){
			section(){
				href "pageGraphs", (sTIT): imgTitle("settings.png", inputTitleStr("Graphs")), (sREQ): false, state: "complete"
			}
			clearDuplicationItems()
		}
	}
}

private pageSectionDisclaimer(){
	section('Disclaimer'){
		paragraph "Please read the following information carefully", (sREQ): true
		paragraph "webCoRE is a web-enabled product, which means data travels across the internet. webCoRE is using TLS for encryption of data and NEVER provides real object IDs to any system. IDs are hashed into a string of letters and numbers that cannot be 'decoded' back to their original value. These hashed IDs are stored by your browser and can be cleaned up by using the Logout action in the dashboard."
		paragraph "Access to a webCoRE App is done through the browser using a security password provided during the installation of webCoRE. The browser never stores this password and it is only used during the initial registration and authentication of your browser. A security token is generated for each browser and is used for any subsequent communication. This token expires at a preset life length, or when the password is changed, or when the tokens are manually revoked from the webCoRE App's Settings menu."
	}
	section('Server-side features'){
		paragraph "Some features require that a webcore.co server processes your data. Such features include emails (sending emails out, or triggering pistons with emails), inter-location communication for superglobal variables, fuel streams, backup bins."
		paragraph "At no time does the server receive any real IDs of HE objects, the instance security password, nor the instance security token that your browser uses to communicate with the App. The server is therefore unable to access any information that only an authenticated browser can."
	}
	section('Information collected by the server'){
		paragraph "The webcore.co server(s) collect ANONYMIZED hashes of 1) your unique account identifier, 2) your locations, and 3) installed webCoRE instances. It also collects an encrypted version of your app instances' endpoints that allow the server to trigger pistons on emails (if you use that feature), proxy IFTTT requests to your pistons, or provide inter-location communication between your webCoRE instances, as well as data points provided by you when using the Fuel Stream feature. It also allows for automatic browser registration when you use another browser, by providing that browser basic information about your existing instances. You will still need to enter the password to access each of those instances, the server does not have the password, nor the security tokens."
	}
	section('Information NOT collected by the server'){
		paragraph "The webcore.co server(s) do NOT intentionally collect any real object IDs from HE, any names, phone numbers, email addresses, physical location information, addresses, or any other personally identifiable information."
	}
	section('Fuel Streams'){
		paragraph "The information you provide while using the non-local Fuel Stream feature is not encrypted and is not filtered in any way. Please avoid providing personally identifiable information in either the canister name, the fuel stream name, or the data point."
	}
	section('Local webCoRE servers'){
		paragraph "Advanced users may enable a local webcore www server. Less data sharing with external webCoRE servers is done if this is configured/enabled. Some features may not be available if you choose to do this."
	}
	section('Agreement'){
		paragraph "Certain advanced features may not work if you do not agree to the webcore.co servers collecting the anonymized information described above."
		input "agreement", sBOOL, (sTIT): "Allow webcore.co to collect basic, anonymized, non-personally identifiable information", defaultValue: true
	}
}

private pageDisclaimer(){
	dynamicPage((sNM): "pageDisclaimer"){
		pageSectionDisclaimer()
	}
}

private pageSectionInstructions(){
	state.oAuthRequired=true
	section (){
		paragraph "Please follow these steps:", (sREQ): true
		paragraph "1. Go to your HE console and log in", (sREQ): true
		paragraph "2. Click on 'Apps Code' and locate the 'webCoRE' App in the list", (sREQ): true
		paragraph "3. Click the App name", (sREQ): true
		paragraph "4. Click on 'OAuth'", (sREQ): true
		paragraph "5. Click the 'Enable OAuth in App' button", (sREQ): true
		paragraph "6. Click the 'Update' button", (sREQ): true
	}
}

private pageSectionTimeZoneInstructions(){
	section (){
		paragraph "Please follow these steps to setup your location timezone:", (sREQ): true
		paragraph "1. Using the HE console, abort this installation and go to 'Settings' section", (sREQ): true
		paragraph "2. Click on 'Hub Details'", (sREQ): true
		paragraph "3. Edit your postal code, and time zone, then enter Latitide and Longitude information", (sREQ): true
		paragraph "4. Tap the Save settings button", (sREQ): true
		paragraph "5. Try installing webCoRE again", (sREQ): true
	}
}

private pageInitializeDashboard(){
	//webCoRE Dashboard initialization
	Boolean success=initializeWebCoREEndpoint()
	Boolean hasTZ=mTZ()!=null
	dynamicPage((sNM): "pageInitializeDashboard", nextPage: success && hasTZ ? "pageSelectDevices" : sNL){
		if(!(Boolean)state.installed){
			if(success){
				if(hasTZ){
					section(){
						paragraph "Great, ready to go."
					}
					section(){
						paragraph "Now, please choose a name for this webCoRE instance"
						label( (sNM): "name", (sTIT): "Name", state: (name ? "complete" : sNL), defaultValue: app.name, (sREQ): false)
					}

					pageSectionDisclaimer()

					section(){
						paragraph "${(Boolean)state.installed ? "Tap Done to continue." : "Next, choose a security password for your webCoRE dashboard. You will need to enter this password when accessing your dashboard for the first time, and possibly from time to time, depending on your settings."}", (sREQ): false
					}
				}else{
					section(){
						paragraph "Your location is not correctly setup."
					}
					pageSectionTimeZoneInstructions()
					section(){
						paragraph "Once you have finished the steps above, go back and try again", (sREQ): true
					}
					return
				}
			}else{
				section(){
					paragraph "Sorry, it looks like OAuth is not properly enabled."
				}
				pageSectionInstructions()
				section(){
					paragraph "Once you have finished the steps above, go back and try again", (sREQ): true
				}
				return
			}
		}
		pageSectionPIN()
		pageSectionAcctId(true)
	}
}

private pageEngineBlock(){
	dynamicPage((sNM): "pageEngineBlock", (sTIT): sBLK){
		section(){
			paragraph "Under construction..."
		}

		if(getLogging()[sDBG] || eric()){
			String c='Tap to display'
			String b='complete'
			section('Debug'){
				href sPDPC,(sTIT):'Dump base result Cache', (sDESC): c, state: b
				href sPDPDEV,(sTIT):'Dump devices result', (sDESC): c, state: b
				href "pageDumpDashC",(sTIT):'Dump dashload Cache', (sDESC): c, state: b
				href "pageRebuildCache", (sTIT): "Clean up and rebuild IDE data cache", (sDESC): "Tap to clean up and rebuild your data cache", state: b
			}
		}
	}
}

private pageSelectDevices(){
	dynamicPage((sNM): "pageSelectDevices", nextPage: "pageFinishInstall"){
		Boolean inst=(Boolean)state.installed
		section(){
			paragraph (inst ? "Select the devices you want webCoRE to have access to." : "Great, now let's select some devices.")
			paragraph "A DEVICE ONLY NEEDS TO BE SELECTED ONCE, THE CATEGORIES BELOW ARE TO MAKE THEM EASIER TO FIND."
			paragraph "It is a good idea to only select the devices you plan on using with webCoRE pistons. Pistons will only have access to the devices you selected."
		}
		if(!inst){
			section ('Note'){
				paragraph "Remember, you can always come back to webCoRE and add or remove devices as needed.", (sREQ): true
			}
			section(){
				paragraph "So go ahead, select a few devices, then tap Next"
			}
		}

		section (sectionTitleStr('Select devices by type')){
			paragraph "Most devices should fall into one of these categories"
			input "dev:actuator", "capability.actuator", multiple: true, (sTIT): "Actuators", (sREQ): false
			input "dev:sensor", "capability.sensor", multiple: true, (sTIT): "Sensors", (sREQ): false
			input "dev:all", "capability.*", multiple: true, (sTIT): "Devices", (sREQ): false
		}

		section (sectionTitleStr('Select devices by capability')){
			paragraph "If you cannot find a device by type, you may try looking for it by category below"
			def d; d=null
			for (capability in capabilities().findAll{ (!((String)it.value.d in [null, 'actuators', 'sensors'])) }.sort{ (String)it.value.d }){
				if(capability.value.d!=d) input "dev:${capability.key}", "capability.${capability.key}", multiple: true, (sTIT): "Which ${capability.value.d}", (sREQ): false
				d=capability.value.d
			}
		}
	}
}

private pageFinishInstall(){
	Boolean inst=(Boolean)state.installed
	if(!inst) initTokens()
	refreshDevices()
	dynamicPage((sNM): "pageFinishInstall", /* nextPage: (inst ? "pageSettings" : sBLK),*/ install: true){
		if(!inst){
			section(){
				paragraph "Excellent! You are now ready to use webCoRE"
			}
			section("Note"){
				paragraph "After you tap Done, go to 'Apps', and open the '"+appName()+"' App to access the dashboard.", (sREQ): true
				paragraph "You can also access the dashboard on any another device by entering ${domain()} in the address bar of your browser.", (sREQ): true
			}
			section(){
				paragraph "Now tap Done and enjoy webCoRE!"
			}
		}else{
			section(){
				paragraph "Devices updated"
			}
		}
	}
}

def pageSettings(){
	//clear devices cache
	dynamicPage((sNM): "pageSettings", install: false, uninstall: false){
		section(){
			paragraph pageTitleStr('Settings')
		}
		String b='complete'
		section(){
			label ((sNM): "name", (sTIT): "Name for this main $sWC application", state: (name ? b : sNL), defaultValue: app.name, (sREQ): false)
		}

/*
		def storageApp=getStorageApp()
		if(storageApp!=null){
			section("Storage Application"){
				app([(sTIT): isHubitat() ? 'Do not click - App Launchs automatically' : 'Available Devices', multiple: false, install: true, uninstall: false], 'storage', 'ady624', handleStor())
			}
		}else{*/
			section(){
				href "pageSelectDevices", (sTIT): "Available devices", (sDESC): "Tap to select which devices are available to pistons", state: b
			}
		//}

		section(){
			input "pushDevice", "capability.notification", (sTIT): "Notification device for pushMessage (HE mobile App or pushOver)", multiple: true, (sREQ): false, submitOnChange: true
		}

		section(sectionTitleStr('Enable \$weather via external provider')){
			String apiXU='apiXU'
			String DarkSky='DarkSky'
			String OpnW='OpenWeatherMap'
			input "weatherType", sENUM, (sTIT): "Weather Type to enable?", defaultValue: sBLK, submitOnChange: true, (sREQ): false, options:[apiXU, DarkSky, OpnW, sBLK]
			String defaultLoc,defaultLoc1,zipDesc,zipDesc1
			defaultLoc=sNL
			defaultLoc1=sNL
			String mreq= gtSetStr('weatherType') ?: sNL
			zipDesc=sNL
			zipDesc1=sNL
			if(mreq){
				input "apixuKey", sTXT, (sTIT): mreq+" key?", (sREQ): true
				switch(mreq){
				case apiXU:
					defaultLoc=gtLzip()
					zipDesc="Override zip code (${defaultLoc}), or set city name or latitude,longitude?".toString()
					break
				case DarkSky:
					defaultLoc=gtLlat()+','+gtLlong()
					zipDesc="Override latitude,longitude (Default: ${defaultLoc})?".toString()
					break
				case OpnW:
					defaultLoc=gtLlat()
					defaultLoc1=gtLlong()
					zipDesc="Override latitude (Default: ${defaultLoc})?".toString()
					zipDesc1="Override longitude (Default: ${defaultLoc1})?".toString()
					break
				default:
					break
				}
				input "zipCode", sTXT, (sTIT): zipDesc, defaultValue: defaultLoc, (sREQ): false
				if(mreq==OpnW){
					input "zipCode1", sTXT, (sTIT): zipDesc1, defaultValue: defaultLoc1, (sREQ): false
					input "wunits", sENUM, (sTIT): "Weather units", defaultValue: 'imperial', (sREQ): false, options:['standard','metric','imperial']
					paragraph "OpenWeatherMap Integration uses onecall api.  Ensure your key is compatible"
					input "apiVer", sBOOL, (sTIT): "Api key version (2.5 - Off, 3.0 - On)?", defaultValue: false, submitOnChange: true
				}
			}
		}

		section(sectionTitleStr("Fuel Streams")){
			Boolean lfs = gtSetB(sLCLFS)
			Boolean deft= lfs!=(Boolean)null ? lfs : true
			input sLCLFS, sBOOL, (sTIT): "Use local fuel streams?", defaultValue: deft, submitOnChange: true
			if(deft){
				href "pageFuelStreams", (sTIT): "Fuel Streams", (sDESC): "Tap to manage fuel streams", state: b
			}
		}

/*		section("Integrations"){
			href "pageIntegrations", (sTIT): "Integrations with other services", (sDESC): "Tap to configure your integrations"
		}*/

		section(){
			href "pageChangePassword", (sTIT): "Security", (sDESC): "Tap to change your dashboard security settings", state: b
		}

		section(){
			input "logging", sENUM, (sTIT): "Logging level for main $sWC application", options: [sNONE, sMINML, sMEDIUM, sFULL], defaultValue: sNONE, (sREQ): false
		}

		section(){
			paragraph "webCoRE can run a periodic recovery procedure. This deals with recovery of missed piston timers during hub downtine"
			input "recovery", sENUM, (sTIT): "Run recovery", options: ["Never", "Every 5 minutes", "Every 10 minutes", "Every 15 minutes", "Every 30 minutes", "Every 1 hour", "Every 3 hours"], (sDESC): "Allows recovery procedures to run every so often", defaultValue: "Every 30 minutes", (sREQ): true
		}

		section((sTIT): "Maintenance"){
			paragraph "Memory usage is at ${mem()}", (sREQ): false
			input "disabled", sBOOL, (sTIT): "Disable all pistons", (sDESC): "Disable all pistons belonging to this instance", defaultValue: false, (sREQ): false
			input "logPistonExecutions", sBOOL, (sTIT): "Log piston executions as Location events?", (sDESC): "Tap to change logging pistons as hub location events", defaultValue: false, (sREQ): false
			input "enableDashNotifications", sBOOL, (sTIT): "Enable Dashboard Notifications for device state changes?", (sDESC): "Tap to change enable dashboard notifications of device state changes (more overhead)", defaultValue: false, (sREQ): false
		}

		if(getLogging()[sDBG] || eric()){
			String a='Tap to clear'
			String c='Tap to display'
			section("Display operational data"){
				href "pageDumpGlob",(sTIT):'Dump global variables in use', (sDESC): c, state: b
				href sPDPEXC,(sTIT):'Dump piston Execution Count', (sDESC): c, state: b
			}
			section("Piston Cleanups"){
				href "pageLogCleanups", (sTIT): "Clear all piston logs, trace, stats, optimization caches, reset all piston logs, stats settings to default", (sDESC): a, state: b
				href "pageCleanups", (sTIT): "Clear all piston optimization caches", (sDESC): a, state: b
				href "pageUberCleanups", (sTIT): "Danger: Clear all piston variables, piston caches, and logs", (sDESC): a, state: b
			}
		}

		section(sectionTitleStr("Advanced - Custom Endpoints")){
			paragraph "Custom Endpoints allows use of a local webserver for webCoRE IDE pages and local hub API endpoint address. webCoRE servers are still used for instance registration, non-local backup / restore / import, send email, NFL, store media, and optionally fuel streams"
			input "customEndpoints", sBOOL, submitOnChange: true, (sTIT): "Use custom endpoints?", default: false, (sREQ): true
			if(gtSetB('customEndpoints')){
				Boolean req; req=false
				Boolean lhub=gtSetB('localHubUrl')
				if(lhub) req=true
				input "customWebcoreInstanceUrl", sSTR, (sTIT): "Custom webCoRE webserver (local webserver url different from dashboard.webcore.co)", default: null, (sREQ): req
				if(lhub && !gtSetStr('customWebcoreInstanceUrl')) paragraph "If you use a local hub IDE url you MUST use a custom webCoRE server url, as dashboard.webcore.co site is restricted to Hubitat cloud API access only"
				input "localHubUrl", sBOOL, (sTIT): "Use local hub URL for IDE access?", submitOnChange: true, default: false, (sREQ): false
			}else{
				app.removeSetting('localHubUrl')
				app.removeSetting('customWebcoreInstanceUrl')
			}
			state.endpointCloud=sNL
			state.endpoint=sNL
			state.endpointLocal=sNL
			if((String)state.accessToken) updateEndpoint()
		}

		section((sTIT):"Privacy"){
			href "pageDisclaimer", (sTIT): imgTitle("settings.png", inputTitleStr("Data Collection Notice")), (sREQ): false, state: "complete"
		}

		section("Uninstall"){
			href "pageRemove", (sTIT): "Uninstall webCoRE", (sDESC): "Tap to uninstall ${handle()}"
		}
	}
}

private pageGraphs(){
	dynamicPage((sNM): "pageGraphs", uninstall: false, install: false){
		section(){
			List graphApps = getGraphApps()
			app([(sTIT): 'List of streams and graphs / create a new graph', multiple: true, install: true, uninstall: false], 'fuelStreams', 'ady624', handleFuelS())
			if(graphApps?.size()){
				input "graphDuplicateSelect", sENUM, title: "Duplicate Existing Graph", description: 'Tap to select...', options: graphApps.collectEntries { [(it?.id):it?.getLabel()] }, required: false, multiple: false, submitOnChange: true
				if(settings.graphDuplicateSelect){
					href "graphDuplicationPage", title: "Create Duplicate Graph?", description: 'Tap to proceed...'
				}
			}
		}
	}
}

def graphDuplicationPage(){
	return dynamicPage(name: "graphDuplicationPage", nextPage: "pageGraphs", uninstall: false, install: false){
		section(){
			if((Boolean)state.graphDuplicated){
				paragraph "Graph already duplicated..." + "Return to graph page and select it"
			}else{
				def grf = getGraphApps()?.find { it?.id?.toString() == settings.graphDuplicateSelect?.toString() }
				if(grf){
					Map grfData = grf.getSettingsAndStateMap() ?: [:]
					String grfId = (String)grf.getId().toString()
					if(grfData.settings && grfData.state){
						String myId=app.getId()
						if(!childDupMapFLD[myId]) childDupMapFLD[myId] = [:]
						if(!childDupMapFLD[myId].graphs) childDupMapFLD[myId].graphs = [:]
						childDupMapFLD[myId].graphs[grfId] = grfData
						doLog(sDBG, "Dup Data: ${childDupMapFLD[myId].graphs[grfId]}")
					}
					Map app_name= (Map)grfData.settings['app_name']
					String nm="${grfData.label}"+' (Dup)' //app_name.value+' (Dup)'
					app_name.value= nm
					grfData.settings['app_name']= app_name
					grfData.settings["duplicateFlag"] = [(sTYPE): sBOOL, (sVAL): true]
					// grfData?.settings["actionPause"] = [(sTYPE): sBOOL, (sVAL): true]
					grfData.settings["duplicateSrcId"] = [(sTYPE): sTXT, (sVAL): grfId]
					def a=addChildApp("ady624", handleFuelS(), nm, [settings: grfData.settings])
					paragraph "Graph Duplicated..." + "<br>Return to Graph Page and look for the App with '(Dup)' in the name..."
					state.graphDuplicated = true
				}else{ paragraph "Graph not Found" }
			}
		}
	}
}

@Field volatile static Map<String, Map> childDupMapFLD = [:]

public Map getChildDupeData(String type, String childId){
	String myId=sAppId()
	return (childDupMapFLD[myId] && childDupMapFLD[myId][type] && childDupMapFLD[myId][type][childId]) ? (Map)childDupMapFLD[myId][type][childId] : [:]
}

public void clearDuplicationItems(){
	state.graphDuplicated = false
	if(settings.graphDuplicateSelect) app.removeSetting("graphDuplicateSelect")
	state.remove('graphDuplicated')
}

public void childAppDuplicationFinished(String type, String childId){
	doLog(sTRC,"childAppDuplicationFinished($type, $childId)")
//	Map data = [:]
	String myId=sAppId()
	if(childDupMapFLD[myId] && childDupMapFLD[myId][type] && childDupMapFLD[myId][type][childId]){
		childDupMapFLD[myId][type].remove(childId)
	}
	clearDuplicationItems()
}


List getGraphApps(){
	return ((List)getAllChildApps())?.findAll {
		String t= it?.gtSetting('graphType')
		t && it?.name == handleFuelS() && !(t in ['longtermstorage'])
	}
}

private pageFuelStreams(){
	dynamicPage((sNM): "pageFuelStreams", uninstall: false, install: false){
		section(){
			app([(sTIT): isHubitat() ? 'Do not click - List of streams below that launches automatically' : 'Fuel Streams', multiple: true, install: true, uninstall: false], 'fuelStreams', 'ady624', handleFuelS())
		}
	}
}

private pageSectionAcctId(Boolean ins=false){
	section('<b>Account/Location Identifiers</b>'){

		String acctHash = getHubAccountHash()
		Boolean setA; setA = gtSetB('setACCT')
		String acct; acct = setA ? gtSetStr('acctID') : sNL

		if(!acctHash){
			paragraph "Did not find hub account hash.  It is recommended to register all your hubs and use same account hash in webCoRE."
		}

		if(acctHash && ins && setA==(Boolean)null){
			app.updateSetting('properSID', [(sTYPE): sBOOL, (sVAL): true])
			app.updateSetting('setACCT', [(sTYPE): sBOOL, (sVAL): true])
			app.updateSetting('acctID', [(sTYPE): sTXT, (sVAL): acctHash])
			setA=true
			acct=acctHash
		}

		String msg
		msg= "If you have (or may have) multiple webCoRE instances or multiple hubs running webCoRE), for proper IDE operations all of the hubs should be linked together with a common account identifier."
		msg+= "<br>"
		Boolean noteshown; noteshown=false
		msg+= "\n - hub uuid: " + gtHubUID()
		if(setA && acct){
			msg += "\n - Using Custom account identifier: $setA"
			if(acct!=acctHash){
				msg+="\n - webCoRE account id: ${acct}"
				if(acctHash){
					msg+="\n\n"+span("Found existing account identifier $acct which does not match hub account hash $acctHash",sCLRORG)
					paragraph msg
					msg=sNL
					if(!ins){
						noteshown=true
						paragraph span("NOTE changing these settings will require all pistons to be backed up again.  It may affect pistons calling each other and accessing apps like Homebridge V2 or Echo Speaks)",sCLRORG)
					}
					input "acctUpdate", sBOOL, (sTIT): "Update to hub account hash?", (sDESC): "Tap to change", defaultValue: false, submitOnChange: true, (sREQ): false
					if(gtSetB('acctUpdate')){
						app.updateSetting('properSID', [(sTYPE): sBOOL, (sVAL): true])
						app.updateSetting('setACCT', [(sTYPE): sBOOL, (sVAL): true])
						app.updateSetting('acctID', [(sTYPE): sTXT, (sVAL): acctHash])
						setA=true
						acct=acctHash
					}
				}
			}else{
				app.removeSetting("acctUpdate")
				msg+= "\nUsing hub account hash: $acctHash"
			}
			if(msg){
				paragraph msg
				msg=sNL
			}
		}else{
			app.removeSetting("acctUpdate")
		}
		if(msg){
			paragraph msg
			msg=sNL
		}

		if(!acctHash || !ins){
			paragraph "<br>"
			paragraph "Advanced - Custom account identifier"
			if(!noteshown && !ins){
				noteshown=true
				paragraph span("NOTE changing these settings will require all pistons to be backed up again.  It may affect pistons calling each other and accessing apps like Homebridge V2 or Echo Speaks)",sCLRORG)
			}
			input "setACCT", sBOOL, (sTIT): "Set custom account identifier?", (sDESC): "Tap to change", defaultValue: ins, submitOnChange: true, (sREQ): false
			if(setA){
				//paragraph "An email address is usually a good choice (is not used/shared)"
				input 'acctID', sTXT, (sTIT): 'Account identifier (hub account hash is best)', (sREQ): true
			}else{
				app.removeSetting('acctID')
				app.removeSetting('locID')
				input 'properSID', sBOOL, (sTIT): "Use New SID for location?", (sDESC): "Tap to change", defaultValue: true, (sREQ): false
			}
		}

		if(setA && acct){
			app.updateSetting('properSID', [(sTYPE): sBOOL, (sVAL): true])
			paragraph "<br>"
			paragraph "All hubs in same location may have a common location identifier. This could be Boston, Vacation, or Home1, etc..."
			if(!noteshown && !ins){
				noteshown=true
				paragraph "<br>"
				paragraph span("NOTE changing these settings will require all pistons to be backed up again.  It may affect pistons calling each other and accessing apps like Homebridge V2 or Echo Speaks)",sCLRORG)
			}
			input 'locID', sTXT, (sTIT): 'Location identifier - no imbedded spaces', (sREQ): true
		}

		String wName=sAppId()
		acctlocFLD[wName]=null
		locFLD[wName]=sNL
		clearHashMap(wName)
	}
}

private pageChangePassword(){
	dynamicPage((sNM): "pageChangePassword", uninstall: false, install: false){
		section(){
			paragraph pageTitleStr('Security')
		}
		pageSectionPIN()
		pageSectionAcctId(false)
		section(){
			href "pageClearTokens", (sTIT): "Clear all Browser Security Tokens", (sDESC): "Tap to clear all security tokens in use by browsers", state: "complete"
		}
		if(gtSetStr('PIN')){
			section(){
				paragraph "webCoRE uses an access token to allow communication with webCoRE via REST calls. You may choose to reset this token.", (sREQ): false
				paragraph span( "NOTE resetting the access token will invalidate any remote access to pistons (the URLs they are using), and this will have to be re-enabled / setup once the new access token has been created.", sCLRORG)
				paragraph "If your dashboard fails to load and no log messages appear in Hubitat console 'Logs' when you refresh the dashboard, resetting the access token may restore access to webCoRE.", (sREQ): false
				href "pageResetEndpoint", (sTIT): "Reset access token", (sDESC): "WARNING: URLs for triggering pistons or accessing piston URLs will need to be updated", state: "complete"
			}
		}
	}
}

private pageSectionPIN(){
	section(){
		paragraph "Choose a security password for your dashboard. You will need to enter this password when accessing your dashboard for the first time and possibly from time to time.", (sREQ): false
		input "PIN", "password", (sTIT): "Security password for your dashboard", (sREQ): true
		input "expiry", sENUM, options: ["Every hour", "Every day", "Every week", "Every month (recommended)", "Every three months", "Never (not recommended)"], defaultValue: "Every month (recommended)", (sTIT): "Choose how often the dashboard login expires", (sREQ): true
	}
}

private pageClearTokens(){
	initTokens()
	dynamicPage((sNM): "pageClearTokens", install: false, uninstall: false ){
		section(){
			paragraph "Browser Tokens have been Cleared. You will have to re-login to the webCoRE dashboards."
		}
	}
}

def pageRebuildCache(){
	cleanUp()
	dynamicPage((sNM): "pageRebuildCache", install: false, uninstall: false){
		section(){
			paragraph "Success! Data cache has been cleaned up and rebuilt."
		}
	}
}

def pageResetEndpoint(){
	revokeAccessToken()
	String wName=sAppId()
	lastRecoveredFLD[wName]=0L
	lastRegFLD[wName]=0L
	lastRegTryFLD[wName]=0L
	Boolean success=initializeWebCoREEndpoint()
	clearParentPistonCache("reset endpoint")
	updated()
	dynamicPage((sNM): "pageResetEndpoint", install: false, uninstall: false){
		section(){
			paragraph "Success: $success Please sign out and back in to the webCoRE dashboard."
			paragraph "If you use external URLs to trigger pistons, these URLs must be updated. See the piston detail page for an updated external URL; all pistons will use the same new token."
		}
	}
}

def pageCleanups(){
	String t= 'cleanup old super'
	Boolean didw= getTheLock(t)

	Map<String,Map> vars; vars=(Map<String,Map>)gtAS(sVARS)
	vars=vars ?: [:]
	Boolean fnd; fnd=false
	if(vars){ // clear out obsolete superglobals
		List<String> b; b=vars.collect{ (String)it.key }
		for (String c in b){
			if(c.startsWith(sAT2)){
				def a=vars.remove(c) // @@
				fnd=true
			}
		}
		if(fnd)assignAS(sVARS,vars)
		b=null
	}

	releaseTheLock(t)
	if(fnd){
		clearBaseResult(t)
	}

	clearChldCaches(true)
	return dynamicPage((sNM):'pageCleanups', install: false, uninstall:false){
		section('Clear'){
			paragraph 'Optimization caches have been cleared.'
		}
	}
}

def pageLogCleanups(){
	clearChldCaches(false,true)
	return dynamicPage((sNM):'pageLogCleanups', install: false, uninstall:false){
		section('Clear'){
			paragraph 'Logs been cleared.'
		}
	}
}

def pageUberCleanups(){
	clearChldCaches(false,false, true)
	return dynamicPage((sNM):'pageUberCleanups', install: false, uninstall:false){
		section('Uber Clear'){
			paragraph 'Everything has been cleared.'
		}
	}
}

def pageDumpDashC(){
	Map<String,Object> t0 =api_get_base_result()
	String message=getMapDescStr(t0)
	return dynamicPage((sNM):"pageDumpDashC",(sTIT):sBLK,uninstall:false){
		section('Dashboard Data Cache dump'){
			paragraph message
		}
	}
}

@Field static final String sPDPC='pageDumpPCache'
def pageDumpPCache(){
	String wName=sAppId()
	Map a=base_resultFLD[wName]
	String message=getMapDescStr(a)
	return dynamicPage((sNM):sPDPC,(sTIT):sBLK,uninstall:false){
		section('base result dump'){
			paragraph message
		}
	}
}

def pageDumpGlob(){
	String wName=sAppId()
	String n=handlePistn()
	List t0= childAppsRawFLD[wName] ?: wgetChildApps().findAll{ (String)it.name==n }
	def t1=t0[iZ]
	Map<String,List> t2= t1!=null ? (Map<String,List>)t1.gtGlobalVarsInUse() : [:]
	Map<String,Object> newMap
	newMap=[:]
	Map<String,Map> glbs=listAvailableVariables1()
	String nf= ' (VARIABLE NOT FOUND)'
	t2.each {
		String k
		k= it.key
		List<String> l= (List<String>)it.value
		List<String> newLst
		newLst=[]
		l.each{ String pid ->
			def pist= t0.find { tid -> tid.id.toString() == pid }
			if(pist){
				String nm= normalizeLabel(pist)
				newLst << nm
			}
		}
		if(!glbs.containsKey(k)) k+= nf
		else k+= " (${sMs(mMs(glbs,k),sT)})"
		newMap[k]= []+newLst
	}
	newMap = newMap.sort { (String)it.key }

	String message=getMapDescStr(newMap)
	return dynamicPage((sNM):"pageDumpGlob",(sTIT):sBLK,uninstall:false){
		section('Global variable in use dump'){
			paragraph message
		}
	}
}

def pageRemove(){
	dynamicPage((sNM): "pageRemove", (sTIT): sBLK, install: false, uninstall: true){
		section('CAUTION'){
			paragraph "You are about to completely remove webCoRE and all of its pistons.", (sREQ): true
			paragraph "This action is irreversible.", (sREQ): true
			paragraph "It is suggested save a hub backup prior to this delete, or all piston backup to a file from the webCoRE IDE.", (sREQ): true
			paragraph "If you are sure you want to do this, please tap on the Remove button below.", (sREQ): true
		}
	}
}

void revokeAccessToken(){
	state.accessToken=null
	state.endpointCloud=sNL
	state.endpoint=sNL
	state.endpointLocal=sNL
	resetFuelStreamList()
	initTokens()
}


/******************************************************************************/
/***																		***/
/*** INITIALIZATION ROUTINES												***/
/***																		***/
/******************************************************************************/

void installed(){
	state.installed=true
	initialize()
}

void updated(){
	info "Updated ran webCoRE "+sVER+" HE: "+sHVER
	unsubscribe()
	unschedule()
	initialize()

	Boolean chg,frcResub,verchg,ksDisable
	chg=false
	frcResub=false
	verchg=false
	ksDisable=false

	String dis='disabled'
	Boolean wasDis=(Boolean)gtAS(dis)
	Boolean nowDis=gtSetB(dis)==true
	if(wasDis!=nowDis){
		assignAS(dis,nowDis)
		chg=true
		if(wasDis && !nowDis) frcResub=true   // re-enable: force chld.updated() → resumeP()
		if(!wasDis && nowDis) ksDisable=true  // kill switch on: unsubscribe all active pistons
	}
	Boolean s=gtSetB('logPistonExecutions')
	if((Boolean)gtAS('lPE')!=s){
		assignAS('lPE',s==true)
		chg=true
	}
	if(gtSt('doResub')){
		chg=true
		frcResub=true
		verchg=true
	}
	String cV='cV'
	String hV='hV'
	String scV=(String)gtSt(cV)
	String shV=(String)gtSt(hV)
	if(scV!=sVER || shV!=sHVER){
		debug "Detected version change ${scV} ${sVER} ${shV} ${sHVER}"
		assignSt(cV,sVER)
		assignSt(hV,sHVER)
		frcResub=true
		chg=true
		verchg=true
	}
	Boolean ls= gtSetB(sLCLFS)
	if((Boolean)gtAS('lFS')!=ls){
		assignAS('lFS',ls==true)
		chg=true
	}
	if(chg){
		if(verchg){
			if(ksDisable) assignSt('pendKsDis',true)
			if(frcResub)  assignSt('pendFrcResub',true)
			runIn(150, afterRun) // try to deal with people updating this file first vs. last with HPM
			doLog(sINFO,"webCoRE scheduled install/upgrade completion in 150 seconds")
			return
		}else{
			clearParentPistonCache("parent updated", frcResub, chg, ksDisable)
			cleanUp()
			resetFuelStreamList()
		}
	}else cleanUp()
	clearBaseResult('updated')
}

void afterRun(){
	Boolean ksD=(Boolean)gtSt('pendKsDis')==true
	assignSt('pendKsDis',false)
	assignSt('pendFrcResub',false)
	assignSt('doResub',false)
	clearParentPistonCache("parent updated", true, true, ksD)
	cleanUp()
	resetFuelStreamList()
	clearBaseResult('updated after')
	doLog(sINFO,"webCoRE upgrade completed")
}

// parent states that children share in cache
Map getChildPstate(){ gtPdata() }
Map gtPdata(){
	LinkedHashMap msettings=(LinkedHashMap)gtAS('settings')
	if((String)gtSt('accessToken')) updateEndpoint()
	List a1=[ hashId(((Long)location.id).toString()+sML), hashId(gtHubUID()+gtLname()+sML)]
	String lsid=locationSid()
	return [
		sCv: sVER,
		sHv: sHVER,
		stsettings: msettings,
		lifx: state.lifx ?: [:],
		powerSource: state.powerSource ?: 'mains',
		region: ((String)gtSt('endpointCloud')).contains('graph-eu') ? 'eu' : 'us',
		instanceId: getInstanceSid(),
		accountId: accountSid(),
		newAcctSid: acctANDloc(),
		locationId: lsid,
		oldLocations: a1,
		allLocations: [lsid]+a1,
		enabled: (Boolean)gtAS('disabled')!=true,
		logPExec: (Boolean)gtAS('lPE')==true,
		incidents: getIncidents(),
		useLocalFuelStreams: (Boolean)gtAS('lFS')==true
	]
}

private void clearParentPistonCache(String meth=sNL, Boolean frcResub=false, Boolean callAll=false, Boolean ksDisable=false){
	String wName=sAppId()
	clearHashMap(wName)
	acctlocFLD[wName]=null
	locFLD[wName]=sNL
	incidentsFLD[wName]=null; incidentsFLD=incidentsFLD
	clearMeta(wName)
	mb()
	String n=handlePistn()
	List t0= childAppsRawFLD[wName] ?: wgetChildApps().findAll{ (String)it.name==n }
	if(t0){
		def t1=t0[iZ]
		if(t1!=null) t1.clearParentCache(meth) // will cause one child to read gtPdata
		if(ksDisable){
			t0.each{ chld -> chld.killSwitchDisable() }
		}else if(frcResub){
			t0.sort().each{ chld -> // this runs updated on all child pistons
				chld.updated()
			}
		}else if(callAll){
			clearChldCaches(true)
		}
	}
	t0=null
}

@Field volatile static Map<String,Map<String, Long>> cldClearFLD=[:]

void clearChldCaches(Boolean all=false, Boolean clrLogs=false, Boolean uber=false){
// clear child caches if has not run in 61 mins
	String wName=sAppId()
	String n=handlePistn()
	if(all||clrLogs||uber){
		clearMeta(wName)
	}
	Long t1=wnow()
	List t0= childAppsRawFLD[wName] ?: wgetChildApps().findAll{ (String)it.name==n }
	if(t0){
		if(!cldClearFLD[wName]){ cldClearFLD[wName]=(Map)[:]; cldClearFLD=cldClearFLD }
		if(clrLogs||uber){
			t0.sort().each{ chld ->
				Map a= !uber ? chld.clearLogsQ() : chld.clearAllQ()
				String schld=chld.id.toString()
				cldClearFLD[wName][schld]=t1
			}
			if(uber)clearGlobalPistonCache('uber')
		}else{
			//Long recTime=3660000L // 61 min in ms (regular piston cache cleanup)
			Long recTime; recTime=86460000L // 24hrs + 1 min in ms (regular piston cache cleanup)
			if(all) recTime=1000L // aggressive cache cleanup
			Long threshold=t1 - recTime
			t0.sort().each{ chld ->
				String pid=hashPID(chld.id)
				Map meta; meta=gtMeta(chld,wName,pid)
				String schld=chld.id.toString()
				Long t2; t2=cldClearFLD[wName][schld]
				Long t3=(Long)meta?.t
				Boolean t4=(Boolean)meta?.heCached
				if(t2==null){
					t2=threshold-3600000L
					cldClearFLD[wName][schld]=t2
				}
				else if( all || ( meta!=null && t4 && (Boolean)meta[sA] && t3!=null && t3>t2 && t3<threshold)){
					cldClearFLD[wName][schld]=t1
					Map a=chld.clearCache()
				}
			}
		}
	}
	t0=null
}

// clear child cache of globals (children refill cache via listAvailableVariables()

private void clearGlobalPistonCache(String meth=null){
	String wName=sAppId()
	String n=handlePistn()
	List t0= childAppsRawFLD[wName] ?: wgetChildApps().findAll{ (String)it.name==n }
	def t1=t0[iZ]
	if(t1!=null) t1.clearGlobalCache(meth) // will cause a child to read global Vars
	t0=null
}

private Boolean uidChgd(){ return gtHubUID()!=(String)state.svUUID }

private void initialize(){
	Boolean chg; chg=false
	Boolean reSub; reSub=(Boolean)gtSt('forceResub1')

	if(uidChgd()){
		reSub=null
		warn "hub UUID change detected"
		state.svUUID=gtHubUID()
	}

	String pS='properSID'
	Boolean prpSid; prpSid=(Boolean)gtSt(pS)
	if(reSub==null){
		assignSt('forceResub1',true)
		assignSt(pS,null)
		prpSid=null
		warn "SID reset requested"
	}

	String wName=sAppId()
	loggingFLD[wName]=null; loggingFLD=loggingFLD

	Boolean sprp= gtSetB(pS)
	if(prpSid==null || prpSid!=sprp){
		Boolean t0=sprp!=null ? sprp : true
		assignSt(pS,t0)
		app.updateSetting(pS, [(sTYPE): sBOOL, (sVAL): t0])
		initTokens()
		acctlocFLD[wName]=null
		locFLD[wName]=sNL
		clearHashMap(wName)
		warn "SID reset done"
		chg=true
	}

	if(checkSIDs()) chg=true

	if(chg) assignSt('doResub',true)

	subscribeAll()
	Map t0=(Map)gtAS(sVARS)
	if(t0==null)assignAS(sVARS,[:])

	verFLD[wName]=sVER
	HverFLD[wName]=sHVER
	clearLastPActivity(wName,sNL)
	clearLastDActivity(wName,sNL)
	clearMeta(wName)
	clearCachedchildApps(wName)

	refreshDevices()

	if((String)state.accessToken) updateEndpoint()
	registerInstance()

	cleanDashboardApp()
	checkWeather()

	lastRecoveredFLD[wName]=0L
	String recoveryMethod=(gtSetStr('recovery') ?: 'Every 30 minutes').replace('Every ', 'Every').replace(' minute', 'Minute').replace(' hour', 'Hour')
	if(recoveryMethod!='Never'){
		try{
			"run$recoveryMethod"(recoveryHandler)
		}catch(ignored){}
	}
	schedule('22 4/15 * * * ?', 'clearChldCaches') // regular child cache cleanup
}

Boolean checkSIDs(){
	String wName=sAppId()
	Boolean chg; chg=false
	String a, l, a1, l1
	acctlocFLD[wName]=null
	locFLD[wName]=sNL
	a=accountSid()
	l=locationSid()
	if(!acctANDloc()){
		app.removeSetting('acctID')
		app.removeSetting('locID')
		acctlocFLD[wName]=null
		locFLD[wName]=sNL
		a1=accountSid()
		l1=locationSid()
		if(a!=a1 || l!=l1){
			chg=true
			clearHashMap(wName)
		}
	}
	acctlocFLD[wName]=null
	locFLD[wName]=sNL
	a=accountSid()
	l=locationSid()
	a1=(String)gtAS('aSID')
	l1=(String)gtAS('lSID')
	if(a1!=a || l1!=l){
		if(a1!=null || l1!=null){
			debug "Detected account SID change ${a1} -> ${a} ${l1} -> ${l}"
		}
		initTokens()
		//initT=true
		chg=true
		assignAS('aSID',a)
		assignAS('lSID',l)
		assignSt('lSIDchanged',wnow())
		clearHashMap(wName)
	}
	if(!gtSt('lSIDchanged')) assignSt('lSIDchanged',wnow()) // for upgrade
	return chg
}

private void checkWeather(){
	String wTyp= gtSetStr('weatherType') ?: sNL
	if(wTyp || state.storAppOn){
		String apiK= gtSetStr('apixuKey')
		Boolean t0=wTyp && apiK
		def storageApp=getStorageApp(t0)
		if(storageApp!=null){
			state.storAppOn=true
			storageApp.settingsToState("weatherType", wTyp)
			storageApp.settingsToState("apixuKey", apiK)
			storageApp.settingsToState("zipCode", gtSetStr('zipCode'))
			if(wTyp=='OpenWeatherMap'){
				storageApp.settingsToState("zipCode1", gtSetStr('zipCode1'))
				storageApp.settingsToState("apiVer", gtSetB('apiVer')?:false)
				storageApp.settingsToState("wunits", gtSetStr('wunits')?:'imperial')
			}
			if(t0){
				storageApp.startWeather()
			}else{
				storageApp.stopWeather()
				//delete it ??
			}
		}else state.storAppOn=false
	}
}

Map getWCendpoints(){
	Map t0=[:]
	String ep,epl
	ep=apiServerUrl("$hubUID/apps/${app.id}".toString())
	epl=localApiServerUrl("${app.id}".toString())

	if(ep.endsWith(sDIV))ep=ep.substring(iZ,ep.length()-i1)
	t0.cp=ep

	if(isCustomEndpoint()) ep=epl
	if(ep.endsWith(sDIV))ep=ep.substring(iZ,ep.length()-i1)
	if(epl.endsWith(sDIV))epl=epl.substring(iZ,epl.length()-i1)

	t0.ep=ep
	t0.epl=epl
	t0.at=state.accessToken
	return t0
}

private void updateEndpoint(){
	String accessToken=(String)state.accessToken
	String newEP,newEPLocal
	newEP=apiServerUrl("$hubUID/apps/${app.id}/?access_token=${accessToken}".toString())
	newEPLocal=localApiServerUrl("${app.id}/?access_token=${accessToken}".toString())
	state.endpointCloud=newEP
	if(isCustomEndpoint()) newEP=newEPLocal
	if(newEP!=(String)state.endpoint){
		String wName=sAppId()
		state.endpoint=newEP
		state.endpointLocal=newEPLocal
		lastRegFLD[wName]=0L
		lastRegTryFLD[wName]=0L
		registerInstance()
	}
}

private Boolean initializeWebCoREEndpoint(Boolean disableRetry=false){
	if(!(String)state.endpoint || !(String)state.endpointCloud){
		String accessToken; accessToken=(String)state.accessToken
		if(!accessToken){
			try{
				accessToken=createAccessToken() // this fills in state.accessToken
			}catch(e){
				error "An error has occurred during endpoint initialization: ", null, e
				state.endpointCloud=sNL
				state.endpoint=sNL
				state.endpointLocal=sNL
			}
		}
		if(accessToken){
			updateEndpoint()
		}else if(!disableRetry){
			enableOauth()
			return initializeWebCoREEndpoint(true)
		}else error "Could not get access token"
	}
	return (String)state.endpoint!=sNL
}

private void enableOauth(){
	Map params=[
		uri: "http://localhost:8080/app/edit/update?_action_update=Update&oauthEnabled=true&id=${app.appTypeId}".toString(),
		headers: ['Content-Type':'text/html;charset=utf-8']
	]
	try{
		httpPost(params){ resp ->
			//LogTrace("response (sDATA): ${resp.data}")
		}
	}catch(e){
		error "enableOauth something went wrong: ", null, e
	}
}

private void subscribeAll(){
	subscribe(location, handle()+".poll", webCoREHandler)
//	subscribe(location, sAT2+handle(), webCoREHandler)
	subscribe(location, "systemStart", startHandler)
	subscribe(location, "mode", modeHandler)
//below unused
//	subscribe(location, "HubUpdated", hubUpdatedHandler, [filterEvents: false])
//	subscribe(location, "summary", summaryHandler, [filterEvents: false])
	subscribe(location, "hsmRule", hsmRuleHandler, [filterEvents: false])
	subscribe(location, "hsmRules", hsmRulesHandler, [filterEvents: false])
	subscribe(location, "hsmStatus", hsmHandler, [filterEvents: false])
	subscribe(location, "hsmAlert", hsmAlertHandler, [filterEvents: false])
	setPowerSource(getHub()?.isBatteryInUse() ? 'battery' : 'mains')
}

/******************************************************************************/
/***																		***/
/*** DASHBOARD MAPPINGS														***/
/***																		***/
/******************************************************************************/

mappings{
	//path("/dashboard"){action: [GET: "api_dashboard"]}
	path("/intf/dashboard/load"){action: [GET: "api_intf_dashboard_load"]}
	path("/intf/dashboard/devices"){action: [GET: "api_intf_dashboard_devices"]}
	path("/intf/dashboard/refresh"){action: [GET: "api_intf_dashboard_refresh"]}
	path("/intf/dashboard/piston/new"){action: [GET: "api_intf_dashboard_piston_new"]}
	path("/intf/dashboard/piston/create"){action: [GET: "api_intf_dashboard_piston_create"]}
	path("/intf/dashboard/piston/backup"){action: [GET: "api_intf_dashboard_piston_backup"]}
	path("/intf/dashboard/piston/get"){action: [GET: "api_intf_dashboard_piston_get"]}
	path("/intf/dashboard/piston/getDb"){action: [GET: "api_intf_dashboard_piston_getDb"]}
	path("/intf/dashboard/piston/set"){action: [GET: "api_intf_dashboard_piston_set"]}
	path("/intf/dashboard/piston/set.start"){action: [GET: "api_intf_dashboard_piston_set_start"]}
	path("/intf/dashboard/piston/set.chunk"){action: [GET: "api_intf_dashboard_piston_set_chunk"]}
	path("/intf/dashboard/piston/set.end"){action: [GET: "api_intf_dashboard_piston_set_end"]}
	path("/intf/dashboard/piston/pause"){action: [GET: "api_intf_dashboard_piston_pause"]}
	path("/intf/dashboard/piston/resume"){action: [GET: "api_intf_dashboard_piston_resume"]}
	path("/intf/dashboard/piston/set.bin"){action: [GET: "api_intf_dashboard_piston_set_bin"]}
	path("/intf/dashboard/piston/tile"){action: [GET: "api_intf_dashboard_piston_tile"]}
	path("/intf/dashboard/piston/set.category"){action: [GET: "api_intf_dashboard_piston_set_category"]}
	path("/intf/dashboard/piston/set.modified"){action: [GET: "api_intf_dashboard_piston_set_modified"]}
	path("/intf/dashboard/piston/logging"){action: [GET: "api_intf_dashboard_piston_logging"]}
	path("/intf/dashboard/piston/clear.logs"){action: [GET: "api_intf_dashboard_piston_clear_logs"]}
	path("/intf/dashboard/piston/delete"){action: [GET: "api_intf_dashboard_piston_delete"]}
	path("/intf/dashboard/piston/evaluate"){action: [GET: "api_intf_dashboard_piston_evaluate"]}
	path("/intf/dashboard/piston/test"){action: [GET: "api_intf_dashboard_piston_test"]}
	path("/intf/dashboard/piston/activity"){action: [GET: "api_intf_dashboard_piston_activity"]}
	path("/intf/dashboard/variable/set"){action: [GET: "api_intf_variable_set"]}
	path("/intf/dashboard/settings/set"){action: [GET: "api_intf_settings_set"]}
	path("/intf/fuelstreams/list"){action: [GET: "api_intf_fuelstreams_list"]}
	path("/intf/fuelstreams/get"){action: [GET: "api_intf_fuelstreams_get"]}
	path("/intf/dashboard/presence/create"){action: [GET: "api_intf_dashboard_presence_create"]}
	path("/intf/location/entered"){action: [GET: "api_intf_location_entered"]}
	path("/intf/location/exited"){action: [GET: "api_intf_location_exited"]}
	path("/intf/location/updated"){action: [GET: "api_intf_location_updated"]}
	path("/ifttt/:eventName"){action: [GET: "api_ifttt", POST: "api_ifttt"]}
	path("/email/:pistonId"){action: [POST: "api_email"]}
	path("/execute/:pistonIdOrName"){action: [GET: "api_execute", POST: "api_execute"]}
	path("/global/:varName"){action: [GET: "api_global"]}
	path("/tap"){action: [POST: "api_tap"]}
	path("/tap/:tapId"){action: [GET: "api_tap"]}
	path("/gforward/:pistonIdOrName"){action: [GET: "api_forward", POST: "api_forward"]}
}

private Map api_get_error_result(String error,String m=sNL){
	debug "Dashboard: error: ${error} m:$m"
	String wName=sAppId()
	clearLastPActivity(wName,sNL)
	clearLastDActivity(wName,sNL)
	return [
		(sNM): gtLname() + ' \\ ' + appName(),
		(sERR): error,
		(sNOW): wnow()
	]
}

private static String normalizeLabel(pisN){
	String label=(String)pisN.label
	return normalizeString(label)
}

private static String normalizeString(String s){
	String regex=' <span style.*$'
	String t0=s.replaceAll(regex, sBLK)
	return t0!=sNL ? t0 : s
}

@Field static Semaphore theSerialLockFLD=new Semaphore(1)
@Field volatile static Long lockTimeFLD
private void wpauseExecution(Long t){ pauseExecution(t) }

@CompileStatic
Boolean getTheLock(String meth=sNL){
	Long waitT=1600L
	Boolean wait; wait=false
	Semaphore sema=theSerialLockFLD
	while(!sema.tryAcquire()){
		// did not get the lock
		Long t; t=lockTimeFLD
		if(t==null){
			t=wnow()
			lockTimeFLD=t
		}
		//if(eric())log.warn "waiting for ${qname} lock access $meth"
		wpauseExecution(waitT)
		wait=true
		if((wnow()-t) > 30000L){
			// Drain any lingering permits so we start from a known locked state
			// (permits=0), then break treating ourselves as the holder.
			// releaseTheLock guards against over-release, so the original stuck
			// holder releasing later won't corrupt the permit count.
			sema.drainPermits()
			lockTimeFLD=null
			warn "overriding lock $meth"
			break
		}
	}
	lockTimeFLD=wnow()
	return wait
}

@CompileStatic
static void releaseTheLock(String meth=sNL){
	lockTimeFLD=null
	Semaphore sema=theSerialLockFLD
	// Guard against double-release: only release if currently locked (permits==0).
	// This covers the case where a force-timeout override already drained permits
	// and the stuck original holder later calls release.
	if(sema.availablePermits()==0) sema.release()
}

@Field volatile static Map<String,List<Map>> childAppsFLD= [:]
@Field volatile static Map<String,List> childAppsRawFLD= [:]
@Field static final String sGTCACHED='getCached'

List<Map> gtCachedchildApps(String wName,Boolean haveLock=false){
	List<Map> res
	res= childAppsFLD[wName]
	if(!res){
		String n=handlePistn()
		List freshRaw= wgetChildApps().findAll{ (String)it.name==n }.sort{ (String)it.label }
		List<Map> fresh= freshRaw.collect{
			String pid=hashPID(it.id)
			[ (sID): it.id.toString(), pid: pid, (sNM): (String)it.name, label: (String)it.label, nlabel: normalizeLabel(it) ]
		}
		if(!haveLock) Boolean didw=getTheLock(sGTCACHED)
		res= childAppsFLD[wName]
		if(!res){
			res= fresh
			childAppsFLD[wName]= res
			childAppsRawFLD[wName]= freshRaw
			childAppsFLD= childAppsFLD
			childAppsRawFLD= childAppsRawFLD
		}
		if(!haveLock) releaseTheLock(sGTCACHED)
	}
	return []+res
}

@Field static final String sCLRCACHED='clearCached'

void clearCachedchildApps(String wName, Boolean haveLock=false){
	if(!haveLock) Boolean didw=getTheLock(sCLRCACHED)
		childAppsFLD[wName]= []
		childAppsRawFLD[wName]= []
		childAppsFLD= childAppsFLD
		childAppsRawFLD= childAppsRawFLD
	if(!haveLock) releaseTheLock(sCLRCACHED)
}


@Field static final String sMETA='meta'

/**
 * get Piston details
 * @returns [ [(sID): pid, (sNM): normalizeLabel(it), meta: [:]+meta],... ]
 */
@CompileStatic
private List<Map> presult(String wName,Boolean haveLock=false){
	List<Map> a= gtCachedchildApps(wName,haveLock).sort{ Map it -> sMs(it,'label') }.collect{ Map it ->
		String pid= sMs(it,'pid')
		/*Map meta=[
			(sA):isAct(t0),
			(sC):t0[sCTGRY],
			(sT):(Long)t0[sLEXEC],
			(sM): (Long)t0[sMODFD],
			(sB): (String)t0[sBIN],
			(sN):(Long)t0[sNSCH],
			(sZ):(String)t0.pistonZ,
			(sS):st,
			heCached:(Boolean)t0.Cached ?: false
		] */
		Map meta; meta=gtMeta(null,wName,pid)
		pitem(pid, (String)it.nlabel, meta)
	}
	a
}

Map pitem(String pid, String n, Map meta){
	return [ (sID): pid, (sNM): n, (sMETA): (meta ? [:]+meta : [:])]
}

@Field static final String sCB='clearB'

/**
 * Invalidates the cached dashboard base result for this instance, forcing the next
 * dashboard load to rebuild it, and resets the last-activity tracking for ALL
 * sessions of this instance (not just the caller's), forcing every connected
 * browser to be treated as stale. Call only after data the base result reflects
 * has actually changed - not on every request or on failed/no-op operations.
 */
@CompileStatic
private void clearBaseResult(String meth=sNL,String wNi=sNL){
	String wName= wNi ?: sAppId()
	Boolean didw=getTheLock(sCB)
	Map a=null
	base_resultFLD[wName]=a
	base_resultFLD=base_resultFLD
	clearLastDActivity(wName,sNL)
	releaseTheLock(sCB)
	//if(eric())debug "clearBaseResult "+meth
}

/**
 * Invalidates every parent-side cache that a deleted piston can be found in: the cached
 * child-app list, the piston metadata cache, the hash map cache, and the dashboard base
 * result. Shared by the dashboard delete API and by pistonUninstalled() (called by a piston
 * removed outside the dashboard, e.g. directly from the Hubitat Apps list) so both paths stay
 * in sync.
 */
private void invalidatePistonCaches(String wName, String schld=sNL, String meth='delete Piston'){
	if(schld){
		if(!cldClearFLD[wName]){ cldClearFLD[wName]=(Map)[:]; cldClearFLD=cldClearFLD }
		cldClearFLD[wName].remove(schld)
	}
	clearCachedchildApps(wName)
	clearMeta(wName)
	clearHashMap(wName)
	mb()
	clearBaseResult(meth,wName)
}

@Field volatile static Map<String,Map<String,Object>> base_resultFLD= [:]
@Field volatile static Map<String,Integer> cntbase_resultFLD= [:]


@Field static final String sDEVVER='deviceVersion'

@CompileStatic
private Map<String,Object> api_get_base_result(){
	String t='baseR'
	String wName=sAppId()

	Boolean didw=getTheLock(t)

	Long lnow=wnow()
	if(base_resultFLD[wName]!=null){
		cntbase_resultFLD[wName]=cntbase_resultFLD[wName]+i1
		if(cntbase_resultFLD[wName]>200){
			base_resultFLD[wName]=(Map)null
		}else{
			Map<String,Object> result=[:]+base_resultFLD[wName]
			((Map)result.instance).pistons= presult(wName,true)
			base_resultFLD[wName]=[:]+result
			base_resultFLD=base_resultFLD
			releaseTheLock(t)
			result.put(sNOW,lnow)
			return result
		}
	}

	cntbase_resultFLD[wName]=iZ
	//log.warn "filling in"

	TimeZone tz=mTZ()
	String currentDeviceVersion=(String)gtSt(sDEVVER)
/*
	Long incidentThreshold=Math.round(lnow - 604800000.0D)
	def a=gtSt(sHSMALRTS)
	List<Map> alerts= a ? (List<Map>)a : [] */

	String instanceId=getInstanceSid()
	String locationId=locationSid()

	String myN= appName()
	Map<String,Object> result= [
		(sNM): gtLname()+ ' \\ ' +myN,
		instance: [
			account: [(sID): accountSid(), t: gtSt('lSIDchanged') ],
			pistons: presult(wName,true),
			(sID): instanceId,
			locationId: locationId,
			(sNM): myN,
			uri: (String)gtSt('endpoint'),
			(sDEVVER): currentDeviceVersion,
			coreVersion: sVER,
			heVersion: sHVER,
			enabled: !gtSetB('disabled'),
			settings: gtSt('settings') ?: [:],
			lifx: gtSt('lifx') ?: [:],
			virtualDevices: virtualDevices(),
			globalVars: listAvailableVariables1(),
			fuelStreamUrls: getFuelStreamUrls(instanceId),
		],
		location: [
			////hubs: location.getHubs().findAll{ !((String)it.name).contains(':') }.collect{ [id: it.id /*hashId(it.id)*/, (sNM): (String)it.name, firmware: isHubitat() ? getHubitatVersion()[it.id] : it.getFirmwareVersionString(), physical: it.getType().toString().contains('PHYSICAL'), powerSource: it.isBatteryInUse() ? 'battery' : 'mains' ]},
			//hubs: ((List)location.getHubs()).collect{ [(sID): it.id /*hashId(it.id)*/, (sNM): gtLname(), firmware: isHubitat() ? (String)getHubitatVersion()[(String)it.id.toString()] : (String)it.getFirmwareVersionString(), physical: it.getType().toString().contains('PHYSICAL'), powerSource: it.isBatteryInUse() ? 'battery' : 'mains' ]},
			hubs: gtHubs().collect { [id: it.id, name: gtLname(), firmware: it.fw, physical: it.physical, powerSource: it.powerSource ] },
			//incidents: alerts.collect{it}.findAll{ (Long)it.date >= incidentThreshold },
			incidents: getIncidents(true),
			//incidents: isHubitat() ? [] : location.activeIncidents.collect{[date: it.date.time, (sTIT): it.getTitle(), message: it.getMessage(), args: it.getMessageArgs(), sourceType: it.getSourceType()]}.findAll{ it.date >= incidentThreshold },
			(sID): locationId,
			mode: hashId(gtCurrentMode()[sID]),
			modes: gtModes().collect{ Map it -> [id: hashId(it.id), name: sMs(it,'name') ]},
			shm: transformHsmStatus(gtLhsmStatus()),
			(sNM): gtLname(),
			temperatureScale: gtLtScale(),
			timeZone: tz ? [
				(sID): tz.ID,
				(sNM): tz.displayName,
				offset: tz.rawOffset
			] : null,
			zipCode: gtLzip(),
		],
	] as Map<String, Object>
	base_resultFLD[wName]=[:]+result
	base_resultFLD=base_resultFLD
	releaseTheLock(t)
	result.put(sNOW,lnow)
	return result
}

private Map<String,Map> getFuelStreamUrls(String iid){
	if(!useLocalFuelStreams()){
		String region=((String)state.endpointCloud).contains('graph-eu') ? 'eu' : 'us'
		String baseUrl='https://api-' + region + '-' + iid[i32] + '.webcore.co:9287/fuelStreams'
		Map headers=[ 'Auth-Token' : iid ]

		return [
			list: [(sL): false, (sM): 'POST', (sH): headers, u: baseUrl + '/list', (sD): [(sI): iid]],
			get : [(sL): false, (sM): 'POST', (sH): headers, u: baseUrl + '/get' , (sD): [(sI): iid ], (sP): 'f']
		]
	}

	String baseUrl=isCustomEndpoint() && useLocalFuelStreams() ? customApiServerUrl(sDIV) : apiServerUrl("$hubUID/apps/${app.id}/".toString())

	String params=baseUrl.contains((String)state.accessToken) ? sBLK : "access_token=${state.accessToken}".toString()

	return [
		list: [(sL): true, u: baseUrl + "intf/fuelstreams/list?${params}".toString() ],
		get : [(sL): true, u: baseUrl + "intf/fuelstreams/get?id={fuelStreamId}${params ? "&" + params : sBLK}".toString(), (sP): 'fuelStreamId' ]
	]
}

Boolean useLocalFuelStreams(){
	Boolean b= gtSetB(sLCLFS)
	return b!=null ? b : true
}

// dashboard activity cache
@Field volatile static Map<String,Map<String,String>> lastDActivityFLD = [:]
@Field volatile static Map<String,Map<String,String>> lastDActivityTOKFLD = [:]
@Field volatile static Map<String,Map<String,Long>> tlastDActivityFLD=[:]

// piston activity cache
@Field volatile static Map<String,Map<String,String>> lastPActivityFLD = [:]
@Field volatile static Map<String,Map<String,String>> lastPActivityTOKFLD = [:]
@Field volatile static Map<String,Map<String,String>> lastPActivityPIDFLD = [:]
@Field volatile static Map<String,Map<String,Long>> tlastPActivityFLD=[:]

@CompileStatic
static private clearLastDActivity(String wName,String sess){
	if(!lastDActivityFLD[wName] || !sess) lastDActivityFLD.put(wName,[:])
	if(!lastDActivityTOKFLD[wName] || !sess) lastDActivityTOKFLD.put(wName,[:])
	if(!tlastDActivityFLD[wName] || !sess) tlastDActivityFLD.put(wName,[:])
	if(sess){
		lastDActivityFLD[wName][sess]=sNL
		lastDActivityTOKFLD[wName][sess]=sNL
		tlastDActivityFLD[wName][sess]=0L
	}
	lastDActivityFLD= lastDActivityFLD
	lastDActivityTOKFLD= lastDActivityTOKFLD
	tlastDActivityFLD= tlastDActivityFLD
}

@CompileStatic
static private clearLastPActivity(String wName,String sess){
	if(!lastPActivityFLD[wName] || !sess) lastPActivityFLD.put(wName,[:])
	if(!lastPActivityTOKFLD[wName] || !sess) lastPActivityTOKFLD.put(wName,[:])
	if(!lastPActivityPIDFLD[wName] || !sess) lastPActivityPIDFLD.put(wName,[:])
	if(!tlastPActivityFLD[wName] || !sess) tlastPActivityFLD.put(wName,[:])
	if(sess){
		lastPActivityPIDFLD[wName][sess]=sNL
		lastPActivityTOKFLD[wName][sess]=sNL
		tlastPActivityFLD[wName][sess]=0L
		lastPActivityFLD[wName][sess]=sNL
	}
	lastPActivityPIDFLD= lastPActivityPIDFLD
	lastPActivityTOKFLD= lastPActivityTOKFLD
	tlastPActivityFLD= tlastPActivityFLD
	lastPActivityFLD= lastPActivityFLD
}

private api_intf_dashboard_load(){
	Map result; result=[:]
	recoveryHandler()
	String s='dashLoad'
	String msg; msg=s
	Boolean err; err=false
	Map p=(Map)params
	if(verifySecurityToken(p)){
		msg+=' security ok'
		result=api_get_base_result()

		if(sMs(p,'dashboard')=='1'){
			startDashboard()
		}else{
			if((String)state.dashboard!=sINACT) stopDashboard()
		}
	}else{
		err=true
		msg+=' security NOT ok'
		if(sMs(p,'pin')!=sNL){
			String pi=gtSetStr('PIN')
			if(pi && md5('pin:'+pi)==sMs(p,'pin')){
				result=api_get_base_result()
				result.instance.token=createSecurityToken()
			}else{
				error "Dashboard: Authentication failed due to an invalid PIN"
			}
		}
		if(!result){
			msg+=' returning token error'
			result=api_get_error_result(sERRTOK,s)
		}
	}

	String wName=sAppId()
	String sess=sMs(p,'session') ?: 'default'
	if(!err && result){
		msg+=' no error & result'
		String tok=sMs(p,'token')
		if(result)result.remove('now')
		String jsonData= JsonOutput.toJson(result)
		String rl=generateMD5_A(jsonData)
		Long t=wnow()
		if(!lastDActivityFLD[wName] || !lastDActivityTOKFLD[wName] || !tlastDActivityFLD[wName]){
			clearLastDActivity(wName,sess)
		}
		if( !tlastDActivityFLD[wName][sess] || tlastDActivityFLD[wName][sess] < (t-11000L) ||
				!lastDActivityFLD[wName][sess] || rl!=lastDActivityFLD[wName][sess] ||
				!lastDActivityTOKFLD[wName][sess] || tok!=lastDActivityTOKFLD[wName][sess]){
			//log.warn "rl: $rl lastAct: $lastActivityFLD"
			lastDActivityFLD[wName][sess]=rl
			lastDActivityTOKFLD[wName][sess]=tok
			lastDActivityFLD= lastDActivityFLD
			lastDActivityTOKFLD= lastDActivityTOKFLD
			msg+=' updating cache'
		}else{
			msg+=' using cache'
			result=[:]
		}
		tlastDActivityFLD[wName][sess]=t
		tlastDActivityFLD= tlastDActivityFLD
	}else{
		msg+=' error OR no result'
		clearLastDActivity(wName,sess)
	}

	//debug "Dashboard: load ${params} " +msg

	if(getLogging()[sDBG]) checkResultSize(result, false, s)

	//for accuracy, use the time as close as possible to the render
	result.put(sNOW,wnow())
	renderRes(result)
}

private api_intf_dashboard_devices(){
	Map result; result=[:]
	String s='dashboard_devices '
	Map p=(Map)params
	if(verifySecurityToken(p)){
		String soffset= "${p.offset}".toString()
		Integer offset= soffset.isInteger() ? soffset.toInteger() : iZ
		if(eric())debug s+soffset
		result=listAvailableDevices(false, true, offset) +
				[ (sDEVVER): (String)gtAS(sDEVVER) ]
	}else{ result=api_get_error_result(sERRTOK,s) }
	//for accuracy, use the time as close as possible to the render
	result.put(sNOW,wnow())
	renderRes(result)
}

private api_intf_dashboard_refresh(){
	debug "Dashboard: Request received to refresh instance"
	startDashboard()
	Map result; result=[:]
	if(verifySecurityToken((Map)params)){
		result=getDashboardData()
	}else{
		result=api_get_error_result(sERRTOK)
	}
	//for accuracy, use the time as close as possible to the render
	result.put(sNOW,wnow())
	renderRes(result)
}

private Map getDashboardData(){
//	def start=wnow()
	Map result
	def storageApp //= getStorageApp()
	if(storageApp!=null){
		result=storageApp.getDashboardData()
	}else{
		result=((Map<String,Object>)settings).findAll{ ((String)it.key).startsWith("dev:") }.values().flatten().collectEntries{ dev ->
			[ (hashId(dev.id)): ((List<String>)((List)dev.getSupportedAttributes()).collect{ (String)it.name }).unique().collectEntries{
				def value
				try { value=dev.currentValue(it) }catch(ignored){ value=null }
				return [ (it) : value]
			}]
		}
	}
	return result
}

private api_intf_dashboard_piston_new(){
	Map result
	debug "Dashboard: Request received to generate a new piston name"
	String s='piston_new'
	if(verifySecurityToken((Map)params)){
		result=[(sSTS): sSUCC, (sNM): generatePistonName()]
	}else{ result=api_get_error_result(sERRTOK,s) }
	renderRes(result)
}

private api_intf_dashboard_piston_create(){
	Map result
	debug "Dashboard: Request received to create a new piston"
	Map p=(Map)params
	if(verifySecurityToken(p)){
		String pname=sMs(p,'name')!=sNL ? sMs(p,'name') : generatePistonName()
		String wName=sAppId()
		List<Map> apps; apps=gtCachedchildApps(wName)
		Boolean found; found=false
		for(Map mapp in apps){
			String tN= sMs(mapp,'label') ?: sMs(mapp,sNM)
			if(tN==pname){
				found=true
				break
			}
		}
		apps=null
		if(!found){
			try{
				def piston=addChildApp("ady624", handlePistn(), pname)
				debug "created piston $piston.id params $p"
				if(sMs(p,'author')!=sNL || sMs(p,'bin')!=sNL){
					piston.config([bin: sMs(p,'bin'), author: sMs(p,'author'), initialVersion: sVER])
				}
				debug "Created Piston "+pname
				result=[(sSTS): sSUCC, (sID): hashPID(piston.id)]
				clearCachedchildApps(wName)
			}catch(ignored){
				error "Please install the webCoRE Piston app"
				result=[(sSTS): sERROR, (sERR): sERRUNK]
			}
		}else{
			error "create piston: Name in use "+pname
			result=[(sSTS): sERROR, (sERR): sERRUNK]
		}
	}else{ result=api_get_error_result(sERRTOK,'piston_create') }
	renderRes(result)
}

private findPiston(String id, String nm=sNL, String n=handlePistn()){
	if(id==sNL && nm==sNL) return null
	// Fast path: piston apps are in the lightweight cache with precomputed pid/nlabel
	if(n==handlePistn()){
		String wName=sAppId()
		String matchedId=sNL
		List<Map> cached=gtCachedchildApps(wName)
		if(id!=sNL){
			for(Map entry in cached){
				if((String)entry.pid==id || hashId((String)entry[sID])==id){ matchedId=(String)entry[sID]; break }
			}
		}
		if(!matchedId && nm!=sNL){
			for(Map entry in cached){
				if((String)entry.label==nm || (String)entry.nlabel==nm){ matchedId=(String)entry[sID]; break }
			}
		}
		if(!matchedId) return null
		return wgetChildApps().find{ it.id.toString()==matchedId }
	}
	// Non-piston child apps (e.g. fuel stream): original scan
	List t0=wgetChildApps().findAll{ (String)it.name==n }
	def piston=null
	if(id!=sNL){
		piston=t0.find{ hashPID(it.id)==id || hashId(it.id)==id }
	}
	if(nm!=sNL && !piston) piston=t0.find{ (String)it.label==nm || normalizeLabel(it)==nm }
	return piston
}

private Map gtAllCommands(){
	Map allCmds
	allCmds= commands().sort{ (String)it.value.d!=sNL ? (String)it.value.d : (String)it.value.n }
	return allCmds
}


private api_intf_dashboard_piston_getDb(){
	Map result; result=[:]
	if(verifySecurityToken((Map)params)){
		String serverDbVersion=sHVER
		debug "Dashboard: getDb sending new db current: ${serverDbVersion} in server"
		Map theDb=[
				capabilities: capabilities().sort{ (String)it.value.d },
				commands: [
					physical: gtAllCommands(),
					virtual: virtualCommands().sort{ (String)it.value.d!=sNL ? (String)it.value.d : (String)it.value.n }
				],
				attributes: attributesFLD.sort{ (String)it.key },
				comparisons: comparisonsFLD,
				functions: functionsFLD,
				colors: [
					//standard: colorUtil?.ALL ?: getColors()
					standard: getColors()
				],
		]
		result.dbVersion=serverDbVersion
		result.db=theDb
	}else{ result=api_get_error_result(sERRTOK,'getDb') }
	result.put(sNOW,wnow())
	renderRes(result)
}

private api_intf_dashboard_piston_get(){
	Map result; result=[:]
	Boolean requireDb
	String s='piston_get'
	Map p=(Map)params
	if(verifySecurityToken(p)){
		String pistonId=sMs(p,'id')
		def piston=findPiston(pistonId)
		if(piston){
			debug "Dashboard: Request received to get piston ${pistonId} ${(String)piston.label}"

			String serverDbVersion=sHVER
			String clientDbVersion=sMs(p,'db')
			requireDb=serverDbVersion!=clientDbVersion
			Map t0=(Map)piston.get()
			result[sDATA]=t0!=null ? t0 : [:]
			if(requireDb){
				debug "Dashboard: get piston ${p?.id} needs new db current: ${serverDbVersion} in server ${clientDbVersion}"
				/*Map theDb=[
					capabilities: capabilities().sort{ (String)it.value.d },
					commands: [
						physical: commands().sort{ (String)it.value.d!=sNL ? (String)it.value.d : (String)it.value.n },
						virtual: virtualCommands().sort{ (String)it.value.d!=sNL ? (String)it.value.d : (String)it.value.n }
					],
					attributes: attributesFLD.sort{ (String)it.key },
					comparisons: comparisonsFLD,
					functions: functionsFLD,
					colors: [
						//standard: colorUtil?.ALL ?: getColors()
						standard: getColors()
					],
				]*/
				result.dbVersion=serverDbVersion
				//result.db=theDb
			}
			if(getLogging()[sDBG]) checkResultSize(result, requireDb, "get piston")
		}else{
			result=api_get_error_result(sERRID,s)
			warn "Dashboard: get piston bad ID : ${p?.id}"
		}
	}else{
		result=api_get_error_result(sERRTOK,s)
		warn "Dashboard: get piston bad token: ${p}"
	}

	//for accuracy, use the time as close as possible to the render
	result.put(sNOW,wnow())
	renderRes(result)
}

private void checkResultSize(Map result, Boolean requireDb=false, String caller=sNL){
	if(!isCustomEndpoint() || !gtSetB('localHubUrl')){
		String jsonData; jsonData= JsonOutput.toJson(result)
		//data saver for Hubitat ~100K limit
		Integer responseLength,resl,svLength
		responseLength=jsonData.getBytes(sUTF8).length
		resl= (Integer)(responseLength / 1024)
		//debug "Check size found ${resl}KB response requireDb: (${requireDb}) caller: ${caller}"
		if(resl > 95){ //these are loaded anyway right after loading the piston
			warn "Trimming ${resl}KB response to smaller size (${requireDb}) caller: ${caller}"
			result.trimmed=true

			Map rd= (Map)result[sDATA]
			if(rd){
				rd.logs=[]
				rd[sTRC]=[:]
				rd.localVars=[:]
				rd.state=[:]
				rd.schedules=[]
			}

			svLength=responseLength
			jsonData= JsonOutput.toJson(result)
			responseLength=jsonData.getBytes(sUTF8).length
			resl= (Integer)(responseLength / 1024)
			debug "First Trimmed response length: ${resl}KB"
			if(responseLength==svLength || resl > 105){
				warn "First TRIMMING may be un-successful, trying further trimming ${resl}KB"

				if(rd){
					rd.systemVars=[:]
					rd.stats.timing=[]
				}

				svLength=responseLength
				jsonData= JsonOutput.toJson(result)
				responseLength=jsonData.getBytes(sUTF8).length
				resl= (Integer)(responseLength / 1024)
				debug "Second Trimmed response length: ${resl}KB"
				if(responseLength==svLength || resl > 105){
					warn "Final TRIMMING may be un-successful, you should load a smaller piston then reload this piston ${resl}KB"
				}else warn "Final TRIMMING successful, you should load a small piston again to complete IDE update ${resl}KB"
			}else warn "First TRIMMING successful ${resl}KB"
		}
		//log.debug "Trimmed response length: ${jsonData.getBytes(sUTF8).length}"
	}
}

private api_intf_dashboard_piston_backup(){
	Map result
	result=[
		pistons: [],
		(sNOW):0L
	]
	Map p=(Map)params
	debug "Dashboard: Request received to backup pistons ${p?.ids}"
	if(verifySecurityToken(p)){
		List pistonIds=(sMs(p,'ids') ?: sBLK).tokenize(',')
		String myN= appName()
		for(String pistonId in pistonIds){
			def piston=findPiston(pistonId)
			if(piston){
				Map pd=(Map)piston.get(true)
				if(pd){
					pd.instance=[(sID): getInstanceSid(), (sNM): myN]
					Boolean a=result.pistons.push(pd)
					if(!isCustomEndpoint() || !gtSetB('localHubUrl')){
						String jsonData= JsonOutput.toJson(result)
						Integer responseLength=jsonData.getBytes(sUTF8).length
						if(responseLength > 110 * 1024){
							warn "Backup too big ${ (Integer)(responseLength/1024) }KB response"
						}
					}
				}
			}
		}
	}else{ result=api_get_error_result(sERRTOK,'piston_backup') }
	//for accuracy, use the time as close as possible to the render
	result.put(sNOW,wnow())
	renderRes(result)
}

private String decodeEmoji(String value){
	if(!value) return sBLK
	return value.replaceAll(/(\:%[0-9A-F]{2}%[0-9A-F]{2}%[0-9A-F]{2}%[0-9A-F]{2}\:)/){
		m -> URLDecoder.decode( ((String)m[0]).substring(1, 13), sUTF8)
	}
}

private Map api_intf_dashboard_piston_set_save(String id, String data, Map<String,String>chunks){
	def piston=findPiston(id)
	String myS="Dashboard: Request received to set_save"
	if(piston){
		debug myS
	/*
		def s=decodeEmoji(new String(data.decodeBase64(), sUTF8))
		int cs=512
		for (int a=0; a <= Math.floor(s.size() / cs); a++){
			int x=a * cs + cs - 1
		if(x >= s.size()) x=s.size() - 1
			log.trace s.substring(a * cs, x)
		}
	*/
		LinkedHashMap p=(LinkedHashMap) new JsonSlurper().parseText(decodeEmoji(new String(data.decodeBase64(), sUTF8)))
		Map result=(Map)piston.setup(p, chunks)
		String wName=sAppId()
		clearCachedchildApps(wName) // name may change
		runIn(21, broadcastPistonList)
		return result
	}
	debug myS+" $id $chunks NOT FOUND"
	return null
}

//set is used for small pistons, for large data, using set.start, set.chunk, and set.end
private api_intf_dashboard_piston_set(){
	Map result
	debug "Dashboard: Request received to set a piston"
	Map p=(Map)params
	if(verifySecurityToken(p)){
		String data=sMs(p,'data')
		//save the piston
		Map saved=api_intf_dashboard_piston_set_save(sMs(p,'id'), data, ['chunk:0' : data])
		if(saved){
			if(saved.rtData){
				updateRunTimeData((Map)saved.rtData)
				saved.rtData=null
				String wName=sAppId()
				clearCachedchildApps(wName)
			}
			result=[(sSTS): sSUCC] + saved
		}else{ result=[(sSTS): sERROR, (sERR): sERRUNK] }
	}else{ result=api_get_error_result(sERRTOK) }
	renderRes(result)
}

@Field volatile static LinkedHashMap<String, LinkedHashMap> pPistonChunksFLD= [:]

private api_intf_dashboard_piston_set_start(){
	Map result
	debug "Dashboard: Request received to set a piston (chunked start)"
	Map p=(Map)params
	if(verifySecurityToken(p)){
		String chunkstr="${p?.chunks}".toString()
		Integer chunks=chunkstr.isInteger() ? chunkstr.toInteger() : iZ
		String wName=sAppId()
		String ckey=wName+sCLN+sMs(p,'token')
		if((chunks > iZ) && (chunks < i100)){
			clearHashMap(wName)
			pPistonChunksFLD[ckey]=[(sID): p?.id, count: chunks]
			pPistonChunksFLD=pPistonChunksFLD
			mb()
			result=[(sSTS): "ST_READY"]
		}else{ result=[(sSTS): sERROR, (sERR): "ERR_INVALID_CHUNK_COUNT"] }
	}else{ result=api_get_error_result(sERRTOK) }
	renderRes(result)
}

private api_intf_dashboard_piston_set_chunk(){
	Map result
	String wName=sAppId()
	Map p=(Map)params
	String ckey=wName+sCLN+sMs(p,'token')
	String mchunk="${p?.chunk}".toString()
	Integer chunk=mchunk.isInteger() ? mchunk.toInteger() : -i1
	debug "Dashboard: Request received to set a piston chunk (#${1 + chunk}/${pPistonChunksFLD[ckey]?.count})"
	if(verifySecurityToken(p)){
		String data=(String)p?.data
		mb()
		LinkedHashMap<String,Object>chunks=pPistonChunksFLD[ckey]
		if(chunks && (Integer)chunks.count && (chunk >= iZ) && (chunk < (Integer)chunks.count)){
			chunks["chunk:$chunk".toString()]=data
			pPistonChunksFLD[ckey]=chunks
			mb()
			result=[(sSTS): "ST_READY"]
		}else{ result=[(sSTS): sERROR, (sERR): sERRCHUNK] }
	}else{ result=api_get_error_result(sERRTOK) }
	renderRes(result)
}

private api_intf_dashboard_piston_set_end(){
	Map result
	String wName=sAppId()
	Map ep=(Map)params
	String ckey=wName+sCLN+sMs(ep,'token')
	debug "Dashboard: Request received to set a piston (chunked end)"
	if(verifySecurityToken(ep)){
		mb()
		LinkedHashMap<String,Object> chunks=pPistonChunksFLD[ckey]
		if(chunks && (Integer)chunks.count){
			Boolean ok; ok=true
			StringBuilder sb=new StringBuilder()
			Integer i; i=iZ
			Integer count=(Integer)chunks.count
			while(i<count){
				String s=chunks["chunk:$i".toString()]
				if(s){
					sb.append(s)
				}else{
					ok=false
					break
				}
				i++
			}
			String data=ok ? sb.toString() : sBLK
			state.remove("chunks")
			pPistonChunksFLD[ckey]=null
			mb()
			if(ok){
				//save the piston
				Map saved=api_intf_dashboard_piston_set_save(
						(String)chunks.id,
						data,
						((Map<String,String>)chunks).findAll{ it -> it.key.startsWith('chunk:') }
				)
				if(saved){
					if(saved.rtData){
						updateRunTimeData((Map)saved.rtData)
						saved.rtData=null
						clearCachedchildApps(wName)
					}
					result=[(sSTS): sSUCC] + saved
				}else{ result=[(sSTS): sERROR, (sERR): sERRUNK] }
			}else{ result=[(sSTS): sERROR, (sERR): sERRCHUNK] }
		}else{ result=[(sSTS): sERROR, (sERR): sERRCHUNK] }
	}else{ result=api_get_error_result(sERRTOK) }
	renderRes(result)
}

private common_pause_resume(Map params, String oper, String msg){
	Map result
	String wName=sAppId()
	if(verifySecurityToken(params)){
		def piston=findPiston(sMs(params,sID))
		if(piston){
			Map rtData
			if(oper!='resume')
				rtData=(Map)piston.pausePiston()
			else
				rtData=(Map)piston.resume()

			result=[:]+(Map)rtData.result
			updateRunTimeData(rtData)
			clearCachedchildApps(wName)
			runIn(21, broadcastPistonList)
			result[sSTS]=sSUCC
		}else result=api_get_error_result(sERRID)
	}else result=api_get_error_result(sERRTOK)
	debug "Dashboard: "+msg
	renderRes(result)
}

private api_intf_dashboard_piston_pause(){
	common_pause_resume((Map)params, 'pausePiston', 'Received pause a piston')
}

private api_intf_dashboard_piston_resume(){
	common_pause_resume((Map)params, 'resume', 'Received resume a piston')
}

private common_Simple(Map params, String msg, String oper, arg=null, Boolean clrC=false){
	Map result
	debug "Dashboard: "+msg
	String wName=sAppId()
	if(verifySecurityToken(params)){
		String pid=sMs(params,sID)
		def piston=findPiston(pid)
		if(piston){
			if(arg!=null)
				result=(Map)piston."${oper}"(arg)
			else
				result=(Map)piston."${oper}"()
			if(clrC){
				ptMeta(wName,pid,null)
				clearBaseResult(oper,wName)
			}
			result[sSTS]=sSUCC
		}else result=api_get_error_result(sERRID)
	}else result=api_get_error_result(sERRTOK)
	renderRes(result)
}

private api_intf_dashboard_piston_test(){
	Map p=(Map)params
	common_Simple(p, "Received test a piston", 'test', null, true)
}

private api_intf_dashboard_piston_tile(){
	Map p=(Map)params
	common_Simple(p, "Clicked a piston tile", 'clickTile', p.tile, false)
}

//path("/intf/dashboard/piston/set.bin"){action: [GET: "api_intf_dashboard_piston_set_bin"]}
// ?access_token=token&id=pid&bin=bin&token=idetoken
private api_intf_dashboard_piston_set_bin(){
	Map p=(Map)params
	common_Simple(p, "Received set piston bin", 'setBin', sMs(p,'bin'), true)
}

private api_intf_dashboard_piston_set_category(){
	Map p=(Map)params
	common_Simple(p, "Received set piston category", 'setCategory', p.category, true)
}

private api_intf_dashboard_piston_set_modified(){
	common_Simple((Map)params, "Received set piston modified time", 'updModified', wnow(), true)
}

private api_intf_dashboard_piston_logging(){
	Map p=(Map)params
	common_Simple(p, "Received set piston logging level", 'setLoggingLevel', sMs(p,'level'), true)
}

private api_intf_dashboard_piston_clear_logs(){
	common_Simple((Map)params, "Received clear piston logs", 'clearLogs', null, true)
}

private api_intf_dashboard_piston_delete(){
	Map result
	String wName=sAppId()
	debug "Dashboard: Request received to delete a piston"
	Map p=(Map)params
	if(verifySecurityToken(p)){
		String id=sMs(p,'id')
		def piston=findPiston(id)
		if(piston){
			ptMeta(wName,id,null)
			String schld=piston.id.toString()
			result=(Map)piston.deletePiston()
			app.deleteChildApp(piston.id)
//			p_executionFLD[wName][id]=null
//			p_executionFLD=p_executionFLD
			invalidatePistonCaches(wName, schld)
			result=[(sSTS): sSUCC]
			//cleanUp()
			//clearParentPistonCache("piston deleted")
			runIn(21, broadcastPistonList)
		}else{ result=api_get_error_result(sERRID) }
	}else{ result=api_get_error_result(sERRTOK) }
	renderRes(result)
}

private api_intf_dashboard_presence_create(){
	Map result
	Map p=(Map)params
	if(verifySecurityToken(p)){
		String dni=sMs(p,'dni')
		def sensor=(dni ? wgetChildDevices().find{ (String)it.getDeviceNetworkId()==dni } : null) ?: addChildDevice("ady624", handlePres(), dni ?: hashId("${wnow()}"), null, [label: sMs(p,'name')])
		if(sensor){
			sensor.label=sMs(p,'name')
			result=[
					(sSTS): sSUCC,
					deviceId: hashId(sensor.id)
			]
			refreshDevices()
		}else result=api_get_error_result("ERR_COULD_NOT_CREATE_DEVICE")
	}else result=api_get_error_result(sERRTOK)
	renderRes(result)
}

private api_intf_location_entered(){
	Map p=(Map)params
	String deviceId=sMs(p,'device')
	String dni=sMs(p,'dni')
	def device=wgetChildDevices().find{ ((String)it.getDeviceNetworkId()==dni) || (hashId(it.id)==deviceId) }
	if(device && p.place) device.processEvent([(sNM): 'entered', place: p.place, places: state.settings.places])
}

private api_intf_location_exited(){
	Map p=(Map)params
	String deviceId=sMs(p,'device')
	String dni=sMs(p,'dni')
	def device=wgetChildDevices().find{ ((String)it.getDeviceNetworkId()==dni) || (hashId(it.id)==deviceId) }
	if(device && p.place) device.processEvent([(sNM): 'exited', place: p.place, places: state.settings.places])
}

private api_intf_location_updated(){
	Map p=(Map)params
	String deviceId=sMs(p,'device')
	String dni=sMs(p,'dni')
	def device=wgetChildDevices().find{ ((String)it.getDeviceNetworkId()==dni) || (hashId(it.id)==deviceId) }
	Map location=p.location ? (LinkedHashMap) new JsonSlurper().parseText(sMs(p,'location')) : [(sERR): "Invalid data"]
	if(device) device.processEvent([(sNM): 'updated', location: location, places: state.settings.places])
}

private api_intf_variable_set(){
	Map result
	String meth="dashboard variable_set "
	debug meth+"Request received to set a variable"
	String meth1; meth1=sNL
	Map p=(Map)params
	if(verifySecurityToken(p)){
		String pid=sMs(p,'id')
		String name; name=sMs(p,'name')
		LinkedHashMap value=p.value ? (LinkedHashMap) new JsonSlurper().parseText(new String((sMs(p,sVAL)).decodeBase64(), sUTF8)) : null
		trace meth+"pid: $pid name: $name value: $value"
		Map<String,Map> globalVars
		Map<String,Object> localVars
		if(!pid){
			Boolean chgd; chgd=false
			String vln; vln=value ? (String)value[sN] : sNL
			if( (name && (Boolean)name.startsWith(sAT2)) || (vln && vln.startsWith(sAT2)) ){
				String vn; vn=sNL
				if(name && !value){
					// delete a global
					vn=name.substring(2)
					chgd=deleteGlobalVar(vn)
					meth1=meth+"DELETE HE global $vn "
					if(!chgd){
						warn meth1+"FAILED"
					}
					else trace meth1
					chgd=true
					//result=[(sNM): name, (sVAL): null, (sTYPE): null]
				}else if(value && value[sN]){
					vln=((String)value[sN]).substring(2)
					if(name=='null') name=sNL
					if(!name || name!=(String)value[sN] ){
						if(name){
							vn= name.substring(2)
							meth1=meth+"DELETE before update of HE global $vn "
							try{
								chgd= deleteGlobalVar(vn)
							}catch(ignored){
								meth1=meth+"DELETE not allowed HE global $vn "
							}
							if(!chgd) warn meth1+"FAILED"
							else trace meth1
						}
						// add a variable
						def vl; vl=value.v
						if(sMs(value,sT)==sTIME){
							Long aa=vl.toLong()
							// the browers is in local zone but internally HE is utc
							Integer mmtvl=mTZ().rawOffset
							if(eric()) debug "att is adjustment is $mmtvl"
							vl=vl - mmtvl
						}
						Map ta=fixHeGType(true, sMs(value,sT), vl, sNL)
						for(it in ta){
							String typ=(String)it.key
							vl=it.value
							meth1=meth+"CREATE HE global $vln ${sMs(value,sT)} ${typ} ${vl} "
							try{
								chgd=createGlobalVar(vln, vl, typ)
							}catch(ignored){
								meth1=meth+"CREATE not allowed HE global $vn "
							}
							if(!chgd) warn meth1+"FAILED"
							else trace meth1
						}
					}else{
						//update a variable
						def hg=getGlobalVar(vln)
						if(!hg) warn meth+"GET HE global failed $vln"
						else{
							def vl; vl=value.v
							if(vl){
								if(eric())debug "vl is ${myObj(vl)}"
								if(sMs(value,sT)==sTIME){
									Long aa="${vl}".toLong()
									if(eric())debug "aa is $aa"
									// the browser is in local zone but internally HE is utc
									if(vl instanceof Long){
										Integer mtvl=mTZ().getOffset(wnow())
										Integer mmtvl=mTZ().rawOffset
										if(eric()) debug "btt is adjustment is ${mmtvl} - ${mtvl}"
										vl=vl-mmtvl-mtvl
									}
									if(eric()) debug "found time $vl"
								}
								Map ta=fixHeGType(true, sMs(value,sT), vl, (String)hg.type)
								String typ
								for(it in ta){
									typ=(String)it.key
									vl=it.value
									chgd=false
									try{ chgd=setGlobalVar(vln, vl) }catch(ignored){}
									meth1=meth+"SET HE global $vln ${vl} "
									if(!chgd) warn meth1+"FAILED mismatch $vln ${hg.type} ${typ} ${value.t} ${vl}"
									else trace meth1
								}
							}else warn meth1+"no value"
						}
					}
				}
			}else{
				def am=gtAS(sVARS)
				globalVars= am? (Map<String,Map>)am : [:]
				if(name && !value){
					//deleting a variable
					globalVars.remove(name)
					result=[(sNM): name, (sVAL): null, (sTYPE): null]
					chgd=true
				}else if(value && value[sN]){
					if(!name || name!=vln ){
						//add a new variable
						if(name) globalVars.remove(name)
						name=vln
					}
					//update a variable
					if(name){
						globalVars[name]=[(sT): sMs(value,sT), (sV): value[sV]]
						result=[(sNM): name, (sVAL): value[sV], (sTYPE): sMs(value,sT)]
						chgd=true
					}
				}
				if(chgd){
					assignAS(sVARS,globalVars)
					clearGlobalPistonCache("dashboard set")
					//clearBaseResult('api_intf_variable_set')
					//noinspection GroovyVariableNotAssigned
					sendVariableEvent(result)
				}else trace meth+"SET webcore global FAILED $name"
			}
			if(chgd){
				// return all globals
				globalVars=(Map)gtAS(sVARS)
				globalVars=globalVars ?: [:]
				Map heV=AddHeGlobals(globalVars, meth)
				globalVars=globalVars+heV
				result=[(sSTS): sSUCC]+[globalVars: globalVars]
			}else result=[(sSTS): sERROR, (sERR): sERRUNK]
		}else{
			def piston=findPiston(pid)
			if(piston){
				localVars=(Map)piston.setLocalVariable(name, value?.v)
				//clearBaseResult('api_intf_variable_set')
				result=[(sSTS): sSUCC] + [(sID): pid, localVars: localVars]
			}else{ result=api_get_error_result(sERRID) }
		}
	}else{ result=api_get_error_result(sERRTOK) }
	clearBaseResult('set var')
	renderRes(result)
}



//@Field static final String sDTIME='datetime'
@Field static final String sTIME='time'
@Field static final String sDATE='date'
//@Field static final String sSTR='string'
//@Field static final String sINT='integer'
//@Field static final String sDEC='decimal'
//@Field static final String sDYN='dynamic'
//@Field static final String sBOOLN='boolean'
@Field static final String sDEV='device'

@Field static final Long lMSDAY=86400000L

private Long getMidnightTime(){
	return wtimeToday('00:00',mTZ()).getTime()
}




void resetFuelStreamList(){
	state.fuelStreams=[]
/*
	name=handleFuelS()
	fuelStreams=wgetChildApps().findAll{ it.name==name }.collect { it.label }
	state.fuelStreams=fuelStreams
*/
	state.remove("fuelStreams")
}

def findCreateFuel(Map req){
	String n=handleFuelS()
	def result; result=null

	// LTS can return multple streams
	if(req[sC] == 'LTS'){
		def lts = gtLTS()
		String[] s= sMs(req,sN).split('_')
		String sensorId= s[iZ]
		String attribute= s[i1]
		if(lts!=null && (Boolean)lts.isStorage(sensorId, attribute)){
			result= lts
		}

	}else{
		String streamName="${(req[sC] ?: sBLK)}||${req[sN]}"
		List allFuel=wgetChildApps().findAll{ (String)it.name==n }
		List l
		l=allFuel.findAll{ ((String)it.label)?.contains(streamName) }
		for (sa in l){
			String sl=(String)sa.label
			Integer ndx=sl.indexOf(' - ' )
			if(ndx >= iZ){
				String lbl=sl.substring(ndx + i3)
				if(lbl==streamName){
					result=sa
					break
				}
			}
		}
		l=null

		if(!result){
			Integer t0=allFuel.findAll{
				((String)it.label)?.contains(' - ') && ((String)it.label)?.contains('||') }.collect{
					((String)it.label).split(' - ')[0].toInteger() }.max()
			allFuel=null
			Integer id=(t0 ?: iZ) + i1
			try{
				result=addChildApp('ady624', n, "$id - $streamName")
				result.createStream([(sID): id, (sNM): req[sN], canister: req[sC] ?: sBLK])
			}
			catch(ignored){
				error "Please install the webCoRE Fuel Stream app for local Fuel Streams"
				return null
			}
		}
	}
	result
}

List<Map> readFuelStream(Map req){
	def result=findCreateFuel(req)
	if(result) return result.readFuelStream(req)
	return null
}

void writeFuelStream(Map req){
	def result=findCreateFuel(req)
	if(result)result.writeFuelStream(req)
}

void clearFuelStream(Map req){
	def result=findCreateFuel(req)
	if(result)result.clearFuelStream(req)

}

void writeToFuelStream(Map req){
	def result=findCreateFuel(req)
	if(result)result.updateFuelStream(req)
}

List listFuelStreams(Boolean includeLTS=true){
	List result
	result = []
	String n=handleFuelS()
	List chlds = wgetChildApps().findAll{ (String)it.name==n }
	chlds.each { it ->
		List a = (List)it.getFuelStreams(includeLTS)
		if(a) result+= a
	}
	return result
}

private api_intf_fuelstreams_list(){
	debug "Dashboard: Request received to list fuelstreams"
	List result
	result = listFuelStreams()
	/*
	result = []
	//if(verifySecurityToken((Map)params)){
	String n=handleFuelS()
	List chlds = wgetChildApps().findAll{ (String)it.name==n }
	//result=wgetChildApps().findAll{ (String)it.name==n }*.getFuelStreams()
	chlds.each { it ->
		Map a = it.getFuelStreams()
		if(a) result << a
	}
	*/

	Map res=["fuelStreams" : result]
	renderRes(res)
}

private api_intf_fuelstreams_get(){
	List result
	result=[]
	String id=(String)params.id
	debug "Dashboard: Request received to get fuelstream data $id"

	//if(verifySecurityToken((Map)params)){
	String n=handleFuelS()
	// TODO if LTS stream form, need to find proper LTS and pass stream id
	def stream
	if(id.isNumber()){
		stream=wgetChildApps().find {
			(String)it.name==n && ((String)it.label).contains('||') && ((String)it.label).startsWith("$id - ")
		}
	}else{
		stream = gtLTS()
	}
	if(stream)
		result=stream.listFuelStreamData(id)

	Map res=["points" : result]
	renderRes(res)
}


// may not need
Map quantParams(sensorId, String attribute){
	def lts = gtLTS()

	if(lts!=null){
		return (Map)lts.quantParams(sensorId, attribute)
	}else return null
}

Boolean ltsExists(){
	def lts = gtLTS()
	return (lts!=null)
}

// child graphs calls this
Boolean ltsAvailable(sensorId, String attribute){
	def lts = gtLTS()

	if(lts!=null){
		return (Boolean)lts.isStorage(sensorId, attribute)
	}
	return false
}

Boolean ltsQuant(sensorId, String attribute){
	def lts = gtLTS()

	if(lts!=null){
		return (Boolean)lts.isQuant(sensorId, attribute)
	}
	return false
}

Map openWeatherConfig(){ // used by graphs/fuel stream
	String weatherTyp= gtSetStr('weatherType') ?: sNL
	if( state.storAppOn && weatherTyp=='OpenWeatherMap'){
		return [
				latitude: gtSetStr('zipCode'),
				longitude: gtSetStr('zipCode1'),
				apiVer: gtSetB('apiVer'),
				apiKey: gtSetStr('apixuKey'),
				pollInterval: '30 Minutes',
				wunits: gtSetStr('wunits')?:'imperial'
		]
	}
	return null
}

Map getWData(){
	def storageApp=getStorageApp(true)
	Map t0
	t0=[:]
	if(storageApp){
		t0=storageApp.getWData()
	}
	return t0 ?: [:]
}

String getOpenWeatherData(){
	def childDevice = wgetChildDevice("OPEN_WEATHER${app.id}")
	if(!childDevice){
		doLog(sDBG,"Error: No Child Found")
		return sNL
	}
	return childDevice.getWeatherData()
}






private api_intf_settings_set(){
	Map result
	debug "Dashboard: Request received to set settings"
	Map p=(Map)params
	if(verifySecurityToken(p)){
		String pset=sMs(p,'settings')
		LinkedHashMap msettings=pset ? (LinkedHashMap) new JsonSlurper().parseText(new String(pset.decodeBase64(), sUTF8)) : null
		assignAS('settings',msettings)

		clearParentPistonCache("dashboard changed settings")
		clearBaseResult('settings change')

		testLifx()
		result=[(sSTS): sSUCC]
	}else{ result=api_get_error_result(sERRTOK) }
	renderRes(result)
}

private api_intf_dashboard_piston_evaluate(){
	Map result
	debug "Dashboard: Request received to evaluate an expression"
	Map p=(Map)params
	if(verifySecurityToken(p)){
		def piston=findPiston(sMs(p,'id'))
		if(piston){
			List<Map> vars; vars=null
			try{
				vars=(List<Map>) new JsonSlurper().parseText(new String((sMs(p,sV)).decodeBase64(), sUTF8))
			}catch(ignore){}
			LinkedHashMap expression=(LinkedHashMap) new JsonSlurper().parseText(new String((sMs(p,'expression')).decodeBase64(), sUTF8))
			Map msg=timer "Evaluating expression"
			result=[(sSTS): sSUCC, (sVAL): piston.proxyEvaluateExpression(null /* getRunTimeData()*/, expression, sMs(p,'dataType'), vars)]
			trace msg
		}else{ result=api_get_error_result(sERRID) }
	}else{ result=api_get_error_result(sERRTOK) }
	renderRes(result)
}

private api_intf_dashboard_piston_activity(){
	String s; s='piston activity'
	String msg; msg=s
	Map result
	Map p=(Map)params
	if(verifySecurityToken(p)){
		msg+=' security ok'
		String pistonId=sMs(p,'id')
		def piston=findPiston(pistonId)
		if(piston!=null){
			String wName=sAppId()
			String sess=sMs(p,'session') ?: 'default'
			Map t0=(Map)piston.activity(p.log)
			String jsonData= JsonOutput.toJson(t0)
			String rl=generateMD5_A(jsonData)
			String tok=sMs(p,'token')
			Long t=wnow()
			if(!lastPActivityFLD[wName] || !lastPActivityTOKFLD[wName] || !lastPActivityPIDFLD[wName] || !tlastPActivityFLD[wName]){
				clearLastPActivity(wName,sess)
			}
			if( !tlastPActivityFLD[wName][sess] || tlastPActivityFLD[wName][sess] < (t-11000L) ||
					!lastPActivityFLD[wName][sess] || rl!=lastPActivityFLD[wName][sess] ||
					!lastPActivityTOKFLD[wName][sess] || tok!=lastPActivityTOKFLD[wName][sess] ||
					!lastPActivityPIDFLD[wName][sess] || pistonId!=lastPActivityPIDFLD[wName][sess]){
				lastPActivityFLD[wName][sess]=rl
				lastPActivityTOKFLD[wName][sess]=tok
				lastPActivityPIDFLD[wName][sess]=pistonId
				lastPActivityPIDFLD= lastPActivityPIDFLD
				lastPActivityTOKFLD= lastPActivityTOKFLD
				lastPActivityFLD= lastPActivityFLD
				msg+=' updating cache'
				result=[(sSTS):sSUCC, activity: (t0 ?: [:]) + [globalVars: listAvailableVariables1()/*, mode: hashId(location.getCurrentMode().id), shm: location.currentState("alarmSystemStatus")?.value, hubs: location.getHubs().collect{ [id: hashId(it.id), (sNM): it.name, firmware: it.getFirmwareVersionString(), physical: it.getType().toString().contains('PHYSICAL'), powerSource: it.isBatteryInUse() ? 'battery' : 'mains' ]}*/]]
			}else{
				result=[(sSTS):sSUCC, activity: [:]]
				msg+=' using cache'
			}
			tlastPActivityFLD[wName][sess]=wnow()
			tlastPActivityFLD= tlastPActivityFLD
		}else{
			result=api_get_error_result(sERRID)
			msg+=' piston not found'
		}
	}else{
		msg+=' security NOT ok returning token error'
		result=api_get_error_result(sERRTOK)
	}
	if(getLogging()[sDBG]) checkResultSize(result, false, s)
	//debug "Dashboard: Activity request received $params " +msg
	renderRes(result)
}

def api_ifttt(){
	def data; data=[:]
	//def remoteAddr=isHubitat() ? "UNKNOWN" : request.getHeader("X-FORWARDED-FOR") ?: request.getRemoteAddr()
	def remoteAddr; remoteAddr=request.headers.'X-forwarded-for' ?: request.headers.Host
	if(remoteAddr==null)remoteAddr=request.'X-forwarded-for' ?: request.Host
	debug "Request received ifttt call IP $remoteAddr Referer: ${request.headers.Referer}"
//log.debug "params ${params}"
	Map p=(Map)params
	if(p){
		data.params=[:]
		for(param in p){
			if(!((String)param.key in ['access_token', 'theAccessToken', 'appId', 'action', 'controller'])){
				data[(String)param.key]=param.value
			}
		}
	}
	data=data + (request?.JSON ?: [:])
	data.remoteAddr=remoteAddr
	String eventName=sMs(p,'eventName')
	if(eventName){
		sendLocationEvent([(sNM): "ifttt.${eventName}", (sVAL): eventName, isStateChange: true, linkText: "IFTTT event", descriptionText: "${handle()} has received an IFTTT event: $eventName", (sDATA): data])
	}
	wrender( [ (sCONTENTT): 'text/html', (sDATA): "<!DOCTYPE html><html lang=\"en\">Received event $eventName.<body></body></html>" ])
}

def api_email(){
	def data=request?.JSON ?: [:]
	def from=data.from ?: sBLK
	def pistonId=params?.pistonId
	if(pistonId){
		sendLocationEvent([(sNM): "email.${pistonId}", (sVAL): pistonId, isStateChange: true, linkText: "Email event", descriptionText: "${handle()} has received an email from $from", (sDATA): data])
	}
	wrender( [ (sCONTENTT): 'text/plain', (sDATA): 'OK' ])
}

//path("/gforward/:pistonIdOrName"){action: [GET: "api_forward", POST: "api_forward"]}
//return "${t0.cp}/gforward?access_token=${t0.at}&id=${sAppId()}&path=${path}".toString()
// forward to a fuelstream child app
private api_forward(){
	Map data,result
	result=[:]
	data=[:]
	//def remoteAddr=isHubitat() ? "UNKNOWN" : request.getHeader("X-FORWARDED-FOR") ?: request.getRemoteAddr()
	def remoteAddr
	remoteAddr=request.headers.'X-forwarded-for' ?: request.headers.Host
	if(remoteAddr==null)remoteAddr=request.'X-forwarded-for' ?: request.Host
	if(remoteAddr==null)remoteAddr='just'
//log.debug "params ${params} request: ${request}"
	Map p=(Map)params
	if(p){
		for(param in p){
			if(!((String)param.key in ['access_token', 'pistonIdOrName'])){
				data[(String)param.key]=param.value
			}
		}
	}
	data=data+(request?.JSON ?: [:])
	data.remoteAddr=remoteAddr
	data.referer=request.headers.Referer
	String pistonIdOrName= sMs(p,'pistonIdOrName')
	String msg
	def graphChild= findPiston(pistonIdOrName,pistonIdOrName,handleFuelS())
	//private findPiston(String id, String nm=sNL, String n=handlePistn()){
	//private static String handleFuelS(){ return sWC+sFUELS }
	if(graphChild!=null){
		msg = "External forward for graph ${(String)graphChild.label} request from IP $remoteAddr".toString()
		debug "Dashboard or web request received to forward to graph from IP $remoteAddr Referer: ${request.headers.Referer} " + msg
		return graphChild.gforward(data.path)
	}else{
		result.result='ERROR'
		msg = "Fuel stream child not found for dashboard or web Request to forward to a graph $data from IP $remoteAddr $pistonIdOrName"
		error msg
	}
	result[sTMSTMP]=wnow()
	wrender( [ (sCONTENTT): sAPPJAVA, (sDATA): JsonOutput.toJson(result) ] )
}

private api_execute(){
	Map data,result
	result=[:]
	data=[:]
	//def remoteAddr=isHubitat() ? "UNKNOWN" : request.getHeader("X-FORWARDED-FOR") ?: request.getRemoteAddr()
	def remoteAddr
	remoteAddr=request.headers.'X-forwarded-for' ?: request.headers.Host
	if(remoteAddr==null)remoteAddr=request.'X-forwarded-for' ?: request.Host
	if(remoteAddr==null)remoteAddr='just'
//log.debug "params ${params} request: ${request}"
	Map p=(Map)params
	if(p){
		for(param in p){
			if(!((String)param.key in ['access_token', 'pistonIdOrName'])){
				data[(String)param.key]=param.value
			}
		}
	}
	data=data+(request?.JSON ?: [:])
	data.remoteAddr=remoteAddr
	data.referer=request.headers.Referer
	String pistonIdOrName=sMs(p,'pistonIdOrName')
	String msg
	def piston= findPiston(pistonIdOrName,pistonIdOrName)
	if(piston!=null){
		msg = "External piston execute ${(String)piston.label} request from IP $remoteAddr".toString()
		sendExecuteEvt(hashPID(piston.id),
				remoteAddr,
				"Execute event",
				msg,
				data)
		result.result='OK'
	}else{
		result.result='ERROR'
		msg = "Piston not found for dashboard or web Request to execute a piston from IP $remoteAddr $pistonIdOrName"
		error msg
	}
	debug "Dashboard or web request received to execute a piston from IP $remoteAddr Referer: ${request.headers.Referer} " + msg
	result[sTMSTMP]=wnow()
	wrender( [ (sCONTENTT): sAPPJAVA, (sDATA): JsonOutput.toJson(result) ] )
}

void sendExecuteEvt(String pistonId,val,String desc, String desc1,Map data){
	String json=JsonOutput.toJson(data)
	sendLocationEvent((sNM):pistonId,(sVAL):val,isStateChange:true,displayed:false,linkText:desc,descriptionText:desc1,data:json)
}

// get webcore global variables
private api_global(){
	def remoteAddr
	remoteAddr=request.headers.'X-forwarded-for' ?: request.headers.Host
	if(remoteAddr==null)remoteAddr=request.'X-forwarded-for' ?: request.Host
	if(remoteAddr==null)remoteAddr='just'
	Map p=(Map)params
	debug "web request received to get variable from IP $remoteAddr Referer: ${request.headers.Referer} | $p"
	Map result=[:]
	Boolean err; err=true
	String varName=sMs(p,'varName')
	if(varName && (Boolean)varName.startsWith(sAT) ){
		if((Boolean)varName.startsWith(sAT2)){
			String vn=varName.substring(i2)
			def hg=getGlobalVar(vn)
			if(hg){ // could return these as webcore types....this uses what is in HE
				result.val=hg.value
				result[sTYPE]=hg.type
				result[sNM]=vn
				result.desc='HE Hub variable'
				err=false
			}
		}else{
			def am=gtAS(sVARS)
			Map<String,Map> vars= am? (Map<String,Map>)am : [:]
			if(vars[varName]){
				result.val=vars[varName][sV]
				result[sTYPE]=vars[varName][sT]
				result[sNM]=varName
				result.desc='webCoRE global variable'
				err=false
			}
		}
		if(!err) result.result='OK'
	}
	if(err){
		result.result='ERROR'
		error "variable not found for web Request to get variable from IP $remoteAddr $varName"
	}
	Integer st= err ? 400 : 200
	result[sTMSTMP]=wnow()
	wrender( [ (sCONTENTT): sAPPJAVA, (sDATA): JsonOutput.toJson(result), (sSTS): st ] )
}

@Field volatile static Map<String,Long> lastRecoveredFLD= [:]
@Field static Map<String,String> verFLD= [:]
@Field static Map<String,String> HverFLD= [:]

void resetMemSt(String meth,String wName){
	assignAS(sHSMALRTS,[]) // reload or restart
	assignSt(sHSMALRTS,[])
	verFLD[wName]=sVER
	HverFLD[wName]=sHVER
	mb()
	clearParentPistonCache(meth)
}

@Field static final String sVC='ver check'
/** check if webCoRE version has been updated */
@CompileStatic
void verCheck(String wName){
	Boolean uuidChg= uidChgd()
	if(verFLD[wName]==sVER && HverFLD[wName]==sHVER && !uuidChg) return
	if(verFLD[wName]==sNL || HverFLD[wName]==sNL){
		if((String)gtSt('cV')==sVER && (String)gtSt('hV')==sHVER && !uuidChg){
			resetMemSt(sVC,wName)
			clearBaseResult(sVC,wName)
		}
	}
	if(verFLD[wName]!=sVER || HverFLD[wName]!=sHVER || uuidChg){
		info "webCoRE software Updated to "+sVER+" HE: "+sHVER
		resetMemSt(sVC,wName)
		updated()
	}
}

void recoveryHandler(){
	String wName=sAppId()
	verCheck(wName)

	Long t=wnow()
	Long lastRecovered
	lastRecovered=lastRecoveredFLD[wName]
	lastRecovered=lastRecovered ?: 0L
	Long recTime=900000L // 15 min in ms
	if(lastRecovered!=0L && (t-lastRecovered) < recTime) return
	lastRecoveredFLD[wName]=t
	Integer delay=Math.round(200.0D * Math.random()).toInteger() // seconds
	runIn(delay, finishRecovery)
}

@CompileStatic
void finishRecovery(){
	registerInstance(false)
	Long recTime=300000L	// 5 min in ms
	//String n=handlePistn()
	Long lnow=wnow()
	Long threshold= lnow-recTime
	String wName=sAppId()

	List<Map> fPs
	String a=sA
	String n=sN
	String m=sMETA
	fPs=presult(wName).findAll{ Map it ->
		Map meta=(Map)it[m]
		meta!=null && (Boolean)meta[a] && meta[n] && (Long)meta[n] < threshold
	}
	Integer i
	i= fPs.size()
	if(i){
		i=iZ
		Long delay=Math.round(2000.0D * Math.random()) // 2 sec
		for (Map piston in fPs){
			String pid=sMs(piston,sID)
			Map meta= (Map)piston[sMETA] // gtMeta(null, wName,myId)
			Long t=(Long)meta[n]
			if(t < threshold){
				if(i!=iZ) wpauseExecution(delay)
				i++
				String nm= sMs(piston,sNM)
				sendExecuteEvt(pid,'recovery',"Recovery event","Recovery event for piston ${nm}",null)
				warn "Piston ${nm} was sent a recovery signal because it was ${lnow - t}ms late"
			}
		}
	}
	fPs=null
}

/******************************************************************************/
/*** PRIVATE METHODS								***/
/******************************************************************************/

private void cleanUp(){
	try{
		for (item in ((Map<String,Object>)state).findAll{ (it.key.startsWith('sph') || it.key.contains('-') ) }){
			state.remove(item.key)
		}

		List data=['version','versionHE','chunks','hash','virtualDevices','updateDevices', 'hubitatQueryString', 'redirectContactBook',
				   'semaphore','pong','modules','globalVars','devices','migratedStorage','lastRecovered','lastReg','lastRegTry']
		for(String foo in data)state.remove(foo)
		app.removeSetting('hubitatQueryString')

		String wName=sAppId()
		String pid
		List<Map> t0=gtCachedchildApps(wName)
		for(Map it in t0){
			pid=hashId((String)it[sID])
			state.remove(pid)
			pid=sMs(it,'pid')
			state.remove(pid)
		}
		t0=null
		Map a=api_get_base_result()
	}catch(ignored){}
}

private getStorageApp(Boolean install=false){
	String n=handleStor()
	def storageApp
	storageApp=wgetChildApps().find{ (String)it.name==n }

	String n1=handleWeat()
	def weatDev
	weatDev=wgetChildDevices().find{ (String)it.name==n1 }

	if(storageApp!=null){

/*
// Hubitat does not use storage app for settings for performance reasons; Someone could have created it elsewhere in UI
		if(storageApp.getStorageSettings()!=null){ //migrate settings off of storage app
			storageApp.getStorageSettings().findAll { it.key.startsWith('dev:') }.each {
				app.updateSetting(it.key, [(sTYPE): 'capability', (sVAL): it.value.collect { it.id }])
			}
		}
		app.deleteChildApp(storageApp.id)
		return null
*/
	}

	String myN= appName()
	String label=myN+sSTOR
	String label1=myN+sWEAT
	if(storageApp!=null){
		if(label!=storageApp.label){
			storageApp.updateLabel(label)
		}
		if(storageApp!=null && weatDev!=null) return storageApp
	}

	if(install){
		if(storageApp==null){
			try{
				storageApp=addChildApp("ady624", n, label)
			}catch(ignored){
				error "Please install the webCoRE Storage App for \$weather to work"
				return null
			}
		}
		if(weatDev==null){
			try{
				weatDev=addChildDevice("ady624", n1, hashId("${wnow()}"), null, [label: label1])
			}catch(ignored){
//				error "Please install the webCoRE Weather Device for \$weather notification to work"
//				return null
			}
		}
	}
/*
	try{
		storageApp.initData(settings.collect{ it.key.startsWith('dev:') ? it : null }, settings.contacts)
		for (item in settings.collect{ it.key.startsWith('dev:') ? it : null }){
			if(item && item.key){
				//app.updateSetting(item.key, [(sTYPE): sTXT, (sVAL): null])
				app.clearSetting("${item.key}".toString())
			}
		}
		//app.updateSetting('contacts', [(sTYPE): sTXT, (sVAL): null])
		app.clearSetting('contacts')
	}catch(all){
	}
*/

	return storageApp
}

def getWeatDev(){
	String n=handleWeat()
	def weatDev=wgetChildDevices().find{ (String)it.name==n }
	return weatDev
}

private void cleanDashboardApp(){
	if(!gtSetB('enableDashNotifications')){
		String name=handle()+' Dashboard'
		String myN= appName()
		String label=myN+' (dashboard)'
		def dashboardApp
		dashboardApp=wgetChildApps().find{ (String)it.name==name }
		if(dashboardApp!=null){
			app.deleteChildApp(dashboardApp.id)
		}
	}
}

private getDashboardApp(Boolean install=false){
	if(!gtSetB('enableDashNotifications')) return null
	String name=handle()+' Dashboard'
	String myN= appName()
	String label=myN+' (dashboard)'
	def dashboardApp
	dashboardApp=wgetChildApps().find{ (String)it.name==name }
	if(dashboardApp!=null){
		if(label!=dashboardApp.label){
			dashboardApp.updateLabel(label)
		}
		return dashboardApp
	}
	try{
		dashboardApp=addChildApp("ady624", name, myN)
	}catch(ignored){
		return null
	}
	return dashboardApp
}

private String customApiServerUrl(String ipath){
	String path
	path= ipath ?: sBLK
	if(!path.startsWith(sDIV)){
		path=sDIV + path
	}
	if( !gtSetB('localHubUrl')){
		return apiServerUrl("$hubUID/apps/${app.id}".toString()) + path
	}
	return localApiServerUrl(sAppId()) + path
}

private Boolean isCustomEndpoint(){
	gtSetB('customEndpoints') && gtSetB('localHubUrl')
}

String getDashboardUrl(){
	if(!(String)state.endpoint) return sNL

	String aa= gtSetStr('customWebcoreInstanceUrl')
	if(gtSetB('customEndpoints') && aa){
		if(aa.endsWith(sDIV)) return aa
		else return aa + sDIV
	}else{
		return "https://dashboard.${domain()}/".toString()
	}
}

private String getDashboardInitUrl(Boolean reg=false){
	if(eric()) debug "getDashboardInitUrl: reg: $reg"
	String url=reg ? getDashboardRegistrationUrl() : getDashboardUrl()
	if(!url) return sNL
	String t0,regkey
	t0=url + (reg ? "register/" : "init/")
	if(isCustomEndpoint()){
		if(eric())debug "getDashboardInitUrl: isCustomEndpoint"
		//regkey=customApiServerUrl('/')
		//regkey=customApiServerUrl('/?access_token=' + state.accessToken)
		regkey=customApiServerUrl('/?access_token=' + (String)state.accessToken).bytes.encodeBase64()
		if(eric())debug "getDashboardInitUrl: t0 $t0"
		if(eric())debug "getDashboardInitUrl: regkey $regkey"
		t0= t0+regkey
	}else{
		if(eric())debug "getDashboardInitUrl: NOT isCustomEndpoint"
		regkey= apiServerUrl(sBLK)

//		log.debug "t0 $t0"
/*		String a =(
			regkey.replace('http://',sBLK).replace('https://', sBLK).replace('.api.smartthings.com', sBLK).replace(':443', sBLK).replace('/', sBLK) +
			(hubUID.toString() + sAppId()).replace("-", sBLK) + '/?access_token=' + (String)state.accessToken ) */
//		log.debug "regkey $a"
		t0=t0+( regkey.replace('http://',sBLK).replace('https://', sBLK).replace('.api.smartthings.com', sBLK).replace(':443', sBLK).replace(sDIV, sBLK) +
			(gtHubUID() + sAppId()).replace("-", sBLK) + '/?access_token=' + (String)state.accessToken ).bytes.encodeBase64()
	}
	if(eric())debug "getDashboardInitUrl result: $t0"
	return t0
}

private String getDashboardRegistrationUrl(){
	if((String)state.accessToken) updateEndpoint()
	if(!(String)state.endpoint) return sNL
	return "https://api.${domain()}/dashboard/".toString()
}

Map listAvailableDevices(Boolean raw=false, Boolean batch=true, Integer offset=iZ){
	Long time=wnow()
	def storageApp //=getStorageApp()
	Map result; result=[:]
	if(storageApp){
		result=storageApp.listAvailableDevices(raw, offset)
	}else{
		List myDevices
		myDevices=(List)((Map<String,Object>)settings).findAll{ it.key.startsWith("dev:") }.collect{ it.value }.flatten().sort{ it.getDisplayName() }
		List devices
		devices=(List)myDevices.unique{ it.id }
		if(raw){
			result=devices.collectEntries{ dev -> [(hashId(dev.id)): dev]}
		}else{
			Integer deviceCount=devices.size()
			result.devices=[:]
			if(devices){
				devices=devices[offset..-i1]
				Integer dsz=devices.size()
				result.complete=!devices.indexed().find{ Integer idx, dev ->
//				log.debug "Loaded device at ${idx} after ${now() - time}ms. Data size is ${result.toString().size()}"
					result.devices[hashId(dev.id)]=getDevDetails(dev, true)

					Boolean stop; stop=false
					String jsonData=JsonOutput.toJson(result)
					Integer responseLength=jsonData.getBytes(sUTF8).length
					if(batch && (responseLength > 50000 || wnow()-time > 4000) ) stop=true
					if(stop && idx < dsz-i1 ){
						result.nextOffset= offset+idx+i1
						return true
					}
					false
				}
			}else result.complete=true
			debug "Generated list of ${offset}-${offset + ((Map)result.devices).size()-i1} of ${deviceCount} devices in ${wnow() - time}ms. Data size is ${result.toString().size()}"
		}
		myDevices=null
		devices=null
	}
	if(raw || (Boolean)result.complete){
		String n=handlePres()
		List presenceDevices
		presenceDevices=wgetChildDevices().findAll{ (String)it.name==n }
		if(presenceDevices && presenceDevices.size()){
			if(raw){
				result << presenceDevices.collectEntries{ dev -> [(hashId(dev.id)): dev]}
			}else{
				result.devices << presenceDevices.collectEntries{ dev -> [(hashId(dev.id)): dev]}.collectEntries{ id, dev ->
					[ (id): getDevDetails(dev) ]
				}
			}
		}
		presenceDevices=null
	}
	return result
}

// additional device attributes that do not trigger
@Field static final String sDLRSTS='$status'
@Field static final String sLSTACTIVITY='lastActivityWC'
@Field static final String sROOMID='roomIdWC'
@Field static final String sROOMNM='roomNameWC'

Map getDevDetails(dev, Boolean addtransform=false){
	Map<String,Map> overrides=commandOverrides()
	String dnm=dev.getDisplayName()
	List<Map> newCL; newCL=[]
	List cmdL; cmdL=dev.getSupportedCommands()
	// List dev.getCapabilities()
	//if(eric()) error("DEVICE $dnm")
	//if(eric()) warn("COMMANDS $cmdL")
	cmdL=cmdL.unique{ (String)it.getName() }
	Boolean b
	for(cmd in cmdL){
		Map mycmd=[:]
		String cnm=(String)cmd.getName()
		mycmd[sN]=cnm
		List myargs=(List)cmd.getArguments()
		List<Map> prms = cmd.getParameters()
		//if(eric()) warn("$cnm arguments: $myargs")
		//if(eric()) warn("$cnm Parameters: $prms")
		if(myargs){
			Boolean ok; ok=false
			List<Map> myL; myL=[]
			if(prms){
				ok=true
				Integer i
				for(i=iZ; i<prms.size();i++){
					Map myD; myD=[:]
					if(ok && prms[i] && prms[i][sTYPE]){
						String nm1; nm1= sMs(prms[i],sNM)
						if(nm1){
							if(nm1[iN1]=='*'){
								nm1=nm1[iZ..iN2]
								myD[sM]=i1 // mandatory
							}
							myD[sN]= nm1
						}
						if(prms[i][sTYPE]) myD[sT]= sMs(prms[i],sTYPE).toUpperCase()
						if(prms[i][sDESC]) myD[sH]=prms[i][sDESC]
						if(prms[i].constraints) myD[sC]=prms[i].constraints
						b=myL.push([:]+myD)
					}else ok=false
				}
			}
			mycmd[sP]= ok && myL ? myL : myargs
		}
		b=newCL.push(mycmd)
		if(addtransform){
			String an=transformCommand(cmd,overrides,dnm)
			if(an){
				mycmd[sN]=an
				b=newCL.push(mycmd)
			}
		}
	}
	//if(eric()) warn("PROCESSED COMMANDS $newCL")

	newCL= newCL.unique{ (String)it[sN] }

	Map allCmds= gtAllCommands() // built in commands

	for (Map cmd in newCL){
		String cmdName=cmd[sN]
		if(allCmds.containsKey(cmdName)){
			/* if (eric()){
				info("allCmds has device command $cmdName")
				trace("found in allCmds ${allCmds[cmdName]}")
				trace("found in device $cmd")
			} */
		}else{
			cmd.cm=true // custom
			if((List)cmd[sP]){
				List typs; typs=[]
				Integer i; i=iZ
				for(item in (List)cmd[sP]){
					Boolean bad; bad=false
					if(item instanceof String){
						if(item) b=typs.push(item.toUpperCase())
						else bad=true
					}else{
						Map mitem=(Map)item
						String t= mitem ? ((String)mitem[sT] ?: sNL) : sNL
						if(t){
							mitem[sT]= t.toUpperCase()
							typs[i]=mitem
						}else bad=true
					}
					if(bad && (getLogging()[sDBG] || eric())) debug("Device $dnm has strange command $cmdName with commands $cmd has nulls")
					i++
				}
				cmd[sP]=typs
			}
			//if(eric()) warn("adding custom marker to $cmdName $cmd")
		}
	}

	List attrs= ((List)dev.getSupportedAttributes()).unique{ (String)it.name }.collect{
		[ (sN): (String)it.name, (sT): it.getDataType(), (sO): it.getValues() ]
	}
	//attrs.push([(sN):sDLRSTS,(sT):sSTR,(sO):null])
	attrs.push([(sN):sLSTACTIVITY,(sT):sDTIME,(sO):null])
	attrs.push([(sN):sROOMID,(sT):sINT,(sO):null])
	attrs.push([(sN):sROOMNM,(sT):sSTR,(sO):null])
	Map res=[
		(sN): dnm,
		cn: dev.getCapabilities()*.name,
		(sA): attrs,
		/*((List)dev.getSupportedAttributes()).unique{ (String)it.name }.collect{
			//Map x=[
			[
				(sN): (String)it.name,
				(sT): it.getDataType(),
				(sO): it.getValues()
			]
//			try { // removed from UI in 9/2019
//				x.v= dev.currentValue(x.n)
//			} catch(ignored){}
//			x
		}, */
		/*(sC): dev.getSupportedCommands().unique{ transform ? transformCommand(it, overrides) : it.getName() }.collect{[
				(sN): transform ? transformCommand(it, overrides) : it.getName(),
				(sP): it.getArguments()
		]} */
		(sC): newCL.unique{ (String)it.n }
	]
	//if(eric1())debug "getDevDetails transform: $addtransform result: $res"
	return res
}

/*
 Not implemented zwave poller control:
 To add devices to the poll list:
 sendLocationEvent((sNM): "startZwavePoll", (sVAL): devList)

 To remove devices from the poll list:
 sendLocationEvent((sNM): "stopZwavePoll", (sVAL): devList)

 Z-Wave Poller only supports Generic Z-Wave Dimmer and Generic Z-Wave Switch. It won't work with other drivers, as there is a handshake with the driver.

 You can determine if Z-Wave Poller is installed with this
 isAppInstalled("hubitat", "Z-Wave Poller", "SYSTEM")
*/

private String transformCommand(command, Map<String,Map> overrides, String dvn){
	String nm=(String)command.getName()
	Map.Entry override=overrides.find{ (String)it.value.c==nm }
//	Map override=overrides[(String)command.getName()]
	if(override){
		String mcommand=(String)override.value.r
		def args= command.getArguments()?.toString()
		if(override.value.s.toString()==args){
			if(eric())debug "transformCommand device $dvn cmd: $nm -> $mcommand override: $override commandargs: $args"
			return mcommand
		}
	}
	return sNL
}

private void setPowerSource(String powerSource, Boolean atomic=true){
	if(state.powerSource==powerSource) return
	assignAS('powerSource',powerSource)
	sendLocationEvent([(sNM): 'powerSource', (sVAL): powerSource, isStateChange: true, linkText: "webCoRE power source event", descriptionText: handle()+" has detected a new power source: "+powerSource])
}

private Map AddHeGlobals(Map<String,Map> globalVars, String meth){
	Map<String,Map> res=[:]
	def heV=getAllGlobalVars()
	//if(eric()) trace meth+" ALL HE globals $heV"
	String nm,typ
	Map ta
	def vl
	heV?.each{
		nm=sAT2+(String)it.key
		ta=fixHeGType(false, (String)it.value.type, it.value.value, sNL)
		for(iit in ta){
			typ=(String)iit.key
			vl=iit.value
		}
		res[nm]=[(sT):typ,(sV): vl]
	}
	return res
}

// children use this to get variables (they cache)
Map listAvailableVariables(){
	Map myV=(Map)gtAS(sVARS)
	return listAV(myV, 'list variables')
}

// ide uses this
private Map listAvailableVariables1(){
	Map myV=(Map)gtSt(sVARS)
	return listAV(myV, 'list variables1')
}

private Map listAV(Map my, String meth){
	Map<String,Map> myV
	myV=my ?: [:]
	//'@@'
	Map heV=AddHeGlobals(myV, meth)
	myV=myV+heV
	return (myV ?: [:]).sort{ (String)it.key }
}

Map getGStore(){
	Map myS=(Map)gtAS(sSTORE)
	return (myS ?: [:]).sort{ (String)it.key }
}

List getPushDev(){
	return (settings.pushDevice ?: [])
}

@Field static final String sSECTOKENS='securityTokens'
@Field static volatile LinkedHashMap<String,Long> securityTokensFLD=null

private void initTokens(){
	debug "Dashboard: Initializing security tokens"
	securityTokensFLD=new LinkedHashMap<String,Long>()
	assignAS(sSECTOKENS,[:])
}

@CompileStatic
private Boolean verifySecurityToken(Map params){
	String tokenId=sMs(params,'token')
	if(!tokenId) return false
	LinkedHashMap<String,Long> tokens=securityTokensFLD
	if(tokens==null){
		tokens=(LinkedHashMap<String,Long>)gtSt(sSECTOKENS)
		if(!tokens) return false
		securityTokensFLD=tokens
	}
	Long threshold=wnow()
	Boolean modified=false
	//remove all expired tokens
	for(token in tokens.findAll{ (Long)it.value < threshold }){
		tokens.remove((String)token.key)
		modified=true
	}
	if(modified){
		securityTokensFLD=tokens
		assignAS(sSECTOKENS,tokens)
	}
	Long token=tokens[tokenId]
	if(!token){
		error "Dashboard: Authentication failed - token not found or expired for ${tokenId}"
		return false
	}
	return token >= wnow()
}

private String createSecurityToken(){
	trace "Dashboard: Generating new security token after a successful PIN authentication"
	String token=UUID.randomUUID().toString()
	LinkedHashMap<String,Long> tokens=securityTokensFLD
	if(tokens==null){
		Map a=(Map)gtAS(sSECTOKENS)
		tokens=(a ?: [:]) as LinkedHashMap<String,Long>
	}
	Long mexpiry; mexpiry=0L
	String eo=gtSetStr('expiry').toLowerCase().replace("every ", sBLK).replace("(recommended)", sBLK).replace("(not recommended)", sBLK).trim()
	switch(eo){
		case "hour": mexpiry=3600L; break
		case "day": mexpiry=86400L; break
		case "week": mexpiry=604800L; break
		case "month": mexpiry=2592000L; break
		case "three months": mexpiry=7776000L; break
		case "never": mexpiry=3110400000L; break //never means 100 years, okay?
	}
	tokens[token]=Math.round(wnow() + (mexpiry * 1000.0D))
	securityTokensFLD=tokens
	assignAS(sSECTOKENS,tokens)
	return token
}

private void ping(){
	sendLocationEvent( [(sNM): handle(), (sVAL): 'ping', isStateChange: true, displayed: false, linkText: "${handle()} ping reply", descriptionText: "${handle()} has received a ping reply and is replying with a pong", (sDATA): [(sID): getInstanceSid(), (sNM): appName()]] )
}

private void startDashboard(){
	//debug "startDashboard"
	def dashboardApp=getDashboardApp()
	if(!dashboardApp) return //false
	Map t0=listAvailableDevices(true)
	dashboardApp.start(t0.collect{ it.value }, getInstanceSid())
	if((String)state.dashboard!=sACT){
		assignAS('dashboard',sACT)
	}
}

private void stopDashboard(){
	//debug "stopDashboard"
	def dashboardApp=getDashboardApp()
	if(!dashboardApp) return //false
	dashboardApp.stop()
	if((String)state.dashboard!=sINACT) assignAS('dashboard',sINACT)
}

private String accountSid(){
	Boolean stprp= (Boolean)gtSt('properSID')
	Boolean useNew=stprp!=null ? stprp : true
	String t='-A'
	String accountStr
	accountStr= gtHubUID() + (useNew ? t : sNL)
	if(acctANDloc()) accountStr= gtSetStr('acctID')
	//if(eric()) debug "instance acct: $accountStr"
	return hashId(accountStr)
}

@Field static Map<String,String> locFLD= [:]
@Field static Map<String,Boolean> acctlocFLD= [:]

private Boolean acctANDloc(){
	String wName=sAppId()
	Boolean t; t=acctlocFLD[wName]
	if(t==null){
		t= (gtSetStr('acctID') && gtSetStr('locID'))
		acctlocFLD[wName]=t
	}
	return t
}

@Field static final String sML='-L'

private String locationSid(){
	String wName=sAppId()
	String t; t=locFLD[wName]
	if(t==sNL){
		//todo this is ambiguious
		if(acctANDloc()) t= gtSetStr('acctID') + gtSetStr('locID') + sML
		else{
			Boolean stprp= (Boolean)gtSt('properSID')
			Boolean useNew=stprp!=null ? stprp : true
			t= (useNew ? gtHubUID()+gtLname() : ((Long)location.id).toString()) + sML
		}
		//if(eric()) debug "instance location: $t"
		t= hashId(t)
		locFLD[wName]=t
	}
	return t
}

private String getInstanceSid(){
	Boolean stprp= (Boolean)gtSt('properSID')
	Boolean useNew=stprp!=null ? stprp : true
	String hsh=sAppId()
	String t='-I'
	String instStr=useNew ? gtHubUID()+hsh+t : hsh
	//if(eric()) debug "instance ID: $instStr"
	return hashId(instStr)
}

private void testLifx(){
	String token=state.settings?.lifx_token
	if(!token) return
	testLifx1(true)
	runIn(4, testLifx1)
}

void testLifx1(Boolean first=false){
	String token=state.settings?.lifx_token
	if(!token) return
	Map requestParams= [
		uri:  "https://api.lifx.com",
		path: "/v1/scenes",
		headers: [ "Authorization": "Bearer ${token}" ],
		requestContentType: sAPPJSON,
		timeout:20

	]
	if(first) asynchttpGet('lifxHandler', requestParams, [request: 'scenes'])
	else{
		requestParams.path= "/v1/lights/all"
		asynchttpGet('lifxHandler', requestParams, [request: 'lights'])
	}
}

@Field volatile static Map<String,Long> lastRegFLD= [:]
@Field volatile static Map<String,Long> lastRegTryFLD= [:]

private void registerInstance(Boolean force=true){
	String wName=sAppId()
	Long lnow=wnow()
	if((Boolean)state.installed && gtSetB('agreement')){
		if(!force){
			Long lastReg; lastReg=lastRegFLD[wName]
			lastReg=lastReg ?: 0L
			if(lastReg && (lnow - lastReg < 129600000L)) return // 36 hr in ms

			Long lastRegTry; lastRegTry=lastRegTryFLD[wName]
			lastRegTry=lastRegTry ?: 0L
			if(lastRegTry!=0L && (lnow - lastRegTry < 1800000L)) return // 30 min in ms
		}
		if((String)state.accessToken) updateEndpoint()
		lastRegTryFLD[wName]=lnow
		String accountId=accountSid()
		String locationId=locationSid()

		String instanceId=getInstanceSid()
		String endpoint=(String)state.endpointCloud
		String region=endpoint.contains('graph-eu') ? 'eu' : 'us'
		String name=handlePistn()
		List<Map> pistons
		pistons=wgetChildApps().findAll{ (String)it.name==name }.collect{
			String pid=hashPID(it.id)
			Map meta; meta=gtMeta(it,wName,pid)
			[ (sID): pid, (sA): meta?.a ]
		}
		List lpa,lpd
		lpa=pistons.findAll{ it.a }.collect{ it.id }
		Integer pa=lpa.size()
		lpd=pistons.findAll{ !it.a }.collect{ it.id }
		Integer pd=pistons.size() - pa
		pistons=null

		Map params=[
			uri: "https://api-${region}-${instanceId[i32]}.webcore.co:9247".toString(),
			path: '/instance/register',
			headers: ['ST' : instanceId],
			body: [
				(sA): accountId,
				(sL): locationId,
				(sI): instanceId,
				e: endpoint,
				(sV): sVER,
				hv: sHVER,
				(sR): region,
				pa: pa,
				lpa: lpa.join(','),
				pd: pd,
				lpd: lpd.join(',')
			],
			gzipBody: true,
			timeout:20
		]
		lpa=null
		lpd=null
		if(eric()) debug "registering instance: params: $params"
		params << [contentType: sAPPJSON, requestContentType: sAPPJSON]
		// requestContentType: gzip, Header Content-Encoding: gzip;   Accept-Encoding: 'gzip, deflate'
		asynchttpPut('myDone', params, [bbb:0])
		// https://community.hubitat.com/t/asynchttppost-support-content-encoding/108611/3
		//if()
	}
}

void myDone(resp, data){
	String endpoint=(String)state.endpointCloud
	String region=endpoint.contains('graph-eu') ? 'eu' : 'us'
	String instanceId=getInstanceSid()
	String s = "register resp: ${resp?.status} using api-${region}-${instanceId[i32]}.webcore.co:9247"
	if(eric())debug s
	if(resp?.status==200){
		String wName=sAppId()
		lastRegFLD[wName]=wnow()
	}else{
		error s
	}
}

/******************************************************************************/
/***																		***/
/*** PUBLIC METHODS															***/
/***																		***/
/******************************************************************************/
Boolean isInstalled(){
	return (Boolean)state.installed==true
}

String generatePistonName(){
	List apps=wgetChildApps()
	Set<String> used=new HashSet<String>()
	for(mapp in apps){ used << ((String)mapp.label ?: (String)mapp.name) }
	Integer i=i1
	String bname=handlePistn()+' #'
	while(used.contains(bname+i.toString())) i++
	return bname+i.toString()
}

void refreshDevices(){
	assignSt(sDEVVER,wnow().toString())
	assignAS(sDEVVER,(String)gtSt(sDEVVER))
	clearParentPistonCache("refreshDevices") // force virtual device to update
	clearBaseResult('refreshDevices')
	testLifx()
}

static String getWikiUrl(){
	return "https://wiki.${domain()}/".toString()
}

private String mem(Boolean showBytes=true){
	Integer bytes=state.toString().length()
	return Math.round(100.0D * (bytes/ 100000.0D)) + "%${showBytes ? " ($bytes bytes)" : sBLK}"
}

private gtLTS(){ wgetChildAppByLabel("webCoRE Long Term Storage") }



//wrappers
private String gtHubUID(){ return hubUID.toString() }
private Boolean isHubitat(){ return hubUID!=null }

private getHub(){
	return ((List)location.getHubs()).find{ (String)it.getType()=='PHYSICAL' }
}

private List<Map> gtHubs(){
	List a= (List)location.getHubs()
	return a.collect{ it ->
		Long id=it.getId()
		[
				(sID): id,
				fw: it.getFirmwareVersionString(),
				physical: ((String)it.getType()).contains('PHYSICAL'),
				powerSource: it.isBatteryInUse() ? 'battery' : 'mains'
		]
	}
}
/*
private Map getHubitatVersion(){
	return ((List)location.getHubs()).collectEntries{ [(it.id.toString()): it.getFirmwareVersionString()] }
} */

private static TimeZone mTZ(){ return TimeZone.getDefault() }
private gtLocation(){ return location }
private String gtLtScale(){ return (String)location.getTemperatureScale() }
private String gtLname(){ return (String)location.getName() }
private String gtLzip(){ return (String)location.zipCode }
private String gtLlat(){ return ((BigDecimal)location.latitude).toString() }
private String gtLlong(){ return ((BigDecimal)location.longitude).toString() }
private String gtLhsmStatus(){ return (String)location.hsmStatus }
private Map gtCurrentMode(){
	def a=location.getCurrentMode()
	if(a)return [(sID):(Long)a.getId(),(sNM): (String)a.getName()]
	return null
}
private List<Map> gtModes(){
	List modes= (List)location.getModes()
	return modes.collect{ [(sID): (Long)it.getId(), (sNM): (String)it.getName()] }
}

private gtSetting(String nm){ return settings.get(nm) }
private Boolean gtSetB(String nm){ return (Boolean)settings[nm] }
private String gtSetStr(String nm){ return (String)settings[nm] }

private Boolean gtStB(String nm){ return (Boolean)state[nm] }
private gtSt(String nm){ return state.get(nm) }
private gtAS(String nm){ return atomicState.get(nm) }
private void assignSt(String nm,v){ state.put(nm,v) }
private void assignAS(String nm,v){ atomicState.put(nm,v) }
private Date wtoDateTime(String s){ return (Date)toDateTime(s) }
private Date wtimeToday(String str,TimeZone tz){ return (Date)timeToday(str,tz) }
Long wnow(){ return (Long)now() }
List wgetChildApps(){ return (List)getChildApps() }
def wgetChildDevice(String d){ return getChildDevice(d) }
List wgetChildDevices(){ return (List)getChildDevices() }
private wgetChildAppByLabel(String n){ getChildAppByLabel(n) }

private Map renderRes(Map result){
	//debug "wrender: params: ${params} "
	wrender( [ (sCONTENTT): sAPPJAVA, (sDATA): (String)params.callback+'('+JsonOutput.toJson(result)+')' ] )
}

@Field static final String sAE='Accept-Encoding'
@Field static final String sCE='Content-Encoding'
@Field static final String sGZIP='gzip'
private Map wrender(Map options=[:]){
	//debug "wrender: options:: ${options} "
	//debug "request: ${request} "
	/*
	Map h=(Map)request?.headers
	if(h && sMs(h,sAE)?.contains(sGZIP)){
//		debug "will accept gzip"
		String s=sMs(options,sDATA)
		Integer sz=s?.length()
		if(sz>256){
			try{
				String a= string2gzip(s)
				Integer nsz=a.size()
				if(eric1())debug "options.data is $sz after compression $nsz saving ${Math.round((1.0D-(nsz/sz))*1000.0D)/10.0D}%"
//				options[sDATA]=a
//				options[sCE]=sGZIP
			}catch(ignored){}
		}
	}
	 */
	//if()
	// https://community.hubitat.com/t/asynchttppost-support-content-encoding/108611/3
	render(options + [gzipContent: true])
}

static String string2gzip(String s){
	ByteArrayOutputStream baos = new ByteArrayOutputStream()
	GZIPOutputStream zipStream = new GZIPOutputStream(baos)
	zipStream.write(s.getBytes(sUTF8))
	zipStream.close()
	byte[] result = baos.toByteArray()
	baos.close()
	return result.encodeBase64()
}

@Field static final String sURT='updateRunTimeData'
@Field static final String sGVCACHE='gvCache'
@Field static final String sGVSTOREC='gvStoreCache'
@Field static final String sVARS='vars'
@Field static final String sSTORE='store'
@Field static final String sAT='@'
@Field static final String sAT2='@@'
@Field static final String sCTGRY='category'
@Field static final String sSTATS='stats'
@Field static final String sBIN='bin'
@Field static final String sMODFD='modified'
@Field static final String sTMSTMP='timestamp'
@Field static final String sNSCH='nextSchedule'
@Field static final String sPIS='piston'

@Field static final Double d1=1.0D
@Field static final String sRELAY='pCallupdateeRunTimeData'

@CompileStatic
private Long elapseT(Long t,Long n=wnow()){ return Math.round(d1*n-t) }

@Field volatile static Map<String,Map<String,Map>> p_executionFLD=[:]

/**
 * wrapper to gather global piston execution statistics; calls updateRunTimeData
 * @param data
 */
@CompileStatic
void pCallupdateRunTimeData(Map data){
	if(!data || !data[sID]) return
	Long start= (Long)data[sTMSTMP] ?:wnow()

	String id=(String)data[sID]
	String wName=sAppId()

	Boolean didw=getTheLock(sRELAY)
	if(p_executionFLD[wName]==null){ p_executionFLD[wName]=(Map)[:]; p_executionFLD=p_executionFLD }

	Map record = p_executionFLD[wName][id]!=null ? (Map)p_executionFLD[wName][id] : [:]

	List runs; runs = (List)record.execs!=null ? (List)record.execs : []
	runs << [s: start, e: wnow()]
	if(runs.size() > 200) runs = runs.drop(20)

	Long cnt; cnt= record.cnt!=null ? (Long)record.cnt : 0L
	cnt +=1L

	Long tot; tot= record.tot!=null ? (Long)record.tot : 0L
	tot += elapseT(start)

	record.cnt = cnt
	record.execs = runs // add
	record.tot = tot

	p_executionFLD[wName][id]= record
	p_executionFLD=p_executionFLD
	releaseTheLock(sRELAY)

	updateRunTimeData(data,wName,id)
}

/**
 * called after piston execution/state change to update global variables, and piston state for IDE, will call clearBaseResult
 * @param data - map of updated piston data
 * @param wNi - optional instance id
 * @param idi - optional piston id
 */
@CompileStatic
void updateRunTimeData(Map data, String wNi=sNL, String idi=sNL){
	if(!data || !data[sID]) return
	List<Map> variableEvents=[]
	if(data[sGVCACHE]!=null){
		Boolean didw=getTheLock(sURT)

		def am=gtAS(sVARS)
		Map<String,Map> vars= am? (Map<String,Map>)am : [:]
		Boolean mdfd; mdfd=false
		for(Map.Entry<String,Map>var in ((Map<String,Map>)data[sGVCACHE]) ){
			String k=(String)var.key
			if(k!=sNL && k.startsWith(sAT) && vars[k]){
				def val=var.value[sV]
				def oval=vars[k][sV]
				if(val!=oval){
					Boolean a=variableEvents.push([(sNM): k, oldValue: oval, (sVAL): val, (sTYPE): var.value.t])
					vars[k][sV]=val
					mdfd=true
				}
			}
		}
		if(mdfd)assignAS(sVARS,vars)
		releaseTheLock(sURT)
	}
	if(data[sGVSTOREC]!=null){
		Boolean didw=getTheLock(sURT)

		def am=gtAS(sSTORE)
		Map<String,Object> store= am? (Map<String,Object>)am : [:]
		Boolean mdfd; mdfd=false
		for(var in (Map<String,Object>)data[sGVSTOREC]){
			String k=(String)var.key
			if(var.value==null) store.remove(k)
			else store[k]=var.value
			mdfd=true
		}
		if(mdfd)assignAS(sSTORE,store)
		releaseTheLock(sURT)
	}

	// update piston metadata cache; cache what IDE wants and in IDE format
	Map st=[:]+(Map)data.state
	st.remove('old') //remove the old state as we don't need it
	Map piston=[ //gtMeta?
		(sA): (Boolean)data[sACT],
		(sC): data[sCTGRY],
		(sT): data[sTMSTMP] ?:wnow(), //last run
		(sM): data[sMODFD],
		(sB): data[sBIN],
		(sN): (Long)((Map)data[sSTATS])[sNSCH],
		(sZ): sMs((Map)data[sPIS],sZ), //description
		(sS): st,
		heCached:(Boolean)data.Cached
	]
	//log.warn "data: $data piston: $piston old: ${pStateFLD[wName][id]}"
	String wName= wNi ?: sAppId()
	String id= idi ?: sMs(data,sID)
	ptMeta(wName,id,piston)
	clearBaseResult(sURT,wName)

	//broadcast variable change events
	for (Map variable in variableEvents){ // this notifies the other webCoRE master instances and children
		sendVariableEvent(variable)
	}
	//broadcast to dashboard
/*	if((String)state.dashboard==sACT){
		def dashboardApp=getDashboardApp()
		if(dashboardApp) dashboardApp.updatePiston(id, piston)
	} */
	verCheck(wName)
}

@Field volatile static Map<String,Map<String,Map>> pStateFLD=[:]

/** store cached piston metadata */
@CompileStatic
void ptMeta(String wName, String id, Map piston){
	if(wName && id){
		if(pStateFLD[wName]==null){ clearMeta(wName) }
		pStateFLD[wName][id]=piston
		pStateFLD=pStateFLD
	}else error "ptMeta no id"
}

@CompileStatic
static void clearMeta(String wName){
	pStateFLD[wName]=(Map)[:]
	pStateFLD=pStateFLD
	mb()
}

/*Map meta=[
	(sA):isAct(t0),
	(sC):t0[sCTGRY],
	(sT):(Long)t0[sLEXEC],
	(sM): (Long)t0[sMODFD],
	(sB): (String)t0[sBIN],
	(sN):(Long)t0[sNSCH],
	(sZ):(String)t0.pistonZ,
	(sS):st,
	heCached:(Boolean)t0.Cached ?: false
] */
/**
 * get piston meta data (from cached, or from piston if missing)
 */
Map gtMeta(ichld, String wName, String pid){
	Map meta; meta= null
	if(wName && pid){
		if(pStateFLD[wName]==null){ clearMeta(wName) }
		meta= pStateFLD[wName][pid]
		if(meta==null){
			def chld= ichld ?: findPiston(pid)
			if(chld) meta= (Map)chld.curPState()
			else error "gtMeta no child"
			if(meta) ptMeta(wName, pid, meta)
		}
	}else error "gtMeta no id"
	return meta
}

/** child call made from its own uninstalled() when removed by a means other than the
 *  dashboard delete API (e.g. removed directly from the Hubitat Apps list), so the parent's
 *  cached piston list, metadata, and dashboard base result don't keep serving the deleted piston */
void pistonUninstalled(id){
	invalidatePistonCaches(sAppId(), id?.toString(), 'piston uninstalled')
	runIn(21, broadcastPistonList)
}

/** child call to pause a piston */
Boolean pausePiston(String pistonId,String src){
	def piston=findPiston(pistonId,pistonId)
	if(piston){
		Map rtData=piston.pausePiston()
		updateRunTimeData(rtData)
		String wName=sAppId()
		clearCachedchildApps(wName)
		runIn(21, broadcastPistonList)
		return true
	}
	return false
}

/** child call to resume a piston */
Boolean resumePiston(String pistonId,String src){
	def piston=findPiston(pistonId,pistonId)
	if(piston){
		Map rtData=piston.resume()
		updateRunTimeData(rtData)
		String wName=sAppId()
		clearCachedchildApps(wName)
		runIn(21, broadcastPistonList)
		return true
	}
	return false
}

/** child call to find out if a piston is paused */
Boolean isPisPaused(String pistonId){
	def piston=findPiston(pistonId,pistonId)
	Map meta; meta=null
	if(piston){
		String wName=sAppId()
		String pid=hashPID(piston.id)
		meta=gtMeta(piston,wName,pid)
		if(meta && !((Boolean)meta[sA])) return true
	}
	if(!piston || !meta){
		//if(eric1()) debug "isPisPaused no piston $pistonId or metadata"
		return (Boolean)null
	}
	return false
}

/** child call to have parent execute a piston */
Boolean executePiston(String pistonId, Map data, String src){
	def piston=findPiston(pistonId,pistonId)
	if(piston){
		piston.execute(data, src)
		return true
	}
	return false
}

private String appName(){ return (String)app.label ?: (String)app.name }

private void sendVariableEvent(Map variable, Boolean onlyChildren=false){
	String myId=getInstanceSid()
	String myLabel=appName()
	String varN=sMs(variable,sNM)
	if(varN.startsWith(sAT2)) return // TODO ERS
	Map theEvent=[
		(sVAL): varN, isStateChange: true, displayed: false,
		(sDATA): [(sID): myId, (sNM): myLabel, event: sVARIABLE, (sVARIABLE): variable]
	]
// This notifies other webCoRE master instances of super change
/*	if( !onlyChildren && varN.startsWith(sAT2) ){
		String str=handle()+" Super global variable ${varN} changed".toString()
		sendLocationEvent(theEvent + [
			(sNM): (sAT2 + handle()),
			linkText: str, descriptionText: str,
		])
	}*/
// this notifies my children
	String str=handle()+" global variable ${varN} changed".toString()
	sendLocationEvent(theEvent + [
		(sNM): myId + ".${varN}",
		linkText: str, descriptionText: str,
		])
}

@Field volatile static Map<String,Long> lastBroadCastFLD= [:]
void broadcastPistonList(Boolean frc=false){
	String wName=sAppId()
	Long lnow=wnow()
	if(!frc){
		Long lastbcast; lastbcast=lastBroadCastFLD[wName]
		lastbcast=lastbcast ?: 0L
		if(lastbcast && (lnow - lastbcast < 20000L)) return // 20 sec in ms
	}
	lastBroadCastFLD[wName]=lnow
	List t = gtCachedchildApps(wName).collect{ Map it ->
		[ (sID): it.pid, (sN): it.nlabel, (sA): it.label ]
	}
	Map data = [
			(sID): getInstanceSid(),
			(sNM): appName(),
			pistons: t
	]

	String ds = JsonOutput.toJson(data)

	sendLocationEvent(
		[
			(sNM): handle(),
			(sVAL): 'pistonList',
			isStateChange: true,
			displayed: false,
			(sDATA): ds
		])
	trace "broadcastPistonList sent (${t.size()})"
}

private void wrunInMillis(Long t,String m,Map d){ runInMillis(t,m,d) }

def webCoREHandler(event){
	String eN=(String)event.name
	def eV=event.value
	info "received event ${eN} with value $eV"
// receive notification of super Global change
	if(!event || (!eN.startsWith(handle()) && !eN.endsWith(handle()) )) return
	def data=event.jsonData ?: null
//log.error "GOT EVENT WITH DATA $data"
/*	if(data && data.variable && ((String)data.event==sVARIABLE) && eV && eV.startsWith(sAT2)){
		Map variable=data.variable
		String vType=(String)variable.type ?: sDYN
		String vN=(String)variable.name
		def vV=variable.value
		if(vN){
			String t='updateGlobal'
			Boolean didw=getTheLock(t)

			Map<String,Map> vars=(Map<String,Map>)atomicState.vars
			vars=vars ?: [:]
			Map oldVar= vars[vN] ?: [(sT):sBLK, (sV):sBLK]
			if(((String)oldVar.t!=vType) || (oldVar.v!=vV)){ // only notify if it is a change for us.
				if(vV==null){
					vars.remove(vN)
				}else{
					vars[vN]=[(sT): vType, (sV): vV]
				}
				atomicState.vars=vars
				releaseTheLock(t)
				clearGlobalPistonCache("variable event")
// notify my child instances
				if(vV!=null) sendVariableEvent([(sNM): vN, (sVAL): vV, (sTYPE): vType], true)
			}else releaseTheLock(t) // no change
		}else warn "no variable name $data"
		return
	} */
	switch (eV){
		case 'poll':
			Long delay=Math.round(2000.0D * Math.random())
			wrunInMillis(delay,'broadcastPistonList',[:])
			//wpauseExecution(delay)
			//broadcastPistonList()
			break
/*		case 'ping':
		if(data && data.id && data.name && (data.id!=getInstanceSid())){
			sendLocationEvent( [(sNM): handle(), (sVAL): 'pong', isStateChange: true, displayed: false, linkText: "${handle()} ping reply", descriptionText: "${handle()} has received a ping reply and is replying with a pong", (sDATA): [id: getInstanceSid(), (sNM): app.label]] )
		}else{
			break
		}
			//fall through to pong
		case 'pong':
		/*if(data && data.id && data.name && (data.id!=getInstanceSid())){
			def pong=atomicState.pong ?: [:]
			pong[data.id]=data.name
			atomicState.pong=pong
		}*/
	}
}

def instanceRegistrationHandler(response, callbackData){
}

def hubUpdatedHandler(evt){
	if(evt.jsonData && (evt.jsonData.hubType=='PHYSICAL') && evt.jsonData.data && evt.jsonData.data.batteryInUse){
		setPowerSource(evt.jsonData.data.batteryInUse ? 'battery' : 'mains')
	}
}

def summaryHandler(evt){
	//log.error "$evt.name >>> ${evt.jsonData}"
}

def newIncidentHandler(evt){
	//log.error "$evt.name >>> ${evt.jsonData}"
}

@Field static final String sHSMALRTS='hsmAlerts'
@Field static final String sADDHSMEVT='addHsmEvent'

void addHsmEvent(evt){
	String evNm= (String)evt.name
	String evV=evt.value.toString()
	String evDesc=(String)evt.descriptionText
	String nevDesc= normalizeString(evDesc)
	if(eric())log.debug "received event: name: $evNm, value: $evV, Desc:$evDesc desc1: $nevDesc json >> ${evt.jsonData}"

	Boolean didw=getTheLock(sADDHSMEVT)

	def a; a=gtAS(sHSMALRTS)
	Map alert; alert=null
	List<Map> alerts; alerts= a? (List<Map>)a : []

	if(evNm in ['hsmAlert','hsmRule','hsmRules']){
		String s= evNm == 'hsmAlert' ? 'HSM Alert: ' : sBLK
		String title=s+ evV + (evV=='rule' ? ', '+evDesc : sBLK)
		String src=s+ evV
		String msg= evNm == 'hsmAlert' ? 'HSM '+evV+' Alert' : sNL

		alert=[
				(sDATE):((Date)evt.date).getTime(),
				(sNM): evNm,
				(sV):evt.value,
				des:evDesc,
				(sTIT): title,
				message: msg,
				args: evt.data,
				sourceType: src,
				//d: evt.data
		]
		Boolean aa=alerts.push(alert)
		assignAS(sHSMALRTS,alerts)

		releaseTheLock(sADDHSMEVT)

		clearParentPistonCache("hsmAlerts changed")
		clearBaseResult('hsmAlertHandler')
	}else
		releaseTheLock(sADDHSMEVT)

	if(alerts) a=getIncidents() // cause trimming
}

def hsmRuleHandler(evt){ // hsmRule event
	addHsmEvent(evt)
	// gets cancelRuleALerts value, with description as name of rule
}

def hsmRulesHandler(evt){ // hsmRules event
	addHsmEvent(evt)
	// gets armedRule value, with description as name of rule
	// gets disarmedRule value, with description name of rule
}

def hsmHandler(evt){ // hsmStatus event
	state.hsmStatus=evt.value
	addHsmEvent(evt)
	// gets allDisarmed value
	// gets disarmed value (leave rule alone?)
}

def hsmAlertHandler(evt){ // hsmAlert event
	// get value of rule to say rule fired?
	// get value of water, temperature
	addHsmEvent(evt)
}

//incidents: isHubitat() ? [] : location.activeIncidents.collect{[date: it.date.time, (sTIT): it.getTitle(), message: it.getMessage(), args: it.getMessageArgs(), sourceType: it.getSourceType()]}.findAll{ it.date >= incidentThreshold },
// this should search the db from hsmAlert events? - they are not there

@Field static final String sGTINCIDENTS='getIncidents'
@Field static volatile Map<String,List<Map>> incidentsFLD=[:]

private List<Map> getIncidents(Boolean haveLock=false){
	String wName=sAppId()
	List<Map> cached=incidentsFLD[wName]
	if(cached!=null) return cached

	if(!haveLock) Boolean didw=getTheLock(sGTINCIDENTS)

	List<Map> alerts,newAlerts,new2Alerts,new3Alerts,new4Alerts
	def a; a=gtAS(sHSMALRTS)
	alerts= a? (List<Map>)a : []
	Integer osz; osz=alerts.size()
	if(osz==iZ){
		incidentsFLD[wName]=[]; incidentsFLD=incidentsFLD
		if(!haveLock) releaseTheLock(sGTINCIDENTS)
		return []
	}

	String locStat=(String)location.hsmStatus
	if(locStat==sALLDISARM){ alerts=[]; state.remove(sHSMALRTS) }

/*
	(sNM): evNm,
	(sV):evt.value,
	des:evDesc,
	ndes:nevDesc,
	String evNm = (String)evt.name
	String evV=evt.value.toString()
	String evDesc=(String)evt.descriptionText
	String nevDesc= normalizeString(evDesc)
*/
	Long incidentThreshold=Math.round(wnow() - 604800000.0D) // 1 week
	newAlerts=alerts.findAll{
		(Long)it[sDATE] >= incidentThreshold }.sort{ (Long)it[sDATE] }

	new2Alerts=[]
	Map<String,List<Map>> rules
	rules=[:]
	String intrusion='intrusion'
	Boolean chgd; chgd=false

	Boolean b
	for(Map myE in newAlerts){
		String evNm= sMs(myE,sNM)
		String v= sMs(myE,sV)
		String desc= sMs(myE,'des')
		if(evNm=='hsmAlert'){
			if(v.contains(intrusion)){
				if(locStat!=sDISARMD) b= new2Alerts.push(myE)
				else chgd=true
			}else if(v in ['water','smoke','arming']){
				b=new2Alerts.push(myE)
			}else if(v == sCANCEL){
				new2Alerts=[]
				rules= [:]
				chgd=true
			}else if(v=='rule'){
				String ruleKey= stripH(desc)
				if(ruleKey){
					List<Map> trule= rules[ruleKey] ?: []
					b= trule.push(myE)
					rules[ruleKey]= trule
				}else chgd=true
			}else { log.warn "unknown $evNm $v"; chgd=true }
		}else if(evNm=='hsmRule' && v==sCANRULEA){
			String ruleKey= stripH(desc)
			if(ruleKey) rules[ruleKey]=[]
			chgd=true
		}else if(evNm=='hsmRules' && v=='disarmedRule'){
			String ruleKey= stripH(desc)
			if(ruleKey) rules[ruleKey]=[]
			chgd=true
		}else if(evNm=='hsmRules' && v=='armedRule'){ // ignored
			chgd=true
		}else { log.warn "unknown $evNm $v"; chgd=true }
	}
	new3Alerts= []+new2Alerts
	for(l in rules.keySet()){
		new3Alerts += rules[l]
	}
	new4Alerts = new3Alerts.sort { (Long)it[sDATE] }

	Integer nsz=new4Alerts.size()
	if(osz!=nsz || chgd){
		assignAS(sHSMALRTS,[]+new4Alerts)
		incidentsFLD[wName]=null; incidentsFLD=incidentsFLD

		if(!haveLock) releaseTheLock(sGTINCIDENTS)

		clearParentPistonCache("hsmAlerts changed")
		clearBaseResult('hsmAlertHandler')
	}else{
		incidentsFLD[wName]=new4Alerts; incidentsFLD=incidentsFLD
		if(!haveLock) releaseTheLock(sGTINCIDENTS)
	}
	return new4Alerts
}

static String stripH(String str){
	if(!str) return sBLK
	Integer first; first = str.indexOf('<span')
	String res; res = str[iZ..(first>iZ ? first-i1 : str.length()-i1)]
	first = str.indexOf('CancelAlert')
	String res1; res1 = res[iZ..(first>iZ ? first-i1 : res.length()-i1)]
	res = res1.trim()
	return res
}

void modeHandler(evt){
	clearBaseResult('mode handler')
}

void startHandler(evt){
	debug "startHandler called"
	String wName=sAppId()
	lastRecoveredFLD[wName]=0L
	lastRegFLD[wName]=0L
	lastRegTryFLD[wName]=0L
	runIn(20, startWork)
}

void startWork(){
	checkWeather()
	recoveryHandler()
	broadcastPistonList(true)
}

def lifxHandler(response, Map cbkData){
	if((response.status==200)){
		def data= response.data instanceof List ? response.data : new JsonSlurper().parseText((String)response.data)
		//cbkData= cbkData instanceof Map ? cbkData : (LinkedHashMap) new JsonSlurper().parseText(cbkData)
		Boolean fnd; fnd=false
		if(data instanceof List){
			state.lifx= state.lifx ?: [:]
			switch (sMs(cbkData,'request')){
			case 'scenes':
				state.lifx.scenes= data.collectEntries{[(it.uuid): it.name]}
				fnd=true
				break
			case 'lights':
				state.lifx.lights= data.collectEntries{[(it.id): it.label]}
				state.lifx.groups= data.collectEntries{[(it.group.id): it.group.name]}
				state.lifx.locations= data.collectEntries{[(it.location.id): it.location.name]}
				fnd=true
				break
			}
			if(fnd) debug "got lifx data $cbkData.request"
		}
	}
}

/******************************************************************************/
/*** SECURITY METHODS														***/
/******************************************************************************/
@Field volatile static Map<String,Map> theHashMapVFLD=[:]

/*private String temperatureUnit(){
	return "°" + location.temperatureScale
}*/

/******************************************************************************/
/*** DEBUG FUNCTIONS														***/
/******************************************************************************/

@Field static volatile Map<String,Map<String,Boolean>> loggingFLD=[:]

private Map<String,Boolean> getLogging(){
	String wName=sAppId()
	Map<String,Boolean> res=(Map<String,Boolean>)loggingFLD[wName]
	if(res) return res
	String lgging=gtSetStr('logging') ?: sNL
	res=[
		(sERR): true,
		(sWARN): true,
		(sINFO): (lgging!=sNONE && lgging!=sNL),
		(sTRC): (lgging==sMEDIUM) || (lgging==sFULL),
		(sDBG): (lgging==sFULL)
	]
	loggingFLD[wName]=res
	loggingFLD=loggingFLD
	return res
}

private Map log(message, Integer shift=iN2, err=null, String cmd=sNL){
	Long lnow=wnow()
	if(cmd==sTIMER){
		return [(sM): message, (sT): lnow, (sS): shift, (sE): err]
	}
	String myMsg; myMsg=sNL
	def merr; merr=err
	if(message instanceof Map){
		//shift=(Integer)message.s
		merr=message[sE]
		myMsg=sMs((Map)message,sM) + " (${lnow - (Long)message[sT]}ms)"
	}else myMsg=message
	String mcmd=cmd ? cmd : sDBG
	Map<String,Boolean> myLog=getLogging()
	if(mcmd!=sERR && mcmd!=sWARN){
		if(!myLog[sINFO] && mcmd==sINFO) return [:]
		if(!myLog[sTRC] && mcmd==sTRC) return [:]
		if(!myLog[sDBG] && mcmd==sDBG) return [:]
	}
	String prefix=sBLK
/*	Boolean debugging=false
	if(debugging){
		//mode is
		// 0 - initialize level, level set to 1
		// 1 - start of routine, level up
		// -1 - end of routine, level down
		// anything else - nothing happens
		Integer maxLevel=4
		Integer level=state.debugLevel ? state.debugLevel : 0
		Integer levelDelta=iZ
		prefix="║"
		String pad="░"
		switch (shift){
			case iZ:
				level=iZ
				prefix=sBLK
				break
			case i1:
				level += i1
				prefix="╚"
				pad="═"
				break
			case -1:
				levelDelta=-(level > iZ ? i1 : iZ)
				pad="═"
				prefix="╔"
			break
		}

		if(level > iZ){
			prefix=prefix.padLeft(level, "║").padRight(maxLevel, pad)
		}

		level += levelDelta
		state.debugLevel=level

		prefix += " "
	}*/

	if(merr){
		myMsg += sSPC+merr.toString()
	}
	doLog(mcmd,prefix+myMsg)
	return [:]
}

void doLog(String mcmd, String msg){
	String clr
	switch(mcmd){
		case sINFO:
			clr= '#0299b1'
			break
		case sTRC:
			clr= sCLRGRY
			break
		case sDBG:
			clr= 'purple'
			break
		case sWARN:
			clr= sCLRORG
			break
		case sERROR:
		default:
			clr= sCLRRED
	}
	String myMsg= msg.replaceAll(sLTH, '&lt;').replaceAll(sGTH, '&gt;')
	log."$mcmd" span(myMsg,clr)
}

private void info(String message, Integer shift=iN2, err=null)	{ Map a=log message, shift, err, sINFO }
private void debug(String message, Integer shift=iN2, err=null)	{ Map a=log message, shift, err, sDBG }
private void trace(message, Integer shift=iN2, err=null)	{ Map a=log message, shift, err, sTRC }
private void warn(String message, Integer shift=iN2, err=null)	{ Map a=log message, shift, err, sWARN }
private void error(String message, Integer shift=iN2, err=null)	{ Map a=log message, shift, err, sERR }
//error "object: ${describeObject(e)}",r9
private Map timer(String message, Integer shift=iN2, err=null)	{ log message, shift, err, sTIMER }

@Field static final String sLTH='<'
@Field static final String sGTH='>'
@Field static final String sCLR4D9	= '#2784D9'
@Field static final String sCLRRED	= 'red'
@Field static final String sCLRRED2	= '#cc2d3b'
@Field static final String sCLRGRY	= 'gray'
@Field static final String sCLRGRN	= 'green'
@Field static final String sCLRGRN2	= '#43d843'
@Field static final String sCLRORG	= 'orange'
@Field static final String sLINEBR	= '<br>'
@CompileStatic
static String span(String str,String clr=sNL,String sz=sNL,Boolean bld=false,Boolean br=false){
	return str ? "<span ${(clr || sz || bld) ? "style='${clr ? "color: ${clr};":sBLK}${sz ? "font-size: ${sz};":sBLK}${bld ? "font-weight: bold;":sBLK}'":sBLK}>${str}</span>${br ? sLINEBR:sBLK}": sBLK
}

/******************************************************************************/
/*** DATABASE																***/
/******************************************************************************/

@Field static final String sSTR='string'
@Field static final String sINT='integer'
@Field static final String sDEC='decimal'
@Field static final String sENUM='enum'
@Field static final String sDYN='dynamic'
@Field static final String sDUR='duration'
@Field static final String sDURATION='Duration'
@Field static final String sBOOLN='boolean'
@Field static final String sLVL='level'
@Field static final String sON='on'
@Field static final String sOFF='off'
@Field static final String sOPEN='open'
@Field static final String sCLOSE='close'
@Field static final String sCLOSED='closed'
@Field static final String sCLEAR='clear'
@Field static final String sDETECTED='detected'
@Field static final String sDTIME='datetime'
@Field static final String sVOLUME='Volume'
@Field static final String sSWITCH='switch'
@Field static final String sCOLOR='color'
@Field static final String sCCOLOR='Color'
@Field static final String sTOGON='toggle-on'
@Field static final String sTHERM='thermostatMode'
@Field static final String sTHERFM='thermostatFanMode'
@Field static final String sCLOCK='clock'
@Field static final String sONLYIFSWIS='Only if switch is...'
@Field static final String sIFALREADY=' if switch is already {v}'
@Field static final String sATVOL=' at volume {v}'
@Field static final String sNUMFLASH='Number of flashes'
@Field static final String sACT='active'
@Field static final String sINACT='inactive'

	//n=name
	//d=friendly devices name
	//a=default attribute
	//c=accepted commands
	//m=momentary
	//s=number of subdevices
	//i=subdevice index in event data
@Field final Map<String,Map> capabilitiesFLD=[
	accelerationSensor	: [ (sN): "Acceleration Sensor",	(sD): "acceleration sensors",		(sA): "acceleration",								],
	actuator			: [ (sN): "Actuator",				(sD): "actuators",																	],
	airQuality			: [ (sN): "Air Quality Sensor",		(sD): "air quality sensors",		(sA): "airQualityIndex",							],
	alarm				: [ (sN): "Alarm",					(sD): "alarms and sirens",			(sA): "alarm",		(sC): [sOFF, "strobe", "siren", "both"],			],
	audioNotification	: [ (sN): "Audio Notification",		(sD): "audio notification devices",				(sC): ["playText", "playTextAndResume", "playTextAndRestore", "playTrack", "playTrackAndResume", "playTrackAndRestore"],			],
	audioVolume			: [ (sN): "Audio Volume",			(sD): "audio volume devices",		(sA): "volume",		(sC): ["mute", "setVolume", "unmute", "volumeDown", "volumeUp"],			],
	battery				: [ (sN): "Battery",				(sD): "battery powered devices",	(sA): "battery",									],
	beacon				: [ (sN): "Beacon",					(sD): "beacons",					(sA): "presence",									],
	bulb				: [ (sN): "Bulb",					(sD): "bulbs",						(sA): sSWITCH,		(sC): [sOFF, sON],					],
	carbonDioxideMeasurement	: [ (sN): "Carbon Dioxide Measurement",	(sD): "carbon dioxide sensors",		(sA): "carbonDioxide",								],
	carbonMonoxideDetector		: [ (sN): "Carbon Monoxide Detector",	(sD): "carbon monoxide detectors",		(sA): "carbonMonoxide",								],
	changeLevel			: [ (sN): "Change Level",			(sD): "level adjustment devices",					(sC): ["startLevelChange", "stopLevelChange"],		],
	chime				: [ (sN): "Chime",					(sD): "chime devices",				(sA): "status",		(sC): ["playSound", "stop"],				],
	colorControl		: [ (sN): "Color Control",			(sD): "adjustable color lights",	(sA): sCOLOR,		(sC): ["setColor", "setHue", "setSaturation"],		],
	colorMode			: [ (sN): "Color Mode",				(sD): "color mode devices",		(sA): "colorMode",									],
	colorTemperature	: [ (sN): "Color Temperature",		(sD): "adjustable white lights",	(sA): "colorTemperature",	(sC): ["setColorTemperature"],				],
	configuration		: [ (sN): "Configuration",			(sD): "configurable devices",					(sC): ["configure"],					],
	consumable			: [ (sN): "Consumable",				(sD): "consumables",				(sA): "consumableStatus",	(sC): ["setConsumableStatus"],				],
	contactSensor		: [ (sN): "Contact Sensor",			(sD): "contact sensors",			(sA): "contact",									],
	currentMeter		: [ (sN): "Current Meter",			(sD): "current meter sensors",		(sA): "amperage",								],
	doorControl			: [ (sN): "Door Control",			(sD): "automatic doors",			(sA): "door",		(sC): [sCLOSE, sOPEN],					],
	doubleTapableButton	: [ (sN): "Double Tappable Button",	(sD): "double tappable buttons",		(sA): "doubleTapped",	(sM): true,	(sC): ["doubleTap"], /* (sS): "numberOfButtons,numButtons", i: "buttonNumber",*/	],
	energyMeter			: [ (sN): "Energy Meter",			(sD): "energy meters",				(sA): "energy",									],
	estimatedTimeOfArrival	: [ (sN): "Estimated Time of Arrival",	(sD): "moving devices (ETA)",		(sA): "eta",									],
	fanControl			: [ (sN): "Fan Control",			(sD): "fan devices",				(sA): "speed",		(sC): ["setSpeed", "cycleSpeed"],					],
	filterStatus		: [ (sN): "Filter Status",			(sD): "filters",					(sA): "filterStatus",								],
//	flash				: [ (sN): "Flash",					(sD): "flashers",								(sC): ["flash"],					],
	garageDoorControl	: [ (sN): "Garage Door Control",	(sD): "automatic garage doors",	(sA): "door",		(sC): [sCLOSE, sOPEN],					],
	gasDetector			: [ (sN): "Gas Detector",			(sD): "gas detectors",				(sA): "naturalGas",							],
	healthCheck			: [ (sN): "HealthCheck",			(sD): "healthcheck devices",		(sA): "checkInterval",			(sC): ["ping"],		],
	holdableButton		: [ (sN): "Holdable Button",		(sD): "holdable buttons",			(sA): "held",		(sM): true,	(sC): ["hold"], /* (sS): "numberOfButtons,numButtons", i: "buttonNumber",*/		],
	illuminanceMeasurement	: [ (sN): "Illuminance Measurement",	(sD): "illuminance sensors",		(sA): "illuminance",										],
	imageCapture		: [ (sN): "Image Capture",			(sD): "cameras, imaging devices",	(sA): "image",		(sC): ["take"],						],
	indicator			: [ (sN): "Indicator",				(sD): "indicator devices",			(sA): "indicatorStatus",	(sC): ["indicatorNever", "indicatorWhenOn", "indicatorWhenOff"],		],
	levelPreset			: [ (sN): "Level Preset",			(sD): "adjustable levels",			(sA): "levelPreset",	(sC): ["presetLevel"],							],
	light				: [ (sN): "Light",					(sD): "lights",					(sA): sSWITCH,		(sC): [sOFF, sON],							],
	lightEffects		: [ (sN): "Light Effects",			(sD): "light effects",				(sA): "effectName",	(sC): ["setEffect", "setNextEffect", "setPreviousEffect"],			],
	liquidFlowRate		: [ (sN): "Liquid Flow Rate",		(sD): "liquid flow rates",			(sA): "rate",											],
//	locationMode		: [ (sN): "Mode",					(sD): "modes",						(sA): "mode",			],
	lock				: [ (sN): "Lock",					(sD): "electronic locks",			(sA): "lock",		(sC): ["lock", "unlock"],	/*s:"numberOfCodes,numCodes", i: "usedCode",*/	],
	lockCodes			: [ (sN): "Lock Codes",				(sD): "locks with lock codes",		(sA): "codeChanged",	(sC): ["deleteCode", "getCodes", "setCode", "setCodeLength"],		],
//	lockOnly			: [ (sN): "Lock Only",				(sD): "electronic locks (lock only)",	(sA): "lock",		(sC): ["lock"],								],
	mediaController		: [ (sN): "Media Controller",		(sD): "media controllers",			(sA): "currentActivity",	(sC): ["startActivity", "getAllActivities", "getCurrentActivity"],		],
	mediaInputSource	: [ (sN): "Media Input Source",		(sD): "media input sources",			(sA): "mediaInputSource",	(sC): ["setInputSource"],		],
	mediaTransport		: [ (sN): "Media Transport",		(sD): "media transport",			(sA): "transportStatus",	(sC): ["play", "pause", "stop"],		],
//	momentary			: [ (sN): "Momentary",				(sD): "momentary switches",					(sC): ["push"],								],
	momentary			: [ (sN): "Momentary",				(sD): "momentary switches",			(sM): true,	(sC): ["pushMomentary"],					],
	motionSensor		: [ (sN): "Motion Sensor",			(sD): "motion sensors",			(sA): "motion",											],
	musicPlayer			: [ (sN): "Music Player",			(sD): "music players",				(sA): "status",	(sC): ["mute", "nextTrack", "pause", "play", "playTrack", "previousTrack", "restoreTrack", "resumeTrack", "setLevel", "setTrack", "stop", "unmute"],		],
	notification		: [ (sN): "Notification",			(sD): "notification devices",					(sC): ["deviceNotification"],						],
	outlet				: [ (sN): "Outlet",					(sD): "lights",					(sA): sSWITCH,		(sC): [sOFF, sON],							],
	pHMeasurement		: [ (sN): "pH Measurement",			(sD): "pH sensors",				(sA): "pH",											],
	polling				: [ (sN): "Polling",				(sD): "pollable devices",						(sC): ["poll"],								],
	powerMeter			: [ (sN): "Power Meter",			(sD): "power meters",				(sA): "power",											],
	powerSource			: [ (sN): "Power Source",			(sD): "multisource powered devices",	(sA): "powerSource",										],
	presenceSensor		: [ (sN): "Presence Sensor",		(sD): "presence sensors",			(sA): "presence",											],
	pressureMeasurement	: [ (sN): "Pressure Measurement",	(sD): "pressure sensors",		(sA): "pressure",										],
	pushableButton		: [ (sN): "Pushable Button",		(sD): "pushable buttons",			(sA): "pushed",		(sM): true,	(sC): ["push"], /* (sS): "numberOfButtons,numButtons", i: "buttonNumber",*/		],
	refresh				: [ (sN): "Refresh",				(sD): "refreshable devices",					(sC): ["refresh"],								],
	relativeHumidityMeasurement	: [ (sN): "Relative Humidity Measurement",	(sD): "humidity sensors",			(sA): "humidity",											],
	relaySwitch			: [ (sN): "Relay Switch",			(sD): "relay switches",			(sA): sSWITCH,		(sC): [sOFF, sON],							],
	releasableButton	: [ (sN): "Releasable Button",		(sD): "releasable buttons",		(sA): "released",		(sM): true,	(sC): ["release"], /* (sS): "numberOfButtons,numButtons", i: "buttonNumber",*/			],
//	samsungTV			: [ (sN): "Samsung TV",		(sD): "Samsung TVs",			(sA): "switch",	(sC): ["mute", sOFF, sON, "setPictureMode", "setSoundMode", "setVolume", "showMessage", "unmute", "volumeDown", "volumeUp"],										],
	securityKeypad		: [ (sN): "Security Keypad",		(sD): "security keypads",			(sA): "securityKeypad",	(sC): ["armAway", "armHome", "deleteCode", "disarm", "getCodes", "setCode", "setCodeLength", "setEntryDelay", "setExitDelay"],										],
	sensor				: [ (sN): "Sensor",					(sD): "sensors",					(sA): "sensor",											],
	shockSensor			: [ (sN): "Shock Sensor",			(sD): "shock sensors",				(sA): "shock",											],
	signalStrength		: [ (sN): "Signal Strength",		(sD): "wireless devices",			(sA): "rssi",											],
	sleepSensor			: [ (sN): "Sleep Sensor",			(sD): "sleep sensors",				(sA): "sleeping",											],
	smokeDetector		: [ (sN): "Smoke Detector",			(sD): "smoke detectors",			(sA): "smoke",											],
	soundPressureLevel	: [ (sN): "Sound Pressure Level",	(sD): "sound pressure sensors",	(sA): "soundPressureLevel",									],
	soundSensor			: [ (sN): "Sound Sensor",			(sD): "sound sensors",				(sA): "sound",											],
	speechRecognition	: [ (sN): "Speech Recognition",		(sD): "speech recognition devices",	(sA): "phraseSpoken",				(sM): true,					],
	speechSynthesis		: [ (sN): "Speech Synthesis",		(sD): "speech synthesizers",					(sC): ["speak"],								],
	stepSensor			: [ (sN): "Step Sensor",			(sD): "step counters",				(sA): "steps",											],
	(sSWITCH)			: [ (sN): "Switch",					(sD): "switches",					(sA): sSWITCH,		(sC): [sOFF, sON],							],
	switchLevel			: [ (sN): "Switch Level",			(sD): "dimmers and dimmable lights",	(sA): sLVL,		(sC): ["setLevel"],							],
//	tv					: [ (sN): "TV",		(sD): "TVs",			(sA): "power",	(sC): ["channelDown", "channelUp", "volumeDown", "volumeUp"],										],
	tamperAlert			: [ (sN): "Tamper Alert",			(sD): "tamper sensors",			(sA): "tamper",											],
//	telnet				: [ (sN): "Telnet",					(sD): "telnet devices",		(sA): "networkStatus",		(sC): ["sendMsg"]				],
	temperatureMeasurement		: [ (sN): "Temperature Measurement",	(sD): "temperature sensors",		(sA): "temperature",										],
//	testCapability		: [ (sN): "Test Ability",				(sD): "test devices",		(				],
	thermostat			: [ (sN): "Thermostat",				(sD): "thermostats",			(sA): sTHERM,	(sC): ["auto", "cool", "eco", "emergencyHeat", "fanAuto", "fanCirculate", "fanOn", "heat", sOFF, "setCoolingSetpoint", "setHeatingSetpoint", /* "setSchedule",*/ "setThermostatFanMode", "setThermostatMode"],	],
	thermostatCoolingSetpoint	: [ (sN): "Thermostat Cooling Setpoint",	(sD): "thermostats (cooling)",		(sA): "coolingSetpoint",	(sC): ["setCoolingSetpoint"],						],
	thermostatFanMode	: [ (sN): "Thermostat Fan Mode",	(sD): "fans",					(sA): sTHERFM,	(sC): ["fanAuto", "fanCirculate", "fanOn", "setThermostatFanMode"],	],
	thermostatHeatingSetpoint	: [ (sN): "Thermostat Heating Setpoint",	(sD): "thermostats (heating)",		(sA): "heatingSetpoint",	(sC): ["setHeatingSetpoint"],						],
	thermostatMode		: [ (sN): "Thermostat Mode",		(sD): "thermostat modes",						(sA): sTHERM,	(sC): ["auto", "cool", "eco", "emergencyHeat", "heat", sOFF, "setThermostatMode"],	],
	thermostatOperatingState	: [ (sN): "Thermostat Operating State",		(sD): "thermostat operating states",		(sA): "thermostatOperatingState",									],
//	thermostatSchedule	: [ (sN): "Thermostat Schedule",							(sA): "schedule",									],
	thermostatSetpoint	: [ (sN): "Thermostat Setpoint",	(sD): "thermostat setpoints",					(sA): "thermostatSetpoint",									],
	threeAxis			: [ (sN): "Three Axis Sensor",		(sD): "three axis sensors",		(sA): "orientation",										],
	timedSession		: [ (sN): "Timed Session",			(sD): "timers",				(sA): "sessionStatus",	(sC): [sCANCEL, "pause", "setTimeRemaining", "start", "stop", ],		],
	tone				: [ (sN): "Tone",					(sD): "tone generators",						(sC): ["beep"],								],
	touchSensor			: [ (sN): "Touch Sensor",			(sD): "touch sensors",			(sA): "touch",  /* (sM): true */									],
	ultravioletIndex	: [ (sN): "Ultraviolet Index",		(sD): "ultraviolet sensors",		(sA): "ultravioletIndex",										],
	valve				: [ (sN): "Valve",					(sD): "valves",				(sA): "valve",		(sC): [sCLOSE, sOPEN],							],
//	variable			: [ (sN): "Variable",				(sD): "variables",				(sA): sVARIABLE,		(sC): ["setVariable"],							],
	videoCamera			: [ (sN): "Video Camera",			(sD): "cameras",				(sA): "camera",		(sC): ["flip", "mute", sOFF, sON, "unmute"],				],
	voltageMeasurement	: [ (sN): "Voltage Measurement",	(sD): "voltmeters",			(sA): "voltage",											],
	waterSensor			: [ (sN): "Water Sensor",			(sD): "water and leak sensors",		(sA): "water",											],
	windowBlind			: [ (sN): "Window Blind",			(sD): "automatic window blinds",		(sA): "windowBlind",	(sC): [sCLOSE, sOPEN, "setPosition", "startPositionChange", "stopPositionChange", "setTiltLevel"],					],
	windowShade			: [ (sN): "Window Shade",			(sD): "automatic window shades",		(sA): "windowShade",	(sC): [sCLOSE, sOPEN, "setPosition", "startPositionChange", "stopPositionChange"],					],
]

Map capabilities(){
	return capabilitiesFLD
}

Map getChildAttributes(){
	Map<String,Map> result=attributesFLD
	Map<String,Map> cleanResult=[:]
	Map defv=[(sN):sA]
	for(it in result){
		Map t0; t0=[:]
		//String hasI=it.value.i
		Boolean hasP=it.value.p
		String hasT=it.value.t
		Boolean hasM=it.value.m
		//if(hasI) t0=t0 + [(sI):hasI]
		if(hasP!=null) t0=t0 + [(sP):hasP] //physical
		if(hasT) t0=t0 + [(sT):hasT] // type
		if(hasM!=null) t0=t0 + [(sM):hasM] // momentary
		if(t0==[:]) t0=defv
		cleanResult[it.key.toString()]=t0
	}
	return cleanResult
}

@Field final Map<String,Map> attributesFLD=[
	acceleration		: [ (sN): "acceleration",		(sT): sENUM,		(sO): [sACT, sINACT],						],
	activities			: [ (sN): "activities",			(sT): "object",											],
	airQualityIndex		: [ (sN): "air quality index",	(sT): sINT,	(sR): [iZ, 500],		u: "AQI",				],
	alarm				: [ (sN): "alarm",				(sT): sENUM,		(sO): ["both", sOFF, "siren", "strobe"],	],
	amperage			: [ (sN): "amperage",			(sT): sDEC,	(sR): [iZ, null],		u: "A",					],
	battery				: [ (sN): "battery",			(sT): sINT,	(sR): [iZ, i100],		u: "%",							],
	camera				: [ (sN): "camera",				(sT): sENUM,		(sO): [sON, sOFF, "restarting", "unavailable"],				],
	carbonDioxide		: [ (sN): "carbon dioxide",		(sT): sDEC,	(sR): [iZ, null],									],
	carbonMonoxide		: [ (sN): "carbon monoxide",	(sT): sENUM,		(sO): [sCLEAR, sDETECTED, "tested"],					],
	codeChanged			: [ (sN): "lock code",			(sT): sENUM,		(sO): ["added", "changed", "deleted", "failed"],				],
//	codeLength			: [ (sN): "Lock code length",	(sT): sINT,											],
	(sCOLOR)			: [ (sN): sCOLOR,				(sT): sCOLOR,											],
//	colorName			: [ (sN): "color name",			(sT): sSTR,											],
	colorMode			: [ (sN): "color mode",			(sT): sENUM,		(sO): ["CT", "RGB"],							],
	colorTemperature	: [ (sN): "color temperature",	(sT): sINT,	(sR): [1000, 30000],	u: "°K",						],
	consumableStatus	: [ (sN): "consumable status",	(sT): sENUM,		(sO): ["good", "maintenance_required", "missing", "order", "replace"],	],
	contact				: [ (sN): "contact",			(sT): sENUM,		(sO): [sCLOSED, sOPEN],							],
	coolingSetpoint		: [ (sN): "cooling setpoint",	(sT): sDEC,	(sR): [-127, 127],		u: '°?',						],
	currentActivity		: [ (sN): "current activity",	(sT): sSTR,											],
//	p: is interaction type
	door				: [ (sN): "door",				(sT): sENUM,		(sO): [sCLOSED, "closing", sOPEN, "opening", "unknown"],		(sP): true,	],
	energy				: [ (sN): "energy",				(sT): sDEC,	(sR): [iZ, null],		u: "kWh",						],
	eta					: [ (sN): "ETA",				(sT): sDTIME,											],
	effectName			: [ (sN): "effect name",		(sT): sSTR,											],
	filterStatus		: [ (sN): "filter status",		(sT): sENUM,		(sO):["normal", "replace"],						],
	frequency			: [ (sN): "frequency",			(sT): sDEC,		u: "Hz",							],
	goal				: [ (sN): "goal",				(sT): sINT,	(sR): [iZ, null],									],
	heatingSetpoint		: [ (sN): "heating setpoint",	(sT): sDEC,	(sR): [-127, 127],		u: '°?',						],
	hex					: [ (sN): "hexadecimal code",	(sT): "hexcolor",											],
	hue					: [ (sN): "hue",				(sT): sINT,	(sR): [iZ, 360],		u: "°",							],
	humidity			: [ (sN): "relative humidity",	(sT): sINT,	(sR): [iZ, i100],		u: "%",							],
	illuminance			: [ (sN): "illuminance",		(sT): sINT,	(sR): [iZ, null],		u: "lux",						],
	image				: [ (sN): "image",				(sT): "image",											],
	indicatorStatus		: [ (sN): "indicator status",	(sT): sENUM,		(sO): ["never", "when off", "when on"],					],
//	infraredLevel		: [ (sN): "infrared level",		(sT): sINT,	(sR): [iZ, i100],		u: "%",							],
//	lastCodeName		: [ (sN): "last lock code",		(sT): sSTR,											],
	level				: [ (sN): sLVL,					(sT): sINT,	(sR): [iZ, i100],		u: "%",							],
	levelPreset			: [ (sN): "preset level",		(sT): sINT,	(sR): [i1, i100],		u: "%",							],
	lightEffects		: [ (sN): "light effects",		(sT): "object",											],
// (sS): is subdevices
	lock				: [ (sN): "lock",				(sT): sENUM,		(sO): ["locked", "unknown", "unlocked", "unlocked with timeout"],	/*(sC): "lock",*/	(sP):true,		/*(sS):"numberOfCodes,numCodes", (sI):"usedCode", sd: "user code"*/		],
	lockCodes			: [ (sN): "lock codes",			(sT): "object",											],
	lqi					: [ (sN): "link quality",		(sT): sINT,	(sR): [iZ, 255],									],
//	maxCodes			: [ (sN): "Max Lock codes",		(sT): sINT,											],
//	momentary			: [ (sN): "momentary",			(sT): sENUM,		(sO): ["pushed"],								],
	motion				: [ (sN): "motion",				(sT): sENUM,		(sO): [sACT, sINACT],						],
	mute				: [ (sN): "mute",				(sT): sENUM,		(sO): ["muted", "unmuted"],						],
	naturalGas			: [ (sN): "natural gas",		(sT): sENUM,		(sO): [sCLEAR, sDETECTED, "tested"],					],
	pH					: [ (sN): "pH level",			(sT): sDEC,	(sR): [iZ, 14],									],
	phraseSpoken		: [ (sN): "phrase",				(sT): sSTR,											],
	position			: [ (sN): "position",			(sT): sINT,	(sR): [iZ, i100],		u: "%",							],
	power				: [ (sN): "power",				(sT): sDEC,		u: "W",									],
	powerSource			: [ (sN): "power source",		(sT): sENUM,		(sO): ["battery", "dc", "mains", "unknown"],				],
	presence			: [ (sN): "presence",			(sT): sENUM,		(sO): ["not present", "present"],						],
	pressure			: [ (sN): "pressure",			(sT): sDEC,								],
	rate				: [ (sN): "liquid flow rate",	(sT): sDEC,											],
//	RGB					: [ (sN): "rgb",				(sT): sSTR,											],
	rssi				: [ (sN): "signal strength",	(sT): sINT,	(sR): [iZ, i100],		u: "%",							],
	saturation			: [ (sN): "saturation",			(sT): sINT,	(sR): [iZ, i100],		u: "%",							],
//	schedule			: [ (sN): "schedule",			(sT): "object",											],
	securityKeypad		: [ (sN): "security keypad",	(sT): sENUM,		(sO): [sDISARMD, "armed home", "armed away", "unknown"],			],
	sessionStatus		: [ (sN): "session status",		(sT): sENUM,		(sO): ["canceled", "paused", "running", "stopped"],			],
	settings			: [ (sN): "settings",			(sT): "object",											],
	shock				: [ (sN): "shock",				(sT): sENUM,		(sO): [sCLEAR, sDETECTED],						],
	sleeping			: [ (sN): "sleeping",			(sT): sENUM,		(sO): ["not sleeping", "sleeping"],					],
	smoke				: [ (sN): "smoke",				(sT): sENUM,		(sO): [sCLEAR, sDETECTED, "tested"],					],
	sound				: [ (sN): "sound",				(sT): sENUM,		(sO): [sDETECTED, "not detected"],					],
	soundEffects		: [ (sN): "sound effects",		(sT): "object",											],
	soundName			: [ (sN): "sound name",			(sT): sSTR,											],
	soundPressureLevel	: [ (sN): "sound pressure level",		(sT): sINT,	(sR): [iZ, null],		u: "dB",						],
	speed				: [ (sN): "speed",				(sT): sENUM,		(sO): ["low", "medium-low", "medium", "medium-high", "high", sON, sOFF, "auto"],						],
	status				: [ (sN): "status",				(sT): sENUM,		(sO): ["playing", "stopped"],						],
	statusMessage		: [ (sN): "status message",		(sT): sSTR,								],
//	status				: [ (sN): "status",				(sT): sSTR,											],
	steps				: [ (sN): "steps",				(sT): sINT,		(sR): [iZ, null],									],
	supportedFanSpeeds	: [ (sN): "supported fan speeds",	(sT): "object",											],
	supportedInputs		: [ (sN): "supported inputs",	(sT): "object",											],
	supportedThermostatFanModes		: [ (sN): "supported thermostat fan modes",	(sT): "object",											],
	supportedThermostatModes		: [ (sN): "supported thermostat modes",	(sT): "object",											],
	(sSWITCH)			: [ (sN): sSWITCH,				(sT): sENUM,		(sO): [sOFF, sON],		(sP): true,					],
	tamper				: [ (sN): "tamper",				(sT): sENUM,		(sO): [sCLEAR, sDETECTED],						],
	temperature			: [ (sN): "temperature",		(sT): sDEC,		(sR): [-460, 10000],	u: '°?',						],
	thermostatFanMode	: [ (sN): "fan mode",			(sT): sENUM,		(sO): ["auto", "circulate", sON],						],
	thermostatMode		: [ (sN): "thermostat mode",	(sT): sENUM,		(sO): ["auto", "cool", "eco", "emergency heat", "heat", sOFF],		],
	thermostatOperatingState	: [ (sN): "operating state",		(sT): sENUM,		(sO): ["cooling", "fan only", "heating", "idle", "pending cool", "pending heat", "vent economizer"],	],
	thermostatSetpoint	: [ (sN): "setpoint",			(sT): sDEC,		(sR): [-127, 127],		u: '°?',						],
	threeAxis			: [ (sN): "vector",				(sT): "vector3",											],
	tilt				: [ (sN): "tilt",				(sT): sINT,		(sR): [iZ, i100],		u: "%",							],
	timeRemaining		: [ (sN): "time remaining",		(sT): sINT,		(sR): [iZ, null],		u: sS,							],
	touch				: [ (sN): "touch",				(sT): sENUM,		(sO): ["touched"],								],
	trackData			: [ (sN): "track data",			(sT): "object",											],
	trackDescription	: [ (sN): "track description",		(sT): sSTR,											],
	ultravioletIndex	: [ (sN): "UV index",			(sT): sDEC,		(sR): [iZ, null],									],
	valve				: [ (sN): "valve",				(sT): sENUM,		(sO): [sCLOSED, sOPEN],							],
//	variable			: [ (sN): "variable value",	(sT): sSTR,											],
	voltage				: [ (sN): "voltage",			(sT): sDEC,		(sR): [null, null],	u: "V",							],
	volume				: [ (sN): "volume",				(sT): sINT,		(sR): [iZ, i100],		u: "%",							],
	water				: [ (sN): "water",				(sT): sENUM,		(sO): ["dry", "wet"],							],
	windowShade			: [ (sN): "window shade",		(sT): sENUM,		(sO): [sCLOSED, "closing", sOPEN, "opening", "partially open", "unknown"],	],
	windowBlind			: [ (sN): "window blind",		(sT): sENUM,		(sO): [sCLOSED, "closing", sOPEN, "opening", "partially open", "unknown"],	],
// buttons
	doubleTapped		: [ (sN): "double tapped button",	(sT): sINT,	(sR): [null, null],	(sM): true,	/*s: "numberOfButtons",	(sI): "buttonNumber"*/			],
	held				: [ (sN): "held button",		(sT): sINT,	(sR): [null, null],	(sM): true,	/*s: "numberOfButtons",	(sI): "buttonNumber"*/			],
	released			: [ (sN): "released button",	(sT): sINT,	(sR): [null, null],	(sM): true,	/*s: "numberOfButtons",	(sI): "buttonNumber"*/			],
	pushed				: [ (sN): "pushed button",		(sT): sINT,	(sR): [null, null],	(sM): true,	/*s: "numberOfButtons",	(sI): "buttonNumber"*/			],

// pseudo attributes (first 5 builtin to IDE)
	axisX				: [ (sN): "X axis",				(sT): sINT,	(sR): [-1024, 1024],	/*(sS): "threeAxis",*/			],
	axisY				: [ (sN): "Y axis",				(sT): sINT,	(sR): [-1024, 1024],	/*(sS): "threeAxis",*/			],
	axisZ				: [ (sN): "Z axis",				(sT): sINT,	(sR): [-1024, 1024],	/*(sS): "threeAxis",*/			],
	orientation			: [ (sN): "orientation",		(sT): sENUM,		(sO): ["rear side up", "down side up", "left side up", "front side up", "up side up", "right side up"],	/*(sS): "threeAxis",*/ ],
//	(sDLRSTS)			: [ (sN): "device status",		(sT): sENUM,		(sO): ["ACTIVE", "INACTIVE"], ],
	(sLSTACTIVITY)		: [ (sN): "last activity",		(sT): sDTIME,											],
	(sROOMID)			: [ (sN): "room id",			(sT): sINT,											],
	(sROOMNM)			: [ (sN): "room name",			(sT): sSTR,											],

//webCoRE Presence Sensor
	altitude			: [ (sN): "altitude (usc)",		(sT): sDEC,	(sR): [null, null],	u: "ft",						],
	altitudeMetric		: [ (sN): "altitude (metric)",	(sT): sDEC,	(sR): [null, null],	u: sM,							],
	floor				: [ (sN): "floor",				(sT): sINT,	(sR): [null, null],								],
	distance			: [ (sN): "distance (usc)",		(sT): sDEC,	(sR): [null, null],	u: "mi",						],
	distanceMetric		: [ (sN): "distance (metric)",	(sT): sDEC,	(sR): [null, null],	u: "km",						],
	currentPlace		: [ (sN): "current place",		(sT): sSTR,											],
	previousPlace		: [ (sN): "previous place",		(sT): sSTR,											],
	closestPlace		: [ (sN): "closest place",		(sT): sSTR,											],
	arrivingAtPlace		: [ (sN): "arriving at place",	(sT): sSTR,											],
	leavingPlace		: [ (sN): "leaving place",		(sT): sSTR,											],
	places				: [ (sN): "places",				(sT): sSTR,											],
	horizontalAccuracyMetric	: [ (sN): "horizontal accuracy (metric)",	(sT): sDEC,	(sR): [null, null],	u: sM,							],
	horizontalAccuracy	: [ (sN): "horizontal accuracy (usc)",	(sT): sDEC,	(sR): [null, null],	u: "ft",						],
	verticalAccuracy	: [ (sN): "vertical accuracy (usc)",	(sT): sDEC,	(sR): [null, null],	u: "ft",						],
	verticalAccuracyMetric		: [ (sN): "vertical accuracy (metric)",	(sT): sDEC,	(sR): [null, null],	u: sM,							],
	latitude			: [ (sN): "latitude",			(sT): sDEC,	(sR): [null, null],	u: "°",							],
	longitude			: [ (sN): "longitude",			(sT): sDEC,	(sR): [null, null],	u: "°",							],
	closestPlaceDistance		: [ (sN): "distance to closest place (usc)",	(sT): sDEC,	(sR): [null, null],	u: "mi",					],
	closestPlaceDistanceMetric	: [ (sN): "distance to closest place (metric)",	(sT): sDEC,	(sR): [null, null],	u: "km",					],
//don't confuse with fanspeed
	speedUSC			: [ (sN): "speed (usc)",		(sT): sDEC,	(sR): [null, null],	u: "ft/s",						],
	speedMetric			: [ (sN): "speed (metric)",		(sT): sDEC,	(sR): [null, null],	u: "m/s",						],
	bearing				: [ (sN): "bearing",			(sT): sDEC,	(sR): [iZ, 360],		u: "°",							],

// custom for Leak sensor
	underHeat			: [ (sN): "under heat",			(sT): sENUM,		(sO): [sCLEAR, sDETECTED],						]
]

/*private Map attributes(){
	return attributesFLD
}*/

/* Push command has multiple overloads in hubitat */
// the command r: is replaced with command c.
private static Map<String,Map> commandOverrides(){
	return ( [ //s: command signature
		push	: [(sC): "push",	(sS): null , (sR): "pushMomentary"], // for capability momentary
		flash	: [(sC): "flash",	(sS): null , (sR): "flashNative"] //flash native command conflicts with flash emulated command. Also needs "o" option on command described later
	] ) as HashMap
}

Map getChildCommands(){
	Map<String,Map> result=commands()
	Map<String,Map> cleanResult=[:]
	Map defv=[(sN):sA]
	Map t0
	String hasA,hasV
	for(it in result){
		t0=[:]
		hasA=(String)it.value[sA]
		hasV=(String) it.value[sV]
		if(hasA) t0=t0 + [(sA):hasA]
		if(hasV) t0=t0 + [(sV):hasV]
		if(t0==[:]) t0=defv
		cleanResult[it.key]=t0
	}
	return cleanResult
}

@Field final Map<String,Map> commandsFLD=[
	armAway				: [ (sN): "Arm Away",				(sA): "securityKeypad",		(sV): "armed away",				],
	armHome				: [ (sN): "Arm Home",				(sA): "securityKeypad",		(sV): "armed home",				],
	auto				: [ (sN): "Set to Auto",			(sA): sTHERM,					(sV): "auto",						],
	beep				: [ (sN): "Beep",																				],
	both				: [ (sN): "Strobe and Siren",		(sA): "alarm",					(sV): "both",						],
	(sCANCEL)			: [ (sN): "Cancel",																			],
	close				: [ (sN): "Close",					(sA): "door",					(sV): sCLOSED,						],
	configure			: [ (sN): "Configure",		(sI): 'cog',															],
	cool				: [ (sN): "Set to Cool",		(sI): 'snowflake', is: 'l',	(sA): sTHERM,		(sV): "cool",			],
	cycleSpeed			: [ (sN): "Cycle speed",																	],
	deleteCode			: [ (sN): "Delete Code...",		(sD): "Delete code {0}",			(sP): [[(sN):"Code position", (sT):sINT]],					],
	deviceNotification	: [ (sN): "Send device notification...",	(sD): "Send device notification \"{0}\"",			(sP): [[(sN):"Message", (sT):sSTR]],				],
	disarm				: [ (sN): "Disarm",				(sA): "securityKeypad",				(sV): sDISARMD,						],
	eco					: [ (sN): "Set to Eco",		(sI): 'leaf',	(sA): sTHERM,				(sV): "eco",						],
	emergencyHeat		: [ (sN): "Set to Emergency Heat",			(sA): sTHERM,				(sV): "emergency heat",					],
	fanAuto				: [ (sN): "Set fan to Auto",			(sA): sTHERFM,				(sV): "auto",						],
	fanCirculate		: [ (sN): "Set fan to Circulate",			(sA): sTHERFM,				(sV): "circulate",						],
	fanOn				: [ (sN): "Set fan to On",				(sA): sTHERFM,				(sV): sON,							],
	flip				: [ (sN): "Flip",																		],
	getAllActivities	: [ (sN): "Get all activities",																],
	getCodes			: [ (sN): "Get Codes",																	],
	getCurrentActivity	: [ (sN): "Get current activity",																],
	heat				: [ (sN): "Set to Heat",		(sI): 'fire',	(sA): sTHERM,				(sV): "heat",						],
	indicatorNever		: [ (sN): "Disable indicator",																],
	indicatorWhenOff	: [ (sN): "Enable indicator when off",															],
	indicatorWhenOn		: [ (sN): "Enable indicator when on",															],
	lock				: [ (sN): "Lock",			(sI): "lock",	(sA): "lock",					(sV): "locked",						],
	mute				: [ (sN): "Mute",			(sI): 'volume-off',	(sA): "mute",				(sV): "muted",						],
	nextTrack			: [ (sN): "Next track",																	],
	off					: [ (sN): "Turn off",		(sI): 'circle-notch',	(sA): sSWITCH,				(sV): sOFF,						],
	on					: [ (sN): "Turn on",		(sI): "power-off",		(sA): sSWITCH,				(sV): sON,						],
	open				: [ (sN): "Open",						(sA): "door",				(sV): sOPEN,						],
	pause				: [ (sN): "Pause",																		],
	play				: [ (sN): "Play",																		],
	playSound			: [ (sN): "Play Sound",				(sD): "Play Sound {0}",		(sP): [[(sN):"Sound Number", (sT):sINT]],					],
	playText			: [ (sN): "Speak text...",				(sD): "Speak text \"{0}\"{1}",	(sP): [[(sN):"Text", (sT):sSTR], [(sN):sVOLUME, (sT):sLVL, (sD):sATVOL]]	],
	playTextAndRestore	: [ (sN): "Speak text and restore...",		(sD): "Speak text \"{0}\"{1} and restore",	(sP): [[(sN):"Text", (sT):sSTR], [(sN):sVOLUME, (sT):sLVL, (sD):sATVOL]],			],
	playTextAndResume	: [ (sN): "Speak text and resume...",		(sD): "Speak text \"{0}\"{1} and resume",	(sP): [[(sN):"Text", (sT):sSTR], [(sN):sVOLUME, (sT):sLVL, (sD):sATVOL]],			],
	playTrack			: [ (sN): "Play track...",					(sD): "Play track {0}{1}",					(sP): [[(sN):"Track URL", (sT):"uri"], [(sN):sVOLUME, (sT):sLVL, (sD):sATVOL]],			],
	playTrackAndRestore	: [ (sN): "Play track and restore...",		(sD): "Play track {0}{1} and restore",		(sP): [[(sN):"Track URL", (sT):"uri"], [(sN):sVOLUME, (sT):sLVL, (sD):sATVOL]],	],
	playTrackAndResume	: [ (sN): "Play track and resume...",		(sD): "Play track {0}{1} and resume",		(sP): [[(sN):"Track URL", (sT):"uri"], [(sN):sVOLUME, (sT):sLVL, (sD):sATVOL]],	],
	poll				: [ (sN): "Poll",						(sI): 'question',											],
	presetLevel			: [ (sN): "Set preset level...",		(sI): 'signal',	(sD): "Set preset level to {0}",			(sA): "presetLevel",			(sP): [[(sN):"Preset Level", (sT):"levelPreset"]],	],
//	presetPosition		: [ (sN): "Move to preset position",		(sA): "windowShade",		(sV): "partially open",	],
	previousTrack		: [ (sN): "Previous track",										],

	refresh				: [ (sN): "Refresh",					(sI): 'sync',											],
	restoreTrack		: [ (sN): "Restore track...",				(sD): "Restore track <uri>{0}</uri>",							(sP): [[(sN):"Track URL", (sT):"url"]],			],
	resumeTrack			: [ (sN): "Resume track...",				(sD): "Resume track <uri>{0}</uri>",							(sP): [[(sN):"Track URL", (sT):"url"]],			],
	setCode				: [ (sN): "Set Code...",				(sD): "Set code {0} to {1} {2}",						(sP): [[(sN):"Code Position", (sT):sINT], [(sN):"Pin", (sT):sSTR], [(sN):"Name", (sT):sSTR]],							],
	setCodeLength		: [ (sN): "Set Code Max Length...",		(sD): "Set code length to {0}",						(sP): [[(sN):"Code Length", (sT):sINT]],						],
	setColor			: [ (sN): "Set color...",		(sI): 'palette', is: sL,	(sD): "Set color to {0}{1}",			(sA): sCOLOR,				(sP): [[(sN):sCCOLOR, (sT):sCOLOR], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],							],
	setColorTemperature	: [ (sN): "Set color temperature...",		(sD): "Set color temperature to {0}°K{2}{3}{1}",			(sA): "colorTemperature",			(sP): [[(sN):"Color Temperature", (sT):"colorTemperature"], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY],[(sN):"Level", (sT):sLVL,(sD):" and set level {v}"],[(sN):"Transition duration (seconds)", (sT):"number",(sD):" over {v} seconds"]]	],
	setConsumableStatus	: [ (sN): "Set consumable status...",		(sD): "Set consumable status to {0}",								(sP): [[(sN):"Status", (sT):"consumable"]],		],
	setCoolingSetpoint	: [ (sN): "Set cooling point...",			(sD): "Set cooling point at {0}{T}",			(sA): "thermostatCoolingSetpoint",		(sP): [[(sN):"Desired temperature", (sT):"thermostatSetpoint"]],	],
	setEffect			: [ (sN): "Set Light Effect...",			(sD): "Set light effect to {0}",									(sP): [[(sN):"Effect number", (sT):sINT]],				],
	setEntryDelay		: [ (sN): "Set Entry Delay...",			(sD): "Set entry delay to {0}",									(sP): [[(sN):"Entry Delay", (sT):sINT]],				],
	setExitDelay		: [ (sN): "Set Exit Delay...",			(sD): "Set exit delay to {0}",									(sP): [[(sN):"Exit Delay", (sT):sINT]],				],
	setHeatingSetpoint	: [ (sN): "Set heating point...",			(sD): "Set heating point at {0}{T}",			(sA): "thermostatHeatingSetpoint",		(sP): [[(sN):"Desired temperature", (sT):"thermostatSetpoint"]],																	],
	setHue				: [ (sN): "Set hue...",		(sI): 'palette', is: sL,	(sD): "Set hue to {0}°{1}",			(sA): "hue",				(sP): [[(sN):"Hue", (sT):"hue"], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],								],
	setInfraredLevel	: [ (sN): "Set infrared level...",	(sI): 'signal',	(sD): "Set infrared level to {0}%{1}",			(sA): "infraredLevel",			(sP): [[(sN):"Level", (sT):"infraredLevel"], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],					],
	setLevel			: [ (sN): "Set level...",		(sI): 'signal',	(sD): "Set level to {0}%{2}{1}",				(sA): sLVL,				(sP): [[(sN):"Level", (sT):sLVL], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY],[(sN):"Transition duration (seconds)", (sT):"number",(sD):" over {v} seconds"]],							],
	setNextEffect		: [ (sN): "Set next light effect",																					],
	setPreviousEffect	: [ (sN): "Set previous light effect",																					],
	setPosition			: [ (sN): "Move to position",			(sD): "Set position to {0}",				(sA): "position",				(sP): [[(sN):"Position", (sT):"position"]],		],
	setSaturation		: [ (sN): "Set saturation...",			(sD): "Set saturation to {0}{1}",				(sA): "saturation",			(sP): [[(sN):"Saturation", (sT):"saturation"], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],					],
//	setSchedule			: [ (sN): "Set thermostat schedule...",		(sD): "Set schedule to {0}",				(sA): "schedule",				(sP): [[(sN):"Schedule", (sT):"object"]],			],
	setSpeed			: [ (sN): "Set fan speed...",			(sD): "Set fan speed to {0}",				(sA): "speed",				(sP): [[(sN):"Fan Speed", (sT):"speed"]],			],
	setThermostatFanMode		: [ (sN): "Set fan mode...",			(sD): "Set fan mode to {0}",				(sA): sTHERFM,			(sP): [[(sN):"Fan mode", (sT):sTHERFM]],	],
	setThermostatMode	: [ (sN): "Set thermostat mode...",		(sD): "Set thermostat mode to {0}",			(sA): sTHERM,			(sP): [[(sN):"Thermostat mode", (sT):sTHERM]],	],
	setTiltLevel		: [ (sN): "Move to tilt",				(sD): "Set tilt to {0}",					(sA): "tilt",				(sP): [[(sN):"Tilt", (sT):"tilt"]],		],
	setTimeRemaining	: [ (sN): "Set remaining time...",			(sD): "Set remaining time to {0}s",			(sA): "timeRemaining",			(sP): [[(sN):"Remaining time [seconds]", (sT):"number"]],	],
	setTrack			: [ (sN): "Set track...",				(sD): "Set track to <uri>{0}</uri>",								(sP): [[(sN):"Track URL", (sT):"url"]],			],
//	setVariable			: [ (sN): "Set Device Variable...",		(sD): "Set Device Variable to {0}",			(sA): sVARIABLE,				(sP):[[(sN):"device variable value", (sT):sVARIABLE]],			],
	setVolume			: [ (sN): "Set Volume...",				(sD): "Set Volume to {0}",					(sA): "volume",				(sP):[[(sN):"Level", (sT):"volume"]],			],
	siren				: [ (sN): "Siren",												(sA): "alarm",				(sV): "siren",					],
	speak				: [ (sN): "Speak...",				(sD): "Speak \"{0}\"{1}",								(sP): [[(sN):"Message", (sT):sSTR],[(sN):sVOLUME, (sT):sLVL,(sD):sATVOL ]],			],
	start				: [ (sN): "Start",																							],
	startActivity		: [ (sN): "Start activity...",			(sD): "Start activity \"{0}\"",									(sP): [[(sN):"Activity", (sT):sSTR]],		],
	startLevelChange	: [ (sN): "Start Level Change...",			(sD): "Start Level Change \"{0}\"",				(sP): [[(sN):"Direction", (sT):sSTR]],						],
	stopLevelChange		: [ (sN): "Stop Level Change...",			(sD): "Stop Level Change",																],
	startPositionChange	: [ (sN): "Start Position Change...",		(sD): "Start Position Change \"{0}\"",				(sP): [[(sN):"Direction", (sT):sENUM, (sO):[sOPEN, sCLOSE]]],						],
	stopPositionChange	: [ (sN): "Stop Position Change...",		(sD): "Stop Position Change",																],
	stop				: [ (sN): "Stop",																							],
	strobe				: [ (sN): "Strobe",											(sA): "alarm",				(sV): "strobe",					],
	take				: [ (sN): "Take a picture",																					],
	unlock				: [ (sN): "Unlock",		(sI): 'unlock-alt',							(sA): "lock",				(sV): "unlocked",					],
	unmute				: [ (sN): "Unmute",		(sI): 'volume-up',								(sA): "mute",				(sV): "unmuted",					],
	volumeDown			: [ (sN): "Lower volume",																					],
	volumeUp			: [ (sN): "Raise volume",																					],

// these are device virtual commands
	doubleTap			: [ (sN): "Double Tap",			(sD): "Double tap button {0}",			(sA): "doubleTapped",			(sP):[[(sN): "Button #", (sT): sINT]]	],
	hold				: [ (sN): "Hold",					(sD): "Hold Button {0}",				(sA): "held",					(sP):[[(sN):"Button #", (sT): sINT]]	],
	push				: [ (sN): "Push",					(sD): "Push button {0}",				(sA): "pushed",				(sP):[[(sN): "Button #", (sT): sINT]]	],
	release				: [ (sN): "Release",				(sD): "Release button {0}",			(sA): "released",				(sP):[[(sN): "Button #", (sT): sINT]]	],
// non standard ES commands
	parallelPlayAnnouncement	: [ (sN): "Parallel Play Announcement...",			(sD): "Parallel Play Announcement \"{0}\"",								(sP): [[(sN):"Message",(sT):sSTR], [(sN):"Title",(sT):sSTR]],			],
	parallelSpeak		: [ (sN): "Parallel Speak...",			(sD): "Parallel Speak \"{0}\"",								(sP): [[(sN):"Message", (sT):sSTR]],			],
	parallelSpeakIgnoreDnd		: [ (sN): "Parallel Speak Ignore Dnd...",			(sD): "Parallel Speak Ignore Dnd \"{0}\"",								(sP): [[(sN):"Message", (sT):sSTR]],			],
/* predfined commands below */
	//general
	quickSetCool		: [ (sN): "Quick set cooling point...",	(sD): "Set quick cooling point at {0}{T}",				(sP): [[(sN):"Desired temperature", (sT):"thermostatSetpoint"]],		],
	quickSetHeat		: [ (sN): "Quick set heating point...",	(sD): "Set quick heating point at {0}{T}",				(sP): [[(sN):"Desired temperature", (sT):"thermostatSetpoint"]],		],
	toggle				: [ (sN): "Toggle",																						],
	reset				: [ (sN): "Reset",																							],
	//hue
	startLoop			: [ (sN): "Start color loop",																					],
	stopLoop			: [ (sN): "Stop color loop",																					],
	setLoopTime			: [ (sN): "Set loop duration...",			(sD): "Set loop duration to {0}",				(sP): [[(sN):sDURATION, (sT):sDUR]]							],
//	setDirection		: [ (sN): "Switch loop direction",																					],
	alert				: [ (sN): "Alert with lights...",			(sD): "Alert \"{0}\" with lights",				(sP): [[(sN):"Alert type", (sT):sENUM, (sO):["Blink","Breathe","Okay","Stop"]]],			],
	setAdjustedColor	: [ (sN): "Transition to color...",		(sD): "Transition to color {0} in {1}{2}",			(sP): [[(sN):sCCOLOR, (sT):sCOLOR], [(sN):sDURATION, (sT):sDUR],[(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																	],
	setAdjustedHSLColor	: [ (sN): "Transition to HSL color...",		(sD): "Transition to color H:{0}° / S:{1}% / L:{2}% in {3}{4}",			(sP): [[(sN):"Hue", (sT):"hue"],[(sN):"Saturation", (sT):"saturation"],[(sN):"Level", (sT):sLVL],[(sN):sDURATION, (sT):sDUR],[(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																	],
	//harmony
	allOn				: [ (sN): "Turn all on",																						],
	allOff				: [ (sN): "Turn all off",																						],
	hubOn				: [ (sN): "Turn hub on",																						],
	hubOff				: [ (sN): "Turn hub off",																						],
	//blink camera
	enableCamera		: [ (sN): "Enable camera",																						],
	disableCamera		: [ (sN): "Disable camera",																					],
	monitorOn			: [ (sN): "Turn monitor on",																					],
	monitorOff			: [ (sN): "Turn monitor off",																					],
	ledOn				: [ (sN): "Turn LED on",																						],
	ledOff				: [ (sN): "Turn LED off",																						],
	ledAuto				: [ (sN): "Set LED to Auto",																					],
	setVideoLength		: [ (sN): "Set video length...",			(sD): "Set video length to {0}",				(sP): [[(sN):sDURATION, (sT):sDUR]],							],
	//dlink camera
	pirOn				: [ (sN): "Enable PIR motion detection",																				],
	pirOff				: [ (sN): "Disable PIR motion detection",																				],
	nvOn				: [ (sN): "Set Night Vision to On",																				],
	nvOff				: [ (sN): "Set Night Vision to Off",																				],
	nvAuto				: [ (sN): "Set Night Vision to Auto",																				],
	vrOn				: [ (sN): "Enable local video recording",																				],
	vrOff				: [ (sN): "Disable local video recording",																				],
	left				: [ (sN): "Pan camera left",																					],
	right				: [ (sN): "Pan camera right",																					],
	up					: [ (sN): "Pan camera up",																						],
	down				: [ (sN): "Pan camera down",																					],
	home				: [ (sN): "Pan camera to the Home",																				],
	presetOne			: [ (sN): "Pan camera to preset #1",																				],
	presetTwo			: [ (sN): "Pan camera to preset #2",																				],
	presetThree			: [ (sN): "Pan camera to preset #3",																				],
	presetFour			: [ (sN): "Pan camera to preset #4",																				],
	presetFive			: [ (sN): "Pan camera to preset #5",																				],
	presetSix			: [ (sN): "Pan camera to preset #6",																				],
	presetSeven			: [ (sN): "Pan camera to preset #7",																				],
	presetEight			: [ (sN): "Pan camera to preset #8",																				],
	presetCommand		: [ (sN): "Pan camera to preset...",		(sD): "Pan camera to preset #{0}",				(sP): [[(sN):"Preset #", (sT):sINT,(sR):[i1,99]]],						],

	flashNative			: [ (sN): "Flash",																						],
	pushMomentary		: [ (sN): "Push"																						]
]

private Map commands(){
	return commandsFLD
}

static Map getChildVirtCommands(){
	Map<String,Map> result=virtualCommands()
	Map<String,Map> cleanResult=[:]
	Map defv=[(sN):sA]
	Map t0
	Boolean hasA,hasO
	for(it in result){
		t0=[:]
		hasA=it.value[sA]
		hasO=it.value[sO]
		if(hasA!=null) t0=t0 + [(sA):hasA]
		if(hasO!=null) t0=t0 + [(sO):hasO]
		if(t0==[:]) t0=defv
		cleanResult[it.key]=t0
	}
	return cleanResult
}

	//a=aggregate (only execute once for a list of devices)
	//o=override physical with virtual

	//d=display (UI)
	//n=name (UI)
	//i=icon   (UI) fontawesome
	//is= iconstyle (UI)  s- solid (default); r- regular, l- light  not used: b - brand, d- duotone V5 - letter; V6 is full word
	//p=parameters (UI)
	//t=type (UI parameters)
	//r= require command (UI)
@Field static final String sBVB='{v}'
private static Map<String,Map> virtualCommands(){
	List<String> tileIndexes=['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16']
	String is='is'
	Map<String,Map> a
	a=[
		noop					: [ (sN): "No operation",				(sA): true,	(sI): "circle",				(sD): "No operation",						],
		wait					: [ (sN): "Wait...",					(sA): true,	(sI): sCLOCK, (is): sR,		(sD): "Wait {0}",						(sP): [[(sN):sDURATION, (sT):sDUR]],				],
		waitRandom				: [ (sN): "Wait randomly...",			(sA): true,	(sI): sCLOCK, (is): sR,		(sD): "Wait randomly between {0} and {1}",									(sP): [[(sN):"At least", (sT):sDUR],[(sN):"At most", (sT):sDUR]],	],
		waitForTime				: [ (sN): "Wait for time...",			(sA): true,	(sI): sCLOCK, (is): sR,		(sD): "Wait until {0}",													(sP): [[(sN):"Time", (sT):sTIME]],	],
		waitForDateTime			: [ (sN): "Wait for date & time...",	(sA): true,	(sI): sCLOCK, (is): sR,		(sD): "Wait until {0}",													(sP): [[(sN):"Date & Time", (sT):sDTIME]],	],
		executePiston			: [ (sN): "Execute piston...",			(sA): true,	(sI): sCLOCK, (is): sR,		(sD): "Execute piston \"{0}\"{1}{2}",									(sP): [[(sN):"Piston", (sT):"piston"], [(sN):"Arguments", (sT):"variables", (sD):" with arguments {v}"],[(sN):"Wait for execution", (sT):sBOOLN,(sD):" and wait for execution to finish",w:"webCoRE can only wait on piston executions of pistons within the same webCoRE instance as the caller. Please note that a) if the callee piston pauses, or waits, the caller piston will continue; b) global variables updated in the callee piston do NOT get reflected immediately in the caller piston, the new values will be available on the next run."]],	],
		pausePiston				: [ (sN): "Pause piston...",			(sA): true,	(sI): sCLOCK, (is): sR,		(sD): "Pause piston \"{0}\"",												(sP): [[(sN):"Piston", (sT):"piston"]],	],
		resumePiston			: [ (sN): "Resume piston...",			(sA): true,	(sI): sCLOCK, (is): sR,		(sD): "Resume piston \"{0}\"",												(sP): [[(sN):"Piston", (sT):"piston"]],	],
		executeRule				: [ (sN): "Execute Rule...",			(sA): true,	(sI): sCLOCK, (is): sR,		(sD): "Execute Rule \"{0}\" with action {1}",								(sP): [[(sN):"Rule", (sT):"rule"], [(sN):"Argument", (sT):sENUM, (sO):['Run','Stop','Pause','Resume','Evaluate','Set Boolean True','Set Boolean False']] ]	],
		toggle					: [ (sN): "Toggle", (sR): [sON, sOFF],				(sI): sTOGON																				],
		toggleRandom			: [ (sN): "Random toggle", (sR): [sON, sOFF],		(sI): sTOGON,				(sD): "Random toggle{0}",													(sP): [[(sN):"Probability for on", (sT):sLVL, (sD):" with a {v}% probability for on"]],	],
		setSwitch				: [ (sN): "Set switch...", (sR): [sON, sOFF],		(sI): sTOGON,				(sD): "Set switch to {0}",													(sP): [[(sN):"Switch value", (sT):sSWITCH]],																],
		setHSLColor				: [ (sN): "Set color... (hsl)",						(sI): "palette", (is): sL,			(sD): "Set color to H:{0}° / S:{1}% / L%:{2}{3}",				(sR): ["setColor"],				(sP): [[(sN):"Hue", (sT):"hue"], [(sN):"Saturation", (sT):"saturation"], [(sN):"Level", (sT):sLVL], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],							],
		toggleLevel				: [ (sN): "Toggle level...",						(sI): "toggle-off",					(sD): "Toggle level between 0% and {0}%",	(sR): [sON, sOFF, "setLevel"],	(sP): [[(sN):"Level", (sT):sLVL]],																																	],
		sendNotification		: [ (sN): "Send notification...",		(sA): true,	(sI): "comment-alt", (is): sR,			(sD): "Send notification \"{0}\"",											(sP): [[(sN):"Message", (sT):sSTR]],												],
		sendPushNotification	: [ (sN): "Send PUSH notification...",	(sA): true,	(sI): "comment-alt", (is): sR,			(sD): "Send PUSH notification \"{0}\"{1}",									(sP): [[(sN):"Message", (sT):sSTR],[(sN):"Store in Messages", (sT):sBOOLN, (sD):" and store in Messages", (sS):1]],	],
		sendSMSNotification		: [ (sN): "Send SMS notification...",	(sA): true,	(sI): "comment-alt", (is): sR,			(sD): "Send SMS notification \"{0}\" to {1}{2}",							(sP): [[(sN):"Message", (sT):sSTR],[(sN):"Phone number", (sT):"phone",w:"HE requires +countrycode in phone number."],[(sN):"Store in Messages", (sT):sBOOLN, (sD):" and store in Messages", (sS):1]],	],
		log						: [ (sN): "Log to console...",			(sA): true,	(sI): "bug",				(sD): "Log {0} \"{1}\"{2}",													(sP): [[(sN):"Log type", (sT):sENUM, (sO):[sINFO,sTRC,sDBG,sWARN,sERR]],[(sN):"Message", (sT):sSTR],[(sN):"Store in Messages", (sT):sBOOLN, (sD):" and store in Messages", (sS):1]],	],
		httpRequest				: [ (sN): "Make a web request",			(sA): true,	(sI): "anchor", (is): sR,		(sD): "Make a {1} request to {0}",										(sP): [[(sN):"URL", (sT):"uri"],[(sN):"Method", (sT):sENUM, (sO):["GET","POST","PUT","DELETE","HEAD"]],[(sN):"Request body type", (sT):sENUM, (sO):["JSON","FORM","CUSTOM"]],[(sN):"Send variables", (sT):"variables", (sD):"data {v}"],[(sN):"Request body", (sT):sSTR, (sD):"data {v}"],[(sN):"Request content type", (sT):sENUM, (sO):["text/plain","text/html",sAPPJSON,"application/x-www-form-urlencoded","application/xml"]],[(sN):"Authorization header", (sT):sSTR, (sD):sBVB]],	],
		setVariable				: [ (sN): "Set variable...",			(sA): true,	(sI): "superscript", (is):sR,	(sD): "Set variable {0} = {1}",											(sP): [[(sN):"Variable", (sT):sVARIABLE],[(sN):"Value", (sT):sDYN]],	],
		setState				: [ (sN): "Set piston state...",		(sA): true,	(sI): "align-left", (is):sL,	(sD): "Set piston state to \"{0}\"",										(sP): [[(sN):"State", (sT):sSTR]],	],
		setTileColor			: [ (sN): "Set piston tile colors...",	(sA): true,	(sI): "info-square", (is):sL,	(sD): "Set piston tile #{0} colors to {1} over {2}{3}",					(sP): [[(sN):"Tile Index", (sT):sENUM,(sO):tileIndexes],[(sN):"Text Color", (sT):sCOLOR],[(sN):"Background Color", (sT):sCOLOR],[(sN):"Flash mode", (sT):sBOOLN,(sD):" (flashing)"]],	],
		setTileTitle			: [ (sN): "Set piston tile title...",	(sA): true,	(sI): "info-square", (is):sL,	(sD): "Set piston tile #{0} title to \"{1}\"",								(sP): [[(sN):"Tile Index", (sT):sENUM,(sO):tileIndexes],[(sN):"Title", (sT):sSTR]],	],
		setTileOTitle			: [ (sN): "Set piston tile mouseover title...",	(sA): true,	(sI): "info-square", (is):sL,	(sD): "Set piston tile #{0} mouseover title to \"{1}\"",								(sP): [[(sN):"Tile Index", (sT):sENUM,(sO):tileIndexes],[(sN):"Title", (sT):sSTR]],	],
		setTileText				: [ (sN): "Set piston tile text...",	(sA): true,	(sI): "info-square", (is):sL,	(sD): "Set piston tile #{0} text to \"{1}\"",								(sP): [[(sN):"Tile Index", (sT):sENUM,(sO):tileIndexes],[(sN):"Text", (sT):sSTR]],	],
		setTileFooter			: [ (sN): "Set piston tile footer...",	(sA): true,	(sI): "info-square", (is):sL,	(sD): "Set piston tile #{0} footer to \"{1}\"",							(sP): [[(sN):"Tile Index", (sT):sENUM,(sO):tileIndexes],[(sN):"Footer", (sT):sSTR]],	],
		setTile					: [ (sN): "Set piston tile...",			(sA): true,	(sI): "info-square", (is):sL,	(sD): "Set piston tile #{0} title to \"{1}\", text to \"{2}\", footer to \"{3}\", and colors to {4} over {5}{6}",		(sP): [[(sN):"Tile Index", (sT):sENUM,(sO):tileIndexes],[(sN):"Title", (sT):sSTR],[(sN):"Text", (sT):sSTR],[(sN):"Footer", (sT):sSTR],[(sN):"Text Color", (sT):sCOLOR],[(sN):"Background Color", (sT):sCOLOR],[(sN):"Flash mode", (sT):sBOOLN,(sD):" (flashing)"]],	],
		clearTile				: [ (sN): "Clear piston tile...",		(sA): true,	(sI): "info-square", (is):sL,	(sD): "Clear piston tile #{0}",											(sP): [[(sN):"Tile Index", (sT):sENUM,(sO):tileIndexes]],	],
		setLocationMode			: [ (sN): "Set location mode...",		(sA): true,	(sI): sBLK,						(sD): "Set location mode to {0}",											(sP): [[(sN):"Mode", (sT):"mode"]],																														],
		sendEmail				: [ (sN): "Send email...",				(sA): true,	(sI): "envelope",				(sD): "Send email with subject \"{1}\" to {0}",							(sP): [[(sN):"Recipient", (sT):"email"],[(sN):"Subject", (sT):sSTR],[(sN):"Message body", (sT):sSTR]],																							],
		wolRequest				: [ (sN): "Wake a LAN device",			(sA): true,	(sI): sBLK,						(sD): "Wake LAN device at address {0}{1}",									(sP): [[(sN):"MAC address", (sT):sSTR],[(sN):"Secure code", (sT):sSTR,(sD):" with secure code {v}"]],	],
		adjustLevel				: [ (sN): "Adjust level...",	(sR): ["setLevel"],	(sI): sTOGON,				(sD): "Adjust level by {0}%{1}",											(sP): [[(sN):"Adjustment", (sT):sINT,(sR):[-i100,i100]], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		adjustInfraredLevel		: [ (sN): "Adjust infrared level...",	(sR): ["setInfraredLevel"],	(sI): sTOGON,	(sD): "Adjust infrared level by {0}%{1}",								(sP): [[(sN):"Adjustment", (sT):sINT,(sR):[-i100,i100]], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		adjustSaturation		: [ (sN): "Adjust saturation...",	(sR): ["setSaturation"],	(sI): sTOGON,		(sD): "Adjust saturation by {0}%{1}",										(sP): [[(sN):"Adjustment", (sT):sINT,(sR):[-i100,i100]], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		adjustHue				: [ (sN): "Adjust hue...",	(sR): ["setHue"],		(sI): sTOGON,					(sD): "Adjust hue by {0}°{1}",												(sP): [[(sN):"Adjustment", (sT):sINT,(sR):[-360,360]], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		adjustColorTemperature	: [ (sN): "Adjust color temperature...",	(sR): ["setColorTemperature"],	(sI): sTOGON,				(sD): "Adjust color temperature by {0}°K%{1}",		(sP): [[(sN):"Adjustment", (sT):sINT,(sR):[-29000,29000]], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		fadeLevel				: [ (sN): "Fade level...",	(sR): ["setLevel"],		(sI): sTOGON,				(sD): "Fade level{0} to {1}% in {2}{3}",									(sP): [[(sN):"Starting level", (sT):sLVL,(sD):" from {v}%"],[(sN):"Final level", (sT):sLVL],[(sN):sDURATION, (sT):sDUR], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		fadeInfraredLevel		: [ (sN): "Fade infrared level...",	(sR): ["setInfraredLevel"],		(sI): sTOGON,				(sD): "Fade infrared level{0} to {1}% in {2}{3}",		(sP): [[(sN):"Starting infrared level", (sT):sLVL,(sD):" from {v}%"],[(sN):"Final infrared level", (sT):sLVL],[(sN):sDURATION, (sT):sDUR], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		fadeSaturation			: [ (sN): "Fade saturation...",	(sR): ["setSaturation"],		(sI): sTOGON,				(sD): "Fade saturation{0} to {1}% in {2}{3}",					(sP): [[(sN):"Starting saturation", (sT):sLVL,(sD):" from {v}%"],[(sN):"Final saturation", (sT):sLVL],[(sN):sDURATION, (sT):sDUR], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		fadeHue					: [ (sN): "Fade hue...",			(sR): ["setHue"],		(sI): sTOGON,				(sD): "Fade hue{0} to {1}° in {2}{3}",								(sP): [[(sN):"Starting hue", (sT):"hue",(sD):" from {v}°"],[(sN):"Final hue", (sT):"hue"],[(sN):sDURATION, (sT):sDUR], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		fadeColorTemperature	: [ (sN): "Fade color temperature...",		(sR): ["setColorTemperature"],		(sI): sTOGON,				(sD): "Fade color temperature{0} to {1}°K in {2}{3}",									(sP): [[(sN):"Starting color temperature", (sT):"colorTemperature",(sD):" from {v}°K"],[(sN):"Final color temperature", (sT):"colorTemperature"],[(sN):sDURATION, (sT):sDUR], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
//		flash					: [ (sN): "Flash...",	(sR): [sON, sOFF],		(sI): sTOGON,				(sD): "Flash on {0} / off {1} for {2} times{3}",							(sP): [[(sN):"On duration", (sT):sDUR],[(sN):"Off duration", (sT):sDUR],[(sN):sNUMFLASH, (sT):sINT], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		flashLevel				: [ (sN): "Flash (level)...",	(sR): ["setLevel"],	(sI): sTOGON,		(sD): "Flash {0}% {1} / {2}% {3} for {4} times{5}",						(sP): [[(sN):"Level 1", (sT):sLVL],[(sN):"Duration 1", (sT):sDUR],[(sN):"Level 2", (sT):sLVL],[(sN):"Duration 2", (sT):sDUR],[(sN):sNUMFLASH, (sT):sINT], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		flashColor				: [ (sN): "Flash (color)...",	(sR): ["setColor"],	(sI): sTOGON,		(sD): "Flash {0} {1} / {2} {3} for {4} times{5}",						(sP): [[(sN):"Color 1", (sT):sCOLOR],[(sN):"Duration 1", (sT):sDUR],[(sN):"Color 2", (sT):sCOLOR],[(sN):"Duration 2", (sT):sDUR],[(sN):sNUMFLASH, (sT):sINT], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																],
		lifxScene				: [ (sN): "LIFX - Activate scene...",		(sA): true,			(sD): "Activate LIFX Scene '{0}'{1}",									(sP): [[(sN): "Scene", (sT):"lifxScene"],[(sN): sDURATION, (sT):sDUR, (sD):" for {v}"]],			],
		lifxState				: [ (sN): "LIFX - Set State...",			(sA): true,			(sD): "Set LIFX lights matching {0} to {1}{2}{3}{4}{5}",				(sP): [[(sN): "Selector", (sT):"lifxSelector"],[(sN): "Switch (power)", (sT):sENUM,(sO):[sON,sOFF],(sD):" switch '{v}'"],[(sN): sCCOLOR, (sT):sCOLOR,(sD):" color '{v}'"],[(sN): "Level (brightness)", (sT):sLVL,(sD):" level {v}%"],[(sN): "Infrared level", (sT):"infraredLevel",(sD):" infrared {v}%"],[(sN): sDURATION, (sT):sDUR,(sD):" in {v}"]], ],
		lifxToggle				: [ (sN): "LIFX - Toggle...",		(sA): true,					(sD): "Toggle LIFX lights matching {0}{1}",				(sP): [[(sN): "Selector", (sT):"lifxSelector"],[(sN): sDURATION, (sT):sDUR,(sD):" in {v}"]], ],
		lifxBreathe				: [ (sN): "LIFX - Breathe...",		(sA): true,					(sD): "Breathe LIFX lights matching {0} to color {1}{2}{3}{4}{5}{6}{7}",	(sP): [[(sN): "Selector", (sT):"lifxSelector"],[(sN): sCCOLOR, (sT):sCOLOR],[(sN): "From color", (sT):sCOLOR,(sD):" from color '{v}'"],[(sN): "Period", (sT):sDUR, (sD):" with a period of {v}"],[(sN): "Cycles", (sT):sINT, (sD):" for {v} cycles"],[(sN):"Peak", (sT):sLVL,(sD):" with a peak at {v}% of the period"],[(sN):"Power on", (sT):sBOOLN,(sD):" and power on at start"],[(sN):"Persist", (sT):sBOOLN,(sD):" and persist"] ], ],
		lifxPulse				: [ (sN): "LIFX - Pulse...",		(sA): true,					(sD): "Pulse LIFX lights matching {0} to color {1}{2}{3}{4}{5}{6}",			(sP): [[(sN): "Selector", (sT):"lifxSelector"],[(sN): sCCOLOR, (sT):sCOLOR],[(sN): "From color", (sT):sCOLOR,(sD):" from color '{v}'"],[(sN): "Period", (sT):sDUR, (sD):" with a period of {v}"],[(sN): "Cycles", (sT):sINT, (sD):" for {v} cycles"],[(sN):"Power on", (sT):sBOOLN,(sD):" and power on at start"],[(sN):"Persist", (sT):sBOOLN,(sD):" and persist"] ], ],

		writeToFuelStream		: [ (sN): "Append to fuel stream...",		(sA): true,			(sD): "Append data point '{2}' to fuel stream {0}{1}{3}",	(sP): [[(sN): "Canister", (sT):sTXT, (sD):"{v} \\ "], [(sN):"Fuel stream name", (sT):sTXT], [(sN): "Data", (sT):sDYN], [(sN): "Data source", (sT):sTXT, (sD):" from source '{v}'"]],			],
		iftttMaker				: [ (sN): "Send an IFTTT Maker event...",	(sA): true,			(sD): "Send the {0} IFTTT Maker event{1}{2}{3}",			(sP): [[(sN):"Event", (sT):sTXT], [(sN):"Value 1", (sT):sSTR, (sD):", passing value1 = '{v}'"], [(sN):"Value 2", (sT):sSTR, (sD):", passing value2 = '{v}'"], [(sN):"Value 3", (sT):sSTR, (sD):", passing value3 = '{v}'"]],				],
		storeMedia				: [ (sN): "Store media...",				(sA): true,				(sD): "Store media",														(sP): [],					],
		saveStateLocally		: [ (sN): "Capture attributes to local store...",				(sD): "Capture attributes {0} to local state{1}{2}",						(sP): [[(sN): "Attributes", (sT):"attributes"],[(sN):'State container name', (sT):sSTR,(sD):sSPC+sBVB],[(sN):'Prevent overwriting existing state', (sT):sENUM, (sO):['true','false'], (sD):' only if store is empty']], ],
		saveStateGlobally		: [ (sN): "Capture attributes to global store...",				(sD): "Capture attributes {0} to global state{1}{2}",						(sP): [[(sN): "Attributes", (sT):"attributes"],[(sN):'State container name', (sT):sSTR,(sD):sSPC+sBVB],[(sN):'Prevent overwriting existing state', (sT):sENUM, (sO):['true','false'], (sD):' only if store is empty']], ],
		loadStateLocally		: [ (sN): "Restore attributes from local store...",				(sD): "Restore attributes {0} from local state{1}{2}",						(sP): [[(sN): "Attributes", (sT):"attributes"],[(sN):'State container name', (sT):sSTR,(sD):sSPC+sBVB],[(sN):'Empty state after restore', (sT):sENUM, (sO):['true','false'], (sD):' and empty the store']], ],
		loadStateGlobally		: [ (sN): "Restore attributes from global store...",			(sD): "Restore attributes {0} from global state{1}{2}",						(sP): [[(sN): "Attributes", (sT):"attributes"],[(sN):'State container name', (sT):sSTR,(sD):sSPC+sBVB],[(sN):'Empty state after restore', (sT):sENUM, (sO):['true','false'], (sD):' and empty the store']], ],
		parseJson				: [ (sN): "Parse JSON data...",			(sA): true,				(sD): "Parse JSON data {0}",												(sP): [[(sN): "JSON string", (sT):sSTR]],																											],
		cancelTasks				: [ (sN): "Cancel all pending tasks",	(sA): true,				(sD): "Cancel all pending tasks",											(sP): [],																											],

		readFile				: [ (sN): "Read from file...",		(sA): true,					(sD): "Read from file {0} to \$file",		(sP): [[(sN): "File name", (sT):sSTR ], [(sN):"Username", (sT):'email', (sD):", username {v}"], [(sN): "Password", (sT):"uri", (sD):", password {v}"] ],					],
		writeFile				: [ (sN): "Write to file...",		(sA): true,					(sD): "Write to file {0}",					(sP): [[(sN): "File name", (sT):sSTR ], [(sN):"Data", (sT):sSTR, ],[(sN):"Username", (sT):'email', (sD):", username {v}"], [(sN): "Password", (sT):"uri", (sD):", password {v}"] ],					],
		appendFile				: [ (sN): "Append to file...",		(sA): true,					(sD): "Append to file {0}",					(sP): [[(sN): "File name", (sT):sSTR ], [(sN):"Data", (sT):sSTR, ],[(sN):"Username", (sT):'email', (sD):", username {v}"], [(sN): "Password", (sT):"uri", (sD):", password {v}"] ],					],
		deleteFile				: [ (sN): "Delete file...",			(sA): true,					(sD): "Delete file {0}",					(sP): [[(sN): "File name", (sT):sSTR ]  ],				],

		setAlarmSystemStatus	: [ (sN): "Set Hubitat Safety Monitor status...",	(sA): true, (sI): sBLK,				(sD): "Set Hubitat Safety Monitor status to {0}",							(sP): [ [(sN):"Status", (sT):sENUM, (sO): getAlarmSystemStatusActions().collect {[(sN): it.value, (sV): it.key]}], [(sN):"Arm delay", (sT):sINT,(sD):" with an arm delay of {v} seconds"]	],					],
		//keep emulated flash to not break old pistons
		emulatedFlash			: [ (sN): "(Old do not use) Emulated Flash",	(sR): [sON, sOFF],			(sI): sTOGON,				(sD): "(Old do not use)Flash on {0} / off {1} for {2} times{3}",							(sP): [[(sN):"On duration", (sT):sDUR],[(sN):"Off duration", (sT):sDUR],[(sN):sNUMFLASH, (sT):sINT], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],																], //add back emulated flash with "o" option so that it overrides the native flash command
		flash					: [ (sN): "Flash...",	(sR): [sON, sOFF],		(sI): sTOGON,				(sD): "Flash on {0} / off {1} for {2} times{3}",							(sP): [[(sN):"On duration", (sT):sDUR],[(sN):"Off duration", (sT):sDUR],[(sN):sNUMFLASH, (sT):sINT], [(sN):sONLYIFSWIS, (sT):sENUM,(sO):[sON,sOFF], (sD):sIFALREADY]],		(sO): true /*override physical command*/													]
	] as Map<String,Map>
	if(graphsOn()){
		a = a + [
			readFuelStream		: [ (sN): "Read fuel stream...",		(sA): true,		(sD): "Read entire fuel stream {0}{1} to \$fuel",			(sP): [[(sN): "Canister", (sT):sTXT, (sD):"{v} \\ "], [(sN):"Fuel stream name", (sT):sTXT] ],					],
			writeFuelStream		: [ (sN): "Overwrite fuel stream...",	(sA): true,		(sD): "Write entire fuel stream {0}{1}{3}",					(sP): [[(sN): "Canister", (sT):sTXT, (sD):"{v} \\ "], [(sN):"Fuel stream name", (sT):sTXT], [(sN): "Data", (sT):sDYN],	[(sN): "Data source", (sT):sTXT, (sD):" from source '{v}'"]],			],
			clearFuelStream		: [ (sN): "Clear fuel stream...",		(sA): true,		(sD): "Clear fuel stream {0}{1}{2}",						(sP): [[(sN): "Canister", (sT):sTXT, (sD):"{v} \\ "], [(sN):"Fuel stream name", (sT):sTXT], 							[(sN): "Data source", (sT):sTXT, (sD):" from source '{v}'"] ],			],
		//	addToFuelStream		: [ (sN): "Add to fuel stream...",		(sA): true,		(sD): "Add data point '{2}' to fuel stream {0}{1}{3}",		(sP): [[(sN): "Canister", (sT):sTXT, (sD):"{v} \\ "], [(sN):"Fuel stream name", (sT):sTXT], [(sN): "Data", (sT):sDYN],	[(sN): "Time stamp", (sT): sDTIME ],	[(sN): "Data source", (sT):sTXT, (sD):" from source '{v}'"]],			],
				// listFuelStreams(includeLTS)
				// existsFuelStream (canister, name)
				// removeFuelStream (canister,name)

				// listLTS
				// createLTS (device,attribute,keepinfo)
				// existsLTS (device,attribute)
				// removeLTS(device,attribute)

				// quantStream(istream, dstream, quantparams)
				// graphStream(istream, graphparams, quantparams)
		] as Map<String,Map>
	}
	return a
}

@Field static final String sCONDITIONS='conditions'
@Field static final String sTRIGGERS='triggers'

Map getChildComparisons(){
	Map<String,Map<String,Map>> result=comparisonsFLD
	Map<String,Map<String,Map>> cleanResult=[:]
	cleanResult[sCONDITIONS]=[:] as Map<String,Map>
	Map defv=[(sN):sA]
	Map t0
	Integer hasP,hasT
	for(it in result[sCONDITIONS]){
		t0=[:]
		hasP=(Integer)it.value[sP]
		hasT=(Integer)it.value[sT]
		if(hasP!=null) t0=t0+[(sP):hasP]
		if(hasT!=null) t0=t0+[(sT):hasT]
		if(t0==[:]) t0=defv
		cleanResult[sCONDITIONS][it.key]=t0
	}
	cleanResult[sTRIGGERS]=[:] as Map<String,Map>
	for(it in result[sTRIGGERS]){
		t0=[:]
		hasP=(Integer)it.value[sP]
		hasT=(Integer)it.value[sT]
		if(hasP!=null) t0=t0+[(sP):hasP]
		if(hasT!=null) t0=t0+[(sT):hasT]
		if(t0==[:]) t0=defv
		cleanResult[sTRIGGERS][it.key]=t0
	}
	return cleanResult
}

// m - multiple
// p - parameter count
// t - timed  1 - for/last; 2-at least/less than (using .f )
// used by ide
// g types
//      t = timed
//      f = image
//      s = string
//      m = momentary
//      v = virtual device
//      d = decimal
//      i = integer
//		b = boolean
//		n = number (decimal)
//		e = email (not implemented)
@Field static final String sDD='dd'
@Field static final String sDI='di'
@Field final Map<String,Map> comparisonsFLD=[
	conditions: [
		changed				: [ (sD): "changed",									(sG):"bdfis",				(sT): i1	],
		did_not_change		: [ (sD): "did not change",								(sG):"bdfis",				(sT): i1	],
		is					: [ (sD): "is",				(sDD): "are",					(sG):"bs",		(sP): i1					],
		is_not				: [ (sD): "is not",			(sDD): "are not",					(sG):"bs",		(sP): i1					],
		is_any_of			: [ (sD): "is any of",			(sDD): "are any of",				(sG):sS,		(sP): i1,	(sM): true,			],
		is_not_any_of		: [ (sD): "is not any of",			(sDD): "are not any of",				(sG):sS,		(sP): i1,	(sM): true,			],
		is_equal_to			: [ (sD): "is equal to",			(sDD): "are equal to",				(sG):sDI,		(sP): i1					],
		is_different_than	: [ (sD): "is different than",		(sDD): "are different than",			(sG):sDI,		(sP): i1					],
		is_less_than		: [ (sD): "is less than",			(sDD): "are less than",				(sG):sDI,		(sP): i1					],
		is_less_than_or_equal_to	: [ (sD): "is less than or equal to",	(sDD): "are less than or equal to",		(sG):sDI,		(sP): i1					],
		is_greater_than		: [ (sD): "is greater than",		(sDD): "are greater than",				(sG):sDI,		(sP): i1					],
		is_greater_than_or_equal_to	: [ (sD): "is greater than or equal to",	(sDD): "are greater than or equal to",		(sG):sDI,		(sP): i1					],
		is_inside_of_range	: [ (sD): "is inside of range",		(sDD): "are inside of range",			(sG):sDI,		(sP): i2					],
		is_outside_of_range	: [ (sD): "is outside of range",		(sDD): "are outside of range",			(sG):sDI,		(sP): i2					],
		is_even				: [ (sD): "is even",			(sDD): "are even",					(sG):sDI,							],
		is_odd				: [ (sD): "is odd",			(sDD): "are odd",					(sG):sDI,							],
//		is_true				: [ (sD): "is true",			(sDD): "are true",					(sG):"bs",		(sP): iZ					],
//		is_false			: [ (sD): "is false",			(sDD): "are false",					(sG):"bs",		(sP): iZ					],
		was					: [ (sD): "was",				(sDD): "were",					(sG):"bs",		(sP): i1,			(sT): i2,	],
		was_not				: [ (sD): "was not",			(sDD): "were not",					(sG):"bs",		(sP): i1,			(sT): i2,	],
		was_any_of			: [ (sD): "was any of",			(sDD): "were any of",				(sG):sS,		(sP): i1,	(sM): true,	(sT): i2,	],
		was_not_any_of		: [ (sD): "was not any of",		(sDD): "were not any of",				(sG):sS,		(sP): i1,	(sM): true,	(sT): i2,	],
		was_equal_to		: [ (sD): "was equal to",			(sDD): "were equal to",				(sG):sDI,		(sP): i1,			(sT): i2,	],
		was_different_than	: [ (sD): "was different than",		(sDD): "were different than",			(sG):sDI,		(sP): i1,			(sT): i2,	],
		was_less_than		: [ (sD): "was less than",			(sDD): "were less than",				(sG):sDI,		(sP): i1,			(sT): i2,	],
		was_less_than_or_equal_to	: [ (sD): "was less than or equal to",	(sDD): "were less than or equal to",		(sG):sDI,		(sP): i1,			(sT): i2,	],
		was_greater_than	: [ (sD): "was greater than",		(sDD): "were greater than",			(sG):sDI,		(sP): i1,			(sT): i2,	],
		was_greater_than_or_equal_to	: [ (sD): "was greater than or equal to",	(sDD): "were greater than or equal to",		(sG):sDI,		(sP): i1,			(sT): i2,	],
		was_inside_of_range	: [ (sD): "was inside of range",		(sDD): "were inside of range",			(sG):sDI,		(sP): i2,			(sT): i2,	],
		was_outside_of_range	: [ (sD): "was outside of range",		(sDD): "were outside of range",			(sG):sDI,		(sP): i2,			(sT): i2,	],
		was_even			: [ (sD): "was even",			(sDD): "were even",				(sG):sDI,					(sT): i2,	],
		was_odd				: [ (sD): "was odd",			(sDD): "were odd",					(sG):sDI,					(sT): i2,	],
//		was_true			: [ (sD): "was true",			(sDD): "were true",					(sG):"bs",		(sP): iZ					],
//		was_false			: [ (sD): "was false",			(sDD): "were false",				(sG):"bs",		(sP): iZ					],
		is_any				: [ (sD): "is any",									(sG):sT,		(sP): iZ					],
		is_before			: [ (sD): "is before",									(sG):sT,		(sP): i1					],
		is_after			: [ (sD): "is after",									(sG):sT,		(sP): i1					],
		is_between			: [ (sD): "is between",									(sG):sT,		(sP): i2					],
		is_not_between		: [ (sD): "is not between",								(sG):sT,		(sP): i2					],
	],
	triggers: [
		happens_daily_at	: [ (sD): "happens daily at",								(sG):sT,		(sP): i1					],
		arrives				: [ (sD): "arrives",									(sG):"e",		(sP): i2					],
		executes			: [ (sD): "executes",									(sG):sV,		(sP): i1					],
		changes				: [ (sD): "changes",			(sDD): "change",					(sG):"bdfis",						],
		changes_to			: [ (sD): "changes to",			(sDD): "change to",				(sG):"bdis",	(sP): i1,					],
		changes_away_from	: [ (sD): "changes away from",		(sDD): "change away from",				(sG):"bdis",	(sP): i1,					],
		changes_to_any_of	: [ (sD): "changes to any of",		(sDD): "change to any of",				(sG):"dis",	(sP): i1,	(sM): true,			],
		changes_away_from_any_of	: [ (sD): "changes away from any of",	(sDD): "change away from any of",			(sG):"dis",	(sP): i1,	(sM): true,			],
		drops				: [ (sD): "drops",				(sDD): "drop",					(sG):sDI,							],
		does_not_drop		: [ (sD): "does not drop",			(sDD): "do not drop",				(sG):sDI,							],
		drops_below			: [ (sD): "drops below",			(sDD): "drop below",				(sG):sDI,		(sP): i1,					],
		drops_to_or_below	: [ (sD): "drops to or below",		(sDD): "drop to or below",				(sG):sDI,		(sP): i1,					],
		remains_below		: [ (sD): "remains below",			(sDD): "remains below",				(sG):sDI,		(sP): i1,					],
		remains_below_or_equal_to	: [ (sD): "remains below or equal to",	(sDD): "remains below or equal to",		(sG):sDI,		(sP): i1,					],
		rises				: [ (sD): "rises",				(sDD): "rise",					(sG):sDI,							],
		does_not_rise		: [ (sD): "does not rise",			(sDD): "do not rise",				(sG):sDI,							],
		gets				: [ (sD): "gets",										(sG):sM+sV,		(sP): i1					],
		gets_any			: [ (sD): "gets any",									(sG):sM+sV,							],
		event_occurs		: [ (sD): "event occurs",									(sG):sS+sV,						],
		receives			: [ (sD): "receives",			(sDD): "receive",					(sG):"bdis",	(sP): i1,					],
		rises_above			: [ (sD): "rises above",			(sDD): "rise above",				(sG):sDI,		(sP): i1,					],
		rises_to_or_above	: [ (sD): "rises to or above",		(sDD): "rise to or above",				(sG):sDI,		(sP): i1,					],
		remains_above		: [ (sD): "remains above",			(sDD): "remains above",				(sG):sDI,		(sP): i1,					],
		remains_above_or_equal_to	: [ (sD): "remains above or equal to",	(sDD): "remains above or equal to",		(sG):sDI,		(sP): i1,					],
		enters_range		: [ (sD): "enters range",			(sDD): "enter range",				(sG):sDI,		(sP): i2,					],
		remains_outside_of_range	: [ (sD): "remains outside of range",	(sDD): "remain outside of range",			(sG):sDI,		(sP): i2,					],
		exits_range			: [ (sD): "exits range",			(sDD): "exit range",				(sG):sDI,		(sP): i2,					],
		remains_inside_of_range		: [ (sD): "remains inside of range",	(sDD): "remain inside of range",			(sG):sDI,		(sP): i2,					],
		becomes_even		: [ (sD): "becomes even",			(sDD): "become even",				(sG):sDI,							],
		remains_even		: [ (sD): "remains even",			(sDD): "remain even",				(sG):sDI,							],
		becomes_odd			: [ (sD): "becomes odd",			(sDD): "become odd",				(sG):sDI,							],
		remains_odd			: [ (sD): "remains odd",			(sDD): "remain odd",				(sG):sDI,							],
		stays_unchanged		: [ (sD): "stays unchanged",	(sDD): "stay unchanged",				(sG):"bdfis",				(sT): i1,	],
		stays				: [ (sD): "is now and stays",		(sDD): "are now and stay",				(sG):"bdis",	(sP): i1,			(sT): i1,	],
		stays_not			: [ (sD): "is not and stays not",		(sDD): "are not and stay not",			(sG):"bdis",	(sP): i1,			(sT): i1,	],
		stays_away_from		: [ (sD): "is away and stays away from",		(sDD): "are away and stay away from",	(sG):"bdis",	(sP): i1,			(sT): i1,	],
		stays_any_of		: [ (sD): "is any and stays any of",		(sDD): "are any and stay any of",				(sG):"dis",	(sP): i1,	(sM): true,	(sT): i1,	],
		stays_away_from_any_of		: [ (sD): "is away and stays away from any of",	(sDD): "are away and stay away from any of",		(sG):"bdis",	(sP): i1,	(sM): true,	(sT): i1,	],
		stays_equal_to		: [ (sD): "is equal to and stays equal to",	(sDD): "are equal or stay equal to",			(sG):sDI,		(sP): i1,			(sT): i1,	],
		stays_different_than		: [ (sD): "is different and stays different than",	(sDD): "are different and stay different than",		(sG):sDI,		(sP): i1,			(sT): i1,	],
		stays_less_than		: [ (sD): "is less and stays less than",		(sDD): "are less and stay less than",			(sG):sDI,		(sP): i1,			(sT): i1,	],
		stays_less_than_or_equal_to	: [ (sD): "is less or equal and stays less than or equal to",	(sDD): "are less or equal and stay less than or equal to",		(sG):sDI,		(sP): i1,			(sT): i1,	],
		stays_greater_than	: [ (sD): "is greater and stays greater than",	(sDD): "are greater and stay greater than",		(sG):sDI,		(sP): i1,			(sT): i1,	],
		stays_greater_than_or_equal_to	: [ (sD): "is greater or equal and stays greater than or equal to",	(sDD): "are greater or equal stay greater than or equal to",	(sG):sDI,		(sP): i1,			(sT): i1,	],
		stays_inside_of_range		: [ (sD): "is inside and stays inside of range",	(sDD): "are inside and stay inside of range",		(sG):sDI,		(sP): i2,			(sT): i1,	],
		stays_outside_of_range		: [ (sD): "is outside and stays outside of range",	(sDD): "stay outside of range",		(sG):sDI,		(sP): i2,			(sT): i1,	],
		stays_even			: [ (sD): "is even and stays even",		(sDD): "are even and stay even",		(sG):sDI,					(sT): i1,	],
		stays_odd			: [ (sD): "is odd and stays odd",			(sDD): "are odd and stay odd",		(sG):sDI,					(sT): i1,	],
	]
]

/*private Map comparisons(){
	return comparisonsFLD
}*/

@Field final Map<String,Map> functionsFLD=[
	age				: [ (sT): sINT,						],
	previousage		: [ (sT): sINT,	(sD): "previousAge",	],
	previousvalue	: [ (sT): sDYN,	(sD): "previousValue",	],
	newer			: [ (sT): sINT,						],
	older			: [ (sT): sINT,						],
	least			: [ (sT): sDYN,						],
	most			: [ (sT): sDYN,						],
	avg				: [ (sT): sDEC,						],
	variance		: [ (sT): sDEC,						],
	median			: [ (sT): sDEC,						],
	stdev			: [ (sT): sDEC,						],
	round			: [ (sT): sDEC,						],
	ceil			: [ (sT): sDEC,						],
	ceiling			: [ (sT): sDEC,						],
	floor			: [ (sT): sDEC,						],
	sort			: [ (sT): sDYN+'[]',				],
	min				: [ (sT): sDEC,						],
	max				: [ (sT): sDEC,						],
	sum				: [ (sT): sDEC,						],
	count			: [ (sT): sINT,						],
	size			: [ (sT): sINT,						],
	left			: [ (sT): sSTR,						],
	right			: [ (sT): sSTR,						],
	mid				: [ (sT): sSTR,						],
	substring		: [ (sT): sSTR,						],
	sprintf			: [ (sT): sSTR,						],
	format			: [ (sT): sSTR,						],
	string			: [ (sT): sSTR,						],
	replace			: [ (sT): sSTR,						],
	indexof			: [ (sT): sINT,	(sD): "indexOf",		],
	lastindexof		: [ (sT): sINT,	(sD): "lastIndexOf",	],
	concat			: [ (sT): sSTR,						],
	(sTXT)			: [ (sT): sSTR,						],
	lower			: [ (sT): sSTR,						],
	upper			: [ (sT): sSTR,						],
	title			: [ (sT): sSTR,						],
	int				: [ (sT): sINT,						],
	integer			: [ (sT): sINT,						],
	float			: [ (sT): sDEC,						],
	(sDEC)			: [ (sT): sDEC,						],
	number			: [ (sT): sDEC,						],
	(sBOOL)			: [ (sT): sBOOLN,					],
	(sBOOLN)		: [ (sT): sBOOLN,					],
	ispistonpaused	: [ (sT): sBOOLN,					],
	power			: [ (sT): sDEC,						],
	pow				: [ (sT): sDEC,						],
	sin				: [ (sT): sDEC,						],
	asin			: [ (sT): sDEC,						],
	cos				: [ (sT): sDEC,						],
	tan				: [ (sT): sDEC,						],
	atan2			: [ (sT): sDEC,						],
	log				: [ (sT): sDEC,						],
	toradians		: [ (sT): sDEC,	(sD): "toRadians"			],
	todegrees		: [ (sT): sDEC,	(sD): "toDegrees"			],
	sqr				: [ (sT): sDEC,						],
	sqrt			: [ (sT): sDEC,						],
	setvariable		: [ (sT): sBOOLN,	(sD): "setVariable",			],
	dewpoint		: [ (sT): sDEC,	(sD): "dewPoint",	],
	fahrenheit		: [ (sT): sDEC,						],
	celsius			: [ (sT): sDEC,						],
	converttemperatureifneeded	: [ (sT):sDEC, (sD): "convertTemperatureIfNeeded",	],
	dateAdd			: [ (sT): sTIME,		(sD): "dateAdd",		],
	startswith		: [ (sT): sBOOLN,	(sD): "startsWith",	],
	endswith		: [ (sT): sBOOLN,	(sD): "endsWith",		],
	contains		: [ (sT): sBOOLN,					],
	matches			: [ (sT): sBOOLN,					],
	exists			: [ (sT): sBOOLN,					],
	eq				: [ (sT): sBOOLN,					],
	lt				: [ (sT): sBOOLN,					],
	le				: [ (sT): sBOOLN,					],
	gt				: [ (sT): sBOOLN,					],
	ge				: [ (sT): sBOOLN,					],
	not				: [ (sT): sBOOLN,					],
	isempty			: [ (sT): sBOOLN,	(sD): "isEmpty",		],
	if				: [ (sT): sDYN,						],
	datetime		: [ (sT): sDTIME,					],
	date			: [ (sT): sDATE,					],
	time			: [ (sT): sTIME,					],
	addseconds		: [ (sT): sDTIME,	(sD): "addSeconds"		],
	addminutes		: [ (sT): sDTIME,	(sD): "addMinutes"		],
	addhours		: [ (sT): sDTIME,	(sD): "addHours"		],
	adddays			: [ (sT): sDTIME,	(sD): "addDays"		],
	addweeks		: [ (sT): sDTIME,	(sD): "addWeeks"		],
	parsedatetime	: [ (sT): sDTIME,	(sD): "parseDateTime"	],
	isbetween		: [ (sT): sBOOLN,	(sD): "isBetween"		],
	formatduration	: [ (sT): sSTR,	(sD): "formatDuration"	],
	formatdatetime	: [ (sT): sSTR,	(sD): "formatDateTime"	],
	roundtimetominutes	: [ (sT): sDTIME,	(sD): "roundTimeToMinutes"	],
	settzid			: [ (sT): sSTR,	(sD): "setTzid"	],
	random			: [ (sT): sDYN,					],
	strlen			: [ (sT): sINT,					],
	length			: [ (sT): sINT,					],
	coalesce		: [ (sT): sDYN,					],
	weekdayname		: [ (sT): sSTR,	(sD): "weekDayName"	],
	monthname		: [ (sT): sSTR,	(sD): "monthName"		],
	arrayitem		: [ (sT): sDYN,	(sD): "arrayItem"		],
	trim			: [ (sT): sSTR							],
	trimleft		: [ (sT): sSTR,	(sD): "trimLeft"		],
	ltrim			: [ (sT): sSTR							],
	trimright		: [ (sT): sSTR,	(sD): "trimRight"		],
	rtrim			: [ (sT): sSTR							],
	hsltohex		: [ (sT): sSTR,	(sD): "hslToHex"		],
	abs				: [ (sT): sDYN						],
	rangevalue		: [ (sT): sDYN,	(sD): "rangeValue"		],
	rainbowvalue	: [ (sT): sSTR,	(sD): "rainbowValue"	],
	distance		: [ (sT): sDEC						],
	json			: [ (sT): sDYN						],
	urlencode		: [ (sT): sSTR,	(sD): "urlEncode"				],
	encodeuricomponent	: [ (sT): sSTR,	(sD): "encodeURIComponent"			],
//	roomid (roomname) sT: sINT
//	roomname (roomid) sT: sSTR
//	deviceinroom (roomid or name, device) sT: sBOOLN
//	roomexists (roomid or name) sT: sBOOLN
//  devicesinroom(roomid or name)  sT: sDEV
]

/*private Map functions(){
	return functionsFLD
}

def getIftttKey(){
	def module=state.modules?.IFTTT
	return (module && module.connected ? module.key : null)
}*/
/*
def getLifxToken(){
	def module=state.modules?.LIFX
	return (module && module.connected ? module.token : null)
}
*/
private Map getLocationModeOptions(){
	Map result=[:]
	for (mode in location.modes){
		if(mode) result[hashId((Long)mode.getId())]=(String)mode.name
	}
	return result
}
private static Map<String,String> getAlarmSystemStatusActions(){
	return [
		armAway:		"Arm Away", // intrusion
		armHome:		"Arm Home", // intrusion
		armNight:		"Arm Night", // intrusion
		disarm:			"Disarm", // intrusion
		armRules:		"Arm Monitor Rules",
		disarmRules:	"Disarm Monitor Rules",
		disarmAll:		"Disarm All",
		armAll:			"Arm All", // intrusion, smoke, water and HSM monitoring rules
		cancelAlerts:	"Cancel Alerts"
	]
}

/*
private static Map getAlarmSystemStatusOptions(){
	return [
	off:	"Disarmed",
	stay:	"Armed/Stay",
	away:	"Armed/Away"
	]
}
*/

@Field static final String sDISARMD='disarmed'
@Field static final String sCANCEL='cancel'
@Field static final String sALLDISARM='allDisarmed'
@Field static final String sCANRULEA='cancelRuleAlerts'

@CompileStatic
private static String transformHsmStatus(String status){
	if(status==sNL) return "unconfigured"
	switch(status){
		case sDISARMD:
		case sALLDISARM:
			return sOFF
			break
		case "armedHome":
		case "armedNight":
			return "stay"
			break
		case "armedAway":
			return "away"
			break
		default:
			return "Unknown"
	}
}

private static Map getHubitatAlarmSystemStatusOptions(){
	return [
		armedAway:		"Armed Away",
		armingAway:		"Arming Away Pending exit delay",
		armedHome:		"Armed Home",
		armingHome:		"Arming Home pending exit delay",
		armedNight:		"Armed Night",
		armingNight:	"Arming Night pending exit delay",
		(sDISARMD):		"Disarmed",
		(sALLDISARM):	"All Disarmed"
	]
}

private static Map getAlarmSystemAlertOptions(){
	return [
		intrusion:		"Intrusion Away",
		"intrusion-delay": "Intrusion Away Delay",
		"intrusion-home":	"Intrusion Home",
		"intrusion-home-delay": "Intrusion Home Delay",
		"intrusion-night":	"Intrusion Night",
		"intrusion-night-delay": "Intrusion Night Delay",
		smoke:			"Smoke",
		water:			"Water",
		rule:			"Rule",
		cancel:			"Alerts cancelled",
		arming:			"Arming failure"
	]
}

private static Map getAlarmSystemRulesOptions(){
	return [
			armedRule:	"Armed Rule",
			disarmedRule:	"Disarmed Rule"
	]
}

private static Map getAlarmSystemRuleOptions(){
	return [
		cancelRuleAlerts:	"Cancel Rule Alerts"
	]
}


/*
private Map getRoutineOptions(){
	def routines=location.helloHome?.getPhrases()
	def result=[:]
	if(routines){
		routines=routines.sort{ it?.label ?: sBLK }
		for(routine in routines){
			if(routine && routine?.label)
				result[hashId(routine.id)]=routine.label
		}
	}
	return result
}

private Map getAskAlexaOptions(){
	return state.askAlexaMacros ?: [null:"AskAlexa not installed - please install or open AskAlexa"]
}

private Map getEchoSistantOptions(){
	return state.echoSistantProfiles ?: [null:"EchoSistant not installed - please install or open EchoSistant"]
}
*/

import hubitat.helper.RMUtils

private Map getRuleOptions(){
	Map result=[:]
	['4.1', '5.0'].each { String ver ->
		List<Map> rules= (List<Map>)RMUtils.getRuleList(ver ?: sNL)
		rules.each{rule->
			rule.each{pair->
				result[hashId(pair.key)]=pair.value
			}
		}
	}
	return result
}

Map getChildVirtDevices(){
	Map<String,Map> result=virtualDevices()
	Map cleanResult=[:]
	Map defv=[(sN):sA]
	Map t0
	def hasAC, hasO
	//result.each{
	for(it in result){
		t0=[:]
		hasAC=it.value.ac
		hasO=it.value.o
		if(hasAC!=null) t0=t0+[ac:hasAC]
		if(hasO!=null) t0=t0+[(sO):hasO]
		if(t0==[:]) t0=defv
		cleanResult[it.key.toString()]=t0
	}
	return cleanResult
}

// m - momentary - restrict to comparisons that accept virtual devices - g: includes 'v', or datatype match (e) executes
// x - use all comparisons, and exclude by datatype && no g:v (x mean attribute has history?)
private Map<String,Map> virtualDevices(){
	return [
		date:			[ (sN): 'Date',				(sT): sDATE,		],
		datetime:		[ (sN): 'Date & Time',		(sT): sDTIME,	],
		time:			[ (sN): 'Time',				(sT): sTIME,		],
		email:			[ (sN): 'Email',			(sT): 'email',						(sM): true	],
		powerSource:	[ (sN): 'Hub power source',	(sT): sENUM,	(sO): [battery: 'battery', mains: 'mains'],					(sX): true	],
		ifttt:			[ (sN): 'IFTTT',			(sT): sSTR,						(sM): true	],
		mode:			[ (sN): 'Location mode',	(sT): sENUM,	(sO): getLocationModeOptions(),	(sX): true],
		tile:			[ (sN): 'Piston tile',		(sT): sENUM,	(sO): ['1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16'],		(sM): true	],
		pistonResume: 	[ (sN): 'Piston Resumed',	(sT): sSTR,		(sM): true],
// HE specific events
		rule:			[ (sN): 'Rule',				(sT): sENUM,	(sO): getRuleOptions(),		(sM): true ],
		cloudBackup:	[ (sN): 'Cloud Backup',		(sT): sSTR,		(sM): true],
		manualReboot:	[ (sN): 'Manual Reboot',	(sT): sSTR,		(sM): true],
		update:			[ (sN): 'Software Update',	(sT): sSTR,		(sM): true],
		lowMemory:		[ (sN): 'Low Memory',		(sT): sSTR,		(sM): true],
		systemStart:	[ (sN): 'System Start',		(sT): sSTR,		(sM): true],
		severeLoad:		[ (sN): 'Severe Load',		(sT): sSTR,		(sM): true],
		zigbeeOff:		[ (sN): 'Zigbee Off',		(sT): sSTR,		(sM): true],
		zigbeeOn:		[ (sN): 'Zigbee On',		(sT): sSTR,		(sM): true],
		zwaveCrashed:	[ (sN): 'Z-Wave crashed',	(sT): sSTR,		(sM): true],
		sunriseTime:	[ (sN): 'Sunrise Time',		(sT): sSTR,		(sM): true],
		sunsetTime:		[ (sN): 'Sunset Time',		(sT): sSTR,		(sM): true],
//ac - actions. hubitat doesn't reuse the status for actions
		alarmSystemStatus:	[ (sN): 'Hubitat Safety Monitor status',	(sT): sENUM,		(sO): getHubitatAlarmSystemStatusOptions(), ac: getAlarmSystemStatusActions(),		(sX): true],
		alarmSystemEvent:	[ (sN): 'Hubitat Safety Monitor command event',		(sT): sENUM,		(sO): getAlarmSystemStatusActions(),	(sM): true],
		alarmSystemAlert:	[ (sN): 'Hubitat Safety Monitor alert event',		(sT): sENUM,		(sO): getAlarmSystemAlertOptions(),		(sM): true],
		alarmSystemRule:	[ (sN): 'Hubitat Safety Monitor rule event',		(sT): sENUM,		(sO): getAlarmSystemRuleOptions(),		(sM): true],
		alarmSystemRules:	[ (sN): 'Hubitat Safety Monitor rules event',		(sT): sENUM,		(sO): getAlarmSystemRulesOptions(),		(sM): true]
	]
}

@Field final List theColorsFLD=[
		[(sNM): "Alice Blue", (sRGB): "#F0F8FF", (sH): 208, (sS): i100, (sL): 97], [(sNM): "Antique White", (sRGB): "#FAEBD7", (sH): 34, (sS): 78, (sL): 91],
		[(sNM): "Aqua", (sRGB): "#00FFFF", (sH): 180, (sS): i100, (sL): 50], [(sNM): "Aquamarine", (sRGB): "#7FFFD4", (sH): 160, (sS): i100, (sL): 75],
		[(sNM): "Azure", (sRGB): "#F0FFFF", (sH): 180, (sS): i100, (sL): 97], [(sNM): "Beige", (sRGB): "#F5F5DC", (sH): 60, (sS): 56, (sL): 91],
		[(sNM): "Bisque", (sRGB): "#FFE4C4", (sH): 33, (sS): i100, (sL): 88], [(sNM): "Blanched Almond", (sRGB): "#FFEBCD", (sH): 36, (sS): i100, (sL): 90],
		[(sNM): "Blue", (sRGB): "#0000FF", (sH): 240, (sS): i100, (sL): 50], [(sNM): "Blue Violet", (sRGB): "#8A2BE2", (sH): 271, (sS): 76, (sL): 53],
		[(sNM): "Brown", (sRGB): "#A52A2A", (sH): iZ, (sS): 59, (sL): 41], [(sNM): "Burly Wood", (sRGB): "#DEB887", (sH): 34, (sS): 57, (sL): 70],
		[(sNM): "Cadet Blue", (sRGB): "#5F9EA0", (sH): 182, (sS): 25, (sL): 50], [(sNM): "Chartreuse", (sRGB): "#7FFF00", (sH): 90, (sS): i100, (sL): 50],
		[(sNM): "Chocolate", (sRGB): "#D2691E", (sH): 25, (sS): 75, (sL): 47], [(sNM): "Cool White", (sRGB): "#F3F6F7", (sH): 187, (sS): 19, (sL): 96],
		[(sNM): "Coral", (sRGB): "#FF7F50", (sH): 16, (sS): i100, (sL): 66], [(sNM): "Corn Flower Blue", (sRGB): "#6495ED", (sH): 219, (sS): 79, (sL): 66],
		[(sNM): "Corn Silk", (sRGB): "#FFF8DC", (sH): 48, (sS): i100, (sL): 93], [(sNM): "Crimson", (sRGB): "#DC143C", (sH): 348, (sS): 83, (sL): 58],
		[(sNM): "Cyan", (sRGB): "#00FFFF", (sH): 180, (sS): i100, (sL): 50], [(sNM): "Dark Blue", (sRGB): "#00008B", (sH): 240, (sS): i100, (sL): 27],
		[(sNM): "Dark Cyan", (sRGB): "#008B8B", (sH): 180, (sS): i100, (sL): 27], [(sNM): "Dark Golden Rod", (sRGB): "#B8860B", (sH): 43, (sS): 89, (sL): 38],
		[(sNM): "Dark Gray", (sRGB): "#A9A9A9", (sH): iZ, (sS): iZ, (sL): 66], [(sNM): "Dark Green", (sRGB): "#006400", (sH): 120, (sS): i100, (sL): 20],
		[(sNM): "Dark Khaki", (sRGB): "#BDB76B", (sH): 56, (sS): 38, (sL): 58], [(sNM): "Dark Magenta", (sRGB): "#8B008B", (sH): 300, (sS): i100, (sL): 27],
		[(sNM): "Dark Olive Green", (sRGB): "#556B2F", (sH): 82, (sS): 39, (sL): 30], [(sNM): "Dark Orange", (sRGB): "#FF8C00", (sH): 33, (sS): i100, (sL): 50],
		[(sNM): "Dark Orchid", (sRGB): "#9932CC", (sH): 280, (sS): 61, (sL): 50], [(sNM): "Dark Red", (sRGB): "#8B0000", (sH): iZ, (sS): i100, (sL): 27],
		[(sNM): "Dark Salmon", (sRGB): "#E9967A", (sH): 15, (sS): 72, (sL): 70], [(sNM): "Dark Sea Green", (sRGB): "#8FBC8F", (sH): 120, (sS): 25, (sL): 65],
		[(sNM): "Dark Slate Blue", (sRGB): "#483D8B", (sH): 248, (sS): 39, (sL): 39], [(sNM): "Dark Slate Gray", (sRGB): "#2F4F4F", (sH): 180, (sS): 25, (sL): 25],
		[(sNM): "Dark Turquoise", (sRGB): "#00CED1", (sH): 181, (sS): i100, (sL): 41], [(sNM): "Dark Violet", (sRGB): "#9400D3", (sH): 282, (sS): i100, (sL): 41],
		[(sNM): "Daylight White", (sRGB): "#CEF4FD", (sH): 191, (sS): 9, (sL): 90], [(sNM): "Deep Pink", (sRGB): "#FF1493", (sH): 328, (sS): i100, (sL): 54],
		[(sNM): "Deep Sky Blue", (sRGB): "#00BFFF", (sH): 195, (sS): i100, (sL): 50], [(sNM): "Dim Gray", (sRGB): "#696969", (sH): iZ, (sS): iZ, (sL): 41],
		[(sNM): "Dodger Blue", (sRGB): "#1E90FF", (sH): 210, (sS): i100, (sL): 56], [(sNM): "Fire Brick", (sRGB): "#B22222", (sH): iZ, (sS): 68, (sL): 42],
		[(sNM): "Floral White", (sRGB): "#FFFAF0", (sH): 40, (sS): i100, (sL): 97], [(sNM): "Forest Green", (sRGB): "#228B22", (sH): 120, (sS): 61, (sL): 34],
		[(sNM): "Fuchsia", (sRGB): "#FF00FF", (sH): 300, (sS): i100, (sL): 50], [(sNM): "Gainsboro", (sRGB): "#DCDCDC", (sH): iZ, (sS): iZ, (sL): 86],
		[(sNM): "Ghost White", (sRGB): "#F8F8FF", (sH): 240, (sS): i100, (sL): 99], [(sNM): "Gold", (sRGB): "#FFD700", (sH): 51, (sS): i100, (sL): 50],
		[(sNM): "Golden Rod", (sRGB): "#DAA520", (sH): 43, (sS): 74, (sL): 49], [(sNM): "Gray", (sRGB): "#808080", (sH): iZ, (sS): iZ, (sL): 50],
		[(sNM): "Green", (sRGB): "#008000", (sH): 120, (sS): i100, (sL): 25], [(sNM): "Green Yellow", (sRGB): "#ADFF2F", (sH): 84, (sS): i100, (sL): 59],
		[(sNM): "Honeydew", (sRGB): "#F0FFF0", (sH): 120, (sS): i100, (sL): 97], [(sNM): "Hot Pink", (sRGB): "#FF69B4", (sH): 330, (sS): i100, (sL): 71],
		[(sNM): "Indian Red", (sRGB): "#CD5C5C", (sH): iZ, (sS): 53, (sL): 58], [(sNM): "Indigo", (sRGB): "#4B0082", (sH): 275, (sS): i100, (sL): 25],
		[(sNM): "Ivory", (sRGB): "#FFFFF0", (sH): 60, (sS): i100, (sL): 97], [(sNM): "Khaki", (sRGB): "#F0E68C", (sH): 54, (sS): 77, (sL): 75],
		[(sNM): "Lavender", (sRGB): "#E6E6FA", (sH): 240, (sS): 67, (sL): 94], [(sNM): "Lavender Blush", (sRGB): "#FFF0F5", (sH): 340, (sS): i100, (sL): 97],
		[(sNM): "Lawn Green", (sRGB): "#7CFC00", (sH): 90, (sS): i100, (sL): 49], [(sNM): "Lemon Chiffon", (sRGB): "#FFFACD", (sH): 54, (sS): i100, (sL): 90],
		[(sNM): "Light Blue", (sRGB): "#ADD8E6", (sH): 195, (sS): 53, (sL): 79], [(sNM): "Light Coral", (sRGB): "#F08080", (sH): iZ, (sS): 79, (sL): 72],
		[(sNM): "Light Cyan", (sRGB): "#E0FFFF", (sH): 180, (sS): i100, (sL): 94], [(sNM): "Light Golden Rod Yellow", (sRGB): "#FAFAD2", (sH): 60, (sS): 80, (sL): 90],
		[(sNM): "Light Gray", (sRGB): "#D3D3D3", (sH): iZ, (sS): iZ, (sL): 83], [(sNM): "Light Green", (sRGB): "#90EE90", (sH): 120, (sS): 73, (sL): 75],
		[(sNM): "Light Pink", (sRGB): "#FFB6C1", (sH): 351, (sS): i100, (sL): 86], [(sNM): "Light Salmon", (sRGB): "#FFA07A", (sH): 17, (sS): i100, (sL): 74],
		[(sNM): "Light Sea Green", (sRGB): "#20B2AA", (sH): 177, (sS): 70, (sL): 41], [(sNM): "Light Sky Blue", (sRGB): "#87CEFA", (sH): 203, (sS): 92, (sL): 75],
		[(sNM): "Light Slate Gray", (sRGB): "#778899", (sH): 210, (sS): 14, (sL): 53], [(sNM): "Light Steel Blue", (sRGB): "#B0C4DE", (sH): 214, (sS): 41, (sL): 78],
		[(sNM): "Light Yellow", (sRGB): "#FFFFE0", (sH): 60, (sS): i100, (sL): 94], [(sNM): "Lime", (sRGB): "#00FF00", (sH): 120, (sS): i100, (sL): 50],
		[(sNM): "Lime Green", (sRGB): "#32CD32", (sH): 120, (sS): 61, (sL): 50], [(sNM): "Linen", (sRGB): "#FAF0E6", (sH): 30, (sS): 67, (sL): 94],
		[(sNM): "Maroon", (sRGB): "#800000", (sH): iZ, (sS): i100, (sL): 25], [(sNM): "Medium Aquamarine", (sRGB): "#66CDAA", (sH): 160, (sS): 51, (sL): 60],
		[(sNM): "Medium Blue", (sRGB): "#0000CD", (sH): 240, (sS): i100, (sL): 40], [(sNM): "Medium Orchid", (sRGB): "#BA55D3", (sH): 288, (sS): 59, (sL): 58],
		[(sNM): "Medium Purple", (sRGB): "#9370DB", (sH): 260, (sS): 60, (sL): 65], [(sNM): "Medium Sea Green", (sRGB): "#3CB371", (sH): 147, (sS): 50, (sL): 47],
		[(sNM): "Medium Slate Blue", (sRGB): "#7B68EE", (sH): 249, (sS): 80, (sL): 67], [(sNM): "Medium Spring Green", (sRGB): "#00FA9A", (sH): 157, (sS): i100, (sL): 49],
		[(sNM): "Medium Turquoise", (sRGB): "#48D1CC", (sH): 178, (sS): 60, (sL): 55], [(sNM): "Medium Violet Red", (sRGB): "#C71585", (sH): 322, (sS): 81, (sL): 43],
		[(sNM): "Midnight Blue", (sRGB): "#191970", (sH): 240, (sS): 64, (sL): 27], [(sNM): "Mint Cream", (sRGB): "#F5FFFA", (sH): 150, (sS): i100, (sL): 98],
		[(sNM): "Misty Rose", (sRGB): "#FFE4E1", (sH): 6, (sS): i100, (sL): 94], [(sNM): "Moccasin", (sRGB): "#FFE4B5", (sH): 38, (sS): i100, (sL): 85],
		[(sNM): "Navajo White", (sRGB): "#FFDEAD", (sH): 36, (sS): i100, (sL): 84], [(sNM): "Navy", (sRGB): "#000080", (sH): 240, (sS): i100, (sL): 25],
		[(sNM): "Old Lace", (sRGB): "#FDF5E6", (sH): 39, (sS): 85, (sL): 95], [(sNM): "Olive", (sRGB): "#808000", (sH): 60, (sS): i100, (sL): 25],
		[(sNM): "Olive Drab", (sRGB): "#6B8E23", (sH): 80, (sS): 60, (sL): 35], [(sNM): "Orange", (sRGB): "#FFA500", (sH): 39, (sS): i100, (sL): 50],
		[(sNM): "Orange Red", (sRGB): "#FF4500", (sH): 16, (sS): i100, (sL): 50], [(sNM): "Orchid", (sRGB): "#DA70D6", (sH): 302, (sS): 59, (sL): 65],
		[(sNM): "Pale Golden Rod", (sRGB): "#EEE8AA", (sH): 55, (sS): 67, (sL): 80], [(sNM): "Pale Green", (sRGB): "#98FB98", (sH): 120, (sS): 93, (sL): 79],
		[(sNM): "Pale Turquoise", (sRGB): "#AFEEEE", (sH): 180, (sS): 65, (sL): 81], [(sNM): "Pale Violet Red", (sRGB): "#DB7093", (sH): 340, (sS): 60, (sL): 65],
		[(sNM): "Papaya Whip", (sRGB): "#FFEFD5", (sH): 37, (sS): i100, (sL): 92], [(sNM): "Peach Puff", (sRGB): "#FFDAB9", (sH): 28, (sS): i100, (sL): 86],
		[(sNM): "Peru", (sRGB): "#CD853F", (sH): 30, (sS): 59, (sL): 53], [(sNM): "Pink", (sRGB): "#FFC0CB", (sH): 350, (sS): i100, (sL): 88],
		[(sNM): "Plum", (sRGB): "#DDA0DD", (sH): 300, (sS): 47, (sL): 75], [(sNM): "Powder Blue", (sRGB): "#B0E0E6", (sH): 187, (sS): 52, (sL): 80],
		[(sNM): "Purple", (sRGB): "#800080", (sH): 300, (sS): i100, (sL): 25], [(sNM): "Red", (sRGB): "#FF0000", (sH): iZ, (sS): i100, (sL): 50],
		[(sNM): "Rosy Brown", (sRGB): "#BC8F8F", (sH): iZ, (sS): 25, (sL): 65], [(sNM): "Royal Blue", (sRGB): "#4169E1", (sH): 225, (sS): 73, (sL): 57],
		[(sNM): "Saddle Brown", (sRGB): "#8B4513", (sH): 25, (sS): 76, (sL): 31], [(sNM): "Salmon", (sRGB): "#FA8072", (sH): 6, (sS): 93, (sL): 71],
		[(sNM): "Sandy Brown", (sRGB): "#F4A460", (sH): 28, (sS): 87, (sL): 67], [(sNM): "Sea Green", (sRGB): "#2E8B57", (sH): 146, (sS): 50, (sL): 36],
		[(sNM): "Sea Shell", (sRGB): "#FFF5EE", (sH): 25, (sS): i100, (sL): 97], [(sNM): "Sienna", (sRGB): "#A0522D", (sH): 19, (sS): 56, (sL): 40],
		[(sNM): "Silver", (sRGB): "#C0C0C0", (sH): iZ, (sS): iZ, (sL): 75], [(sNM): "Sky Blue", (sRGB): "#87CEEB", (sH): 197, (sS): 71, (sL): 73],
		[(sNM): "Slate Blue", (sRGB): "#6A5ACD", (sH): 248, (sS): 53, (sL): 58], [(sNM): "Slate Gray", (sRGB): "#708090", (sH): 210, (sS): 13, (sL): 50],
		[(sNM): "Snow", (sRGB): "#FFFAFA", (sH): iZ, (sS): i100, (sL): 99], [(sNM): "Soft White", (sRGB): "#B6DA7C", (sH): 83, (sS): 44, (sL): 67],
		[(sNM): "Spring Green", (sRGB): "#00FF7F", (sH): 150, (sS): i100, (sL): 50], [(sNM): "Steel Blue", (sRGB): "#4682B4", (sH): 207, (sS): 44, (sL): 49],
		[(sNM): "Tan", (sRGB): "#D2B48C", (sH): 34, (sS): 44, (sL): 69], [(sNM): "Teal", (sRGB): "#008080", (sH): 180, (sS): i100, (sL): 25],
		[(sNM): "Thistle", (sRGB): "#D8BFD8", (sH): 300, (sS): 24, (sL): 80], [(sNM): "Tomato", (sRGB): "#FF6347", (sH): 9, (sS): i100, (sL): 64],
		[(sNM): "Turquoise", (sRGB): "#40E0D0", (sH): 174, (sS): 72, (sL): 56], [(sNM): "Violet", (sRGB): "#EE82EE", (sH): 300, (sS): 76, (sL): 72],
		[(sNM): "Warm White", (sRGB): "#DAF17E", (sH): 72, (sS): 20, (sL): 72], [(sNM): "Wheat", (sRGB): "#F5DEB3", (sH): 39, (sS): 77, (sL): 83],
		[(sNM): "White", (sRGB): "#FFFFFF", (sH): iZ, (sS): iZ, (sL): i100], [(sNM): "White Smoke", (sRGB): "#F5F5F5", (sH): iZ, (sS): iZ, (sL): 96],
		[(sNM): "Yellow", (sRGB): "#FFFF00", (sH): 60, (sS): i100, (sL): 50], [(sNM): "Yellow Green", (sRGB): "#9ACD32", (sH): 80, (sS): 61, (sL): 50]
]

List getColors(){
	return theColorsFLD
}


private static String sectionTitleStr(String title)	{ return '<h3>'+title+'</h3>' }
private static String inputTitleStr(String title)	{ return '<u>'+title+'</u>' }
private static String pageTitleStr(String title)	{ return '<h2>'+title+'</h2>' }
//private static String paraTitleStr(String title)	{ return '<b>'+title+'</b>' }

@Field static final String sGITP='https://cdn.jsdelivr.net/gh/imnotbob/webCoRE@hubitat-patches/resources/icons/'
private static String gimg(String imgSrc){ return sGITP+imgSrc }

@CompileStatic
private static String imgTitle(String imgSrc,String titleStr,String color=sNL,Integer imgWidth=30,Integer imgHeight=iZ){
	String imgStyle
	imgStyle=sBLK
	String myImgSrc=gimg(imgSrc)
	imgStyle+=imgWidth>iZ ? 'width: '+imgWidth.toString()+'px !important;':sBLK
	imgStyle+=imgHeight>iZ ? imgWidth!=iZ ? sSPC:sBLK+'height:'+imgHeight.toString()+'px !important;':sBLK
	if(color!=sNL) return """<div style="color: ${color}; font-weight:bold;"><img style="${imgStyle}" src="${myImgSrc}"> ${titleStr}</img></div>""".toString()
	else return """<img style="${imgStyle}" src="${myImgSrc}"> ${titleStr}</img>""".toString()
}

static String myObj(obj){
	if(obj instanceof String)return 'String'
	else if(obj instanceof Map)return 'Map'
	else if(obj instanceof List)return 'List'
	else if(obj instanceof ArrayList)return 'ArrayList'
	else if(obj instanceof BigInteger)return 'BigInt'
	else if(obj instanceof Long)return 'Long'
	else if(obj instanceof Integer)return 'Int'
	else if(obj instanceof Boolean)return 'Bool'
	else if(obj instanceof BigDecimal)return 'BigDec'
	else if(obj instanceof Double)return 'Double'
	else if(obj instanceof Float)return 'Float'
	else if(obj instanceof Byte)return 'Byte'
	else if(obj instanceof com.hubitat.app.DeviceWrapper)return 'Device'
	else return 'unknown'
}

/** Returns true if string is encoded device hash */
@CompileStatic
private static Boolean isWcDev(String dev){ return (dev && dev.size()==34 && dev.startsWith(sCLN) && dev.endsWith(sCLN)) }

@Field static final Double d60=60.0D
@Field static final Double d1000=1000.0D
@Field static final Double dSECHR=3600.0D

/** Converts v to either webCoRE or hubitat hub variable types and values */
@SuppressWarnings('GroovyAssignabilityCheck')
@CompileStatic
Map fixHeGType(Boolean toHubV,String typ,v,String dtyp){
	Map ret; ret=[:]
	def myv; myv=v
	String T='T'
	String s9s='9999'
	String format="yyyy-MM-dd'T'HH:mm:ss.sssXX"
	if(toHubV){ // from webcore(9 types) -> hub (5 types + 3 overloads + sDYN becomes sSTR)
		switch(typ){
			case sINT:
				ret=[(sINT):v]
				break
			case sBOOLN:
				ret=[(sBOOLN):v]
				break
			case sDEC:
				ret=['bigdecimal':v]
				break
			case sDEV:
				// HE is a List<String> -> String of words separated by a space (can split())
				List<String> dL= v instanceof List ? (List<String>)v: ((v ? [v]:[]) as List<String>)
				String res; res=sNL
				Boolean ok; ok=true
				for(String d in dL){
					if(ok && isWcDev(d)){
						res=res ? res+sSPC+d:d
					}else ok=false
				}
				if(ok){
					ret=[(sSTR):res]
					break
				}
			case sDYN:
			case sSTR:
				ret=[(sSTR):v]
				break
			case sTIME:
				Double aa
				Boolean fnd; fnd=false
				try{
					aa= v as Double
					fnd=true
				}catch(ignored){}
				Long aaa= fnd ? aa.toLong():("$v".isNumber() ? v as Long: null)
				if(aaa!=null){
					if(aaa<lMSDAY && aaa>=lZ){
						Long t0=getMidnightTime()
						Long a1=t0+aaa
						TimeZone tz=mTZ()
						myv=a1+(tz.getOffset(t0)-tz.getOffset(a1))
					}else{
						Date t1=new Date(aaa)
						Long t2=Math.round((t1.hours*dSECHR+t1.minutes*d60+t1.seconds)*d1000)
						myv=t2
					}
				}else if(eric()) warn "trying to convert nonnumber time",null
			case sDATE:
			case sDTIME: //@@
				Date nTime=new Date((Long)myv)
				SimpleDateFormat formatter=new SimpleDateFormat(format)
				formatter.setTimeZone(mTZ())
				String tt=formatter.format(nTime)
				String[] t1=tt.split(T)

				if(typ==sDATE){
					// comes in long format should be string -> 2021-10-13T99:99:99:999-9999
					String t2=t1[iZ]+'T99:99:99:999-9999'
					ret=[(sDTIME):t2]
					break
				}
				if(typ==sTIME){
					//comes in long format should be string -> 9999-99-99T14:25:09.009-0700
					String t2='9999-99-99T'+t1[i1]
					ret=[(sDTIME):t2]
					break
				}
				//	if(typ==sDTIME){
				// long needs to be string -> 2021-10-13T14:25:09.009-0700
				ret=[(sDTIME):tt]
				break
				//	}
		}
	}else{ // from hub (5 types + 3 overloads ) -> to webcore(8 (cannot restore sDYN)
		switch(typ){
			case sINT:
				ret=[(sINT):v]
				break
			case sBOOLN:
				ret=[(sBOOLN):v]
				break
				// these match
			case 'bigdecimal':
				ret=[(sDEC):v]
				break
			case sSTR:
				// if(dtyp==sDEV)
				List<String> dvL=[]
				Boolean ok; ok=true
				String[] t1=((String)v).split(sSPC)
				Boolean b
				for(String t in t1){
					// sDEV is a string in hub need to detect if it is really devices :xxxxx:
					if(ok && isWcDev(t))b=dvL.push(t)
					else ok=false
				}
				if(ok) ret=[(sDEV):dvL]
				else ret=[(sSTR):v]
				break
				// cannot really return a string to dynamic type here res=sDYN
			case sDTIME: // global times: everything is datetime -> these come in string and needs to be a long of the type
				String iD,mtyp,res
				iD=v
				mtyp=sDTIME
				res=v
				if(iD.endsWith(s9s) || iD.startsWith(s9s)){
					Date nTime=new Date()
					SimpleDateFormat formatter=new SimpleDateFormat(format)
					formatter.setTimeZone(mTZ())
					String tt=formatter.format(nTime)
					String[] mystart=tt.split(T)

					String[] t1=iD.split(T)

					if(iD.endsWith(s9s)){
						mtyp=sDATE
						res=t1[iZ]+T+mystart[i1]
					}else if(iD.startsWith(s9s)){
						mtyp=sTIME
						// we are ignoring the -0000 offset at end and using our current one
						String withOutEnd=t1[i1][iZ..-i6]
						String myend=tt[-i5..-i1]
						res=mystart[iZ]+T+withOutEnd+myend
					}
				}
				Date tt1; tt1=null
				Long lres; lres=null
				try{
					tt1=wtoDateTime(res)
				} catch(e){
					error "datetime parse of hub variable failed",iN2,e
				}
				if(tt1!=null){
					lres=tt1.getTime()
					if(mtyp==sTIME){
						Long m2=Math.round((tt1.hours*dSECHR+tt1.minutes*d60+tt1.seconds)*d1000)
						lres=m2
					}
				}
				//if(eric())warn "returning $lres"
				ret=[(mtyp):lres]
		}
	}
	return ret
}

@CompileStatic
private static String generateMD5_A(String s){
	MessageDigest.getInstance(sMD5).digest(s.bytes).encodeHex().toString()
}

@CompileStatic
private static String md5(String s){
	return MessageDigest.getInstance(sMD5).digest(s.getBytes()).encodeHex().toString()
}

@CompileStatic
static void clearHashMap(String wName){
	theHashMapVFLD[wName]=[:]
	theHashMapVFLD=theHashMapVFLD
}

private String sAppId(){ return ((Long)app.id).toString() }

String hashPID(id){
	if(acctANDloc()) return hashId(locationSid()+id.toString()) //todo still not unique
	return hashId(id)
}

@Field static final String sCR='core.'
@Field static final String sMD5='MD5'

private String hashId(id){
	//enabled hash caching for faster processing
	String result
	String myId=id.toString()
	//String wName= parent ? parent.id.toString() : sAppId()
	String wName= sAppId()
	if(theHashMapVFLD[wName]==null){ theHashMapVFLD[wName]= [:]; theHashMapVFLD=theHashMapVFLD }
	result=sMs(theHashMapVFLD[wName],myId)
	if(result==sNL){
		result=sCLN+md5(sCR + myId)+sCLN
		theHashMapVFLD[wName][myId]=result
		theHashMapVFLD=theHashMapVFLD
	}
	return result
}

@Field static Semaphore theMBLockFLD=new Semaphore(1)

// Memory Barrier
static void mb(String meth=sNL){
	if(theMBLockFLD.tryAcquire()){
		theMBLockFLD.release()
	}
}

@Field static final Long lZ=0L
@Field static final Integer iN1=-1
@Field static final Integer iN2=-2
@Field static final Integer iZ=0
@Field static final Integer i1=1
@Field static final Integer i2=2
@Field static final Integer i3=3
@Field static final Integer i5=5
@Field static final Integer i6=6
@Field static final Integer i32=32
@Field static final Integer i100=100

@Field static final String sSPCSB7='      │'
@Field static final String sSPCSB6='     │'
@Field static final String sSPCS6 ='      '
@Field static final String sSPCS5 ='     '
@Field static final String sSPCST='┌─ '
@Field static final String sSPCSM='├─ '
@Field static final String sSPCSE='└─ '
@Field static final String sNWL='\n'
@Field static final String sDBNL='\n\n • '

@CompileStatic
static String spanStr(Boolean html,String s){ return html? span(s) : s }

@CompileStatic
static String doLineStrt(Integer level,List<Boolean>newLevel){
	String lineStrt; lineStrt=sNWL
	Boolean dB; dB=false
	Integer i
	for(i=iZ;i<level;i++){
		if(i+i1<level){
			if(!newLevel[i]){
				if(!dB){ lineStrt+=sSPCSB7; dB=true }
				else lineStrt+=sSPCSB6
			}else lineStrt+= !dB ? sSPCS6:sSPCS5
		}else lineStrt+= !dB ? sSPCS6:sSPCS5
	}
	return lineStrt
}

@CompileStatic
static String dumpListDesc(List data,Integer level,List<Boolean> lastLevel,String listLabel,Boolean html=false,Boolean reorder=true){
	String str; str=sBLK
	Integer cnt; cnt=i1
	List<Boolean> newLevel=lastLevel

	List list1=data?.collect{it}
	Integer sz=list1.size()
	for(Object par in list1){
		String lbl=listLabel+"[${cnt-i1}]".toString()
		if(par instanceof Map){
			Map newmap=[:]
			newmap[lbl]=(Map)par
			Boolean t1=cnt==sz
			newLevel[level]=t1
			str+=dumpMapDesc(newmap,level,newLevel,cnt,sz,!t1,html,reorder)
		}else if(par instanceof List || par instanceof ArrayList){
			Map newmap=[:]
			newmap[lbl]=par
			Boolean t1=cnt==sz
			newLevel[level]=t1
			str+=dumpMapDesc(newmap,level,newLevel,cnt,sz,!t1,html,reorder)
		}else{
			String lineStrt
			lineStrt=doLineStrt(level,lastLevel)
			lineStrt+=cnt==i1 && sz>i1 ? sSPCST:(cnt<sz ? sSPCSM:sSPCSE)
			str+=spanStr(html, lineStrt+lbl+": ${par} (${objType(par)})".toString() )
		}
		cnt+=i1
	}
	return str
}

@CompileStatic
static String dumpMapDesc(Map data,Integer level,List<Boolean> lastLevel,Integer listCnt=null,Integer listSz=null,Boolean listCall=false,Boolean html=false,Boolean reorder=true){
	String str; str=sBLK
	Integer cnt; cnt=i1
	Integer sz=data?.size()
	Map svMap,svLMap,newMap; svMap=[:]; svLMap=[:]; newMap=[:]
	for(par in data){
		String k=(String)par.key
		def v=par.value
		if(reorder && v instanceof Map){
			svMap+=[(k): v]
		}else if(reorder && (v instanceof List || v instanceof ArrayList)){
			svLMap+=[(k): v]
		}else newMap+=[(k):v]
	}
	newMap+=svMap+svLMap
	Integer lvlpls=level+i1
	for(par in newMap){
		String lineStrt
		List<Boolean> newLevel=lastLevel
		Boolean thisIsLast=cnt==sz && !listCall
		if(level>iZ)newLevel[(level-i1)]=thisIsLast
		Boolean theLast
		theLast=thisIsLast
		if(level==iZ)lineStrt=sDBNL
		else{
			theLast=theLast && thisIsLast
			lineStrt=doLineStrt(level,newLevel)
			if(listSz && listCnt && listCall)lineStrt+=listCnt==i1 && listSz>i1 ? sSPCST:(listCnt<listSz ? sSPCSM:sSPCSE)
			else lineStrt+=((cnt<sz || listCall) && !thisIsLast) ? sSPCSM:sSPCSE
		}
		String k=(String)par.key
		def v=par.value
		String objType=objType(v)
		if(v instanceof Map){
			str+=spanStr(html, lineStrt+"${k}: (${objType})".toString() )
			newLevel[lvlpls]=theLast
			str+=dumpMapDesc((Map)v,lvlpls,newLevel,null,null,false,html,reorder)
		}
		else if(v instanceof List || v instanceof ArrayList){
			str+=spanStr(html, lineStrt+"${k}: [${objType}]".toString() )
			newLevel[lvlpls]=theLast
			str+=dumpListDesc((List)v,lvlpls,newLevel,sBLK,html,reorder)
		}
		else{
			str+=spanStr(html, lineStrt+"${k}: (${v}) (${objType})".toString() )
		}
		cnt+=i1
	}
	return str
}

@CompileStatic
static String objType(obj){ return span(myObj(obj),sCLRORG) }

@CompileStatic
static String getMapDescStr(Map data,Boolean reorder=true){
	List<Boolean> lastLevel=[true]
	String str=dumpMapDesc(data,iZ,lastLevel,null,null,false,true,reorder)
	return str!=sBLK ? str:'No Data was returned'
}

@Field static final String sPDPDEV='pageDumpDevices'
def pageDumpDevices(){
	String wName=sAppId()
	Map result; result=[:]
	result=listAvailableDevices(false, false) +
				[ (sDEVVER): (String)gtSt(sDEVVER) ]
	String message=getMapDescStr(result)
	return dynamicPage((sNM):sPDPDEV,(sTIT):sBLK,uninstall:false){
		section('Devices dump'){
			paragraph message
		}
	}
}

@Field static final String sPDPEXC='pageDumpExecution'
def pageDumpExecution(){
	String wName=sAppId()
	if(p_executionFLD[wName]==null){ p_executionFLD[wName]=(Map)[:]; p_executionFLD=p_executionFLD }
	String t='tot'
	String c='cnt'
	List<Map> b= gtCachedchildApps(wName).collect{ Map it ->
		String pid=sMs(it,'pid')
		Map a= p_executionFLD[wName][pid] ?: [:]
		[ (sID): pid, (sNM): sMs(it,'nlabel'), (c): a[c], (t): a[t]  ]
	}
	LinkedHashMap<String,Map> a; a=[:]
	b.sort{ Map bb -> (bb[c]!= null ? -(Long)bb[c] : bb[c]) }.each { Map it ->
		if((Long)it[c]) //noinspection GrReassignedInClosureLocalVar
			a= a+ [ (sMs(it,sID)): [(sNM):it[sNM], (c): it[c], (t): it[t]] ] as LinkedHashMap<String, Map>
	}
	String message=getMapDescStr(a)
	return dynamicPage((sNM):sPDPEXC,(sTIT):sBLK,uninstall:false){
		section('Piston Execution dump'){
			paragraph message
		}
	}
}
