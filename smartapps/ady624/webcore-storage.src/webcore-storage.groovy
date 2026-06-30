/*
 *  webCoRE - Community's own Rule Engine - Web Edition
 *
 *  Copyright 2016 Adrian Caramaliu <ady624("at" sign goes here)gmail.com>
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
 * Last update June 28, 2026 for Hubitat
 */

//file:noinspection GroovySillyAssignment
//file:noinspection GrDeprecatedAPIUsage
//file:noinspection GroovyDoubleNegation
//file:noinspection GroovyUnusedAssignment
//file:noinspection unused
//file:noinspection SpellCheckingInspection
//file:noinspection GroovyFallthrough

@Field static final String sVER='v0.3.114.20220203'
@Field static final String sHVER='v0.3.114.20230222_HE'

static String version(){ return sVER }
static String HEversion(){ return sHVER }

/******************************************************************************/
/*** webCoRE DEFINITION														***/
/******************************************************************************/
private static String handle(){ return "webCoRE" }
definition(
	name: "${handle()} Storage",
	namespace: "ady624",
	author: "Adrian Caramaliu",
	description: "Do not install this directly, use webCoRE instead",
	category: "Convenience",
	/* icons courtesy of @chauger - thank you */
	iconUrl:gimg('app-CoRE.png'),
	iconX2Url:gimg('app-CoRE@2x.png'),
	iconX3Url:gimg('app-CoRE@3x.png'),
	importUrl: "https://raw.githubusercontent.com/imnotbob/webCoRE/hubitat-patches/smartapps/ady624/webcore-storage.src/webcore-storage.groovy",
	parent: "ady624:${handle()}"
)

preferences {
	//UI pages
	page(name: "pageSettings")
	page(name: "pageSelectDevices")
	page(name: "pageDumpWeather")
}


import groovy.json.JsonOutput
import groovy.transform.Field
import groovy.transform.CompileStatic

import java.security.MessageDigest

@Field static final String sNL=(String)null
@Field static final String sBLK=''
@Field static final String sSPC=' '
@Field static final String sCOLON=':'

/******************************************************************************/
/*** 																		***/
/*** CONFIGURATION PAGES													***/
/*** 																		***/
/******************************************************************************/
def pageSettings(){
	//clear devices cache
	if(!parent || !parent.isInstalled()){
		return dynamicPage(name: "pageSettings", title: sBLK, install: false, uninstall: false){
			section(){
				paragraph "Sorry, you cannot install a piston directly from the Dashboard, please use the webCoRE App instead."
			}
			section(sectionTitleStr("Installing webCoRE")){
				paragraph "If you are trying to install webCoRE, please go back one step and choose webCoRE, not webCoRE Piston. You can also visit wiki.webcore.co for more information on how to install and use webCoRE"
				if(parent){
					def t0 = parent.getWikiUrl()
					href sBLK, title: imgTitle("app-CoRE.png", inputTitleStr("More information")), description: t0, style: "external", url: t0, required: false
				}
			}
		}
	}
	dynamicPage(name: "pageSettings", title: sBLK, install: true, uninstall: false){
/*
		section("Available devices"){
			href "pageSelectDevices", title: "Available devices", description: "Tap here to select which devices are available to pistons"
		}
		section(sectionTitleStr('enable \$weather via ApiXU.com')){
			input "apixuKey", "text", title: "ApiXU key?", description: "ApiXU key", required: false
			input "zipCode", "text", title: "Override Zip code or set city name or latitude,longitude? (Default: ${location.zipCode})", defaultValue: null, required: false
		}
*/
		section(){
			href 'pageDumpWeather', title:'Dump weather structure', description:''
//			paragraph "Under Construction, managed by webCoRE App."
		}
	}
}

private pageSelectDevices(){
	parent.refreshDevices()
	dynamicPage(name: "pageSelectDevices", title: sBLK){
		section(){
			paragraph "Select the devices you want ${handle()} to have access to."
			paragraph "It is a good idea to only select the devices you plan on using with ${handle()} pistons. Pistons will only have access to the devices you selected."
		}

		section ('Select devices by type'){
			paragraph "Most devices should fall into one of these two categories"
				input "dev:actuator", "capability.actuator", multiple: true, title: "Which actuators", required: false, submitOnChange: true
				input "dev:sensor", "capability.sensor", multiple: true, title: "Which sensors", required: false, submitOnChange: true
				input "dev:all", "capability.*", multiple: true, title: "Devices", required: false
			}

		section ('Select devices by capability'){
			paragraph "If you cannot find a device by type, you may try looking for it by category below"
			def d
			d=null
			for (capability in parent.capabilities().findAll{ (!(it.value.d in [null, 'actuators', 'sensors'])) }.sort{ it.value.d }){
				if(capability.value.d != d) input "dev:${capability.key}", "capability.${capability.key}", multiple: true, title: "Which ${capability.value.d}", required: false, submitOnChange: true
				d = capability.value.d
			}
		}
	}
}

private static String sectionTitleStr(String title)	{ return '<h3>'+title+'</h3>' }
private static String inputTitleStr(String title)	{ return '<u>'+title+'</u>' }
//private static String pageTitleStr(String title)	{ return '<h1>'+title+'</h1>' }
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

/******************************************************************************/
/*** 																		***/
/*** INITIALIZATION ROUTINES												***/
/*** 																		***/
/******************************************************************************/


void installed(){
	initialize()
}

void updated(){
	unsubscribe()
	unschedule()
	initialize()
	startWeather()
}

public void startWeather(){
	String myKey = (String)state.apixuKey ?: sNL
	String weatherType = (String)state.weatherType ?: sNL
	if(myKey && weatherType){
		unschedule()
		runEvery30Minutes(updateWeatherD)
		updateWeatherD()
	}
}

public void stopWeather(){
	state.apixuKey = sNL
	unschedule()
	stateRemove("obs")
}

private void initialize(){
	//update parent if this is managed devices.
	//parent.refreshDevices()
	stateRemove("obs")
	stateRemove('hash')
}
private gtSetting(String nm){ return settings."${nm}" }

public void updateWeatherD(){
	String myKey = (String)state.apixuKey ?: sNL
	String weatherType = (String)state.weatherType ?: sNL
	String myZip, myZip1
	myZip = state.zipCode
	myZip1 = state.zipCode1
	if((String)state.zipCode==sNL || (String)state.zipCode == sBLK){
		switch(weatherType){
		case 'apiXU':
			myZip = location.zipCode
			break
		case 'DarkSky':
			myZip = location.latitude.toString()+','+location.longitude.toString()
			break
		case 'OpenWeatherMap':
			myZip = location.latitude.toString().replace(sSPC, sBLK)
			myZip1 = location.longitude.toString().replace(sSPC, sBLK)
		}
	}
	if(myKey && myZip && weatherType){
		String myUri
		switch(weatherType){
		case 'apiXU':
			myUri = 'https://api.apixu.com/v1/forecast.json?key=' +myKey+ '&q=' +myZip+ '&days=7'
			break
		case 'DarkSky':
			myUri = 'https://api.darksky.net/forecast/'+myKey+'/' + myZip + '?units=us&exclude=minutely,flags'
			break
		case 'OpenWeatherMap':
			//myUri = 'https://api.openweathermap.org/data/2.5/onecall?lat=' + myZip + '&lon=' + myZip1 + '&exclude=minutely,hourly&mode=json&units=imperial&appid=' + myKey
			Boolean apiVer = (Boolean)state.apiVer ?: false
			String wunits= (String)state.wunits ?:'imperial'
			myUri = 'https://api.openweathermap.org/data/'+(apiVer ? '3.0':'2.5')+'/onecall?lat=' + myZip + '&lon=' + myZip1 + '&exclude=minutely&mode=json&units='+ wunits + '&appid=' + myKey
		}
		if(myUri){
			Map header; header=[:]
			header += ['Accept-Encoding': 'gzip,deflate']
			Map params = [ uri: myUri, headers: header, timeout:20 ]
			try {
				asynchttpGet('ahttpRequestHandler', params, [tt: 'finishPoll'])
			} catch (e){
				log.error "http call failed for $weatherType weather api: $e"
			}
		}else{ log.error "no weather URI found $weatherType" }
	}else{ log.error "missing some parameter" }
}

@Field static Map theObsFLD

public void ahttpRequestHandler(resp, callbackData){
	Map json; json = [:]
	Map obs; obs = [:]
//	def err
	String weatherType = (String)state.weatherType ?: sNL
	String wunits= (String)state.wunits ?:'imperial'
	Long t=wnow()
	if((resp.status == 200) && resp.data){
		try {
			json = resp.getJson()
		} catch (ignored){
			json = [:]
			return
		}

		if(!json) return

		// add some common fields to all results
		json.time=t
		json.weatherType=weatherType
		json.wunits=wunits
		json.name = location.name
		json.zipCode = location.zipCode

		LinkedHashMap<String,Double> coords = getPosition()
		json.altitude= coords.altitude
		json.azimuth= coords.azimuth

		if(weatherType == 'apiXU'){
			if(json.forecast && json.forecast.forecastday){
				List<Map> lt0=(List<Map>)json.forecast.forecastday
				Integer i
				for(i = 0; i <= 6; i++){
					Integer t0 = lt0[i]?.day?.condition?.code
					if(!t0) continue
					String t1 = getWUIconName(t0,1)
					lt0[i].day.condition.wuicon_name = t1
					String t2 = getWUIconNum(t0)
					lt0[i].day.condition.wuicon = t2
				}
				json.forecast.forecastday=lt0
			}
			Integer tt0 = json.current.condition.code
			String tt1 = getWUIconName(tt0,1)
			json.current.condition.wuicon_name = tt1
			String tt2 = getWUIconNum(tt0)
			json.current.condition.wuicon = tt2
		} else if(weatherType == 'DarkSky'){

			def sunTimes = app.getSunriseAndSunset()
			Long sunrise, sunset, time

			sunrise = sunTimes.sunrise.time
			sunset = sunTimes.sunset.time
			time = t

			Boolean is_day
			is_day = (sunrise <= time && sunset >= time)

			if(json.currently){
				Map t0
				t0 = (Map)json.currently
				String c_code
				c_code = getdsIconCode((String)t0.icon, (String)t0.summary, !is_day)
				json.currently.condition_code = c_code
				json.currently.condition_text = getcondText(c_code)

				c_code = getdsIconCode((String)t0.icon, (String)t0.summary)
				String c1 = getStdIcon(c_code)
				Integer wuCode
				wuCode = getWUConditionCode(c1)
				String tt2
				tt2 = getWUIconNum(wuCode)
				json.currently.code = wuCode
				json.currently.wuicon = tt2

				List<Map> lt0=(List)json?.daily?.data
				t0 = lt0 ? (Map)lt0[0] : [:]
				String f_code
				f_code = getdsIconCode((String)t0?.icon, (String)t0?.summary, !is_day)
				json.currently.forecast_code = f_code
				json.currently.forecast_text = getcondText(f_code)

				f_code = getdsIconCode((String)t0.icon, (String)t0.summary)
				String f1 = getStdIcon(f_code)
				wuCode = getWUConditionCode(f1)
				//String tt1 = getWUIconName(wuCode,1)
				tt2 = getWUIconNum(wuCode)
				json.currently.fcode = wuCode
				//json.currently.wuicon_name = tt1
				json.currently.fwuicon = tt2
			}
			if(json.hourly && json.hourly.data){
				List<Map> lt0=(List<Map>)json?.hourly?.data
				List<Map> lt1=(List<Map>)json?.daily?.data
				Integer i,indx,hr
				hr = new Date(wnow()).hours
				indx = 0
				for(i = 0; i <= 50; i++){
					Map t0 = (Map)lt0[i]
					if(!t0) continue

					Map t1 = lt1 ? (Map)lt1[indx] : [:]

					sunrise = (Long)t1.sunriseTime
					sunset = (Long)t1.sunsetTime
					time = (Long)t0.time.toLong()

					is_day = (sunrise <= time && sunset >= time)

					String c_code
					c_code = getdsIconCode((String)t0.icon, (String)t0.summary, !is_day)
					lt0[i].condition_code = c_code
					lt0[i].condition_text = getcondText(c_code)

					c_code = getdsIconCode((String)t0.icon, (String)t0.summary)
					String c1 = getStdIcon(c_code)
					Integer wuCode
					wuCode = getWUConditionCode(c1)
					String tt2
					tt2 = getWUIconNum(wuCode)
					lt0[i].code = wuCode
					lt0[i].wuicon = tt2

					String f_code
					f_code = getdsIconCode((String)t1?.icon, (String)t1?.summary)
					lt0[i].forecast_code = f_code
					lt0[i].forecast_text = getcondText(f_code)

					f_code = getdsIconCode((String)t1.icon, (String)t1.summary)
					String f1 = getStdIcon(f_code)
					wuCode = getWUConditionCode(f1)
					tt2 = getWUIconNum(wuCode)
					lt0[i].fcode = wuCode
					lt0[i].fwuicon = tt2

					hr+=1
					if(hr != hr%24){
						hr %= 24
						indx += 1
					}
				}
				json.hourly.data=lt0
			}
			if(json.daily && json.daily.data){
				List<Map> lt0=(List<Map>)json?.daily?.data
				Integer i
				for(i = 0; i <= 31; i++){
					Map t0 = lt0 ? (Map)lt0[i] : [:]
					if(!t0) continue
					String c_code = getdsIconCode((String)t0.icon, (String)t0.summary)
					lt0[i].condition_code = c_code
					lt0[i].condition_text = getcondText(c_code)

					String c1 = getStdIcon(c_code)
					Integer wuCode = getWUConditionCode(c1)
					String tt2 = getWUIconNum(wuCode)
					lt0[i].code = wuCode
					lt0[i].wuicon = tt2
				}
				json.daily.data=lt0
			}
//			String jsonData = groovy.json.JsonOutput.toJson(json)
//log.debug jsonData

		} else if(weatherType == 'OpenWeatherMap'){
//			String jsonData = groovy.json.JsonOutput.toJson(json)
//log.debug jsonData


			def sunTimes = app.getSunriseAndSunset()
			Long sunrise, sunset, time
			sunrise = sunTimes.sunrise.time
			sunset = sunTimes.sunset.time
			time = t
			Boolean is_day
			is_day = (sunrise <= time && sunset >= time)

			if(json.current){
				fillCodes((Map)((List)((Map)json.current).weather)[0],is_day)
			}

			if(json.daily){
				Integer i
				for(i=0;i<8;i++){
					fillCodes((Map)((List)((Map)((List)json.daily)[i]).weather)[0],true)
				}
			}

			if(json.hourly){
				Integer i,indx,hr
				hr = new Date(wnow()).hours
				indx = 0
				for(i=0;i<48;i++){
					Map t0=(Map)((List)json.hourly)[i] ?: [:]
					if(!t0) continue
					Map t1 = (Map)((List)json.daily)[indx] ?: [:]
					if(!t1) continue

					sunrise = (Integer)t1.sunrise
					sunset = (Integer)t1.sunset
					time = (Integer)t0.dt
					is_day = (sunrise <= time && sunset >= time)

					fillCodes((Map)((List)t0.weather)[0],is_day)
					hr+=1
					if(hr != hr%24){
						hr %= 24
						indx += 1
					}
				}
			}
		}
	}else{
		if(resp.hasError()){
			log.error "$weatherType http Response Status: ${resp.status}  error Message: ${resp.getErrorMessage()}"
			return
		}
		log.error "$weatherType no data: ${resp.status}  resp.data: ${resp.data} resp.json: ${resp.json}"
		return
	}
	theObsFLD = json
	def wdev=parent?.getWeatDev()
	if(wdev) wdev.setVar('updated', "${t}".toString())
	//log.debug "$json"
}

void fillCodes(Map t0,Boolean is_day){
	String c_code
	c_code = getCondCode((Integer)t0.id ?: 999,is_day.toString())
	t0.condition_code = c_code
	t0.condition_text = getcondText(c_code)

	c_code = getCondCode((Integer)t0.id ?: 999,sTRU)
	String c1 = getStdIcon(c_code)
	Integer wuCode
	wuCode = getWUConditionCode(c1)
	String tt2
	tt2 = getWUIconNum(wuCode)
	t0.code = wuCode
	t0.wuicon = tt2
}

///
/// Calculations
// based on SunCalc by Justin Walker
///

// date/time constants and conversions
static Double dayMs() { return 1000.0D * 60 * 60 * 24 }

static Double J1970() { return 2440588.0D }

static Double J2000() { return 2451545.0D }

static Double rad() { return  Math.PI / 180.0D }

static Double e() { return  rad() * 23.4397D } // obliquity of the Earth

static Double toJulian() {
	Date date = new Date()
	Double l = date.getTime().toDouble() / dayMs() - 0.5D + J1970()
	return l
}

static Date fromJulian(Double j)  { return new Date(Math.round((j + 0.5D - J1970()) * dayMs()) ) }
static Double toDays(){ return toJulian() - J2000() }

// general calculations for position

static Double rightAscension(Double l, Double b) { return Math.atan2(Math.sin(l) * Math.cos(e()) - Math.tan(b) * Math.sin(e()), Math.cos(l)) }
static Double declination(Double l, Double b)    { return Math.asin(Math.sin(b) * Math.cos(e()) + Math.cos(b) * Math.sin(e()) * Math.sin(l)) }

static Double azimuth(Double H, Double phi, Double dec)  { return Math.atan2(Math.sin(H), Math.cos(H) * Math.sin(phi) - Math.tan(dec) * Math.cos(phi)) }
static Double altitude(Double H, Double phi, Double dec) { return Math.asin(Math.sin(phi) * Math.sin(dec) + Math.cos(phi) * Math.cos(dec) * Math.cos(H)) }

static Double siderealTime(Double d, Double lw) { return rad() * (280.16D + 360.9856235D * d) - lw }

// general sun calculations

static Double solarMeanAnomaly(Double d) { return rad() * (357.5291D + 0.98560028D * d) }

static Double eclipticLongitude(Double M) {

	Double C = rad() * (1.9148D * Math.sin(M) + 0.02D * Math.sin(2.0D * M) + 0.0003D * Math.sin(3.0D * M)) // equation of center
	Double P = rad() * 102.9372D // perihelion of the Earth

	return M + C + P + Math.PI
}

static LinkedHashMap<String,Double> sunCoords(Double d) {

	Double M = solarMeanAnomaly(d)
	Double L = eclipticLongitude(M)

	return [dec: declination(L, 0D), ra: rightAscension(L, 0D)]
}

// calculates sun position for a given date and latitude/longitude

LinkedHashMap<String,Double> getPosition() {

	Double lng = ((BigDecimal)location.longitude).toDouble()
	Double lat = ((BigDecimal)location.latitude).toDouble()

	Double lw  = rad() * -lng
	Double phi = rad() * lat
	Double d   = toDays()
	LinkedHashMap<String,Double> c  = sunCoords(d)
	Double H  = siderealTime(d, lw) - c.ra

	Double az; az = azimuth(H, phi, c.dec)
	az = (az * 180.0D / Math.PI) + 180.0D

	Double al; al = altitude(H, phi, c.dec)
	al = al * 180.0D / Math.PI

	return [
			azimuth: az,
			altitude: al,
	]
}

public Map getWData(){
	Map obs; obs = [:]
	String weatherType = (String)state.weatherType ?: sNL
	if(theObsFLD){
		if(weatherType == 'apiXU'){
			obs = theObsFLD
			String t0 = "${obs.current.last_updated}".toString()
			String t1 = formatDt(Date.parse("yyyy-MM-dd HH:mm", t0))
			Integer s = GetTimeDiffSeconds(t1, sNL, "getApiXUData").toInteger()
			if(s > (60*60*6)){ // if really old
				log.warn "removing very old weather data $t0   $s"
				theObsFLD = null
				obs = [:]
			}
		}
		if(weatherType == 'DarkSky' || weatherType == 'OpenWeatherMap'){
			obs = theObsFLD
		}
	}
	return obs
}

@Field static final Integer iZ=0
@Field static final Integer i1=1
@Field static final Integer i2=2
@Field static final Integer i3=3

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

def pageDumpWeather(){
	Map obs = theObsFLD
	String message=getMapDescStr(obs)
	return dynamicPage(name:'pageDumpWeather', title:sBLK, uninstall:false){
		section('Weather Data dump'){
			paragraph message
		}
	}
}

private static TimeZone mTZ(){ return TimeZone.getDefault() } // (TimeZone)location.timeZone

static String getDtNow(){
	Date now = new Date()
	return formatDt(now)
}

import java.text.SimpleDateFormat
//import groovy.time.*

static String formatDt(Date dt){
	SimpleDateFormat tf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy")
	tf.setTimeZone(mTZ())
	return tf.format(dt)
}

static Long GetTimeDiffSeconds(String strtDate, String stpDate=sNL, String methName=sNL){
	if((strtDate && !stpDate) || (strtDate && stpDate)){
		//if(strtDate?.contains("dtNow")){ return 10000 }
		Date now = new Date()
		String stopVal = stpDate ? stpDate.toString() : formatDt(now)
		Long start = Date.parse("E MMM dd HH:mm:ss z yyyy", strtDate).getTime()
		Long stop = Date.parse("E MMM dd HH:mm:ss z yyyy", stopVal).getTime()
		Long diff = Math.round((stop - start) / 1000L)
		return diff
	}else{ return null }
}

public void settingsToState(String myKey, setval){
	if(!myKey) return
	if(setval!=null){
		atomicState."${myKey}" = setval
		state."${myKey}" = setval
	} else state.remove("${myKey}" as String)
}

void stateRemove(String key){
	if(!key) return
	state.remove(key)
}

/******************************************************************************/
/*** 																		***/
/*** PUBLIC METHODS															***/
/*** 																		***/
/******************************************************************************/

public getStorageSettings(){
 	settings
}

public void initData(devices, contacts){
	if(devices){
		for(item in devices){
			if(item){
				def deviceType = item.key.replace('dev:', 'capability.')
				def deviceIdList = item.value.collect{ it.id }
				app.updateSetting(item.key, [type: deviceType, value: deviceIdList])
			}
		}
	}
}

public Map listAvailableDevices(Boolean raw=false, Integer offset=0){
	Long time = wnow()
	Map response; response = [:]
	List myDevices = (List)settings.findAll{ it.key.startsWith("dev:") }.collect{ it.value }.flatten().sort{ it.getDisplayName() }
	List devices
	devices = (List)myDevices.unique{ it.id }
	if(raw){
		response = devices.collectEntries{ dev -> [(hashId(dev.id)): dev]}
	}else{
		Integer deviceCount = devices.size()
		//Map<String,Map> overrides = commandOverrides()
		response.devices = [:]
		if(devices){
			devices = devices[offset..-1]
			Integer accSize = iZ
			response.complete = !devices.indexed().find{ idx, dev ->
				String hid = hashId(dev.id)
				Map details = getDevDetails(dev, true)
				String detailsJson = JsonOutput.toJson(details)
				response.devices[hid] = details
				accSize += hid.length() + 4 + detailsJson.length()
				Boolean stop = false
				if(accSize > 50 * 1024) stop = true
				if(wnow() - time > 4000) stop = true
				if(stop && idx < devices.size()-1){
					response.nextOffset = offset + idx + 1
					return true
				}
				false
			}
		} else response.complete=true
		log.debug "Generated list of ${offset}-${offset + devices.size()} of ${deviceCount} devices in ${wnow() - time}ms. Data size is ${response.toString().size()}"
	}
	return response
}

Map getDevDetails(dev, Boolean transform=false){
	Map<String,Map> overrides=commandOverrides()
	return [
			n: dev.getDisplayName(),
			cn: dev.getCapabilities()*.name,
			a: dev.getSupportedAttributes().unique{ (String)it.name }.collect{
//				Map x=[
				[
					n: (String)it.name,
					t: it.getDataType(),
					o: it.getValues()
				]
//				try {
//					x.v = dev.currentValue(x.n)
//				} catch(ignored){}
//				x
			},
			c: dev.getSupportedCommands().unique{ transform ? transformCommand(it, overrides) : it.getName() }.collect{[
					n: transform ? transformCommand(it, overrides) : it.getName(),
					p: it.getArguments()
			]}
	]
}

private static String transformCommand(command, Map<String,Map> overrides){
	Map override = overrides[(String)command.getName()]
	if(override && (String)override.s == command.getArguments()?.toString()){
		return (String)override.r
	}
	return (String)command.getName()
}

public Map getDashboardData(){
//	def start = wnow()
	return settings.findAll{ it.key.startsWith("dev:") }.collect{ it.value }.flatten().collectEntries{ dev -> [(hashId(dev.id)): dev]}.collectEntries{ id, dev ->
		[ (id): dev.getSupportedAttributes().collect{ it.name }.unique().collectEntries{
			def value
			value=null
			try { value = dev.currentValue(it) } catch (ignored){ value = null}
			return [ (it) : value]
		}]
	}
}

public String mem(Boolean showBytes = true){
	Integer bytes = state.toString().length()
	return Math.round(100.00 * (bytes/ 100000.00)) + "%${showBytes ? " ($bytes bytes)" : ""}"
}

/* Push command has multiple overloads in hubitat */
private static Map<String, Map> commandOverrides(){
	return ( [ //s: command signature
		push    : [c: "push",   s: null , r: "pushMomentary"],
		flash   : [c: "flash",  s: null , r: "flashNative"] //flash native command conflicts with flash emulated command. Also needs "o" option on command described later
	] ) as HashMap
}

/******************************************************************************/
/***																		***/
/*** SECURITY METHODS														***/
/***																		***/
/******************************************************************************/
@CompileStatic
private static String md5(String md5){
	MessageDigest md=MessageDigest.getInstance('MD5')
	byte[] array=md.digest(md5.getBytes())
	String result
	result=sBLK
	Integer l=array.size()
	for(Integer i=iZ; i<l;++i){
		result+=Integer.toHexString((array[i] & 0xFF)| 0x100).substring(i1,i3)
	}
	return result
}

@Field static Map theHashMapFLD=[:]

static private String hashId(id){
	//enabled hash caching for faster processing
	String myId=id.toString()
	String result
	result = (String)theHashMapFLD[myId]
	if(result==sNL){
		result=sCOLON+md5('core.'+myId)+sCOLON
		theHashMapFLD[myId]=result
	}
	return result
}

/*private isHubitat(){
 	return hubUID != null
}*/

String getWUIconName(Integer condition_code, Integer is_day=0)	 {
	Integer cC = condition_code
	String wuIcon
	wuIcon = (conditionFactor[cC] ? (String)conditionFactor[cC][2] : sBLK)
	if(is_day != 1 && wuIcon) wuIcon = 'nt_' + wuIcon
	//log.info("getWUIconName Input: code: " + condition_code + ' is_day: ' + is_day.toString() + ' result: '+wuIcon)
	return wuIcon
}

@Field static volatile Map<String,Integer> conditionFactorRevFLD = [:]

Integer getWUConditionCode(String code){
	if(!conditionFactorRevFLD){
		Map<String,Integer> m=[:]
		conditionFactor.each{ Integer k, List v -> m[(String)v[2]]=k }
		conditionFactorRevFLD=m
	}
	Integer res=conditionFactorRevFLD[code]
	return res!=null ? res : 0
}

@Field final Map<Integer,List>	conditionFactor = [
	1000: ['Sunny', 1, 'sunny'],						1003: ['Partly cloudy', 0.8, 'partlycloudy'],
	1006: ['Cloudy', 0.6, 'cloudy'],					1009: ['Overcast', 0.5, 'cloudy'],
	1030: ['Mist', 0.5, 'fog'],						1063: ['Patchy rain possible', 0.8, 'chancerain'],
	1066: ['Patchy snow possible', 0.6, 'chancesnow'],			1069: ['Patchy sleet possible', 0.6, 'chancesleet'],
	1072: ['Patchy freezing drizzle possible', 0.4, 'chancesleet'],		1087: ['Thundery outbreaks possible', 0.2, 'chancetstorms'],
	1114: ['Blowing snow', 0.3, 'snow'],					1117: ['Blizzard', 0.1, 'snow'],
	1135: ['Fog', 0.2, 'fog'],						1147: ['Freezing fog', 0.1, 'fog'],
	1150: ['Patchy light drizzle', 0.8, 'rain'],				1153: ['Light drizzle', 0.7, 'rain'],
	1168: ['Freezing drizzle', 0.5, 'sleet'],				1171: ['Heavy freezing drizzle', 0.2, 'sleet'],
	1180: ['Patchy light rain', 0.8, 'rain'],				1183: ['Light rain', 0.7, 'rain'],
	1186: ['Moderate rain at times', 0.5, 'rain'],				1189: ['Moderate rain', 0.4, 'rain'],
	1192: ['Heavy rain at times', 0.3, 'rain'],				1195: ['Heavy rain', 0.2, 'rain'],
	1198: ['Light freezing rain', 0.7, 'sleet'],				1201: ['Moderate or heavy freezing rain', 0.3, 'sleet'],
	1204: ['Light sleet', 0.5, 'sleet'],					1207: ['Moderate or heavy sleet', 0.3, 'sleet'],
	1210: ['Patchy light snow', 0.8, 'flurries'],				1213: ['Light snow', 0.7, 'snow'],
	1216: ['Patchy moderate snow', 0.6, 'snow'],				1219: ['Moderate snow', 0.5, 'snow'],
	1222: ['Patchy heavy snow', 0.4, 'snow'],				1225: ['Heavy snow', 0.3, 'snow'],
	1237: ['Ice pellets', 0.5, 'sleet'],					1240: ['Light rain shower', 0.8, 'rain'],
	1243: ['Moderate or heavy rain shower', 0.3, 'rain'],			1246: ['Torrential rain shower', 0.1, 'rain'],
	1249: ['Light sleet showers', 0.7, 'sleet'],				1252: ['Moderate or heavy sleet showers', 0.5, 'sleet'],
	1255: ['Light snow showers', 0.7, 'snow'],				1258: ['Moderate or heavy snow showers', 0.5, 'snow'],
	1261: ['Light showers of ice pellets', 0.7, 'sleet'],			1264: ['Moderate or heavy showers of ice pellets',0.3, 'sleet'],
	1273: ['Patchy light rain with thunder', 0.5, 'tstorms'],		1276: ['Moderate or heavy rain with thunder', 0.3, 'tstorms'],
	1279: ['Patchy light snow with thunder', 0.5, 'tstorms'],		1282: ['Moderate or heavy snow with thunder', 0.3, 'tstorms']
]

@Field static volatile Map<Integer,String> imgNamesCodeMapFLD = [:]
@Field static volatile Map<Integer,Map> imgNamesMapFLD = [:]

private static void buildImgNamesMaps(List<Map> src){
	Map<Integer,String> cm=[:]
	Map<Integer,Map> dm=[:]
	src.each{ Map it ->
		Integer c=(Integer)it.code; Integer d=(Integer)it.day
		if(!cm.containsKey(c)) cm[c]=(String)it.img
		if(!dm[c]) dm[c]=[:]
		((Map)dm[c])[d]=it.img
	}
	imgNamesCodeMapFLD=cm; imgNamesMapFLD=dm
}

String getWUIconNum(Integer wCode){
	if(!imgNamesCodeMapFLD) buildImgNamesMaps(imgNames)
	String res=(String)imgNamesCodeMapFLD[wCode]
	return res ?: '44'
}

private String getImgName(Integer wCode, is_day){
	if(!imgNamesCodeMapFLD) buildImgNamesMaps(imgNames)
	String url="https://cdn.rawgit.com/adey/bangali/master/resources/icons/weather/"
	Map dayMap=(Map)imgNamesMapFLD[wCode]
	String img=dayMap ? (String)dayMap[(Integer)is_day] : null
	return url+(img ?: 'na')+'.png'
}

@Field final List<Map> imgNames = [
	[code: 1000, day: 1, img: '32', ],	// DAY - Sunny
	[code: 1003, day: 1, img: '30', ],	// DAY - Partly cloudy
	[code: 1006, day: 1, img: '28', ],	// DAY - Cloudy
	[code: 1009, day: 1, img: '26', ],	// DAY - Overcast
	[code: 1030, day: 1, img: '20', ],	// DAY - Mist
	[code: 1063, day: 1, img: '39', ],	// DAY - Patchy rain possible
	[code: 1066, day: 1, img: '41', ],	// DAY - Patchy snow possible
	[code: 1069, day: 1, img: '41', ],	// DAY - Patchy sleet possible
	[code: 1072, day: 1, img: '39', ],	// DAY - Patchy freezing drizzle possible
	[code: 1087, day: 1, img: '38', ],	// DAY - Thundery outbreaks possible
	[code: 1114, day: 1, img: '15', ],	// DAY - Blowing snow
	[code: 1117, day: 1, img: '16', ],	// DAY - Blizzard
	[code: 1135, day: 1, img: '21', ],	// DAY - Fog
	[code: 1147, day: 1, img: '21', ],	// DAY - Freezing fog
	[code: 1150, day: 1, img: '39', ],	// DAY - Patchy light drizzle
	[code: 1153, day: 1, img: '11', ],	// DAY - Light drizzle
	[code: 1168, day: 1, img: '8', ],	// DAY - Freezing drizzle
	[code: 1171, day: 1, img: '10', ],	// DAY - Heavy freezing drizzle
	[code: 1180, day: 1, img: '39', ],	// DAY - Patchy light rain
	[code: 1183, day: 1, img: '11', ],	// DAY - Light rain
	[code: 1186, day: 1, img: '39', ],	// DAY - Moderate rain at times
	[code: 1189, day: 1, img: '12', ],	// DAY - Moderate rain
	[code: 1192, day: 1, img: '39', ],	// DAY - Heavy rain at times
	[code: 1195, day: 1, img: '12', ],	// DAY - Heavy rain
	[code: 1198, day: 1, img: '8', ],	// DAY - Light freezing rain
	[code: 1201, day: 1, img: '10', ],	// DAY - Moderate or heavy freezing rain
	[code: 1204, day: 1, img: '5', ],	// DAY - Light sleet
	[code: 1207, day: 1, img: '6', ],	// DAY - Moderate or heavy sleet
	[code: 1210, day: 1, img: '41', ],	// DAY - Patchy light snow
	[code: 1213, day: 1, img: '18', ],	// DAY - Light snow
	[code: 1216, day: 1, img: '41', ],	// DAY - Patchy moderate snow
	[code: 1219, day: 1, img: '16', ],	// DAY - Moderate snow
	[code: 1222, day: 1, img: '41', ],	// DAY - Patchy heavy snow
	[code: 1225, day: 1, img: '16', ],	// DAY - Heavy snow
	[code: 1237, day: 1, img: '18', ],	// DAY - Ice pellets
	[code: 1240, day: 1, img: '11', ],	// DAY - Light rain shower
	[code: 1243, day: 1, img: '12', ],	// DAY - Moderate or heavy rain shower
	[code: 1246, day: 1, img: '12', ],	// DAY - Torrential rain shower
	[code: 1249, day: 1, img: '5', ],	// DAY - Light sleet showers
	[code: 1252, day: 1, img: '6', ],	// DAY - Moderate or heavy sleet showers
	[code: 1255, day: 1, img: '16', ],	// DAY - Light snow showers
	[code: 1258, day: 1, img: '16', ],	// DAY - Moderate or heavy snow showers
	[code: 1261, day: 1, img: '8', ],	// DAY - Light showers of ice pellets
	[code: 1264, day: 1, img: '10', ],	// DAY - Moderate or heavy showers of ice pellets
	[code: 1273, day: 1, img: '38', ],	// DAY - Patchy light rain with thunder
	[code: 1276, day: 1, img: '35', ],	// DAY - Moderate or heavy rain with thunder
	[code: 1279, day: 1, img: '41', ],	// DAY - Patchy light snow with thunder
	[code: 1282, day: 1, img: '18', ],	// DAY - Moderate or heavy snow with thunder
	[code: 1000, day: 0, img: '31', ],	// NIGHT - Clear
	[code: 1003, day: 0, img: '29', ],	// NIGHT - Partly cloudy
	[code: 1006, day: 0, img: '27', ],	// NIGHT - Cloudy
	[code: 1009, day: 0, img: '26', ],	// NIGHT - Overcast
	[code: 1030, day: 0, img: '20', ],	// NIGHT - Mist
	[code: 1063, day: 0, img: '45', ],	// NIGHT - Patchy rain possible
	[code: 1066, day: 0, img: '46', ],	// NIGHT - Patchy snow possible
	[code: 1069, day: 0, img: '46', ],	// NIGHT - Patchy sleet possible
	[code: 1072, day: 0, img: '45', ],	// NIGHT - Patchy freezing drizzle possible
	[code: 1087, day: 0, img: '47', ],	// NIGHT - Thundery outbreaks possible
	[code: 1114, day: 0, img: '15', ],	// NIGHT - Blowing snow
	[code: 1117, day: 0, img: '16', ],	// NIGHT - Blizzard
	[code: 1135, day: 0, img: '21', ],	// NIGHT - Fog
	[code: 1147, day: 0, img: '21', ],	// NIGHT - Freezing fog
	[code: 1150, day: 0, img: '45', ],	// NIGHT - Patchy light drizzle
	[code: 1153, day: 0, img: '11', ],	// NIGHT - Light drizzle
	[code: 1168, day: 0, img: '8', ],	// NIGHT - Freezing drizzle
	[code: 1171, day: 0, img: '10', ],	// NIGHT - Heavy freezing drizzle
	[code: 1180, day: 0, img: '45', ],	// NIGHT - Patchy light rain
	[code: 1183, day: 0, img: '11', ],	// NIGHT - Light rain
	[code: 1186, day: 0, img: '45', ],	// NIGHT - Moderate rain at times
	[code: 1189, day: 0, img: '12', ],	// NIGHT - Moderate rain
	[code: 1192, day: 0, img: '45', ],	// NIGHT - Heavy rain at times
	[code: 1195, day: 0, img: '12', ],	// NIGHT - Heavy rain
	[code: 1198, day: 0, img: '8', ],	// NIGHT - Light freezing rain
	[code: 1201, day: 0, img: '10', ],	// NIGHT - Moderate or heavy freezing rain
	[code: 1204, day: 0, img: '5', ],	// NIGHT - Light sleet
	[code: 1207, day: 0, img: '6', ],	// NIGHT - Moderate or heavy sleet
	[code: 1210, day: 0, img: '41', ],	// NIGHT - Patchy light snow
	[code: 1213, day: 0, img: '18', ],	// NIGHT - Light snow
	[code: 1216, day: 0, img: '41', ],	// NIGHT - Patchy moderate snow
	[code: 1219, day: 0, img: '16', ],	// NIGHT - Moderate snow
	[code: 1222, day: 0, img: '41', ],	// NIGHT - Patchy heavy snow
	[code: 1225, day: 0, img: '16', ],	// NIGHT - Heavy snow
	[code: 1237, day: 0, img: '18', ],	// NIGHT - Ice pellets
	[code: 1240, day: 0, img: '11', ],	// NIGHT - Light rain shower
	[code: 1243, day: 0, img: '12', ],	// NIGHT - Moderate or heavy rain shower
	[code: 1246, day: 0, img: '12', ],	// NIGHT - Torrential rain shower
	[code: 1249, day: 0, img: '5', ],	// NIGHT - Light sleet showers
	[code: 1252, day: 0, img: '6', ],	// NIGHT - Moderate or heavy sleet showers
	[code: 1255, day: 0, img: '16', ],	// NIGHT - Light snow showers
	[code: 1258, day: 0, img: '16', ],	// NIGHT - Moderate or heavy snow showers
	[code: 1261, day: 0, img: '8', ],	// NIGHT - Light showers of ice pellets
	[code: 1264, day: 0, img: '10', ],	// NIGHT - Moderate or heavy showers of ice pellets
	[code: 1273, day: 0, img: '47', ],	// NIGHT - Patchy light rain with thunder
	[code: 1276, day: 0, img: '35', ],	// NIGHT - Moderate or heavy rain with thunder
	[code: 1279, day: 0, img: '46', ],	// NIGHT - Patchy light snow with thunder
	[code: 1282, day: 0, img: '18', ]	// NIGHT - Moderate or heavy snow with thunder
]

// From Darksky.net driver for HE https://community.hubitat.com/t/release-darksky-net-weather-driver-no-pws-required/22699
@SuppressWarnings('GroovyFallthrough')
static String getdsIconCode(String iicon='unknown', String idcs='unknown', Boolean isNight=false){
	String dcs, icon
	dcs=idcs
	icon=iicon
	String unk='unknown'
	if(dcs==null) dcs=unk
	if(icon==null) icon=unk
	switch(icon){
		case 'rain':
		// rain=[Possible Light Rain, Light Rain, Rain, Heavy Rain, Drizzle, Light Rain and Breezy, Light Rain and Windy,
		//       Rain and Breezy, Rain and Windy, Heavy Rain and Breezy, Rain and Dangerously Windy, Light Rain and Dangerously Windy],
			if(dcs == 'Drizzle'){
				icon = 'drizzle'
			} else if       (dcs.startsWith('Light Rain')){
				icon = 'lightrain'
				if(dcs.contains('Breezy')) icon += 'breezy'
				else if(dcs.contains('Windy')) icon += 'windy'
			} else if       (dcs.startsWith('Heavy Rain')){
				icon = 'heavyrain'
				if	(dcs.contains('Breezy')) icon += 'breezy'
				else if(dcs.contains('Windy')) icon += 'windy'
			} else if       (dcs == 'Possible Light Rain'){
				icon = 'chancelightrain'
			} else if       (dcs.startsWith('Possible')){
				icon = 'chancerain'
			} else if       (dcs.startsWith('Rain')){
				if	(dcs.contains('Breezy')) icon += 'breezy'
				else if(dcs.contains('Windy')) icon += 'windy'
			}
			break
		case 'snow':
			if      (dcs == 'Light Snow') icon = 'lightsnow'
			else if(dcs == 'Flurries') icon = 'flurries'
			else if(dcs == 'Possible Light Snow') icon = 'chancelightsnow'
			else if(dcs.startsWith('Possible Light Snow')){
				if      (dcs.contains('Breezy')) icon = 'chancelightsnowbreezy'
				else if(dcs.contains('Windy')) icon = 'chancelightsnowwindy'
			} else if(dcs.startsWith('Possible')) icon = 'chancesnow'
			break
		case 'sleet':
			if(dcs.startsWith('Possible')) icon = 'chancesleet'
			else if(dcs.startsWith('Light')) icon = 'lightsleet'
			break
		case 'thunderstorm':
			if(dcs.startsWith('Possible')) icon = 'chancetstorms'
			break
		case 'partly-cloudy-night':
			if(dcs.contains('Mostly Cloudy')) icon = 'mostlycloudy'
			else icon = 'partlycloudy'
			break
		case 'partly-cloudy-day':
			if(dcs.contains('Mostly Cloudy')) icon = 'mostlycloudy'
			else icon = 'partlycloudy'
			break
		case 'cloudy-night':
			icon = 'cloudy'
			break
		case 'cloudy':
		case 'cloudy-day':
			icon = 'cloudy'
			break
		case 'clear-night':
			icon = 'clear'
			break
		case 'clear':
		case 'clear-day':
			icon = 'clear'
			break
		case 'fog':
		case 'wind':
			// wind=[Windy and Overcast, Windy and Mostly Cloudy, Windy and Partly Cloudy, Breezy and Mostly Cloudy, Breezy and Partly Cloudy,
			// Breezy and Overcast, Breezy, Windy, Dangerously Windy and Overcast, Windy and Foggy, Dangerously Windy and Partly Cloudy, Breezy and Foggy]}
			if(dcs.contains('Windy')){
				// icon = 'wind'
				if	(dcs.contains('Overcast'))	icon = 'windovercast'
				else if(dcs.contains('Mostly Cloudy')) icon = 'windmostlycloudy'
				else if(dcs.contains('Partly Cloudy')) icon = 'windpartlycloudy'
				else if(dcs.contains('Foggy'))	   icon = 'windfoggy'
			} else if(dcs.contains('Breezy')){
				icon = 'breezy'
				if	(dcs.contains('Overcast'))	icon = 'breezyovercast'
				else if(dcs.contains('Mostly Cloudy')) icon = 'breezymostlycloudy'
				else if(dcs.contains('Partly Cloudy')) icon = 'breezypartlycloudy'
				else if(dcs.contains('Foggy'))		icon = 'breezyfoggy'
			}
			break
		case '':
			icon = unk
			break
		default:
			icon = unk
	}
	if(isNight) icon = 'nt_' + icon
	return icon
}

@Field static volatile Map<String,Map> LUTableMapFLD = [:]

private void ensureLUTableMap(){
	if(!LUTableMapFLD) LUTableMapFLD=LUTable.collectEntries{ [((String)it.ccode): it] }
}

String getcondText(String wCode){
	ensureLUTableMap()
	String code=wCode.contains('nt_') ? wCode.substring(3, wCode.size()) : wCode
	Map LUitem=(Map)LUTableMapFLD[code]
	return LUitem ? (String)LUitem.ctext : sBLK
}

String getStdIcon(String code){
	ensureLUTableMap()
	Map LUitem=(Map)LUTableMapFLD[code]
	return LUitem ? (String)LUitem.stdIcon : sBLK
}

@Field final List<Map> LUTable = [
[ ccode: 'breezy', altIcon: '23.png', ctext: 'Breezy', owmIcon: '50d', stdIcon: 'partlycloudy', luxpercent: 0.8 ],
[ ccode: 'breezyfoggy', altIcon: '48.png', ctext: 'Breezy and Foggy', owmIcon: '50d', stdIcon: 'fog', luxpercent: 0.2 ],
[ ccode: 'breezymostlycloudy', altIcon: '51.png', ctext: 'Breezy and Mostly Cloudy', owmIcon: '04d', stdIcon: 'cloudy', luxpercent: 0.6 ],
[ ccode: 'breezyovercast', altIcon: '49.png', ctext: 'Breezy and Overcast', owmIcon: '04d', stdIcon: 'cloudy', luxpercent: 0.6 ],
[ ccode: 'breezypartlycloudy', altIcon: '53.png', ctext: 'Breezy and Partly Cloudy', owmIcon: '03d', stdIcon: 'partlycloudy', luxpercent: 0.8 ],
[ ccode: 'chancelightrain', altIcon: '39.png', ctext: 'Chance of Light Rain', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'chancelightsnow', altIcon: '41.png', ctext: 'Possible Light Snow', owmIcon: '13d', stdIcon: 'snow', luxpercent: 0.3 ],
[ ccode: 'chancelightsnowbreezy', altIcon: '54.png', ctext: 'Possible Light Snow and Breezy', owmIcon: '13d', stdIcon: 'snow', luxpercent: 0.3 ],
[ ccode: 'chancerain', altIcon: '39.png', ctext: 'Chance of Rain', owmIcon: '10d', stdIcon: 'chancerain', luxpercent: 0.7 ],
[ ccode: 'chancesleet', altIcon: '41.png', ctext: 'Chance of Sleet', owmIcon: '13d', stdIcon: 'chancesleet', luxpercent: 0.7 ],
[ ccode: 'chancesnow', altIcon: '41.png', ctext: 'Chance of Snow', owmIcon: '13d', stdIcon: 'chancesnow', luxpercent: 0.3 ],
[ ccode: 'chancetstorms', altIcon: '38.png', ctext: 'Chance of Thunderstorms', owmIcon: '11d', stdIcon: 'chancetstorms', luxpercent: 0.2 ],
[ ccode: 'chancelightsnowwindy', altIcon: '54.png', ctext: 'Possible Light Snow and Windy', owmIcon: '13d', stdIcon: 'chancesnow', luxpercent: 0.3 ],
[ ccode: 'clear', altIcon: '32.png', ctext: 'Clear', owmIcon: '01d', stdIcon: 'sunny', luxpercent: 1 ],
[ ccode: 'cloudy', altIcon: '26.png', ctext: 'Overcast', owmIcon: '04d', stdIcon: 'cloudy', luxpercent: 0.6 ],
[ ccode: 'drizzle', altIcon: '9.png', ctext: 'Drizzle', owmIcon: '09d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'flurries', altIcon: '13.png', ctext: 'Snow Flurries', owmIcon: '13d', stdIcon: 'flurries', luxpercent: 0.4 ],
[ ccode: 'fog', altIcon: '19.png', ctext: 'Foggy', owmIcon: '50d', stdIcon: 'fog', luxpercent: 0.2 ],
[ ccode: 'heavyrain', altIcon: '12.png', ctext: 'Heavy Rain', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'heavyrainbreezy', altIcon: '1.png', ctext: 'Heavy Rain and Breezy', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'heavyrainwindy', altIcon: '1.png', ctext: 'Heavy Rain and Windy', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'lightrain', altIcon: '11.png', ctext: 'Light Rain', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'lightrainbreezy', altIcon: '2.png', ctext: 'Light Rain and Breezy', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'lightrainwindy', altIcon: '2.png', ctext: 'Light Rain and Windy', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'lightsleet', altIcon: '8.png', ctext: 'Light Sleet', owmIcon: '13d', stdIcon: 'sleet', luxpercent: 0.5 ],
[ ccode: 'lightsnow', altIcon: '14.png', ctext: 'Light Snow', owmIcon: '13d', stdIcon: 'snow', luxpercent: 0.3 ],
[ ccode: 'mostlycloudy', altIcon: '28.png', ctext: 'Mostly Cloudy', owmIcon: '04d', stdIcon: 'cloudy', luxpercent: 0.6 ],
[ ccode: 'partlycloudy', altIcon: '30.png', ctext: 'Partly Cloudy', owmIcon: '03d', stdIcon: 'partlycloudy', luxpercent: 0.8 ],
[ ccode: 'rain', altIcon: '12.png', ctext: 'Rain', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'rainbreezy', altIcon: '1.png', ctext: 'Rain and Breezy', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'rainwindy', altIcon: '1.png', ctext: 'Rain and Windy', owmIcon: '10d', stdIcon: 'rain', luxpercent: 0.5 ],
[ ccode: 'sleet', altIcon: '10.png', ctext: 'Sleet', owmIcon: '13d', stdIcon: 'sleet', luxpercent: 0.5 ],
[ ccode: 'snow', altIcon: '15.png', ctext: 'Snow', owmIcon: '13d', stdIcon: 'snow', luxpercent: 0.3 ],
[ ccode: 'sunny', altIcon: '36.png', ctext: 'Sunny', owmIcon: '01d', stdIcon: 'sunny', luxpercent: 1 ],
[ ccode: 'thunderstorm', altIcon: '0.png', ctext: 'Thunderstorm', owmIcon: '11d', stdIcon: 'tstorms', luxpercent: 0.3 ],
[ ccode: 'wind', altIcon: '23.png', ctext: 'Windy', owmIcon: '50d', stdIcon: 'partlycloudy', luxpercent: 0.8 ],
[ ccode: 'windfoggy', altIcon: '23.png', ctext: 'Windy and Foggy', owmIcon: '50d', stdIcon: 'fog', luxpercent: 0.2 ],
[ ccode: 'windmostlycloudy', altIcon: '51.png', ctext: 'Windy and Mostly Cloudy', owmIcon: '50d', stdIcon: 'cloudy', luxpercent: 0.6 ],
[ ccode: 'windovercast', altIcon: '49.png', ctext: 'Windy and Overcast', owmIcon: '50d', stdIcon: 'cloudy', luxpercent: 0.6 ],
[ ccode: 'windpartlycloudy', altIcon: '53.png', ctext: 'Windy and Partly Cloudy', owmIcon: '50d', stdIcon: 'partlycloudy', luxpercent: 0.8 ],
[ ccode: 'nt_breezy', altIcon: '23.png', ctext: 'Breezy', owmIcon: '50n', stdIcon: 'nt_partlycloudy', luxpercent: 0 ],
[ ccode: 'nt_breezyfoggy', altIcon: '48.png', ctext: 'Breezy and Foggy', owmIcon: '50n', stdIcon: 'nt_fog', luxpercent: 0 ],
[ ccode: 'nt_breezymostlycloudy', altIcon: '50.png', ctext: 'Breezy and Mostly Cloudy', owmIcon: '04n', stdIcon: 'nt_cloudy', luxpercent: 0 ],
[ ccode: 'nt_breezyovercast', altIcon: '49.png', ctext: 'Breezy and Overcast', owmIcon: '04n', stdIcon: 'nt_cloudy', luxpercent: 0 ],
[ ccode: 'nt_breezypartlycloudy', altIcon: '52.png', ctext: 'Breezy and Partly Cloudy', owmIcon: '03n', stdIcon: 'nt_partlycloudy', luxpercent: 0 ],
[ ccode: 'nt_chancelightrain', altIcon: '45.png', ctext: 'Chance of Light Rain', owmIcon: '09n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_chancelightsnow', altIcon: '46.png', ctext: 'Possible Light Snow', owmIcon: '13n', stdIcon: 'nt_snow', luxpercent: 0 ],
[ ccode: 'nt_chancelightsnowbreezy', altIcon: '55.png', ctext: 'Possible Light Snow and Breezy', owmIcon: '13n', stdIcon: 'nt_snow', luxpercent: 0 ],
[ ccode: 'nt_chancerain', altIcon: '39.png', ctext: 'Chance of Rain', owmIcon: '09n', stdIcon: 'nt_chancerain', luxpercent: 0 ],
[ ccode: 'nt_chancesleet', altIcon: '46.png', ctext: 'Chance of Sleet', owmIcon: '13n', stdIcon: 'nt_chancesleet', luxpercent: 0 ],
[ ccode: 'nt_chancesnow', altIcon: '46.png', ctext: 'Chance of Snow', owmIcon: '13n', stdIcon: 'nt_chancesnow', luxpercent: 0 ],
[ ccode: 'nt_chancetstorms', altIcon: '47.png', ctext: 'Chance of Thunderstorms', owmIcon: '11n', stdIcon: 'nt_chancetstorms', luxpercent: 0 ],
[ ccode: 'nt_chancelightsnowwindy', altIcon: '55.png', ctext: 'Possible Light Snow and Windy', owmIcon: '13n', stdIcon: 'nt_chancesnow', luxpercent: 0 ],
[ ccode: 'nt_clear', altIcon: '31.png', ctext: 'Clear', owmIcon: '01n', stdIcon: 'nt_sunny', luxpercent: 0 ],
[ ccode: 'nt_cloudy', altIcon: '26.png', ctext: 'Overcast', owmIcon: '04n', stdIcon: 'nt_cloudy', luxpercent: 0 ],
[ ccode: 'nt_drizzle', altIcon: '9.png', ctext: 'Drizzle', owmIcon: '09n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_flurries', altIcon: '13.png', ctext: 'Flurries', owmIcon: '13n', stdIcon: 'nt_flurries', luxpercent: 0 ],
[ ccode: 'nt_fog', altIcon: '22.png', ctext: 'Foggy', owmIcon: '50n', stdIcon: 'nt_fog', luxpercent: 0 ],
[ ccode: 'nt_heavyrain', altIcon: '12.png', ctext: 'Heavy Rain', owmIcon: '10n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_heavyrainbreezy', altIcon: '1.png', ctext: 'Heavy Rain and Breezy', owmIcon: '10n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_heavyrainwindy', altIcon: '1.png', ctext: 'Heavy Rain and Windy', owmIcon: '10n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_lightrain', altIcon: '11.png', ctext: 'Light Rain', owmIcon: '09n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_lightrainbreezy', altIcon: '11.png', ctext: 'Light Rain and Breezy', owmIcon: '09n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_lightrainwindy', altIcon: '11.png', ctext: 'Light Rain and Windy', owmIcon: '09n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_lightsleet', altIcon: '46.png', ctext: 'Sleet', owmIcon: '13n', stdIcon: 'nt_sleet', luxpercent: 0 ],
[ ccode: 'nt_lightsnow', altIcon: '14.png', ctext: 'Light Snow', owmIcon: '13n', stdIcon: 'nt_snow', luxpercent: 0 ],
[ ccode: 'nt_mostlycloudy', altIcon: '27.png', ctext: 'Mostly Cloudy', owmIcon: '04n', stdIcon: 'nt_cloudy', luxpercent: 0 ],
[ ccode: 'nt_partlycloudy', altIcon: '29.png', ctext: 'Partly Cloudy', owmIcon: '03n', stdIcon: 'nt_partlycloudy', luxpercent: 0 ],
[ ccode: 'nt_rain', altIcon: '11.png', ctext: 'Rain', owmIcon: '10n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_rainbreezy', altIcon: '2.png', ctext: 'Rain and Breezy', owmIcon: '10n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_rainwindy', altIcon: '2.png', ctext: 'Rain and Windy', owmIcon: '10n', stdIcon: 'nt_rain', luxpercent: 0 ],
[ ccode: 'nt_sleet', altIcon: '46.png', ctext: 'Sleet', owmIcon: '13n', stdIcon: 'nt_sleet', luxpercent: 0 ],
[ ccode: 'nt_snow', altIcon: '46.png', ctext: 'Snow', owmIcon: '13n', stdIcon: 'nt_snow', luxpercent: 0 ],
[ ccode: 'nt_thunderstorm', altIcon: '0.png', ctext: 'Thunderstorm', owmIcon: '11n', stdIcon: 'nt_tstorms', luxpercent: 0 ],
[ ccode: 'nt_wind', altIcon: '23.png', ctext: 'Windy', owmIcon: '50n', stdIcon: 'nt_tstorms', luxpercent: 0 ],
[ ccode: 'nt_windfoggy', altIcon: '48.png', ctext: 'Windy and Foggy', owmIcon: '50n', stdIcon: 'nt_fog', luxpercent: 0 ],
[ ccode: 'nt_windmostlycloudy', altIcon: '50.png', ctext: 'Windy and Mostly Cloudy', owmIcon: '50n', stdIcon: 'nt_cloudy', luxpercent: 0 ],
[ ccode: 'nt_windovercast', altIcon: '49.png', ctext: 'Windy and Overcast', owmIcon: '50n', stdIcon: 'nt_cloudy', luxpercent: 0 ],
[ ccode: 'nt_windpartlycloudy', altIcon: '52.png', ctext: 'Windy and Partly Cloudy', owmIcon: '50n', stdIcon: 'nt_cloudy', luxpercent: 0 ],
]



@Field static volatile Map<Integer,Map> LUTable1MapFLD = [:]

String getCondCode(Integer cid, String iconTOD){
	if(!LUTable1MapFLD) LUTable1MapFLD=LUTable1.collectEntries{ [((Integer)it.id): it] }
	Map LUitem=(Map)LUTable1MapFLD[cid]
	return iconTOD==sTRU ? (LUitem ? (String)LUitem.sId : sNPNG) : (LUitem ? (String)LUitem.sIn : sNPNG)
}

@Field static final String sTRU='true'
@Field static final String sFLS='false'
@Field static final String sNPNG='na.png'
@Field static final String s11D='11d.png'
@Field static final String s11N='11n.png'
@Field static final String sCTS='chancetstorms'
@Field static final String sNCTS='nt_chancetstorms'
@Field static final String sRAIN='rain'
@Field static final String sNRAIN='nt_rain'
@Field static final String sPCLDY='partlycloudy'
@Field static final String sNPCLDY='nt_partlycloudy'
@Field static final String s23='23.png'
@Field static final String s9='9.png'
@Field static final String s39='39.png'

@Field final List<Map>  LUTable1 =       [
		[id: 200, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 201, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 202, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 210, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 211, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 212, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 221, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 230, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 231, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 232, OWMd: s11D, OWMn: s11N, Icd: '38.png', Icn: '47.png', luxp: 0.2, sId: sCTS, sIn: sNCTS],
		[id: 300, OWMd: '09d.png', OWMn: '09n.png', Icd: s9, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 301, OWMd: '09d.png', OWMn: '09n.png', Icd: s9, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 302, OWMd: '09d.png', OWMn: '09n.png', Icd: s9, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 310, OWMd: '09d.png', OWMn: '09n.png', Icd: s9, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 311, OWMd: '09d.png', OWMn: '09n.png', Icd: s9, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 312, OWMd: '09d.png', OWMn: '09n.png', Icd: s9, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 313, OWMd: '09d.png', OWMn: '09n.png', Icd: s9, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 314, OWMd: '09d.png', OWMn: '09n.png', Icd: s9, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 321, OWMd: '09d.png', OWMn: '09n.png', Icd: s9, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 500, OWMd: '10d.png', OWMn: '09n.png', Icd: s39, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 501, OWMd: '10d.png', OWMn: '10n.png', Icd: s39, Icn: '11.png', luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 502, OWMd: '10d.png', OWMn: '10n.png', Icd: s39, Icn: '11.png', luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 503, OWMd: '10d.png', OWMn: '10n.png', Icd: s39, Icn: '11.png', luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 504, OWMd: '10d.png', OWMn: '10n.png', Icd: s39, Icn: '11.png', luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 511, OWMd: '10d.png', OWMn: '10n.png', Icd: s39, Icn: '11.png', luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 520, OWMd: '10d.png', OWMn: '09n.png', Icd: s39, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 521, OWMd: '10d.png', OWMn: '10n.png', Icd: s39, Icn: '11.png', luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 522, OWMd: '10d.png', OWMn: '10n.png', Icd: s39, Icn: '11.png', luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 531, OWMd: '10d.png', OWMn: '09n.png', Icd: s39, Icn: s9, luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 600, OWMd: '13d.png', OWMn: '13n.png', Icd: '13.png', Icn: '46.png', luxp: 0.4, sId: 'flurries', sIn: 'nt_snow'],
		[id: 601, OWMd: '13d.png', OWMn: '13n.png', Icd: '14.png', Icn: '46.png', luxp: 0.3, sId: 'snow', sIn: 'nt_snow'],
		[id: 602, OWMd: '13d.png', OWMn: '13n.png', Icd: '16.png', Icn: '46.png', luxp: 0.3, sId: 'snow', sIn: 'nt_snow'],
		[id: 611, OWMd: '13d.png', OWMn: '13n.png', Icd: s9, Icn: '46.png', luxp: 0.5, sId: sRAIN, sIn: 'nt_snow'],
		[id: 612, OWMd: '13d.png', OWMn: '13n.png', Icd: '8.png', Icn: '46.png', luxp: 0.5, sId: 'sleet', sIn: 'nt_snow'],
		[id: 613, OWMd: '13d.png', OWMn: '13n.png', Icd: s9, Icn: '46.png', luxp: 0.5, sId: sRAIN, sIn: 'nt_snow'],
		[id: 615, OWMd: '13d.png', OWMn: '13n.png', Icd: s39, Icn: '45.png', luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 616, OWMd: '13d.png', OWMn: '13n.png', Icd: s39, Icn: '45.png', luxp: 0.5, sId: sRAIN, sIn: sNRAIN],
		[id: 620, OWMd: '13d.png', OWMn: '13n.png', Icd: '13.png', Icn: '46.png', luxp: 0.4, sId: 'flurries', sIn: 'nt_snow'],
		[id: 621, OWMd: '13d.png', OWMn: '13n.png', Icd: '16.png', Icn: '46.png', luxp: 0.3, sId: 'snow', sIn: 'nt_snow'],
		[id: 622, OWMd: '13d.png', OWMn: '13n.png', Icd: '42.png', Icn: '42.png', luxp: 0.6, sId: 'snow', sIn: 'nt_snow'],
		[id: 701, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 711, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 721, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 731, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 741, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 751, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 761, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 762, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 771, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 781, OWMd: '50d.png', OWMn: '50n.png', Icd: s23, Icn: s23, luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 800, OWMd: '01d.png', OWMn: '01n.png', Icd: '32.png', Icn: '31.png', luxp: 1, sId: 'clear', sIn: 'nt_clear'],
		[id: 801, OWMd: '02d.png', OWMn: '02n.png', Icd: '34.png', Icn: '33.png', luxp: 0.9, sId: sPCLDY, sIn: sNPCLDY],
		[id: 802, OWMd: '03d.png', OWMn: '03n.png', Icd: '30.png', Icn: '29.png', luxp: 0.8, sId: sPCLDY, sIn: sNPCLDY],
		[id: 803, OWMd: '04d.png', OWMn: '04n.png', Icd: '28.png', Icn: '27.png', luxp: 0.6, sId: 'mostlycloudy',sIn:'nt_mostlycloudy'],
		[id: 804, OWMd: '04d.png', OWMn: '04n.png', Icd: '26.png', Icn: '26.png', luxp: 0.6, sId: 'cloudy', sIn: 'nt_cloudy'],
		[id: 999, OWMd: '50d.png', OWMn: '50n.png', Icd: sNPNG, Icn: sNPNG, luxp: 1.0, sId: 'unknown', sIn: 'unknown'],
]


Long wnow(){ return (Long)now() }
/******************************************************************************/
/***																		***/
/*** END OF CODE															***/
/***																		***/
/******************************************************************************/
