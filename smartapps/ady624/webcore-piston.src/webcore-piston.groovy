/*
 *  webCoRE - Community's own Rule Engine - Web Edition for HE
 *
 *  Copyright 2016 Adrian Caramaliu <ady624("at" sign goes here)gmail.com>
 *
 *  webCoRE Piston
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
 *  along with this program.  If not see <http://www.gnu.org/licenses/>.
 *
 * Last update July 5, 2026 for Hubitat
 */

//file:noinspection GroovySillyAssignment
//file:noinspection GrDeprecatedAPIUsage
//file:noinspection GroovyDoubleNegation
//file:noinspection GroovyUnusedAssignment
//file:noinspection unused
//file:noinspection SpellCheckingInspection
//file:noinspection GroovyFallthrough
//file:noinspection GrMethodMayBeStatic
//file:noinspection GroovyAssignabilityCheck
//file:noinspection UnnecessaryQualifiedReference

@Field static final String sVER='v0.3.114.20220203'
@Field static final String sHVER='v0.3.114.20240115_HE'

static String version(){ return sVER }
static String HEversion(){ return sHVER }

/** webCoRE DEFINITION	**/

static String handle(){ return 'webCoRE' }

import groovy.json.*
import hubitat.helper.RMUtils
import groovy.transform.CompileStatic
import groovy.transform.Field

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.*
//import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.concurrent.Semaphore

definition(
	name:handle()+' Piston',
	namespace:'ady624',
	author:'Adrian Caramaliu',
	description:'Do not install this directly, use webCoRE instead',
	category:'Convenience',
	iconUrl:gimg('app-CoRE.png'),
	iconX2Url:gimg('app-CoRE@2x.png'),
	iconX3Url:gimg('app-CoRE@3x.png'),
	importUrl:'https://raw.githubusercontent.com/imnotbob/webCoRE/hubitat-patches/smartapps/ady624/webcore-piston.src/webcore-piston.groovy',
	parent: "ady624:${handle()}"
)

@CompileStatic
static Boolean eric(){ return false }
@CompileStatic
static Boolean eric1(){ return false }

//#include ady624.webCoRElib1

@Field static final String sNL=(String)null
@Field static final String sSNULL='null'
@Field static final String sBOOLN='boolean'
@Field static final String sLONG='long'
@Field static final String sSTR='string'
@Field static final String sINT='integer'
@Field static final String sDEC='decimal'
@Field static final String sVEC='vector3'
@Field static final String sDYN='dynamic'
@Field static final String sDTIME='datetime'
@Field static final String sTRUE='true'
@Field static final String sFALSE='false'
@Field static final String sDATE='date'
@Field static final String sDEV='device'
@Field static final String sDBL='double'
@Field static final String sNUMBER='number'
@Field static final String sFLOAT='float'
@Field static final String sVARIABLE='variable'
@Field static final String sMODE='mode'
@Field static final String sINT32='int32'
@Field static final String sINT64='int64'
@Field static final String sBOOL='bool'
@Field static final String sPHONE='phone'
@Field static final String sURI='uri'
@Field static final String sTEXT='text'
@Field static final String sENUM='enum'
@Field static final String sDURATION='duration'

@Field static final String sNM='name'
@Field static final String sREQ='required'
@Field static final String sTYPE='type'
@Field static final String sVAL='value'
@Field static final String sTIT='title'

@Field static final String sERROR='error'
@Field static final String sINFO='info'
@Field static final String sWARN='warn'
@Field static final String sTRC='trace'
@Field static final String sDBG='debug'

@Field static final String sMETA='meta'
@Field static final String sMEM='mem'
@Field static final String sMEMORY='memory'
@Field static final String sRUNS='runStats'
@Field static final String sCURS='curStat'
@Field static final String sALLDEVS='allDevices'

@Field static final String sON='on'
@Field static final String sOFF='off'
@Field static final String sSWITCH='switch'

@Field static final String sTRIG='trigger'
@Field static final String sCONDITION='condition'

@Field static final String sTIME='time'
@Field static final String sPWRSRC='powerSource'

@Field static final String sASYNCREP='wc_async_reply'

@Field static final String sTHREAX='threeAxis'
// derived from threeAxis
@Field static final String sORIENT='orientation'
@Field static final String sAXISX='axisX'
@Field static final String sAXISY='axisY'
@Field static final String sAXISZ='axisZ'

@Field static final String sRULE='rule'
@Field static final String sHSMSTS='hsmStatus'
@Field static final String sALRMSSTATUS='alarmSystemStatus'
@Field static final String sHSMALRT='hsmAlert'
@Field static final String sALRMSYSALRT='alarmSystemAlert'
@Field static final String sHSMSARM='hsmSetArm'
@Field static final String sALRMSYSEVT='alarmSystemEvent'
@Field static final String sALRMSYSRULE='alarmSystemRule'
@Field static final String sALRMSYSRULES='alarmSystemRules'
@Field static final String sHSMRULE='hsmRule'
@Field static final String sHSMRULES='hsmRules'

// additional device attributes that do not trigger
@Field static final String sSTS='$status'
@Field static final String sLSTACTIVITY='lastActivityWC'
@Field static final String sROOMID='roomIdWC'
@Field static final String sROOMNM='roomNameWC'

@Field static final String sCLRC='clearc'
@Field static final String sCLRL='clearl'
@Field static final String sCLRA='cleara'

@Field static final String sBLK=''
@Field static final String sCOMMA=','
@Field static final String sSPC=' '
@Field static final String sDI='di'
@Field static final String sVT='vt'
@Field static final String sA='a'
@Field static final String sB='b'
@Field static final String sC='c'
@Field static final String sD='d'
@Field static final String sE='e'
@Field static final String sF='f'
@Field static final String sG='g'
@Field static final String sH='h'
@Field static final String sI='i'
@Field static final String sK='k'
@Field static final String sL='l'
@Field static final String sM='m'
@Field static final String sN='n'
@Field static final String sO='o'
@Field static final String sP='p'
@Field static final String sR='r'
@Field static final String sS='s'
@Field static final String sT='t'
@Field static final String sU='u'
@Field static final String sV='v'
@Field static final String sW='w'
@Field static final String sX='x'
@Field static final String sY='y'
@Field static final String sZ='z'
@Field static final String sXI='xi'
@Field static final String sMS='ms'
@Field static final String sLB='['
@Field static final String sRB=']'
@Field static final String sLRB='[]'
@Field static final String sOB='{'
@Field static final String sCB='}'
@Field static final String sAT='@'
@Field static final String sAT2='@@'
@Field static final String sDLR='$'

// system variables
@Field static final String sDARGS='$args'
@Field static final String sDLLRDEVICE='$device'
@Field static final String sDLLRDEVS='$devices'
@Field static final String sDLLRINDX='$index'
@Field static final String sDJSON='$json'
@Field static final String sDRESP='$response'
@Field static final String sLOCMODE='$locationMode'
@Field static final String sLOC='$location'
@Field static final String sNOW='$now'
@Field static final String sLOCNOW='$localNow'
@Field static final String sUTC='$utc'
@Field static final String sPLACES='$places'
@Field static final String sFILE='$file'
@Field static final String sDLRWEAT='$weather'
@Field static final String sDLRINCIDENTS='$incidents'
@Field static final String sHSMTRIPPED='$hsmTripped'
@Field static final String sFUEL='$fuel'
@Field static final String sROOMS='$rooms'
@Field static final String sROOMIDS='$roomids'

@Field static final String sHTTPCNTN='$httpContentType'
@Field static final String sHTTPCODE='$httpStatusCode'
@Field static final String sHTTPOK='$httpStatusOk'
@Field static final String sDLRHSMSTS='$hsmStatus'
@Field static final String sIFTTTCODE='$iftttStatusCode'
@Field static final String sIFTTTOK='$iftttStatusOk'
@Field static final String sPEVDATE='$previousEventDate'
@Field static final String sPEVDELAY='$previousEventDelay'
@Field static final String sPEVDEV='$previousEventDevice'
@Field static final String sPEVDEVINDX='$previousEventDeviceIndex'
@Field static final String sPEVATTR='$previousEventAttribute'
@Field static final String sPEVDESC='$previousEventDescription'
@Field static final String sPEVVALUE='$previousEventValue'
@Field static final String sPEVUNIT='$previousEventUnit'
@Field static final String sPEVPHYS='$previousEventDevicePhysical'
@Field static final String sCURDATE='$currentEventDate'
@Field static final String sCURDELAY='$currentEventDelay'
@Field static final String sCURDEV='$currentEventDevice'
@Field static final String sCURDEVINDX='$currentEventDeviceIndex'
@Field static final String sCURATTR='$currentEventAttribute'
@Field static final String sCURDESC='$currentEventDescription'
@Field static final String sCURVALUE='$currentEventValue'
@Field static final String sCURUNIT='$currentEventUnit'
@Field static final String sCURPHYS='$currentEventDevicePhysical'

@Field static final String sAPPJSON='application/json'
@Field static final String sAPPFORM='application/x-www-form-urlencoded'
@Field static final String sCHNK='chunk:'
@Field static final String sGET='GET'
@Field static final String sDELETE='DELETE'
@Field static final String sHEAD='HEAD'
@Field static final String sLVL='level'
@Field static final String sSTLVL='setLevel'
@Field static final String sIFLVL='infraredLevel'
@Field static final String sSTIFLVL='setInfraredLevel'
@Field static final String sSATUR='saturation'
@Field static final String sSTSATUR='setSaturation'
@Field static final String sSTVAR='setVariable'
@Field static final String sHUE='hue'
@Field static final String sSTHUE='setHue'
@Field static final String sSTCLR='setColor'
@Field static final String sCLRTEMP='colorTemperature'
@Field static final String sSTCLRTEMP='setColorTemperature'
@Field static final String sPUSH='push'
@Field static final String sRELEASE='release'
@Field static final String sHOLD='hold'
@Field static final String sDOUBLETAP='doubleTap'
@Field static final String sUTF8='UTF-8'

@Field static final String sPEP='pep'
@Field static final String sAPS='aps'
@Field static final String sCTO='cto'
@Field static final String sMPS='mps'
@Field static final String sCED='ced'
@Field static final String sDCO='dco'
@Field static final String sDES='des'
@Field static final String sISH='ish'

@Field static final String sTCP='tcp'
@Field static final String sCTP='ctp'
@Field static final String sTEP='tep'
@Field static final String sTSP='tsp'

@Field static final String sTASK='task'

@Field static final String sLOGNG='logging'
@Field static final String sID='id'
@Field static final String spId='pId'
@Field static final String snId='nId'
@Field static final String sBIN='bin'
@Field static final String sATHR='author'
@Field static final String sCTGRY='category'
@Field static final String sSTATS='stats'
@Field static final String sDEVS='devices'
@Field static final String sCUREVT='currentEvent'
@Field static final String sTMSTMP='timestamp'
@Field static final String sST='state'
@Field static final String sSYSVARS='systemVars'
@Field static final String sLOGS='logs'
@Field static final String sSTACK='stack'
@Field static final String sSTORE='store'
@Field static final String sPISTN='piston'
@Field static final String sCACHE='cache'
@Field static final String sVARS='vars'
@Field static final String sNSCH='nextSchedule'
@Field static final String sTIMING='timing'
@Field static final String sSCHS='schedules'
@Field static final String sUPDDEVS='updateDevices'
@Field static final String sLEVT='lastEvent'
@Field static final String sLEXEC='lastExecuted'
@Field static final String sSTMTL='stmtLvl'
@Field static final String sMODFD='modified'
@Field static final String sCREAT='created'
@Field static final String sBLD='build'
@Field static final String sACT='active'
@Field static final String sRESULT='result'
@Field static final String sSVLBL='svLabel'
@Field static final String sPCACHE='cachePersist'
@Field static final String sNWCACHE='newCache'
@Field static final String sCNCLATNS='cancelations'
@Field static final String sCNDTNSTC='cndtnStChgd'
@Field static final String sPSTNSTC='pstnStChgd'
@Field static final String sWUP='wakingUp'
@Field static final String sSTACCESS='stateAccess'
@Field static final String sLSTPCQ='lastPCmdQ'
@Field static final String sLSTPCSNT='lastPCmdSnt'
@Field static final String sDBGLVL='debugLevel'
@Field static final String sPREVEVT='previousEvent'
@Field static final String sLOCMODEID='locationModeId'
@Field static final String sLOCALV='localVars'
@Field static final String sJSON='json'
@Field static final String sRESP='response'
@Field static final String sRESUMED='resumed'
@Field static final String sTERM='terminated'
@Field static final String sRESTRICD='restricted'
@Field static final String sCURACTN='curActn'
@Field static final String sCURTSK='curTsk'
@Field static final String sGVCACHE='gvCache'
@Field static final String sGVSTOREC='gvStoreCache'
@Field static final String sINITGS='initGStore'
@Field static final String sGSTORE='globalStore'
@Field static final String sDID3OR5='did3or5'
@Field static final String sPSTNZ='pistonZ'
@Field static final String sTMP='temporary'
@Field static final String sRTHIS='runTimeHis'
@Field static final String sPTS='points'
@Field static final String sTZ='tz'
@Field static final String sZONEID='zoneid'
@Field static final String sPAUSE='pause'
@Field static final String sPAUSES='pauses'
@Field static final String sRESUME='resume'
@Field static final String sWEAT='weather'
@Field static final String sMEDIADATA='mediaData'
@Field static final String sMEDIATYPE='mediaType'
@Field static final String sMEDIAID='mediaId'
@Field static final String sMEDIAURL='mediaUrl'
@Field static final String sHTTPERR='httpError'

@Field static final String sLOCID='locationId'
@Field static final String sUSELFUELS='useLocalFuelStreams'
@Field static final String sSETTINGS='settings'
@Field static final String sINCIDENTS='incidents'
@Field static final String sLOGHE='logsToHE'
@Field static final String sINSTID='instanceId'
@Field static final String sREGION='region'
@Field static final String sENABLED='enabled'
@Field static final String sALLLOC='allLocations'
@Field static final String sOLDLOC='oldLocations'
@Field static final String sNACCTSID='newAcctSid'
@Field static final String sHCOREVER='hcoreVersion'
@Field static final String sLOGPE='logPExec'

@Field static final String sOLD='old'
@Field static final String sNEW='new'
@Field static final String sAUTONEW='autoNew'
@Field static final String sPIS='pis'
@Field static final String sCACHED='cached'

@Field static final String sDV='dev'

@Field static final String sSUBS='subscriptions'
@Field static final String sALLOWR='allowResume'

@Field static final String sARGS='args'
@Field static final String sDATA='data'
@Field static final String sJSOND='jsonData'
@Field static final String sDESCTXT='descriptionText'
@Field static final String sCONTENTT='contentType'
@Field static final String sRECOVERY='recovery'
@Field static final String sRESPDATA='responseData'
@Field static final String sRESPCODE='responseCode'
@Field static final String sSRTDATA='setRtData'
@Field static final String sPHYS='physical'
@Field static final String sUNIT='unit'
@Field static final String sINDX='index'
@Field static final String sDELAY='delay'
@Field static final String sSCH='schedule'

@Field static final String sLO='lo'
@Field static final String sLO2='lo2'
@Field static final String sLO3='lo3'
@Field static final String sRO='ro'
@Field static final String sRO2='ro2'
@Field static final String sTO='to'
@Field static final String sTO2='to2'

@Field static final String sCS='cs'
@Field static final String sSS='ss'
@Field static final String sPS='ps'
@Field static final String sEI='ei'
@Field static final String sTS='ts'
@Field static final String sFS='fs'
@Field static final String sCO='co'
@Field static final String sCT='ct'
@Field static final String sRN='rn'
@Field static final String sROP='rop'
@Field static final String sEXP='exp'

@Field static final String sINMEM='inMem'
@Field static final String sTIMER='timer'
@Field static final String sRESACT='resAct'

@Field static final String sCONDITIONS='conditions'
@Field static final String sTRIGGERS='triggers'
@Field static final String sSTMTS='statements'

@Field static final String sSTAYUNCH='stays_unchanged'
@Field static final String sSTAYS='stays'
@Field static final String sMATCHES='matches'
@Field static final String sMATCHED='matched'
@Field static final String sUNMATCHED='unmatched'
@Field static final String sFRCALL='forceAll'

@Field static final String sZ6='000000'
@Field static final String sHTTPR='httpRequest'
@Field static final String sLIFX='lifx'
@Field static final String sSENDE='sendEmail'
@Field static final String sANY='any'
@Field static final String sALL='all'
@Field static final String sAND='and'
@Field static final String sOR='or'
@Field static final String sIF='if'
@Field static final String sWHILE='while'
@Field static final String sREPEAT='repeat'
@Field static final String sFOR='for'
@Field static final String sEACH='each'
@Field static final String sACTION='action'
@Field static final String sEVERY='every'
@Field static final String sRESTRIC='restriction'
@Field static final String sGROUP='group'
@Field static final String sDO='do'
@Field static final String sEVENT='event'
@Field static final String sEXIT='exit'
@Field static final String sBREAK='break'
@Field static final String sEXPR='expression'
@Field static final String sOPER='operator'
@Field static final String sOPERAND='operand'
@Field static final String sVALUES='values'
@Field static final String sFUNC='function'
@Field static final String sIS='is'
@Field static final String sISINS='is_inside_of_range'
@Field static final String sPLUS='+'
@Field static final String sMINUS='-'
@Field static final String sDOT='.'
@Field static final String sEXPECTING='Expecting '
@Field static final String sSTOREM='storeMedia'
@Field static final String sIFTTM='iftttMaker'
@Field static final String sEND='end'

@Field static final String sTSLF='theSerialLockFLD'
//@Field static final String sTCL='cacheLock'
@Field static final String sTGBL='theGlobal'
@Field static final String sLCK1='lockOrQueue1'
@Field static final String sLCK2='lockOrQueue2'
@Field static final String sGETTRTD='getTempRtd'
@Field static final String sGETPCACHE='getParentCache'
@Field static final String sGETRTD='getRTD'
@Field static final String sHNDLEVT='handleEvents'
@Field static final String sINTDECSTR='(integer or decimal or string)'
@Field static final String sVALUEN='(value1, value2,..., valueN)'
@Field static final String sDATTRH='([device:attribute])'
@Field static final String sDATTRHT='([device:attribute] [,.., [device:attribute]],threshold)'
@Field static final String sPSTNRSM='pistonResume'
@Field static final String sTILE='tile'
@Field static final String sSUNRISE='sunrise'
@Field static final String sSUNSET='sunset'

@Field static final String sMULP='*'
@Field static final String sQM='?'
@Field static final String sCLN=':'
@Field static final String sPWR='**'
@Field static final String sAMP='&'
@Field static final String sBOR='|'
@Field static final String sBXOR='^'
@Field static final String sBNOT='~'
@Field static final String sBNAND='~&'
@Field static final String sBNOR='~|'
@Field static final String sBNXOR='~^'
@Field static final String sLTH='<'
@Field static final String sGTH='>'
@Field static final String sLTHE='<='
@Field static final String sGTHE='>='
@Field static final String sEQ='=='
@Field static final String sNEQ='!='
@Field static final String sNEQA='<>'
@Field static final String sMOD='%'
@Field static final String sMOD1='\\'
@Field static final String sSBL='<<'
@Field static final String sSBR='>>'
@Field static final String sNEG='!'
@Field static final String sDNEG='!!'
@Field static final String sDIV='/'
@Field static final String sLAND='&&'
@Field static final String sLNAND='!&'
@Field static final String sLOR='||'
@Field static final String sLNOR='!|'
@Field static final String sLXOR='^^'
@Field static final String sLNXOR='!^'
@Field static final String sUNDS='_'

@Field static final String s0='0'
@Field static final String s1='1'
@Field static final String s2='2'
@Field static final Long lZ=0L
@Field static final Long l1=1L
@Field static final Long l100=100L
@Field static final Integer iN1=-1
@Field static final Integer iN2=-2
@Field static final Integer iN3=-3
@Field static final Integer iN5=-5
@Field static final Integer iN9=-9
@Field static final Integer iZ=0
@Field static final Integer i1=1
@Field static final Integer i2=2
@Field static final Integer i3=3
@Field static final Integer i4=4
@Field static final Integer i5=5
@Field static final Integer i6=6
@Field static final Integer i7=7
@Field static final Integer i8=8
@Field static final Integer i9=9
@Field static final Integer i10=10
@Field static final Integer i11=11
@Field static final Integer i12=12
@Field static final Integer i13=13
@Field static final Integer i15=15
@Field static final Integer i16=16
@Field static final Integer i20=20
@Field static final Integer i32=32
@Field static final Integer i34=34
@Field static final Integer i50=50
@Field static final Integer i60=60
@Field static final Integer i100=100
@Field static final Integer i200=200
@Field static final Integer i204=204
@Field static final Integer i300=300
@Field static final Integer i400=400
@Field static final Integer i401=401
@Field static final Integer i500=500
@Field static final Integer i1000=1000
@Field static final Integer i1900=1900
@Field static final Long l500=500L
@Field static final Long lTHOUS=1000L
@Field static final Long lMSDAY=86400000L
@Field static final Double dZ=0.0D
@Field static final Double d1=1.0D
@Field static final Double d2=2.0D
@Field static final Double d3=3.0D
@Field static final Double d60=60.0D
@Field static final Double d100=100.0D
@Field static final Double d360=360.0D
@Field static final Double d1000=1000.0D
@Field static final Double dSECHR=3600.0D
@Field static final Double dMSECHR=3600000.0D
@Field static final Double dMSMINT=60000.0D
@Field static final Double dMSDAY=86400000.0D

@CompileStatic
private static Boolean bIs(Map m,String v){ (Boolean)m.get(v) }

/** parallel piston execution */
@CompileStatic
private static Boolean isPep(Map m){ bIs(m,sPEP) }
@CompileStatic
private static Boolean isAct(Map m){ bIs(m,sACT) }
@CompileStatic
private static Boolean isEnbl(Map m){ bIs(m,sENABLED) }
@CompileStatic
private static Boolean isBrk(Map m){ bIs(m,sBREAK) }

/** m.string */
@CompileStatic
private static Map<String,Object> mMs(Map m,String s){ (Map)m.get(s) }

/** m.string */
@CompileStatic
private static Map<String,Map> msMs(Map m,String s){ (Map<String,Map>)m.get(s) }

/** m.string */
@CompileStatic
private static String sMs(Map m,String v){ (String)m.get(v) }

/** l[integer] */
@CompileStatic
private static String sLi(List l,Integer v){ (String)l[v] }

/** m.a */
@CompileStatic
private static String sMa(Map m){ sMs(m,sA) }

/** m.t */
@CompileStatic
private static String sMt(Map m){ sMs(m,sT) }

/** m.vt */
@CompileStatic
private static String sMvt(Map m){ sMs(m,sVT) }

/** m.t */
@CompileStatic
private static Long lMt(Map m){ (Long)m.get(sT) }

/** m.string */
@CompileStatic
private static Long lMs(Map m,String v){ (Long)m.get(v) }

/** m.s */
@CompileStatic
private static Integer iMsS(Map m){ iMs(m,sS) }

/** m.string */
@CompileStatic
private static Integer iMs(Map m,String v){ (Integer)m.get(v) }

/** returns m.v */
@CompileStatic
private static String sMv(Map m){ sMs(m,sV) }

/** returns m.v */
@CompileStatic
private static List liMv(Map m){ (List)m.get(sV) }

/** returns m.d */
@CompileStatic
private static List<String> liMd(Map m){ (List<String>)m.get(sD) }

/** returns m.string */
@CompileStatic
private static List<Map> liMs(Map m,String s){ (List)m.get(s) }

/** returns l[i] */
@CompileStatic
private static Integer iLi(List l,Integer i){ (Integer)l[i] }

/** m[v] */
@CompileStatic
private static oMs(Map m,String v){ m.get(v) }

/** returns m.v */
@CompileStatic
private static oMv(Map m){ m.get(sV) }

/** returns m.v */
@CompileStatic
private static Map mMv(Map m){ (Map)m.get(sV) }

/** returns m.v.v */
@CompileStatic
private static oMvv(Map m){ Map mv=mMv(m); oMv(mv) }

@CompileStatic
private static Integer gtPOpt(Map r9,String nm){
	Map<String,Object> op=mMs(r9,sPISTN) ?: [:]
	Map<String,Object> o=mMs(op,sO) ?: [:]
	if(o.containsKey(nm))return iMs(o,nm)
	return null
}

@CompileStatic
private static Boolean logIs(Map r9,Integer i){ (iMs(r9,sLOGNG) ?: iZ)>i }
@CompileStatic
private static Boolean isDbg(Map r9){ logIs(r9,i2) }
@CompileStatic
private static Boolean isTrc(Map r9){ logIs(r9,i1) }
@CompileStatic
private static Boolean isInf(Map r9){ logIs(r9,iZ) }
/** super debuggging */
@CompileStatic
private static Boolean isEric(Map r9){ eric1() && isDbg(r9) }

@CompileStatic
private static Boolean badParams(List prms,Integer minParams){ return (prms==null || prms.size()<minParams) }

/** Returns t:t,v:v */
@CompileStatic
private static Map<String,Object> rtnMap(String t,v){ return [(sT):t,(sV):v] }
/** Returns t:duration,v:m.v,vt:m.vt */
@CompileStatic
private static Map<String,Object> rtnMap1(Map m){ return [(sT):sDURATION,(sV):oMv(m),(sVT):sMvt(m)] }
/** Returns t:string,v:v */
@CompileStatic
private static Map rtnMapS(String v){ return [(sT):sSTR,(sV):v] as LinkedHashMap }
@CompileStatic
private static Map rtnMapI(Integer v){ return [(sT):sINT,(sV):v] as LinkedHashMap }
@CompileStatic
private static Map rtnMapD(Double v){ return [(sT):sDEC,(sV):v] as LinkedHashMap }
@CompileStatic
private static Map rtnMapB(Boolean v){ return [(sT):sBOOLN,(sV):v] as LinkedHashMap }
@CompileStatic
private static Map rtnMapE(String s){ return [(sT):sERROR,(sV):s] as LinkedHashMap }
@CompileStatic
private static Map rtnErr(String s){ return rtnMapE(sEXPECTING+s)}
@CompileStatic
private static Boolean isErr(Map m){ return sMt(m)==sERROR }

/** CONFIGURATION PAGES	**/

@Field static final String sPMAIN='pageMain'
@Field static final String sPRUN='pageRun'
@Field static final String sPCLR='pageClear'
@Field static final String sPCLRALL='pageClearAll'
@Field static final String sPDPIS='pageDumpPiston'
@Field static final String sPDPIS1='pageDumpPiston1'
@Field static final String sPDPIS2='pageDumpPiston2'
@Field static final String sPDPC='pageDumpPCache'
@Field static final String sPREM='pageRemove'
preferences{
	page((sNM):sPMAIN)
	page((sNM):sPRUN)
	page((sNM):sPCLR)
	page((sNM):sPCLRALL)
	page((sNM):sPDPC)
	page((sNM):sPDPIS)
	page((sNM):sPDPIS1)
	page((sNM):sPDPIS2)
	page((sNM):sPREM)
}

def pageMain(){
	return dynamicPage((sNM):sPMAIN,(sTIT):sBLK,install:true,uninstall:false){
		if(parent==null || !(Boolean)parent.isInstalled()){
			section(){
				paragraph 'Sorry you cannot install a piston directly from HE console; please use the webCoRE dashboard (dashboard.webcore.co) instead.'
			}
			section(sectionTitleStr('Installing webCoRE')){
				paragraph 'If you are trying to install webCoRE please go back one step and choose webCoRE, not webCoRE Piston. You can also visit wiki.webcore.co for more information on how to install and use webCoRE'
				if(parent!=null){
					String t0=(String)parent.getWikiUrl()
					href sBLK,(sTIT):imgTitle('app-CoRE.png',inputTitleStr('More information')),description:t0,style:'external',url:t0,(sREQ):false
				}
			}
		}else{
			section(sectionTitleStr('General')){
				label([(sNM):sNM,(sTIT):'Name',(sREQ):true,(sST):(name ? 'complete':sNL),defaultValue:(String)parent.generatePistonName(),submitOnChange:true])
			}

			section(sectionTitleStr('Dashboard')){
				String durl; durl=(String)parent.getDashboardUrl()
				if(durl!=sNL){
					durl+='piston/'+hashPID(sAppId())
					href sBLK,(sTIT):imgTitle('dashboard.png',inputTitleStr('View piston in dashboard')),style:'external',url:durl,(sREQ):false
				}else paragraph 'Sorry your webCoRE dashboard does not seem to be enabled; please go to the parent app and enable the dashboard if needed.'
			}

			section(sectionTitleStr('Application Info')){
				LinkedHashMap r9; r9= getTemporaryRunTimeData()
				if(!isEnbl(r9))paragraph 'Piston is disabled by webCoRE'
				if(!isAct(r9))paragraph 'Piston is paused'
				if(sMs(r9,sBIN)!=sNL){
					paragraph 'Automatic backup bin code: '+sMs(r9,sBIN)
				}
				paragraph 'Version: '+sVER
				paragraph 'VersionH: '+sHVER
				paragraph 'Memory Usage: '+mem()
				paragraph 'RunTime History: '+srunTimeHis(r9)
				r9=null
			}

			section(sectionTitleStr('Recovery')){
				href sPRUN,(sTIT):'Test run this piston'
				href sPCLR,(sTIT):'Clear logs',description:'This will remove logs but no variables'
				href sPCLRALL,(sTIT):'Clear all data',description:'This will reset all data stored in local variables'
			}

			section(){
				input sDV,"capability.*",(sTIT):'Devices - Piston devices in use (do not try to add devices here, use main webCoRE)',multiple:true
				input sLOGNG,sENUM,(sTIT):'Logging Level',options:[(s0):"None",(s1):"Minimal",(s2):"Medium","3":"Full"],description:'Piston logging',defaultValue:state[sLOGNG] ? state[sLOGNG].toString():s0
				input sLOGHE,sBOOL,(sTIT):'Piston logs are also displayed in HE console logs?',description:"Logs are available in webCoRE console; also display in HE console 'Logs'?",defaultValue:false
				input sMSTATS,sNUMBER,(sTIT):'Max number of timing history stats',description:'Max number of stats',range:'2..300',defaultValue:i50
				input sMLOGS,sNUMBER,(sTIT):'Max number of history logs',description:'Max number of logs',range:'0..300',defaultValue:i50
			}
			if(eric() || ((String)gtSetting(sLOGNG))?.toInteger()>i2){
				section('Debug'){
					href sPDPIS,(sTIT):'Dump piston structure',description:sBLK
					href sPDPIS1,(sTIT):'Dump cached piston structure',description:sBLK
					href sPDPIS2,(sTIT):'To IDE piston structure',description:sBLK
					href sPDPC,(sTIT):'Dump piston Cache',description:sBLK
				}
			}
			section(){
				href sPREM,(sTIT):'Remove this Piston',description:sBLK
			}
		}
	}
}

def pageRun(){
	test()
	return dynamicPage((sNM):sPRUN,(sTIT):sBLK,uninstall:false){
		section('Run'){
			paragraph 'Piston tested'
			Map<String,String> t0=(Map)parent.getWCendpoints()
			String t1="/execute/${hashPID(sAppId())}?access_token=${t0.at}".toString()
			paragraph "Cloud Execute endpoint ${t0.ep}${t1}".toString()
			paragraph "Local Execute endpoint ${t0.epl}${t1}".toString()
		}
	}
}

def pageClear(){
	clear1(false,true,true,false)
	return dynamicPage((sNM):sPCLR,(sTIT):sBLK,uninstall:false){
		section('Clear'){
			paragraph 'All non-essential data has been cleared.'
		}
	}
}

/**
 * clear data caches for this piston; calls clearMyCache
 * @param ccache data + clear code cache
 * @param some data + clear logs
 * @param most data + clear trace and stats
 * @param all all of above + clear local variables
 * @param reset data + reset log/stats settings
 */
Map clear1(Boolean ccache=false,Boolean some=true,Boolean most=false,Boolean all=false,Boolean reset=false){
	String meth
	meth='clear1'
	if(some||all)state.put(sLOGS,[])
	if(most||all){ state.put(sTRC,[:]);state.put(sSTATS,[:]) }
	if(reset){wappRemoveSetting(sMLOGS); wappRemoveSetting(sMSTATS); wappRemoveSetting(sLOGHE) }
	cleanState()
	LinkedHashMap tRtData, r9
	r9= null
	if(all){
		meth+=' all'

		tRtData= getTemporaryRunTimeData()
		String pNm=sMs(tRtData,snId)
		tRtData=null

		state.put(sCACHE,[:])
		stNeedUpdate()
		state.put(sST,[:])
		state.put(sVARS,[:])
		state.put(sSTORE,[:])
		state.put(sPAUSES,lZ)

		getTheLock(pNm,meth)
		theSemaphoresVFLD.put(pNm,lZ)
		theSemaphoresVFLD=theSemaphoresVFLD
		theQueuesVFLD.put(pNm,[])
		theQueuesVFLD=theQueuesVFLD // forces volatile cache flush
		releaseTheLock(pNm)

	}
	clearMyCache(meth)
	tRtData= getTemporaryRunTimeData()
	if(all){
		clearReadFLDs(tRtData)
		Boolean act=isAct(tRtData)
		Boolean dis=!isEnbl(tRtData)
		if(act && !dis){
			r9= getRunTimeData(tRtData,null,true,true,true) //reinitializes cache variables; caches piston
//		}else{
//			r9= getRunTimeData(tRtData,null,false,true,true)
		}
//	}else{
//		r9= getRunTimeData(tRtData,null,false,true,true)
	}
	if(r9==null) r9= tRtData
	Map nRtd=shortRtd(r9)
	tRtData=null
	r9=null
	clearMyCache(meth)
	if(ccache)clearMyPiston(meth)
	return nRtd
}

def pageClearAll(){
	clear1(true,true,true,true)
	return dynamicPage((sNM):sPCLRALL,(sTIT):sBLK,uninstall:false){
		section('Clear All'){
			paragraph 'All local data has been cleared.'
		}
	}
}

def pageRemove(){
	dynamicPage((sNM): sPREM,(sTIT): sBLK,install: false,uninstall: true){
		section('CAUTION'){
			paragraph "You are about to completely remove this webCoRE piston.",(sREQ): true
			paragraph "This action is irreversible.",(sREQ): true
			paragraph "It is suggested to backup this piston via the webCoRE IDE before proceeding to a local file",(sREQ):true
			paragraph "If you are sure you want to do this, please tap on the Remove button below.",(sREQ): true
		}
	}
}

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
	StringBuilder sb=new StringBuilder()
	Integer n; n=i1
	List<Boolean> newLevel=lastLevel

	List list1=data ?: []
	Integer sz=list1.size()
	for(Object par in list1){
		String lbl=listLabel+"[${n-i1}]".toString()
		if(par instanceof Map){
			Map<String,Object> newmap=[:]
			newmap[lbl]=(Map)par
			Boolean t1=n==sz
			newLevel[level]=t1
			sb.append(dumpMapDesc(newmap,level,newLevel,n,sz,!t1,html,reorder))
		}else if(par instanceof List || par instanceof ArrayList){
			Map<String,Object> newmap=[:]
			newmap[lbl]=par
			Boolean t1=n==sz
			newLevel[level]=t1
			sb.append(dumpMapDesc(newmap,level,newLevel,n,sz,!t1,html,reorder))
		}else{
			String lineStrt
			lineStrt=doLineStrt(level,lastLevel)
			lineStrt+=n==i1 && sz>i1 ? sSPCST:(n<sz ? sSPCSM:sSPCSE)
			sb.append(spanStr(html, lineStrt+lbl+": ${par} (${objType(par)})".toString()))
		}
		n+=i1
	}
	return sb.toString()
}

@CompileStatic
static String dumpMapDesc(Map<String,Object> data,Integer level,List<Boolean> lastLevel,Integer listCnt=null,Integer listSz=null,Boolean listCall=false,Boolean html=false,Boolean reorder=true){
	StringBuilder sb=new StringBuilder()
	Integer n; n=i1
	Integer sz=data?.size()
	Map<String,Object> svMap,svLMap,newMap; svMap=[:]; svLMap=[:]; newMap=[:]
	for(Map.Entry<String,Object> par in data){
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
	for(Map.Entry<String,Object> par in newMap){
		String lineStrt
		List<Boolean> newLevel=lastLevel
		Boolean thisIsLast=n==sz && !listCall
		if(level>iZ)newLevel[(level-i1)]=thisIsLast
		Boolean theLast
		theLast=thisIsLast
		if(level==iZ)lineStrt=sDBNL
		else{
			theLast=theLast && thisIsLast
			lineStrt=doLineStrt(level,newLevel)
			if(listSz && listCnt && listCall)lineStrt+=listCnt==i1 && listSz>i1 ? sSPCST:(listCnt<listSz ? sSPCSM:sSPCSE)
			else lineStrt+=((n<sz || listCall) && !thisIsLast) ? sSPCSM:sSPCSE
		}
		String k=(String)par.key
		def v=par.value
		String objType=objType(v)
		if(v instanceof Map){
			sb.append(spanStr(html, lineStrt+"${k}: (${objType})".toString()))
			newLevel[lvlpls]=theLast
			sb.append(dumpMapDesc((Map)v,lvlpls,newLevel,null,null,false,html,reorder))
		}
		else if(v instanceof List || v instanceof ArrayList){
			sb.append(spanStr(html, lineStrt+"${k}: [${objType}]".toString()))
			newLevel[lvlpls]=theLast
			sb.append(dumpListDesc((List)v,lvlpls,newLevel,sBLK,html,reorder))
		}
		else{
			sb.append(spanStr(html, lineStrt+"${k}: (${v}) (${objType})".toString()))
		}
		n+=i1
	}
	return sb.toString()
}

@CompileStatic
static String objType(obj){ return span(myObj(obj),sCLRORG) }

@CompileStatic
static String getMapDescStr(Map<String,Object> data,Boolean reorder=true){
	List<Boolean> lastLevel=[true]
	String str=dumpMapDesc(data,iZ,lastLevel,null,null,false,true,reorder)
	return str!=sBLK ? str:'No Data was returned'
}

def pageDumpPCache(){
	LinkedHashMap t0=getCachedMaps(sPDPC,false,false)
	String message=getMapDescStr(t0,false)
	return dynamicPage((sNM):sPDPC,(sTIT):sBLK,uninstall:false){
		section('Piston Data Cache dump'){
			paragraph message
		}
	}
}

def pageDumpHelper(Integer i,String nm,String desc){
	LinkedHashMap pis,r9
	r9= getRunTimeData()
	Boolean shorten,inMem,useCache
	shorten=true; inMem=false; useCache=false
	switch(i){
		case iZ: // full
			shorten=false
			break
		case i1: // cached
			inMem=true; useCache=true
			break
		case i2: // returned to iDE cleaned up
			break
	}
	pis=recreatePiston(shorten,inMem,useCache)
	r9[sPISTN]=pis
	subscribeAll(r9,false,inMem)

	pis=null
	String message=getMapDescStr(mMs(r9,sPISTN))
	r9=null
	return dynamicPage((sNM):nm,(sTIT):sBLK,uninstall:false){
		section(desc+'Piston dump'){
			paragraph message
		}
	}
}

def pageDumpPiston2(){ // dumps what to return to IDE
	pageDumpHelper(i2,sPDPIS2,'To IDE ')
}

def pageDumpPiston1(){ // dumps memory cached piston
	pageDumpHelper(i1,sPDPIS1,'Memory cached ')
}

def pageDumpPiston(){ // dumps full piston
	pageDumpHelper(iZ,sPDPIS,'Full ')
}

void installed(){
	if(app.id==null)return
	Long t=wnow()
	state.put(sCREAT,t)
	state.put(sMODFD,t)
	state.put(sBLD,iZ)
	state.put(sVARS,(Map)gtSt(sVARS) ?: [:])
	state.put(sSUBS,(Map)gtSt(sSUBS) ?: [:])
	state.put(sLOGNG,iZ)
	initialize()
}

void updated(){
	wunsubscribe()
	wstateRemove(sTZ)
	initialize()
}

void uninstalled(){
	if(eric())doLog(sDBG,'uninstalled')
	if(!(Boolean)gtAS('pistonDeleted')){
		deletePiston()
		parent.pistonUninstalled(sAppId()) // deleted outside the dashboard API; parent caches wouldn't otherwise be invalidated
	}
}

void initialize(){
	svSunTFLD=[:]; mb()
	Map mst=gtState()
	String tt1=(String)gtSetting(sLOGNG)
	Integer tt2=iMs(mst,sLOGNG) ?: iZ
	String tt3=tt2.toString()
	if(tt1==sNL) setLoggingLevel(tt2 ? tt3:s0,false)
	else if(tt1!=tt3) setLoggingLevel(tt1,false)
	if(bIs(mst,sACT)) resumeP()
	else clearMyCache('initialize')
}

@Field static final List<String> clST=['hash','piston','cVersion','hVersion','disabled','logPExec',
									   'settings','svSunT','temp','debugLevel','lockTime','hubitatQueryString']

void cleanState(){
//cleanups between releases
	String s='sph'
	for(sph in gtState().findAll{ ((String)it.key).startsWith(s)})wstateRemove(sph.key.toString())
	for(String foo in clST)wstateRemove(foo)
	wappRemoveSetting('hubitatQueryString')
}

/** PUBLIC METHODS					**/

Boolean isInstalled(){
	return lMs(gtState(),sCREAT)!=null
}

// Interfaces called by parent on behalf of IDE

@CompileStatic
Map get(Boolean minimal=false){ // minimal is backup
	LinkedHashMap r9
	r9= getRunTimeData()
	Map rVal
	rVal=[
		(sMETA): [
			(sID): sMs(r9,sID),
			(sATHR): sMs(r9,sATHR),
			(sNM): sMs(r9,sNM),
			(sCREAT): lMs(r9,sCREAT),
			(sMODFD): lMs(r9,sMODFD),
			(sBLD): iMs(r9,sBLD),
			(sBIN): sMs(r9,sBIN),
			(sACT): isAct(r9),
			(sCTGRY): sMs(r9,sCTGRY)
		],
		(sPISTN): (LinkedHashMap)r9[sPISTN]
	]
	if(!minimal){
		Map mst=gtState()
		rVal+= [ // use state as getRunTimeData re-initializes these
			(sSYSVARS): getSystemVariablesAndValues(r9),
			(sSUBS): mMs(mst,sSUBS),
			(sST): mMs(mst,sST),
			(sLOGNG): mst[sLOGNG]!=null ? iMs(mst,sLOGNG):iZ,
			(sSTATS): mMs(mst,sSTATS),
			(sLOGS): liMs(mst,sLOGS),
			(sTRC): mMs(mst,sTRC),
			(sLOCALV): mMs(mst,sVARS),
			(sMEMORY): mem(),
			(sLEXEC): lMs(mst,sLEXEC),
			(sNSCH): lMs(mst,sNSCH),
			(sSCHS): liMs(mst,sSCHS)
		]
	}
	r9=null
	return rVal
}

// this is called while the piston is open in IDE
@CompileStatic
Map activity(lastLogTimestamp){
	Map t0
	t0=getCachedMaps('activity')
	if(t0==null)return [:]
	List<Map> logs=[]+liMs(t0,sLOGS)
	Integer lsz=logs.size()
	Long llt=lastLogTimestamp!=null && lastLogTimestamp instanceof String && ((String)lastLogTimestamp).isLong()? ((String)lastLogTimestamp).toLong():lZ
	Integer lidx
	lidx=(llt!=lZ && lsz>iZ)? logs.findIndexOf{Map it-> (Long)it?.t==llt }:iZ
	lidx=lidx>iZ ? lidx:(llt!=lZ ? iZ:lsz)
	Map rVal=[
		(sNM): sMs(t0,sNM),
		(sST): mMs(t0,sST),
		(sLOGS): lidx>iZ ? logs[iZ..lidx-i1]:[],
		(sTRC): mMs(t0,sTRC),
		(sLOCALV): mMs(t0,sVARS), // not reporting global or system variable changes
		(sMEMORY): sMs(t0,sMEM),
		(sLEXEC): lMs(t0,sLEXEC),
		(sNSCH): lMs(t0,sNSCH),
		(sSCHS): liMs(t0,sSCHS),
		(sSYSVARS): mMs(t0,sPCACHE)
	]
	t0=null
	return rVal
}

// called by parent if it does not have piston information for IDE dashboard
// related to shortRtd method
@CompileStatic
Map curPState(){
	Map t0
	t0=getCachedMaps('curPState',true,false)
	if(t0==null)return null
	Map<String,Object> st=[:]+mMs(t0,sST)
	st.remove(sOLD)
	Map rVal=[
		(sA):isAct(t0),
		(sC):sMs(t0,sCTGRY),
		(sT):lMs(t0,sLEXEC),
		(sM):lMs(t0,sMODFD),
		(sB):sMs(t0,sBIN),
		(sN):lMs(t0,sNSCH),
		(sZ):sMs(t0,sPSTNZ),
		(sS):st,
		heCached:bIs(t0,'Cached') ?: false
	]
	t0=null
	return rVal
}

private static String decodeEmoji(String value){
	if(!value) return sBLK
	return value.replaceAll(/(\:%[0-9A-F]{2}%[0-9A-F]{2}%[0-9A-F]{2}%[0-9A-F]{2}\:)/){
		m -> URLDecoder.decode( ((String)m[0]).substring(1,13),sUTF8)
	}
}

@Field static Map<String,Map> thePistonCacheFLD=[:]
@Field static volatile Map<String,List<String>> anyOfCacheFLD=[:]

/** clear piston code cache */
private void clearMyPiston(String meth=sNL){
	String pNm=sAppId()
	if(pNm.length()==iZ)return
	Boolean clrd; clrd=false
	Map<String,Object> pData=mMs(thePistonCacheFLD,pNm)
	if(pData!=null){
		LinkedHashMap t0=(LinkedHashMap)pData[sPIS]
		if(t0){
			thePistonCacheFLD[pNm][sPIS]=null
			mb()
			clrd=true
		}
	}
	if(eric() && clrd){
		debug 'clearing piston-code-cache '+meth,null
		dumpPCsize()
	}
}

/**
 * get the piston in Map format
 * @param shorten optimiztion piston
 * @param inMem optimize for memory cache
 * @param useCache use cached version if available
 * @return
 */
private LinkedHashMap recreatePiston(Boolean shorten=false,Boolean inMem=false,Boolean useCache=true){
	if(shorten && inMem && useCache){
		String pNm=sAppId()
		Map<String,Object> pData; pData=mMs(thePistonCacheFLD,pNm)
		String c='cnt'
		if(pData==null || pData[c]==null){
			pData=[(c):iZ,(sPIS):null]
			thePistonCacheFLD[pNm]=pData
			mb()
		}
		if(pData[sPIS]!=null)return (LinkedHashMap)(pData[sPIS]+[(sCACHED):true])
	}

	if(eric())debug "recreating piston shorten: $shorten inMem: $inMem useCache: $useCache",null
	String sdata; sdata=sBLK
	Integer i; i=iZ
	String s
	while(true){
		s=(String)settings."${sCHNK}$i"
		if(s!=sNL)sdata+=s
		else break
		i++
	}
	if(sdata!=sBLK){
		LinkedHashMap data=(LinkedHashMap)new JsonSlurper().parseText(decodeEmoji(new String(sdata.decodeBase64(),sUTF8)))
		LinkedHashMap piston=[
			(sO): data[sO] ?: [:],
			(sR): data[sR] ?: [],
			(sRN): !!data[sRN],
			(sROP): data[sROP] ?: sAND,
			(sS): data[sS] ?: [],
			(sV): data[sV] ?: [],
			(sZ): data[sZ] ?: sBLK
		]
		assignSt(sPSTNZ,sMs(piston,sZ))
		clearMsetIds(piston)
		msetIds(shorten,inMem,piston)
		return piston
	}
	return [:]
}

Map setup(LinkedHashMap data,Map<String,String>chunks){
	if(data==null){
		doLog(sERROR,'setup: no data')
		return [:]
	}
	String meth='setup'
	clearMyCache(meth)

	String mSmaNm=sAppId()
	getTheLock(mSmaNm,meth)

	assignSt(sMODFD,wnow())
	assignSt(sBLD,gtSt(sBLD)!=null ? iMs(gtState(),sBLD)+i1:i1)
	LinkedHashMap piston=[
		(sO): data[sO] ?: [:],
		(sR): data[sR] ?: [],
		(sRN): !!data[sRN],
		(sROP): data[sROP] ?: sAND,
		(sS): data[sS] ?: [],
		(sV): data[sV] ?: [],
		(sZ): data[sZ] ?: sBLK
	]
	clearMyPiston(meth)
	clearMsetIds(piston)
	msetIds(false,false,piston)

	for(chunk in ((Map)settings).findAll{ ((String)it.key).startsWith(sCHNK) && !chunks[(String)it.key] }){
		wappRemoveSetting((String)chunk.key)
	}
	for(chunk in chunks)wappUpdateSetting((String)chunk.key,[(sTYPE):sTEXT,(sVAL):chunk.value])
	wappUpdateSetting(sBIN,[(sTYPE):sTEXT,(sVAL):sMs(gtState(),sBIN) ?: sBLK])
	wappUpdateSetting(sATHR,[(sTYPE):sTEXT,(sVAL):sMs(gtState(),sATHR) ?: sBLK])

	assignSt(sPEP,!!gtPOpt([(sPISTN):piston],sPEP))

	String lbl=sMs(data,sN)
	if(lbl){
		assignSt(sSVLBL,lbl)
		wappUpdateLabel(lbl)
	}
	assignSt(sSCHS,[])
	assignSt(sVARS,(Map)gtSt(sVARS) ?: [:])
	assignSt('modifiedVersion',sVER)

	assignSt(sCACHE,[:])
	stNeedUpdate()
	assignSt(sLOGS,[])
	assignSt(sTRC,[:])

	Map r9; r9=[:]
	r9[sPISTN]=piston
	releaseTheLock(mSmaNm)
	Map mst=gtState()
	Integer i= iMs(mst,sBLD)
	Boolean b= bIs(mst,sACT)
	if(i==i1 || b)r9= resumeP(piston,false)
	else clearMyCache(meth)
	mst=gtState()
	return [(sACT):b,(sBLD):i,(sMODFD):lMs(mst,sMODFD),(sST):mMs(mst,sST),rtData:r9]
}

@CompileStatic
private void clearMsetIds(Map node){
	if(node==null)return
	for(list in node.findAll{ it.value instanceof List }){
		for(item in ((List)list.value).findAll{ it instanceof Map })clearMsetIds(item as Map)
	}
	if(node instanceof Map && node[sDLR]!=null)node[sDLR]=null

	for(item in node.findAll{ it.value instanceof Map })clearMsetIds(item.value as Map)
}

@Field static List<String> ListCmd=[]
@Field static List<String> ListStmt=[]
private static void fill_STMT(){
	if(ListStmt.size()==iZ)
		ListStmt=[sIF,sACTION,sWHILE,sREPEAT,sFOR,sEACH,sSWITCH,sEVERY,sDO,sON,sEXIT,sBREAK]
	if(ListCmd.size()==iZ)
		ListCmd= ListStmt+[sCONDITION,sRESTRIC,sGROUP,sEVENT]
}


/**
 * add statement ids to piston
 * @param shorten optimize piston
 * @param inMem optimize for memory caching
 * @param node
 * @param mId
 * @param existingIds
 * @param requiringIds
 * @param level
 * @return
 */
@CompileStatic
private Integer msetIds(Boolean shorten,Boolean inMem,Map node,Integer mId=iZ,Map<String,Integer> existingIds=[:],List<Map> requiringIds=[],Integer level=iZ){
	String nodeT=sMt(node)
	Integer maxId; maxId=mId
	//Boolean lg= eric() && settings[sLOGNG]?.toInteger()>i2
	if(nodeT in ListCmd){
		Integer id
		id=iMs(node,sDLR)?:iZ
		String sid
		sid=id.toString()
		if(id==iZ || existingIds[sid]!=null) requiringIds.push(node)
		else{
			maxId=Math.max(maxId,id)
			existingIds[sid]=id
		}
		if(nodeT==sIF && node[sEI]){
			liMs(node,sEI).removeAll{ Map it -> !it.c && !it.s } // modifies code
			for(Map elseIf in liMs(node,sEI)){
				id= iMs(elseIf,sDLR)?:iZ
				sid=id.toString()
				if(id==iZ || existingIds[sid]!=null) requiringIds.push(elseIf)
				else{
					maxId=Math.max(maxId,id)
					existingIds[sid]=id
				}
			}
		}
		if(nodeT==sSWITCH && node[sCS]){
			for(Map _case in liMs(node,sCS)){
				id= iMs(_case,sDLR)?:iZ
				sid=id.toString()
				if(id==iZ || existingIds[sid]!=null) requiringIds.push(_case)
				else{
					maxId=Math.max(maxId,id)
					existingIds[sid]=id
				}
			}
		}
		if(nodeT==sACTION && node[sK]){
			for(Map task in liMs(node,sK)){
				id=iMs(task,sDLR)?:iZ
				sid=id.toString()
				if(id==iZ || existingIds[sid]!=null) requiringIds.push(task)
				else{
					maxId=Math.max(maxId,id)
					existingIds[sid]=id
				}
			}
		}
	}
	for(list in node.findAll{ it.value instanceof List }){
		for(item in ((List)list.value).findAll{ it instanceof Map }){
			maxId= msetIds(shorten,inMem,(Map)item,maxId,existingIds,requiringIds,level+i1)
		}
	}
	if(level==iZ){
		for(Map item in requiringIds){
			maxId+=i1
			item[sDLR]=maxId
		}
		if(shorten)cleanCode(node,inMem)
	}
	return maxId
}

@Field static List<String> ListAL=[]
@Field static List<String> ListC1=[]
@Field static List<String> ListC2=[]
@Field static List<String> ListAVANY=[]
@Field static List<String> ListDIDLR=[]
@Field static List<String> ListTIMEASYNC=[]
@Field static List<String> ListLSTSTSTHREAX
@Field static List<Integer> ListIN35
@Field static List<Integer> ListIZIN9
@Field static List<String> ListDCO
@Field static List<String> ListNOOPT
@Field static List<String> ListDATEDTIME
@Field static List<String> ListSTRDYN
@Field static List<String> ListEC
@Field static List<String> ListC3
@Field static List<String> ListC4
@Field static List<String> ListBC
@Field static List<String> ListBP
@Field static List<String> ListORAND
@Field static List<String> ListANYALL
@Field static List<String> ListSLVLSIFLVL
@Field static List<String> ListI

@Field static final String sAVG='avg'
@Field static final String sZC='zc'
@Field static final String sSTR1='str'
@Field static final String sOK='ok'
@Field static final String sAUTO='auto'
@Field static final String sSM='sm'
@Field static final String sWD='wd'
@Field static final String sWT='wt'

private static void fill_ListAL(){
	if(ListAL.size()==iZ){
		// sP phys/avg (uses a, d, g, p, i, f?)
		// sD devices  (uses d)
		// sV virt (uses v)
		// sS preset (uses s) (operand)  OR it is for switch case 's', 'r' ('r' for range)
		// sX variable (uses x, xi)
		// sC constant (uses c)
		// sE expr (uses exp)
		// sU argument (uses u)
		ListAL=[sP,sD,sV,sS,sX,sC,sE,sU]	// don't need
		ListC1=[      sV,sS,   sC,sE,sU]	//		g,a
		ListC2=[      sV,sS,sX,sC,sE,sU]	//		d

		ListAVANY=[sAVG,sANY]
		ListDIDLR=[sDI,sDLR]
		ListTIMEASYNC=[sTIME,sASYNCREP]
		ListLSTSTSTHREAX=[sLSTACTIVITY,sSTS,sTHREAX,sROOMID,sROOMNM]
		ListIN35=[iN3,iN5]
		ListIZIN9=[iZ,iN9]
		ListDCO=[sSTCLRTEMP,sSTCLR,sSTHUE,sSTSATUR,sPUSH,sRELEASE,sHOLD,sDOUBLETAP] // commands that do not allow command optimization
		ListNOOPT=[sVARIABLE,sFUNC,sDEV,sOPERAND,sDURATION]
		ListDATEDTIME=[sDATE,sDTIME]
		ListSTRDYN=[sSTR,sDYN]
		ListEC=[sE,sC]
		ListC3=[sD,sR,sCS,sFS,sTS,sE,sEI,sS,sK,sP,sV,sC]
		ListC4=[sWD,sRO2,sTO,sTO2]
		ListBC=[sB,sC]
		ListBP=[sB,sP]
		ListORAND=[sOR,sAND]
		ListANYALL=[sANY,sALL]
		ListSLVLSIFLVL=[sSTLVL,sSTIFLVL]
		ListI=[sI]
	}
}

// to reduce memory code size or remove cruft for IDE
@CompileStatic
private void cleanCode(Map i,Boolean inMem){
	if(i==null || !(i instanceof Map))return
	Map<String,Object> item=(Map<String,Object>)i

	if(inMem && bIs(item,sDI)){ // disabled statements
		List<String> b=item.collect{ (String)it.key }
		for(String c in b) if(!(c in ListDIDLR)) item.remove(c)
		return
	}

	String ty=sMt(item)

	// cruft when editing operands/parameters
	if(ty==sNL){
		//tasks with empty mode restriction
		if(item[sC] && item[sM] instanceof List && !(List)item[sM]) item.remove(sM)

		// task parameters (sP) with 'Nothing selected'
		if(sMs(item,sG) in ListAVANY && sMs(item,sF)==sL && item[sVT]!=null){
			if(item[sX]!=null){ item.remove(sX); item.remove(sXI)}
			if(item[sE]!=null) item.remove(sE)
			if(item[sC]!=null) item.remove(sC)
			if(item[sV]!=null) item.remove(sV)
			if(item[sS]!=null) item.remove(sS)
			if(item[sU]!=null) item.remove(sU)
			if(item[sEXP]) item.remove(sEXP)
			if(item[sA]!=null) item.remove(sA)

			// task parameters (sP) without types, but have devices
			if(item[sD] instanceof List && item[sD]) item[sD]=[]

			if(item[sD] instanceof List && !item[sD]){
				//if(item.size()==i5 && item[sC]!=null) a=item.remove(sC)
				if(inMem && item.size()==i4){ item.remove(sD); item.remove(sG) }
			}
		}
	}

	if(ty in ListAL){ // cleanup operands
		// UI important data
		if(inMem){
			String g=sMs(item,sG)
			String vt=sMvt(item)
			if(ty in ListC1){
				if(g in ListAVANY) item.remove(sG)
				//if(item.a instanceof String && item.a==sD)a=item.remove(sA)
			}
			if(ty==sX && vt!=sDEV) // operand values that don't need f,g
				if(g in ListAVANY) item.remove(sG)
			if(ty==sC && !(vt in LT1)) item.remove(sC)
			if(ty==sE && item[sE]!=null) item.remove(sE)
		}
		// cruft when editing operands
		if(ty in ListC2 && item[sD] instanceof List) item.remove(sD)
		if(!(ty in ListEC) && item[sEXP]) item.remove(sEXP) // evaluateOperand
		if(ty!=sX && item[sX]!=null){ item.remove(sX); item.remove(sXI)}
		if(ty!=sE && item[sE]!=null) item.remove(sE)
		if(ty!=sC && item[sC]!=null) item.remove(sC)
		if(ty!=sV && oMv(item)!=null) item.remove(sV)
		if(ty!=sS && item[sS]!=null) item.remove(sS)
		if(ty!=sU && item[sU]!=null) item.remove(sU)
		if(ty!=sP && item[sA]!=null) item.remove(sA)
	}
	if(inMem && ty==sEXPR && item[sI] && liMs(item,sI).size()==i1){ // simplify un-needed nesting
		List<Map> bb=liMs(item,sI)
		Map bb1=bb[iZ]
		if(sMt(bb1)==sEXPR){
			if(bb1[sI] && liMs(bb1,sI).size()==i1){
				List<Map> bab=liMs(bb1,sI)
				Map bab1=bab[iZ]
				if(sMt(bab1)==sEXPR)item[sI]=bab1[sI]
				else item[sI]=bb1[sI]
			}else item[sI]=bb1[sI]
		}
	}

	if(item[sDATA] instanceof Map && !mMs(item,sDATA)) item.remove(sDATA)

	if(inMem){
		// defaults
		if(sMs(item,sF)==sL) item.remove(sF) // timeValue.f
		if(sMs(item,sSM)==sAUTO) item.remove(sSM) // subscription method
		if(sMs(item,sCTP)==sI) item.remove(sCTP) // case traversal policy switch stmt
		if(item[sN] && ty && sMa(item)==sD) item.remove(sA) // variable.a sS -> const, sD-> dynamic

		/*
			statement.t = null //type
			statement.d = [] //devices
			statement.o = 'and' //operator  if, while on
			statement.n = false //negation

			statement.a = '0' //sync '1' async  Execution method
			statement.di = false; //disabled
			statement.tcp = 'c' //tcp - cancel on condition state change task cancelation policy
			statement.tep = '' //tep always task execution policy
			statement.tsp = '' //tsp override task scheduling policy

			statement.rop = 'and'; //restriction operator
			statement.rn = false; //restriction negation
			statement.r = []; // Restrictions

			statement.c = []  // conditions  (on, if)
			statement.k = [] // Tasks (ACTION)
			statement.s = [] // statements

			statement.ei = [] // else if statements (if)
			statement.e = [] // else (if, switch)
			statement.cs = [] // case statements (switch)
			statement.lo		// operand data (switch)
			statement.ctp = 'i' // case traversal policy

			statement.x = '' //variable (for, each)

			statement.z = '' //desc

			statement.p = '' // old
			statement.pr = '' // old
			statement.os = '' // old

			statement.sm = // subscription method, always, never, auto/''
			condition.co // condition operator
			condition.ts = [] // true statements
			condition.fs= [] // false statements
			condition.wt = wait type for followed by // wt: l- loose (ignore unexpected events), s- strict, n- negated (lack of requested event continues group)
			condition.wd = wait delay  for followed by

			//added by subscribe all
			condition.s = 'local'; //tos - subscription
			condition / statement.w = warning
			condition.ct = t or c (trigger, condition)
		*/
		// task cancellation policy
		// from IDE: cancel on c- condition state change (def), p- piston state change, b- condition or piston state change, ""- never cancel
		// makes 'c' the default empty for the groovy code
		if(ty in ListStmt){
			if(!sMs(item,sTCP))item[sTCP]=sN
			else if(sMs(item,sTCP)==sC) item.remove(sTCP)
			if(item[sA] instanceof String && sMa(item)==s0) item.remove(sA) // async
		}
		if(sMs(item,sTCP)==sC)warn "found tcp in $ty",null

		// item.w is warnings
		if(item[sW] instanceof List) item.remove(sW)

		if(item[sROP] && (!item[sR] || liMs(item,sR).size()==iZ)){ item.remove(sROP); item.remove(sRN) }
	}

	for(String t in ListC3){
		if(item.containsKey(t) && item[t] instanceof List){
			List<Map>lt= liMs(item,t)
			if(inMem && lt.size()==iZ) item.remove(t)
			else if(lt[iZ] instanceof Map) for(Map eI in lt)cleanCode(eI,inMem)
		}
	}
	for(String t in ListI){ // expression items
		if(item.containsKey(t) && item[t] instanceof List){
			List<Map>lt= liMs(item,t)
			if(inMem && lt.size()==iZ) item.remove(t)
			else{
				if(lt[iZ] instanceof Map) for(Map eI in lt)cleanCode(eI,inMem)
				else if(inMem) item.remove(t)
			}
		}
	}

	if(inMem){
		// comments
		if(item[sZ]!=null) item.remove(sZ)
		if(item[sZC]!=null) item.remove(sZC)
		// UI operand operating keys
		if(item[sSTR1]!=null) item.remove(sSTR1)
		if(item[sOK]!=null) item.remove(sOK)
		if(item[sL]!=null && item[sL] instanceof String) item.remove(sL)

		// operands
		if(ty==sEVERY){ // scheduleTimer
			if(sMvt(mMs(item,sLO)) in [sMS,sS,sM,sH]){ item.remove(sLO2); item.remove(sLO3) }
			else if(sMt(mMs(item,sLO2))==sC) item.remove(sLO3)
		}

		if(item[sCO]!=null){
			String co=sMs(item,sCO)
			Map comparison=(Map)AllComparisons()[co]
			if(comparison!=null){
				Integer pCnt= comparison[sP]!=null ? iMs(comparison,sP):iZ
				Integer tCnt= comparison[sT]!=null ? iMs(comparison,sT):iZ
				if(tCnt==iZ){
					switch(pCnt){
						case iZ:
							if(item[sRO]!=null)item.remove(sRO)
							if(item[sTO]!=null)item.remove(sTO)
						case i1:
							if(item[sRO2]!=null)item.remove(sRO2)
							if(item[sTO2]!=null)item.remove(sTO2)
						default:
							if(item[sRO]!=null && sMt(mMs(item,sRO))==sC) item.remove(sTO)
							if(item[sRO2]!=null && sMt(mMs(item,sRO2))==sC) item.remove(sTO2)
					}
				}
			}
		}
	}

	if(item[sEXP]!=null)cleanCode(mMs(item,sEXP),inMem)
	if(oMv(item) instanceof Map)cleanCode(mMv(item),inMem)
	if(item[sLO]!=null)cleanCode(mMs(item,sLO),inMem)
	if(item[sLO2]!=null)cleanCode(mMs(item,sLO2),inMem)
	if(item[sLO3]!=null)cleanCode(mMs(item,sLO3),inMem)

	if(item[sRO]!=null){ // .ro was overloaded in some old pistons as String like 'and'
		if(inMem && (item[sRO] instanceof String || fndEmptyOper(mMs(item,sRO))) ) item.remove(sRO)
		else if(item[sRO] instanceof Map) cleanCode(mMs(item,sRO),inMem)
	}
	for(String t in ListC4){ //['wd',sRO2,sTO,sTO2]
		if(item.containsKey(t)){
			Map mt=mMs(item,t)
			if(mt!=null){
				if(inMem && fndEmptyOper(mt)) item.remove(t)
				else cleanCode(mt,inMem)
			}
		}
	}
}

@CompileStatic
static Boolean fndEmptyOper(Map<String,Object> oper){
	Integer sz=oper.size()
	return sz==iZ || ( (sz==i2 || sz==i3) && sMt(oper)==sC && !oper[sD] && sMs(oper,sG)==sANY)
}

@CompileStatic
private void checkLabel(Map r9=null){
	Boolean act=isAct(r9)
	Boolean dis=!isEnbl(r9)
	String savedLabel=sMs(r9,sSVLBL)
	if(savedLabel==sNL){
		doLog(sERROR,"null label")
		return
	}
	String appLbl=savedLabel
	if(savedLabel!=sNL){
		if(act && !dis)wappUpdateLabel(savedLabel)
		if(!act || dis){
			String tstr
			if(dis)tstr=act ? '(Disabled) Kill switch is active' : '(Paused) Kill switch is active'
			else tstr='(Paused)'
			String res=appLbl+sSPC+span(tstr,sCLRORG)
			wappUpdateLabel(res)
		}
	}
}

// main api call points

Map deletePiston(){
	assignAS('pistonDeleted',true)
	String meth='deletePiston'
	if(eric())doLog(sDBG,meth)
	wremoveAllInUseGlobalVar()
	assignSt(sACT,false)
	clear1(true,true,true,true)	// calls clearMyCache(meth) && clearMyPiston
	return [:]
}

void config(Map data){ // creates a new piston
	if(data==null)return
	String r=sMs(data,sBIN)
	if(r!=sNL){
		assignSt(sBIN,r)
		wappUpdateSetting(sBIN,[(sTYPE):sTEXT,(sVAL):r])
	}
	String t; t=sMs(data,sATHR)
	if(t!=sNL){
		assignSt(sATHR,t)
		wappUpdateSetting(sATHR,[(sTYPE):sTEXT,(sVAL):t])
	}
	String s='initialVersion'
	t=sMs(data,s)
	if(t!=sNL)assignSt(s,t)
	clearMyCache('config')
}

/**
 * called to pause this piston
 * @return updated runTimeData for IDE
 */
@CompileStatic
Map pauseP(){
	assignSt(sACT,false)
	cleanState()
	clearMyCache('pauseP')

	LinkedHashMap r9
	r9= getRunTimeData()
	Boolean lg=isInf(r9)
	Map msg; msg=null
	if(lg){
		info 'Stopping piston...',r9,iZ
		msg=timer 'Piston stopped',r9,iN1
	}
	wunsubscribe()
	assignSt(sSUBS,[:])
	wunschedule()
	chgNextSch(r9,lZ)
	assignSt(sSCHS,[])
	wremoveAllInUseGlobalVar()
	wappRemoveSetting(sDV)
	assignSt(sTRC,[:]) // need this to clear timers in IDE if open
	if(lg) info msg,r9
	updateLogs(r9)
	assignSt(sACT,false)
	assignSt(sST,[:]+mMs(r9,sST))
	wstateRemove(sLEVT)
	Map nRtd= clear1(true,false,false,false)	// calls clearMyCache(meth) && clearMyPiston
	Map t=[ (sACT):false ]
	nRtd[sRESULT]=t
	r9=null
	return nRtd
}

/**
 * resume piston, and initialize its subscriptions & states
 * @param piston
 * @param sndEvt
 * @return ide runtime data
 */
@CompileStatic
Map resumeP(LinkedHashMap piston=null,Boolean sndEvt=true){
	assignSt(sACT,true)
	assignSt(sSUBS,[:])
	assignSt(sSCHS,[])
	assignSt(sCACHE,[:]) // reset followed by
	stNeedUpdate()
	cleanState()

	String mSmaNm=sAppId()
	getTheLock(mSmaNm,sRESUME)
	theSemaphoresVFLD[mSmaNm]=lZ
	theSemaphoresVFLD=theSemaphoresVFLD
	theQueuesVFLD[mSmaNm]=[]
	theQueuesVFLD=theQueuesVFLD
	releaseTheLock(mSmaNm)

	clearMyCache('resumeP')

	LinkedHashMap tmpRtD,r9
	tmpRtD= getTemporaryRunTimeData()
	Map msg=timer 'Piston started',tmpRtD,iN1
	if(piston!=null)tmpRtD[sPISTN]=piston
	Boolean lg=isInf(tmpRtD)
	if(lg)info 'Starting piston... ('+sHVER+')',tmpRtD,iZ
	r9= getRunTimeData(tmpRtD,null,true,false,false) //performs subscribeAll; reinitializes cache variables
	checkVersion(r9)
	if(lg)info msg,r9
	updateLogs(r9)
	assignSt(sST,[:]+mMs(r9,sST))
	Map nRtd=shortRtd(r9)
	Map t=[
		(sACT):true,
		(sSUBS):mMs(gtState(),sSUBS)
	]
	nRtd[sRESULT]=t
	tmpRtD=null
	r9=null
	// always fire resumeHandler so EVERY/timer schedules are re-registered even for pistons
	// that do not subscribe to pistonResume; EVERY statement calls scheduleTimer unconditionally
	// so the timer is re-registered without executing the EVERY body (ownEvent=false)
	if(sndEvt)
		wrunInMillis(600L,'resumeHandler',[(sDATA): [:]])
	return nRtd
}

/** get short form of runTime Data */
@CompileStatic
static Map shortRtd(Map r9){
	Map<String,Object> st=[:]+mMs(r9,sST)
	st.remove(sOLD)
	Map myRt=[
		(sID):sMs(r9,sID),
		(sACT):isAct(r9),
		(sTMSTMP):lMs(r9,sTMSTMP),
		(sCTGRY):sMs(r9,sCTGRY),
		(sMODFD): lMs(r9,sMODFD),
		(sBIN): sMs(r9,sBIN),
		(sSTATS):[
			(sNSCH):lMs(r9,sNSCH)
		],
		(sPISTN):[
			(sZ):sMs(r9,sPSTNZ)
		],
		(sST):st,
		('Cached'):bIs(r9,'Cached') ?: false
	]
	return myRt
}


Map setBin(String bin){
	String typ='setBin'
	if(!bin){
		doLog(sERROR,typ+': bad bin')
		return [:]
	}
	assignSt(sBIN,bin)
	wappUpdateSetting(sBIN,[(sTYPE):sTEXT,(sVAL):bin])
	clearMyCache(typ)
	return [:]
}

Map setLoggingLevel(String level,Boolean clrC=true){
	Integer mlogging; mlogging=level.isInteger()? level.toInteger():iZ
	mlogging=Math.min(Math.max(iZ,mlogging),i3)
	wappUpdateSetting(sLOGNG,[(sTYPE):sENUM,(sVAL):mlogging.toString()])
	assignSt(sLOGNG,mlogging)
	if(mlogging==iZ)assignSt(sLOGS,[])
	if(clrC)clearMyCache('setLoggingLevel')
	return [(sLOGNG):mlogging]
}

Map setCategory(String category){
	assignSt(sCTGRY,category)
	clearMyCache(sCTGRY)
	return [(sCTGRY):category]
}

Map updModified(Long t){
	assignSt(sMODFD,t)
	clearMyCache(sMODFD)
	return [(sMODFD):t]
}


@Field volatile static Map<String,Long> lockTimesVFLD=[:]
@Field volatile static Map<String,String> lockHolderVFLD=[:]

@CompileStatic
void getTheLock(String qname,String meth=sNL,Boolean longWait=false){
	getTheLockW(qname,meth,longWait)
}

@CompileStatic
Boolean getTheLockW(String qname,String meth=sNL,Boolean longWait=false){
	Long waitT=longWait? lTHOUS:20L
	Boolean wait; wait=false
	Integer semaNum=semaNum(qname)
	String semaSNum=semaNum.toString()
	Semaphore sema=sema(semaNum)
	while(!sema.tryAcquire()){
		// did not get lock
		Long t; t=lockTimesVFLD[semaSNum]
		if(t==null){
			t=wnow()
			lockTimesVFLD[semaSNum]=t
			lockTimesVFLD=lockTimesVFLD
		}
		if(eric())warn "waiting for ${qname} ${semaSNum} lock access, $meth, long: $longWait, holder: "+sMs(lockHolderVFLD,semaSNum),null
		wpauseExecution(waitT)
		wait=true
		if(elapseT(t)>30000L){
			sema.drainPermits()
			lockTimesVFLD[semaSNum]=(Long)null
			lockTimesVFLD=lockTimesVFLD
			if(eric())warn "overriding lock $meth",null
			break
		}
	}
	lockTimesVFLD[semaSNum]=wnow()
	lockTimesVFLD=lockTimesVFLD
	if(eric()){
		lockHolderVFLD[semaSNum]=sAppId()+sSPC+meth
		lockHolderVFLD=lockHolderVFLD
	}
	return wait
}

@CompileStatic
void releaseTheLock(String qname){
	Integer semaNum=semaNum(qname)
	String semaSNum=semaNum.toString()
	Semaphore sema=sema(semaNum)
	lockTimesVFLD[semaSNum]=(Long)null
	lockTimesVFLD=lockTimesVFLD
//	lockHolderVFLD[semaSNum]=sNL
//	lockHolderVFLD=lockHolderVFLD
	if(sema.availablePermits()==0) sema.release()
}

@Field static Semaphore theLock0FLD=new Semaphore(1)
@Field static Semaphore theLock1FLD=new Semaphore(1)
@Field static Semaphore theLock2FLD=new Semaphore(1)
@Field static Semaphore theLock3FLD=new Semaphore(1)
@Field static Semaphore theLock4FLD=new Semaphore(1)
@Field static Semaphore theLock5FLD=new Semaphore(1)
@Field static Semaphore theLock6FLD=new Semaphore(1)
@Field static Semaphore theLock7FLD=new Semaphore(1)
@Field static Semaphore theLock8FLD=new Semaphore(1)
@Field static Semaphore theLock9FLD=new Semaphore(1)
@Field static Semaphore theLock10FLD=new Semaphore(1)
@Field static Semaphore theLock11FLD=new Semaphore(1)
@Field static Semaphore theLock12FLD=new Semaphore(1)
@Field static Semaphore theLock13FLD=new Semaphore(1)
@Field static Semaphore theLock14FLD=new Semaphore(1)
@Field static Semaphore theLock15FLD=new Semaphore(1)
@Field static Semaphore theLock16FLD=new Semaphore(1)
@Field static Semaphore theLock17FLD=new Semaphore(1)
@Field static Semaphore theLock18FLD=new Semaphore(1)
@Field static Semaphore theLock19FLD=new Semaphore(1)
@Field static Semaphore theLock20FLD=new Semaphore(1)
@Field static Semaphore theLock21FLD=new Semaphore(1)
@Field static Semaphore theLock22FLD=new Semaphore(1)
@Field static Semaphore theLock23FLD=new Semaphore(1)

@Field static final Integer iStripes=22
@Field static Map<String,Integer> semaNumCacheFLD=[:]
@CompileStatic
static Integer semaNum(String name){
	Integer cached=semaNumCacheFLD[name]
	if(cached!=null)return cached
	Integer result
	if(name.isInteger())result=name.toInteger()%iStripes
	else if(name==sTSLF)result=iStripes
	else if(name==sTGBL)result=iStripes+i1
	else result=(smear(name.hashCode()) & 0x7FFFFFFF)%iStripes
	semaNumCacheFLD[name]=result
	return result
}

@CompileStatic
Semaphore sema(Integer snum){
	switch(snum){
		case iStripes: return theLock22FLD
		case iZ: return theLock0FLD
		case i1: return theLock1FLD
		case i2: return theLock2FLD
		case i3: return theLock3FLD
		case i4: return theLock4FLD
		case i5: return theLock5FLD
		case i6: return theLock6FLD
		case i7: return theLock7FLD
		case i8: return theLock8FLD
		case i9: return theLock9FLD
		case i10: return theLock10FLD
		case i11: return theLock11FLD
		case i12: return theLock12FLD
		case i13: return theLock13FLD
		case 14: return theLock14FLD
		case i15: return theLock15FLD
		case i16: return theLock16FLD
		case 17: return theLock17FLD
		case 18: return theLock18FLD
		case 19: return theLock19FLD
		case i20: return theLock20FLD
		case 21: return theLock21FLD
		case (iStripes+i1): return theLock23FLD
		default:
			doLog(sERROR,"bad hash result $snum")
			return theLock0FLD
	}
}

// Murmur3 32-bit finalizer: better avalanche than the old Wang/Jenkins two-step
@CompileStatic
private static Integer smear(Integer hashC){
	int h=hashC
	h ^= (h >>> 16)
	h *= (int)0x85ebca6b
	h ^= (h >>> 13)
	h *= (int)0xc2b2ae35
	h ^= (h >>> 16)
	return h
}

/* wrappers */

/** Normalizes a raw platform event object into a plain LinkedHashMap for use
 *  throughout the runtime (r9).  Extracts the canonical fields (timestamp, name,
 *  value, description text, unit, physical flag) plus the device reference
 *  (converted via cvtDev).  For synthetic / non-native events (anything that is
 *  not a com.hubitat.hub.domain.Event) also copies extended fields: index,
 *  recovery flag, schedule, content-type, response code, sort data, and response
 *  data.  Always appends the raw JSON data field.  Returns null for a null event. */
private static LinkedHashMap fixEvt(event){
	if(event!=null){
		Map mEvt=[
			(sT):((Date)event.date).getTime(),
			(sNM):(String)event[sNM],
			(sVAL):event[sVAL],
			(sDESCTXT):(String)event[sDESCTXT],
			(sUNIT):event[sUNIT],
			(sPHYS):!!event[sPHYS],
			(sSCH) : null,
		]
		def b
		b=event.device; if(b!=null)mEvt[sDEV]=cvtDev(b)
		if(!(event instanceof com.hubitat.hub.domain.Event)){
			for (String s in [sINDX, sRECOVERY, sSCH, sCONTENTT, sRESPCODE, sSRTDATA, sRESPDATA]){
				b=event[s]; if(b!=null)mEvt[s]=b
			}
		}
		mEvt[sJSOND]= event[sJSOND]
		return mEvt
	}
	return null
}

/** Strips noise keys from a normalized event map to reduce memory and
 *  serialization overhead.  Removes unit and description-text entries when null
 *  (absent from the source event), the index when it is the default zero value,
 *  and the physical flag when false (the common case).  Mutates and returns the
 *  same map; callers that need the cleaned form in-place can ignore the return. */
@CompileStatic
private static Map cleanEvt(Map evt){
	if(evt[sUNIT]==null) evt.remove(sUNIT)
	if(evt[sDESCTXT]==null) evt.remove(sDESCTXT)
	if(evt[sINDX]==iZ) evt.remove(sINDX)
	if(!bIs(evt,sPHYS)) evt.remove(sPHYS)
	return evt
}

@Field volatile static Map<String,List<Map>> theQueuesVFLD=[:]
@Field volatile static Map<String,Long> theSemaphoresVFLD=[:]

@Field static final String sSEMA='semaphore'
@Field static final String sSEMAN='semaphoreName'
@Field static final String sSEMADEL='semaphoreDelay'
@Field static final String sWAITED='waited'
@Field static final String sEXITOUT='exitOut'

/** This can a) lock semaphore b) wait for semaphore c) queue event d) just fall through (no locking or waiting) */
@CompileStatic
private LinkedHashMap lockOrQueueSemaphore(Boolean synchr,Map event,Boolean queue,Map r9){
	Long tt1,startTime,r_semaphore,semaphoreDelay,lastSemaphore
	tt1=wnow()
	startTime=tt1
	r_semaphore=lZ
	semaphoreDelay=lZ
	String semaphoreName,mSmaNm
	semaphoreName=sNL
	Boolean didQ,waited,clrC
	didQ=false
	waited=false

	if(synchr){
		mSmaNm=sAppId()
		waited=getTheLockW(mSmaNm,sLCK1)
		tt1=wnow()

		clrC=false
		Integer qsize; qsize=iZ
		while(true){
			Long t0=theSemaphoresVFLD[mSmaNm]
			Long tt0=t0!=null ? t0:lZ
			lastSemaphore=tt0
			if(lastSemaphore==lZ || tt1-lastSemaphore>100000L){
				theSemaphoresVFLD[mSmaNm]=tt1
				theSemaphoresVFLD=theSemaphoresVFLD
				mb()
				semaphoreName=mSmaNm
				semaphoreDelay=waited ? tt1-startTime:lZ
				r_semaphore=tt1
				break
			}
			if(queue){
				if(event!=null){
					Map mEvt=event
					List<Map> evtQ
					evtQ=theQueuesVFLD[mSmaNm]
					evtQ=evtQ!=null ? evtQ:(List<Map>)[]
					qsize=evtQ.size()
					if(qsize>i20) clrC=true
					else{
						evtQ.push(mEvt)
						theQueuesVFLD[mSmaNm]=evtQ
						theQueuesVFLD=theQueuesVFLD
						mb()
						didQ=true
					}
				}
				break
			}else{
				releaseTheLock(mSmaNm)
				waited=true
				wpauseExecution(l100)
				getTheLock(mSmaNm,sLCK2)
				tt1=wnow()
			}
		}
		releaseTheLock(mSmaNm)
		if(clrC){
			error "large queue size ${qsize}, dropping event",r9
			didQ=true
		}
	}
	return [
		(sSEMA):r_semaphore,
		(sSEMAN):semaphoreName,
		(sSEMADEL):semaphoreDelay,
		(sWAITED):waited,
		(sEXITOUT):didQ
	]
}

@Field volatile static LinkedHashMap<String,LinkedHashMap> theCacheVFLD=[:] // each piston has a map

@Field static final String sCLRMC='clearMyCache'
/** clear Piston cache */
@CompileStatic
private void clearMyCache(String meth=sNL){
	String appStr=sAppId()
	String myId=appStr
	if(!myId)return
	Boolean clrd; clrd=false
	String mSmaNm=appStr
	getTheLock(mSmaNm,sCLRMC)
	Map t0; t0=theCacheVFLD[myId]
	if(t0){
		theCacheVFLD[myId]=[:] as LinkedHashMap
		theCacheVFLD=theCacheVFLD
		clrd=true
		t0=null
	}
	releaseTheLock(mSmaNm)
	if(eric() && clrd)debug 'clearing piston data cache '+meth,null
}

/** Returns a snapshot of the piston's in-memory cache map (theCacheVFLD).
 *  Acquires the piston lock, validates the cached entry has the expected structure
 *  (sCACHE map + sBLD integer), and returns a shallow copy.  If the entry is
 *  structurally invalid it is cleared; if it is absent and retry is true, the
 *  cache is rebuilt via getDSCache (with optional state write when Upd=true)
 *  and then re-read.  Returns null when no valid cache can be produced. */
@CompileStatic
private LinkedHashMap getCachedMaps(String meth=sNL,Boolean retry=true,Boolean Upd=true){
	String myId=sAppId()
	String mSmaNm=myId
	LinkedHashMap res
	getTheLock(mSmaNm,sI)
	res=theCacheVFLD[myId]
	if(res){
		if(res[sCACHE] instanceof Map && res[sBLD] instanceof Integer){
			res=new LinkedHashMap(res)
			releaseTheLock(mSmaNm)
			return res
		}
		theCacheVFLD[myId]=([:] as LinkedHashMap)
		theCacheVFLD=theCacheVFLD
	}
	releaseTheLock(mSmaNm)
	if(retry){
		LinkedHashMap t=getDSCache(meth,Upd)
		if(!Upd)return t
		return getCachedMaps(meth,false,Upd)
	}
//	if(eric())doLog(sWARN,'cached map nf')
	return null
}

private Map gtCachedAtomicState(){
	Long atomStart=wnow()
	def aS
	atomicState.loadState()
	aS=atomicState.@backingMap
	if(((String)gtSetting(sLOGNG))?.toInteger()>i2)doLog(sDBG,"AtomicState generated in ${elapseT(atomStart)}ms")
	return aS
}

@Field static final String sGDS='getDSCache'

/** Builds and returns the full piston runtime map (r9), merging piston state with
 *  the parent cache (getParentCache).  Two paths:
 *
 *  Fast path — cache hit: theCacheVFLD already has a valid entry; merges it with
 *  the parent cache and returns without touching state.
 *
 *  Cold path — cache miss: reads gtState(), backfills any missing upgrade fields
 *  (sPEP, sSVLBL), constructs the full r9 map (metadata, cache, store, vars,
 *  schedules, traces, logs, devices, TZs, memory, stats/log limits), then merges
 *  with the parent cache.  If Upd=true the new entry is written back to
 *  theCacheVFLD so subsequent calls take the fast path; if Upd=false the map is
 *  returned without caching it (used by getCachedMaps' non-retry read path).
 *
 *  Always records state-read latency in r9[sSTACCESS] and calls checkLabel on a
 *  fresh build when the piston has a non-zero build number. */
@CompileStatic
private LinkedHashMap getDSCache(String meth,Boolean Upd=true){
	String myId=sAppId()
	String appStr=myId
	String mSmaNm=myId
	LinkedHashMap r9,res,pC
	r9=null
	pC=getParentCache()
	Boolean sendM; sendM=false
	Long stateStart,stateEnd
	stateStart=stateEnd=lZ

	getTheLock(mSmaNm,sGDS)
	res=theCacheVFLD[myId]

	if(!res){
		releaseTheLock(mSmaNm)
		stateStart=wnow()
		Map mst=gtState()
		if(mst[sPEP]==null){ // upgrades of older pistons
			LinkedHashMap piston=recreatePiston()
			assignSt(sPEP, !!gtPOpt([(sPISTN):piston],sPEP) )
		}
		Integer bld=iMs(mst,sBLD)
		String ttt
		ttt=sMs(mst,sSVLBL)
		if(ttt==sNL){
			ttt=gtAppN()
			if(bld>iZ){
				assignSt(sSVLBL,ttt)
			}
		}
		LinkedHashMap te1
		te1=[
			(sFFT):iZ,
			(sRUN):true,
			(sLOGNG): mst[sLOGNG]!=null ? iMs(mst,sLOGNG):iZ,
			(sPEP): bIs(mst,sPEP),
			(sCACHE): [:],
			(sNWCACHE):[:],
			(sDEVS): [:],
			(sPISTN): null,
			(sTRC): [:],
			(sSCHS):[],
			(sVARS):[:],
			(sST):[:],
			(sLOCID): sNL,
			(snId): appStr,
			(spId): sPAppId(),
			(sLOGS):[],
			(sACT): bIs(mst,sACT),
			(sPCACHE):[:],
			(sTMSTMP):lZ,
			(sNSCH): lZ,
			(sLEXEC): lZ,
			(sSTORE): [:],
		]
		Map t1
		t1=te1+[
			(sID): hashPID(appStr), // TODO this is not unique across accounts, locations, webCoRE instances
			(sNM): ttt,
			(sSVLBL): ttt,
			(sCTGRY): mst[sCTGRY] ?: iZ,
			(sCREAT): lMs(mst,sCREAT),
			(sMODFD): lMs(mst,sMODFD),
			(sBLD): bld,
			(sATHR): sMs(mst,sATHR),
			(sBIN): sMs(mst,sBIN),
			(sRTHIS): []
		] as Map
		stateEnd=wnow()

		Map m0,aS
		List l0
		aS=isPep(t1)? gtCachedAtomicState():mst
		m0=mMs(aS,sCACHE); t1[sCACHE]=m0 ? m0:[:]
		m0=mMs(aS,sSTORE); t1[sSTORE]=m0 ? m0:[:]
		m0=mMs(aS,sST); t1[sST]=m0 ? m0:[:]
		t1[sPSTNZ]=sMs(aS,sPSTNZ)
		m0=mMs(aS,sTRC); t1[sTRC]=m0 ? m0:[:]
		l0=liMs(aS,sSCHS); t1[sSCHS]=l0 ? []+l0:[]
		t1[sNSCH]=lMs(aS,sNSCH)
		t1[sLEXEC]=lMs(aS,sLEXEC)
		l0=liMs(aS,sLOGS); t1[sLOGS]=l0 ? []+l0:[]
		m0=mMs(aS,sVARS); t1[sVARS]=m0 ? [:]+m0:[:]

		loadTZs(t1,sMs(aS,sTZ))

		t1[sMEM]=mem()
		resetRandomValues(t1)
		def devs=gtSetting(sDV)
		t1[sDEVS]=devs && devs instanceof List ? ((List)devs).collectEntries{ Object it -> [(hashD(t1,it)):it]}:[:]

		if((Boolean)gtSetting(sLOGHE))t1[sLOGHE]=true
		Integer mS,st,at1
		mS=iMs(gtPLimits(),sMSTATS)
		st=(Integer)gtSetting(sMSTATS)
		at1=st!=null ? st:mS
		if(at1<=iZ)at1=mS
		if(at1<i2)at1=i2
		t1[sMSTATS]=at1

		Integer myL,ml,lim
		myL=iMs(gtPLimits(),sMLOGS)
		ml=(Integer)gtSetting(sMLOGS)
		lim=ml!=null ? ml:myL
		if(lim<iZ)lim=myL
		t1[sMLOGS]=lim

		res= t1 as LinkedHashMap
		r9= (LinkedHashMap)(res+pC)

		sendM=true
		if(Upd){
			t1['Cached']=true
			getTheLock(mSmaNm,sGDS+s2)
			theCacheVFLD[myId]= t1 as LinkedHashMap
			theCacheVFLD=theCacheVFLD
			releaseTheLock(mSmaNm)
		}
		te1=null;t1=null;aS=null;m0=null;l0=null

		if(eric() && sendM){
			String s= Upd ? '/cached':sBLK
			debug 'creating'+s+' my piston cache '+meth,r9
		}
	}else{
		r9= (LinkedHashMap)(res+pC)
		releaseTheLock(mSmaNm)
	}
	if(stateStart!=lZ) r9[sSTACCESS]=stateEnd-stateStart
	pC=null
	res=null
	if(sendM && iMs(r9,sBLD)!=iZ)checkLabel(r9)
	return r9
}

@Field volatile static LinkedHashMap<String,LinkedHashMap> theParentCacheVFLD=[:]

void clearParentCache(String meth=sNL){
	String lockTyp='clearParentCache'
	String semName=sTSLF
	String wName=sPAppId()
	getTheLock(semName,lockTyp,true)
	theParentCacheVFLD[wName]=null
	theParentCacheVFLD=theParentCacheVFLD
	theCacheVFLD=[:] // reset all pistons cache
	clearHashMap(wName)
	releaseTheLock(semName)
	loadCDB()
	if(eric())doLog(sDBG,"clearing parent cache and all piston caches $meth")
}

// load caches
private void loadCDB(){
	VirtualDevicesF()
	ComparisonsF()
	VirtualCommandsF()
	AttributesF()
	getColorsF()
	fill_ListAL()
	fill_LS()
	fill_TIM()
	fill_mL()
	fill_are_async()
	fill_STMT()
	fill_HttpAsync()
	fill_cleanData()
	fill_WCMDS()
	fill_cls()
	fill_FATTRS()
	fill_CACH()
	PhysicalCommandsF()
}

private LinkedHashMap getParentCache(){
	String wName=sPAppId()
	LinkedHashMap res
	res=theParentCacheVFLD[wName]
	if(res==null){
		Map t0=wgtPdata()
		String lpe= sLOGPE
		String aid= 'accountId'
		Map t1=[
			('coreVersion'): sMs(t0,'sCv'),
			(sHCOREVER): sMs(t0,'sHv'),
			(sPWRSRC): sMs(t0,sPWRSRC),
			(sREGION): sMs(t0,sREGION),
			(sINSTID): sMs(t0,sINSTID),
			(sSETTINGS): mMs(t0,'stsettings'),
			(sENABLED): isEnbl(t0),
			(sLIFX): mMs(t0,sLIFX),
			(lpe): bIs(t0,lpe),
			(aid): sMs(t0,aid),
			(sNACCTSID): bIs(t0,sNACCTSID),
			(sLOCID): sMs(t0,sLOCID),
			(sOLDLOC): (List)t0[sOLDLOC],
			(sALLLOC): (List)t0[sALLLOC],
			(sINCIDENTS): (List)t0[sINCIDENTS],
			(sUSELFUELS): bIs(t0,sUSELFUELS)
		]
		String semName=sTSLF
		Boolean sendM; sendM=false
		getTheLock(semName,sGETPCACHE,true)
		res=theParentCacheVFLD[wName]
		if(res==null){
			res=t1
			theParentCacheVFLD[wName]=t1
			theParentCacheVFLD=theParentCacheVFLD
			clearHashMap(wName)
			sendM=true
		}
		releaseTheLock(semName)
		t1=null
		if(eric() && sendM)debug 'gathering parent cache',null
	}
	return res
}

@CompileStatic
/** Produces a minimal r9 map for use before a full getRunTimeData call is
 *  possible (e.g. early-phase operations, error paths, or when no prior r9
 *  exists).  Lazily initializes the physical-commands database the first time it
 *  is called (under the self-semaphore).  Delegates to getDSCache for the piston
 *  cache, then stamps the map as temporary (sTMP=true — removed by getRunTimeData
 *  when it upgrades to a full r9), sets the start timestamp, seeds the log list
 *  with a single start entry, and sets debug level to zero. */
private LinkedHashMap getTemporaryRunTimeData(Long startTime=wnow()){
	if(thePhysCommandsFLD==null){ //do one time load once
		String semName=sTSLF
		getTheLock(semName,sGETTRTD,true)
		if(thePhysCommandsFLD==null) loadCDB()
		releaseTheLock(semName)
	}
	LinkedHashMap r9=getDSCache(sGETTRTD)
	r9[sTMP]=true
	r9[sTMSTMP]=startTime
	r9[sLOGS]=[[(sT):startTime]]
	r9[sDBGLVL]=iZ
	return r9
}

/**
 * get full runTime data
 * @param ir9 some initial runTimeData (optional)
 * @param retSt lock data
 * @param doit reset local variables initial value, perform subscriptions
 * @param shorten optimize piston
 * @param inMem optimize for in memory
 * @return
 */
@CompileStatic
private LinkedHashMap getRunTimeData(LinkedHashMap ir9=null,LinkedHashMap retSt=null,Boolean doit=false,Boolean shorten=true,Boolean inMem=false){
	LinkedHashMap r9,piston,m1
	piston=null
	r9=ir9
	Long started=wnow()
	List logs; logs=[]
	Long lstarted,lended; lstarted=lZ; lended=lZ
	Integer dbgLevel; dbgLevel=iZ
	if(r9!=null){
		logs=r9[sLOGS]!=null ? liMs(r9,sLOGS):[]
		lstarted=r9[sLSTART]!=null ? lMs(r9,sLSTART):lZ
		lended=r9[sLSEND]!=null ? lMs(r9,sLSEND):lZ
		piston=r9[sPISTN]!=null ? (LinkedHashMap)r9[sPISTN]:null
		dbgLevel=r9[sDBGLVL]!=null ? iMs(r9,sDBGLVL):iZ
	}else r9= getTemporaryRunTimeData(started)
	Long timestamp=lMs(r9,sTMSTMP)

	if(r9[sTMP]!=null) r9.remove(sTMP)

	m1=[:]
	if(retSt!=null) m1=retSt
	r9=(LinkedHashMap)(r9+m1)

	r9[sTMSTMP]=timestamp
	r9[sLSTART]=lstarted
	r9[sLSEND]=lended
	r9[sLOGS]= logs.size()>iZ ? logs:[[(sT):timestamp]]
	r9[sDBGLVL]=dbgLevel

	r9[sTRC]=[(sT):timestamp,(sPTS):[:]] as LinkedHashMap
	r9[sSTATS]=[(sNSCH):lZ] as LinkedHashMap
	r9[sNWCACHE]=[:]
	r9[sSCHS]=[]
	r9[sCNCLATNS]=initCncl()
	r9[sUPDDEVS]=false
	r9[sSYSVARS]=getSystemVariables()

	Map aS
	aS=getCachedMaps(sGETRTD)
	aS=aS!=null?aS:[:]
	Map st=mMs(aS,sST)
	Map st1=st!=null && st instanceof Map ? [:]+st : [(sOLD):sBLK,(sNEW):sBLK] as LinkedHashMap
	st1[sOLD]=sMs(st1,sNEW)
	r9[sST]=st1

	r9[sPSTART]=wnow()

	if(piston==null)piston=recreatePiston(shorten,inMem)
	Boolean doSubScribe=!bIs(piston,sCACHED)

	r9[sPISTN]=piston

	getLocalVariables(r9,aS,doit)
	piston=null

	if(doSubScribe || doit){
		subscribeAll(r9,doit,inMem)
		String pNm=sMs(r9,snId)
		Map pData
		pData=mMs(thePistonCacheFLD,pNm)
		if(shorten && inMem && pNm!=sBLK && pData!=null && pData[sPIS]==null){
			pData[sPIS]=[:]+(LinkedHashMap)r9[sPISTN]
			thePistonCacheFLD[pNm]=[:]+pData
			pData=null
			mb()
			if(eric()){
				debug 'creating piston-code-cache',null
				dumpPCsize()
			}
		}
	}
	Long t0=wnow()
	r9[sPEND]=t0
	r9[sGENIN]=t0-started
	return r9
}

private void dumpPCsize(){
	Map pL
	Integer t0,t1
	t0=iZ
	t1=iZ
	try{
		pL=[:]+thePistonCacheFLD
		t0=pL.size()
		t1="${pL}".size()
	}catch(ignored){}
	pL=null
	String mStr="piston plist is ${t0} elements, and ${t1} bytes".toString()
	doLog(sDBG,mStr)
	if(t1>40000000){
		thePistonCacheFLD=[:]
		mb()
		doLog(sWARN,"clearing entire "+mStr)
	}
}

private void checkVersion(Map r9){
	String ver=sHVER
	String t0=sMs(r9,sHCOREVER)
	if(ver!=t0){
		String tt0="child app's version($ver)".toString()
		String tt1="parent app's version($t0)".toString()
		String tt2=' is newer than the '
		String msg
		Closure padVer={ String v -> v.replaceAll(/(\d+)/){ m -> m[0].padLeft(10,'0') } }
		if(padVer(ver)>padVer(t0))msg=tt0+tt2+tt1
		else msg=tt1+tt2+tt0
		warn "WARNING: Results may be unreliable because the "+msg+". Please update both apps to the same version.",r9
	}
	if(mTZ()==null)
		error 'Your location is not setup correctly - timezone information is missing. Please select your location by placing the pin and radius on the map, then tap Save, and then tap Done. You may encounter error or incorrect timing until fixed.',r9
}

@Field static Map FLDPLimits=[:]
static Map gtPLimits(){
	if(FLDPLimits.size()==iZ) FLDPLimits=fillPL()
	return FLDPLimits
}

@Field static final String sSCHREM='schRemain'
@Field static final String sSCHVARIANCE='schVariance'
@Field static final String sEXCTIME='execMaxLimTime'
@Field static final String sSHLIMTIME='slTime'
@Field static final String sLONGLIMTIME='longlTime'
@Field static final String sSHORTDEL='shortDelay'
@Field static final String sLONGDEL='longDelay'
@Field static final String sTPAUSELIM='taskPauseLim'
@Field static final String sDEVMAXDEL='deviceMaxDelay'
@Field static final String sMSTATS='maxStats'
@Field static final String sMLOGS='maxLogs'

static Map fillPL(){
	return [
			(sSCHREM): 15000L, // this or longer remaining executionTime to process additional schedules
			(sSCHVARIANCE): 63L,
			(sEXCTIME): 40000L, // time we stop execution of this run
			(sSHLIMTIME): 14300L, // time before we start inserting pauses
			(sLONGLIMTIME): 20000L, // transition from short to Long delay
			(sSHORTDEL): 150L,
			(sLONGDEL): 500L,
			(sTPAUSELIM): 250L, // piston requested delay less than this can pause
			(sDEVMAXDEL): 1000L,
			(sMSTATS): 50, // actually 1/2 due to graph squaring
			(sMLOGS): 50,
	]
}

@CompileStatic
static String stripH(String str){
	if(!str) return sBLK
	int cut= str.indexOf(sSPANS)
	String res= cut >= iZ ? str.substring(iZ, cut) : str
	cut= res.indexOf('CancelAlert')
	return (cut >= iZ ? res.substring(iZ, cut) : res).trim()
}

@CompileStatic
private static Boolean stJson(String c){ return c!=sNL && c.startsWith(sOB) && c.endsWith(sCB) }
@CompileStatic
private static Boolean stJson1(String c){ return c!=sNL && c.startsWith(sLB) && c.endsWith(sRB) }


/** EVENT HANDLING								**/

/** device events */
void deviceHandler(event){ handleEvents(event) }

@CompileStatic
Map commonHandle(String nm,v=null){
	handleEvents([(sDATE):new Date(),(sDEV):gtLocation(),(sNM):nm,(sVAL):v!=null? v:wnow()])
	return [:]
}

/* IDE calls via parent */

Map test(){ commonHandle('test') }

Map clickTile(tidx){ commonHandle(sTILE,tidx); return (Map)gtSt(sST) ?: [:] }

Map clearLogs(){
	clear1()
	return [:]
}

/* parent calls that each piston must process on itself */

Map clearLogsQ(){ commonHandle(sCLRL) }

Map clearCache(){ commonHandle(sCLRC) }

Map clearAllQ(){ commonHandle(sCLRA) }

Map pausePiston(){
	commonHandle(sPAUSE) // this may queue, below fakes response
	fakeResp(false)
}

/** Called by parent when the global kill switch is enabled.
 *  Unsubscribes and unschedules without touching state['active'] so that
 *  per-piston pause state is preserved for kill-switch re-enable recovery via resumeP(). */
void killSwitchDisable(){
	if(!bIs(gtState(),sACT)) return  // individually paused — subscriptions already gone
	wunsubscribe()
	wunschedule()
	assignSt(sSCHS,[])
	assignSt(sSUBS,[:])
}

Map fakeResp(Boolean v){
	Map tmpRtD, nRtD
	tmpRtD= getTemporaryRunTimeData()
	nRtD= shortRtd(tmpRtD) // getRunTimeData(tmpRtD,null,false,true,true))
	nRtD[sACT]=v
	Map t=[ (sACT):v ]
	nRtD[sRESULT]=t
	return nRtD
}

Map resume(){
	commonHandle(sRESUME) // this may queue, below fakes response
	fakeResp(true)
}

/** called by parent */
void execute(Map data,String src){
	handleEvents([(sDATE):new Date(),(sDEV):gtLocation(),(sNM):'execute',(sVAL): src!=null ? src:wnow(),(sJSOND):data],false)
}


/** called as runInMillis */
void resumeHandler(){ commonHandle(sPSTNRSM) }

@Field static final String sTIMHNDR='timeHandler'
/** called as runInMillis; it really is not an event */
void timeHandler(event){ timeHelper(event,false) }

void timeHelper(event,Boolean recovery){
	Long t= lMt(event)
	handleEvents([(sDATE):new Date(t),(sDEV):gtLocation(),(sNM):sTIME,(sVAL):t,(sSCH):event,(sRECOVERY):recovery],!recovery)
}

/* wrappers */
void sendExecuteEvt(String pistonId,String val,String desc,Map data){
	String json= JsonOutput.toJson(data)
	sendLocationEvent((sNM):pistonId,(sVAL):val,isStateChange:true,displayed:false,linkText:desc,(sDESCTXT):desc,(sDATA):json)
}

void executeHandler(event){
	Map data; data=null
	def d1=event[sDATA]
	if(d1 instanceof String){
		String d=(String)d1
		if(stJson(d))data= (LinkedHashMap)new JsonSlurper().parseText(d)
	}
	if(event[sVAL]==sRECOVERY)
		handleEvents([(sDATE):event.date,(sDEV):gtLocation(),(sNM):sTIME,(sVAL):event[sVAL],(sSCH):[t:wnow()],(sRECOVERY):true],false)
	else
		handleEvents([(sDATE):event.date,(sDEV):gtLocation(),(sNM):'execute',(sVAL):event[sVAL],(sJSOND):(data ?: event[sJSOND])])
}

@Field static final String sEXS='Execution stage started'
@Field static final String sEXC='Execution stage complete.'
@Field static final String sEPS='Event processed successfully'
@Field static final String sEPF='Event processing failed'

@CompileStatic
void handleEvents(evt,Boolean queue=true,Boolean callMySelf=false){
	Long startTime=wnow()
	LinkedHashMap event,tmpRtD,retSt
	event=fixEvt(evt)
	tmpRtD= getTemporaryRunTimeData(startTime)
	Map msg=timer(sEPS,tmpRtD,iN1) ?: ([:] as LinkedHashMap)
	String evntName; evntName=sMs(event,sNM)
	String evntVal=String.valueOf(event[sVAL])
	Long eventDelay=startTime-lMt(event)
	Integer lg=iMs(tmpRtD,sLOGNG)
	if(lg!=iZ){
		String devStr=gtLbl(event[sDEV])
		String recStr
		recStr=evntName==sTIME && bIs(event,sRECOVERY) ? '/recovery':sBLK
		recStr+=bIs(event,sPHYS) ? '/physical':sBLK
		String valStr; valStr= evntVal
		if(evntName in [sHSMALRT,sHSMRULE,sHSMRULES])
			valStr+= ((evntName==sHSMALRT && evntVal==sRULE) || evntName in [sHSMRULE,sHSMRULES] ? sCOMMA+stripH(sMs(event,sDESCTXT)):sBLK)
		String mymsg
		mymsg= !queue && callMySelf ? 'Working queued' : 'Received'
		mymsg+=' event ['+devStr+'].'+evntName+recStr+' = '+valStr+" with a delay of ${eventDelay}ms"
		if(lg>i1)mymsg+=", canQueue: ${queue}, calledMyself: ${callMySelf}"
		mymsg=mymsg.toString()
		info mymsg,tmpRtD,iZ
	}


	Boolean myPep=isPep(tmpRtD)
	Boolean strictSync=true // could be a setting
	Boolean serializationOn=!myPep // && true // on / off switch
	Boolean doSerialization=serializationOn && !callMySelf

	tmpRtD[sLSTART]=wnow()
	retSt=[(sSEMA):lZ,(sSEMAN):sNL,(sSEMADEL):lZ] as LinkedHashMap
	if(doSerialization){
		retSt=lockOrQueueSemaphore(doSerialization,event,queue,tmpRtD)
		if(bIs(retSt,sEXITOUT)){
			if(lg!=iZ){
				msg[sM]='Event queued'
				info msg,tmpRtD
			}
			updateLogs(tmpRtD)
			event=null
			tmpRtD=null
			return
		}
		Long tl= lMs(retSt,sSEMADEL)
		if(lg>i2 && tl>lZ)warn 'Piston waited for semaphore '+tl+sMS,tmpRtD
	}
	tmpRtD[sLSEND]=wnow()


	Boolean clrC; clrC=evntName==sCLRC
	Boolean clrL=evntName==sCLRL
	Boolean clrA=evntName==sCLRA
	Boolean pause=evntName==sPAUSE
	Boolean resume=evntName==sRESUME

	if(pause || resume){
		Map nrtD
		if(lg!=iZ){
			msg[sM]='Event queued'
			info msg,tmpRtD
		}
		updateLogs(tmpRtD)
		if(resume){
			nrtD=resumeP()
		}else{ // pause
			nrtD=pauseP()
		}
		relaypCall(nrtD)
		tmpRtD= getTemporaryRunTimeData(startTime)
	}

	Boolean act=isAct(tmpRtD)
	Boolean dis=!isEnbl(tmpRtD)
	Boolean end = !act || dis
	if(end){
		if(lg!=iZ){
			String tstr=' active, aborting piston execution.'
			if(!act)msg[sM]='Piston is not'+tstr+' (Paused)' // pause/resume piston
			if(dis)msg[sM]='Kill switch is'+tstr
			info msg,tmpRtD
		}
		updateLogs(tmpRtD)
	}
	if(clrC||clrL||clrA){
		updateLogs(tmpRtD)
		Map nrtD; nrtD= null
		if(clrL) nrtD= clear1(true, true, true, false, true)
		else if(clrA) nrtD= clear1(true, true, true, true) // resets semaphore
		else if(clrC){
			Long lexec= lMs(gtState(),sLEXEC)
			if(lexec==null || elapseT(lexec) > 3660000L) nrtD= clear1(true, false, false, false)
			else nrtD= shortRtd(tmpRtD) // getRunTimeData(tmpRtD,null,false,true,true))
		}
		relaypCall(nrtD)
		//if(clrA) return // no longer have semaphore
		tmpRtD= getTemporaryRunTimeData(startTime)
	}



//measure how Long first state access takes
	Long stAccess; stAccess=lZ
	if(lg>iZ && !myPep){
		if(tmpRtD[sSTACCESS]==null){
			Long stStart,b
			stStart=wnow()
			b=(Long)gtSt(sNSCH) // forcing state accesses
			List a=(List)gtSt(sSCHS)
			Map pEvt=(Map)gtSt(sLEVT)
			Long stEnd=wnow()
			stAccess=stEnd-stStart
		}else stAccess=lMs(tmpRtD,sSTACCESS)
	}

	tmpRtD[sPCACHE]=[:]
	LinkedHashMap r9
	r9= getRunTimeData(tmpRtD,retSt,false,true,true)
	tmpRtD=null
	retSt=null
	checkVersion(r9)

	Long theend=wnow()
	Long le=lMs(r9,sLSEND)
	Long t0=theend-startTime
	Long t1=le-lMs(r9,sLSTART)
	Long t2=lMs(r9,sGENIN)
	Long t3=lMs(r9,sPEND)-lMs(r9,sPSTART)
	r9[sCURS]=[(sI):t0.toInteger(),(sL):t1.toInteger(),(sR):t2.toInteger(),(sP):t3.toInteger(),(sS):stAccess.toInteger()] as LinkedHashMap
	if(lg>i1){
		Long missing=t0-t1-t2-stAccess
		Long t4=le-startTime
		Long t5=theend-le
		String Msg= "Runtime (${r9.size()} keys) initialized ".toString()
		String adMsg= lg>i2 || eric() ? "${t0} LockT > ${t1}ms > r9T > ${t2}ms > pistonT > ${t3}ms (first state access ${stAccess} m:${missing} $t4 $t5)".toString() : sBLK
		if(lg>i2)debug Msg+adMsg+" (${sHVER})".toString(),r9
		else trace Msg+"in ${t1+t2+t3}ms (${sHVER}) ".toString()+adMsg,r9
	}
	for(String foo in cleanData1) r9.remove(foo)

	resetRandomValues(r9)
	r9[sTPAUSE]=lZ
	((Map)r9[sSTATS])[sTIMING]=[(sT):startTime,(sD):eventDelay>lZ ? eventDelay:lZ,(sL):elapseT(startTime)] as LinkedHashMap

	Boolean success,firstTime; success=true; firstTime=false
	Long eStrt=wnow()

	if( !(end||clrC||clrL||clrA||pause||resume) ){ // if not an event we already handled...
		//debug
		Long tl=lMs(r9,sNSCH)
		String lsts='lastSchedule'
		if(lMs(r9,lsts)!=tl){
			r9[lsts]=tl
			updateCacheFld(r9,lsts,tl,sT+s1,true)
		}

		Map msg2; msg2=null
		Boolean syncTime
		firstTime=true
		if( (evntName==sTIME && bIs(event,sRECOVERY)) || !(evntName in ListTIMEASYNC)){
			if(lg>i1){
				msg2=timer sEXC,r9,iN1
				trace sEXS,r9,i1
			}
			success=executeEvent(r9,event)
			if(lg>i1)trace msg2,r9
			firstTime=false
		}

		if(evntName==sTIME && !bIs(event,sRECOVERY))
			chgNextSch(r9,lZ)

		syncTime=true
		Boolean sv_syncTime=syncTime

		List<Map> schedules
		Boolean a
		String mSmaNm=sMs(r9,snId)
		Map sch
		Long sVariance=lMs(gtPLimits(),sSCHVARIANCE)
		Long eT=lMs(gtPLimits(),sEXCTIME)
		Long schdR=lMs(gtPLimits(),sSCHREM)
		r9[sDID3OR5]=false
		while(success && !bIs(r9,sDID3OR5) && eT+lMs(r9,sTMSTMP)-wnow()>schdR){
			// if no queued events
			if(!firstTime && serializationOn){
				Boolean inq; inq=false
				getTheLock(mSmaNm,sHNDLEVT)
				List<Map> evtQ=theQueuesVFLD[mSmaNm]
				if(evtQ)inq=true
				releaseTheLock(mSmaNm)
				if(inq){
					if(isEric(r9))warn "found pending queued events",null
					break
				}
			}

			schedules=sgetSchedules(sHNDLEVT,myPep)
			if(schedules==null || schedules==(List<Map>)[] || schedules.size()==iZ)
				break
			Long t=wnow()

			if(evntName==sASYNCREP){
				String r=sR
				event[sSCH]=schedules.find{ Map it -> sMs(it,r)==evntVal }
			}else{
				// find the next timer to be run
				//anything less than scheduleVariance (63ms) in the future is considered due; will do some pause to sync with it
				Long tv=t+sVariance
				Map _schMin=schedules.min{ Map it -> lMt(it) }
				sch=(_schMin!=null && lMt(_schMin)<tv) ? _schMin : null

				evntName=sTIME
				evntVal=t.toString()
				event=[(sT):lMt(event),(sDEV):cvtLoc(),(sNM):evntName,(sVAL):t,(sSCH):sch]
			}

			if(event[sSCH]==null){ // if no timer found, exit
				if(firstTime && eric())
					warn "time event without schedule "+evntVal,r9
				break
			}
			if(lMs(r9,sNSCH)!=lZ){
				chgNextSch(r9,lZ)
				wunschedule(sTIMHNDR)
			}

			sch=mMs(event,sSCH)
			schedules=sgetSchedules(sHNDLEVT+s1,myPep)
			schedules.remove(sch)
			updateSchCache(r9,schedules,sHNDLEVT+s1,sX,myPep)

			if(!firstTime){
				if(lg!=iZ) info 'Processing timer '+evntName+' = '+lMt(sch).toString(),r9

				r9[sPCACHE]=[:]
				Map<String,Map>sysV=msMs(r9,sSYSVARS)
				sysV[sDLLRINDX][sV]=null
				sysV[sDLLRDEVICE][sV]=null
				sysV[sDLLRDEVS][sV]=null
				sysV[sHTTPCNTN][sV]=null
				sysV[sHTTPCODE][sV]=null
				sysV[sHTTPOK][sV]=null
				sysV[sIFTTTCODE][sV]=null
				sysV[sIFTTTOK][sV]=null
				r9[sSYSVARS]=sysV

				event[sT]=lMt(sch)
			}

			if(isEric(r9)) myDetail r9,"async/timer event $event",iN2

			if(evntName==sASYNCREP){
				syncTime=false
				Integer rCode=iMs(event,sRESPCODE)
				Boolean sOk=rCode>=i200 && rCode<i300
				Map m=mMs(event,sSRTDATA)
				String erMsg = m && sMs(m,sHTTPERR) ? sMs(m,sHTTPERR) : sBLK
				if(erMsg){
					error erMsg,r9
					m.remove(sHTTPERR)
				}
				switch(evntVal){
					case sHTTPR:
						Map ee; ee=mMs(sch,sSTACK)
						ee=ee!=null ? ee:[:]
						ee[sRESP]=event[sRESPDATA]
						ee[sJSON]=event[sJSOND]
						((Map)event[sSCH])[sSTACK]=ee
						stSysVarVal(r9,sHTTPCNTN,sMs(event,sCONTENTT))
					case sSTOREM:
						if(m) for(item in m) r9[(String)item.key]=item.value
					case sLIFX:
					case sSENDE:
						stSysVarVal(r9,sHTTPCODE,rCode)
						stSysVarVal(r9,sHTTPOK,sOk)
						break
					case sIFTTM:
						stSysVarVal(r9,sIFTTTCODE,rCode)
						stSysVarVal(r9,sIFTTTOK,sOk)
						break
					default:
						error "unknown async event "+evntVal,r9
				}
				evntName=sTIME
				event[sNM]= evntName
				event[sVAL]= t
				evntVal=t.toString()
			}else{
				String ttyp=sMs(sch,sR)
				if(ttyp in HttpAsync){
					error "Timeout Error "+ttyp,r9
					syncTime=false
					Integer rCode=408
					Boolean sOk=false
					switch(ttyp){
						case sHTTPR:
							stSysVarVal(r9,sHTTPCNTN,sBLK)
							if(sch[sSTACK]!=null)((Map)((Map)event[sSCH])[sSTACK])[sRESP]=null
						case sSTOREM:
							stSysVarVal(r9,sHTTPCODE,rCode)
							stSysVarVal(r9,sHTTPOK,sOk)
							break
						case sLIFX:
						case sSENDE:
							break
						case sIFTTM:
							stSysVarVal(r9,sIFTTTCODE,rCode)
							stSysVarVal(r9,sIFTTTOK,sOk)
							break
					}
				}
			}

			if(syncTime && strictSync){
				Long delay; delay=Math.round(lMt(sch)-d1*wnow())
				if(delay>lZ){
					delay=delay<sVariance ? delay:sVariance
					doPause("Synchronizing scheduled event, waiting for ${delay}ms".toString(),delay,r9,true)
				}
			}
			if(lg>i1){
				msg2=timer sEXC,r9,iN1
				trace sEXS,r9,i1
			}
			success=executeEvent(r9,event)
			if(lg>i1)trace msg2,r9
			firstTime=false
			syncTime=sv_syncTime
		}

		((Map)((Map)r9[sSTATS])[sTIMING])[sE]=elapseT(eStrt)
		if(!success)msg[sM]=sEPF
		if(eric()&& lg>i1){
			String s; s=sMs(msg,sM)
			s+=' Total pauses ms: '+lMs(r9,sTPAUSE).toString()
			if(firstTime)s+=' found nothing to do'
			msg[sM]=s
		}
		finalizeEvent(r9,msg,success)

		if(bIs(r9,sLOGPE)) sendLEvt(r9)
	}

	String mSmaNm=sMs(r9,sSEMAN)
	Long lS=lMs(r9,sSEMA)

	event=null
	r9=null

// any queued events?
	String msgt; msgt=sNL
	if(lg>i2)msgt='Exiting'

	while(doSerialization && mSmaNm!=sNL){
		getTheLock(mSmaNm,sHNDLEVT+s2)
		List<Map> evtQ
		mb()
		evtQ=theQueuesVFLD[mSmaNm]
		if(!evtQ){
			if(theSemaphoresVFLD[mSmaNm]<=lS){
				if(lg>i2)msgt='Released Lock and exiting'
				theSemaphoresVFLD[mSmaNm]=lZ
				theSemaphoresVFLD=theSemaphoresVFLD
				mb()
			}
			releaseTheLock(mSmaNm)
			break
		}else{
			Map theEvent
			evtQ=theQueuesVFLD[mSmaNm]
			List<Map>evtList=evtQ.sort{ Map it -> lMt(it) }
			theEvent=evtList.remove(iZ)
			Integer qsize=evtList.size()
			theQueuesVFLD[mSmaNm]=evtList
			theQueuesVFLD=theQueuesVFLD
			mb()
			releaseTheLock(mSmaNm)

			if(qsize>i8)error "large queue size ${qsize}".toString(),null
			theEvent[sDATE]=new Date(lMt(theEvent))
			handleEvents(theEvent,false,true)
		}
	}
	if(lg>i2)debug msgt,null
}

@CompileStatic
void chgNextSch(Map r9,Long v){
	((Map)r9[sSTATS])[sNSCH]=v
	r9[sNSCH]=v
	assignSt(sNSCH,v)
	updateCacheFld(r9,sNSCH,v,sT+s2,true)
}

/* wrappers */
private void sendLEvt(Map r9){
	Map rtCE=mMs(r9,sCUREVT)
	if(rtCE!=null){
		String s=gtAppN()
		String desc='webCoRE piston \''+s+'\' was executed'
		Map t0=mMs(r9,sST)
		sendLocationEvent(
			(sNM):'webCoRE',(sVAL):'pistonExecuted',isStateChange:true,displayed:false,linkText:desc,(sDESCTXT):desc,
			(sDATA):[
				(sID):r9[sID],
				(sNM):s,
				(sEVENT):[(sDATE):new Date(lMt(rtCE)),(sDELAY):lMs(rtCE,sDELAY),(sDURATION):elapseT(lMt(rtCE)),(sDEV):"${getDevice(r9,(String)rtCE[sDEV])}".toString(),(sNM):(String)rtCE[sNM],(sVAL):rtCE[sVAL],(sPHYS):bIs(rtCE,sPHYS),(sINDX):iMs(rtCE,sINDX)],
				(sST):[(sOLD):sMs(t0,sOLD),(sNEW):sMs(t0,sNEW)]
			]
		)
	}
}

@Field static final String sLSTART='lstarted'
@Field static final String sLSEND='lended'
@Field static final String sGENIN='generatedIn'
@Field static final String sPSTART='pStart'
@Field static final String sPEND='pEnd'

@Field static List<String>cleanData=[]
@Field static List<String>cleanData1=[]
@Field static List<String>cleanData2=[]
@Field static List<String>cleanData3=[]

private static void fill_cleanData(){
	if(cleanData.size()==iZ)
		cleanData= [sALLDEVS, sDID3OR5, sPCACHE, sMEM, sBREAK, sPWRSRC, sOLDLOC, sINCIDENTS, sSEMADEL, sVARS,
					sSTACCESS, sATHR, sBLD, sNWCACHE, sMEDIADATA, sMEDIATYPE, sMEDIAID, sMEDIAURL, sWEAT,
					sLOGS, sTRC, sSYSVARS, sLOCALV, sPREVEVT, sJSON, sRESP,
					sCACHE, sSTORE, sSETTINGS, sLOCMODEID, 'coreVersion', sHCOREVER, sCNCLATNS, sCNDTNSTC, sPSTNSTC, sFFT, sRUN,
					sRESUMED, sTERM, sINSTID, sWUP, sSTMTL, sARGS, 'nfl', sTEMP]
	if(cleanData1.size()==iZ)
		cleanData1= [sLSTART,sLSEND,sGENIN,sPSTART,sPEND]
	if(cleanData2.size()==iZ)
		cleanData2= [sRECOVERY,sCONTENTT,sRESPDATA,sRESPCODE,sSRTDATA,sJSOND]
	if(cleanData3.size()==iZ)
		cleanData3= [sSCH]
}

@Field static List<String> HttpAsync=[]
private static void fill_HttpAsync(){
	if(HttpAsync.size()==iZ)
		HttpAsync= [sHTTPR,sSTOREM,sLIFX,sSENDE,sIFTTM]
}

@CompileStatic
private Boolean executeEvent(Map r9,Map event){
	String myS; myS=sNL
	// see fixEvt for description of event
	String evntName=sMs(event,sNM)
	Boolean lgt=isTrc(r9)
	Boolean lg=isDbg(r9)
	Boolean lge=lg && isEric(r9)
	if(lge){
		myS='executeEvent '+evntName+sSPC+event[sVAL].toString()
		myDetail r9,myS,i1
	}
	Boolean res,ended; res=false; ended=false
	try{
		Integer index; index=iZ //event?.index ?: iZ
		Map ejson=mMs(event,sJSOND)
		if(ejson){
			Map attribute=Attributes()[evntName]
			String attrI=attribute!=null ? sMs(attribute,sI):sNL
			if(attrI!=sNL)
				if(ejson[attrI]) // .i is the attribute to lookup
					index=sMs(ejson,attrI).toInteger()
				else if(!index)index=i1
		}

		Map targs
		targs=event[sJSOND]!=null ? (Map)event[sJSOND]:[:]

		Map srcEvent; srcEvent=null
		r9[sJSON]=[:]
		r9[sRESP]=[:]

		Map es=(Map)event[sSCH]
		if(es!=null && evntName==sTIME){ // this is a resume timer, try to put back original event
			srcEvent=mMs(es,'evt')
			targs=es[sARGS]!=null && es[sARGS] instanceof Map ? mMs(es,sARGS):targs
			Map tMap=mMs(es,sSTACK)
			if(tMap!=null){
				Map<String,Map>sysV=msMs(r9,sSYSVARS)
				sysV[sDLLRINDX][sV]=tMap[sINDX] ?:null
				sysV[sDLLRDEVICE][sV]=tMap[sDEV] ?:null
				sysV[sDLLRDEVS][sV]=tMap[sDEVS] ?:[]
				r9[sSYSVARS]=sysV
				r9[sJSON]=tMap[sJSON] ?: [:]
				r9[sRESP]=tMap[sRESP] ?: [:]
				index=(Integer)srcEvent?.index ?: iZ
// more to restore here? toResume
			}
		}
		stSysVarVal(r9,sDARGS,targs)

		def theDevice1=event[sDEV] ? event[sDEV]:null
		String a=sMs(r9,sLOCID)
		String theFinalDevice=theDevice1!=null ? (!isDeviceLocation(event[sDEV]) ? hashD(r9,theDevice1):a):a

		for(String foo in cleanData2) event.remove(foo)
		//def sv=event[sDEV]
		event[sDEV]=theFinalDevice // device from here on is a hashed string

		Integer s=es ? iMs(es,'svs'):null
		if(s)((Map)event[sSCH])[sS]=s // dealing with pause/wait before happens_daily_at r9.wakingUp

		r9[sEVENT]=event

		String isRSM='isResume'
		Map mEvt,pEvt
		mEvt=[:]+event
		Long evtDelay= (Long)mMs(mMs(r9,sSTATS),sTIMING)?.d
		mEvt[sDELAY]=evtDelay ?: lZ
		mEvt[sINDX]=index
		mEvt[sDV]=cvtDev(getDevice(r9,theFinalDevice)) // documentation
		mEvt[isRSM]=false

		for(String foo in cleanData3) mEvt.remove(foo)

		if(srcEvent!=null){
			mEvt[sNM]=sMs(srcEvent,sNM)
			mEvt[sVAL]=srcEvent[sVAL]
			mEvt[sDEV]=srcEvent[sDEV]
			mEvt[sDESCTXT]=sMs(srcEvent,sDESCTXT)
			mEvt[sUNIT]=srcEvent[sUNIT]
			mEvt[sINDX]=srcEvent[sINDX]
			mEvt[sPHYS]=!!srcEvent[sPHYS]
			mEvt[isRSM]=true
		}else{
			// this is a new run starting from top, so setup local variables that have initial values
			clearReadFLDs(r9)
			Map aS
			aS=getCachedMaps('executeEvent glv')
			aS=aS!=null?aS:[:]
			getLocalVariables(r9,aS,true)
		}
		pEvt=(Map)gtSt(sLEVT)
		if(pEvt==null)pEvt=[:]
		r9[sPREVEVT]=pEvt

		mEvt=cleanEvt(mEvt)
		r9[sCUREVT]=mEvt
		assignSt(sLEVT,mEvt)

		r9[sCNDTNSTC]=false
		r9[sPSTNSTC]=false
		r9[sSTMTL]=iZ
		r9[sBREAK]=false
		r9[sRESUMED]=false
		r9[sTERM]=false
		if(evntName==sTIME)chgRun(r9,iMs(es,sI)) // iN1, iN3, iN5, iN9 or stmt #
		else chgRun(r9,iZ)

		if(lge){
			myDetail r9,sCUREVT+" $mEvt",iN2
			myDetail r9,"json ${myObj(r9[sJSON])} ${r9[sJSON]}",iN2
			myDetail r9,"response ${myObj(r9[sRESP])}  ${r9[sRESP]}",iN2
			myDetail r9,"event ${r9[sEVENT]}",iN2
			myDetail r9,"currun is ${currun(r9)}",iN2
		}

		r9[sSTACK]=[(sC):iZ,(sS):iZ,(sCS):[],(sSS):[]] as LinkedHashMap
		try{
			Map pis=mMs(r9,sPISTN)
			List<Map> r=liMs(pis,sR)
			// piston restriction
			Boolean allowed=!r || r.size()==iZ || evaluateConditions(r9,pis,sR,true)
			Boolean restr=!gtPOpt(r9,sAPS) && !allowed //allowPreScheduled tasks to execute during restrictions iN3,iN5
			r9[sRESTRICD]=restr

			if(allowed || currun(r9)!=iZ /* <iN1 */){ // iN3 iN5 iN9 (allow save runs), ffwds >1 (resume); no 0, iN1 every block runs if restricted
				switch(currun(r9)){
					case iN3:
						//device related time schedules
						Map data=mMs(es,sD)
						if(data!=null){
							if(!restr || bIs(data,'ig')){ // ignore restrictions
								String d=sMs(data,sD)
								String c=sMs(data,sC)
								if(d && c){
									//execute a device schedule
									def device=getDevice(r9,d)
									if(device!=null){
										//used by command execution delay
										Boolean dco=data['dc']!=null ? bIs(data,'dc'):true
										r9[sCURTSK]=[(sDLR):data[sTASK]] as LinkedHashMap
										executePhysicalCommand(r9,device,c,data[sP],lZ,sNL,dco,false,false)
										r9.remove(sCURTSK)
									}
								}
							}else
								if(lgt)trace 'Piston device timer execution aborted due to restrictions in effect',r9
						}
						r9[sDID3OR5]=true
						break

					case iN5:
						//repeat related time schedules, fades, flashes
						if(!restr){
							Map jq=mMs(es,'jq')
							if(jq!=null){
								LinkedHashMap t=jq[sTCP] ? [(sTCP):jq[sTCP]] : [:]
								Map statement= ([(sDLR):stmtNum(jq)] as LinkedHashMap)+t
								Map task=[(sDLR):jq[sTASK]]
								r9[sCURACTN]=statement
								r9[sCURTSK]=task
								def ta=mMs(r9,sSTACK)[sCS]
								((Map)r9[sSTACK])[sCS]=jq[sCS]
								runRepeat(r9,jq)
								((Map)r9[sSTACK])[sCS]=ta
								r9.remove(sCURTSK)
								r9.remove(sCURACTN)
							}
						}else
							if(lgt)trace 'Piston repeat task timer execution aborted due to restrictions in effect',r9
						r9[sDID3OR5]=true
						break

					default:
						if(executeStatements(r9,liMs(pis,sS))){
							ended=true
							// if we ran to end, ensure we saved this event value if it is tracking
							if(theFinalDevice && evntName && !srcEvent)
								updTrkVal(r9,theFinalDevice,evntName, lMt(mEvt) ?: wnow())
						}
				}

			}else{
				if(lgt)trace 'Piston execution aborted due to restrictions in effect; updating piston states',r9
				//run through all to update stuff
				r9[sCACHE]=[:] // reset device cache followed by
				stNeedUpdate()
				assignSt(sCACHE,[:])
				updateCacheFld(r9,sCACHE,[:],myS,true)
				chgRun(r9,iN9)
				executeStatements(r9,liMs(pis,sS))
				ended=true
			}
			res=true
		}catch(Exception all){
			error 'An error occurred while executing event:',r9,iN2,all
		}
	}catch(Exception all){
		error 'An error occurred within executeEvent:',r9,iN2,all
	}
	if(ended && res){
		tracePoint(r9,sEND,lZ,iZ)
		String myId=sAppId()
		needUpdateFLD[myId]=false
		needUpdateFLD=needUpdateFLD
	}
	else if(!bIs(r9,sDID3OR5)) tracePoint(r9,sBREAK,lZ,iZ)
	processSchedules r9
	if(lge)myDetail r9,myS+" result:${res}"
	return res
}

@Field static final String sFINLZ='finalize'
@CompileStatic
private void finalizeEvent(Map r9,Map iMsg,Boolean success=true){
	Long startTime=wnow()
	Boolean myPep=isPep(r9)
	String myId=sMs(r9,snId)
	// warm-up: rebuilds theCacheVFLD[myId] from state if it was cleared; result is
	// only tested for null to guard the two cache-update blocks below
	Map cacheExists=getCachedMaps(sFINLZ)

	processSchedules(r9,true)
//	Long el1=elapseT(startTime)

	if(success){
		if(isInf(r9))info iMsg,r9
	}else error iMsg,r9

	updateLogs(r9,lMs(r9,sTMSTMP))
//	Long el2=elapseT(startTime)

	if(!bIs(r9,sDID3OR5))
		((Map)r9[sTRC])[sD]=elapseT(lMt(mMs(r9,sTRC)))

	//save / update changed cache values
	for(Map.Entry<String,Map> item in msMs(r9,sNWCACHE)) ((Map)r9[sCACHE])[item.key]=item.value

//	Long el3=elapseT(startTime)
	//overwrite state might have changed meanwhile
	if(cacheExists!=null){
		getTheLock(myId,sFINLZ)
		Map nc=theCacheVFLD[myId]
		if(nc){
			nc[sLSTPCQ]=r9[sLSTPCQ]
			nc[sLSTPCSNT]=r9[sLSTPCSNT]
			if(nc[sLSTPCQ]==null) nc.remove(sLSTPCQ)
			if(nc[sLSTPCSNT]==null) nc.remove(sLSTPCSNT)
			// store across runs
			nc[sCACHE]=[:]+mMs(r9,sCACHE)
			nc[sSTORE]=[:]+mMs(r9,sSTORE)
			nc[sST]=[:]+mMs(r9,sST)
			if(!bIs(r9,sDID3OR5))
				nc[sTRC]=[:]+mMs(r9,sTRC)
			theCacheVFLD[myId]=nc
			theCacheVFLD=theCacheVFLD
		}
		releaseTheLock(myId)
	}
	if(myPep){
		assignAS(sCACHE,mMs(r9,sCACHE))
		assignAS(sSTORE,mMs(r9,sSTORE))
		assignAS(sST,[:]+mMs(r9,sST))
		if(!bIs(r9,sDID3OR5)) assignAS(sTRC,mMs(r9,sTRC))
	}else{
		assignSt(sCACHE,mMs(r9,sCACHE))
		assignSt(sSTORE,mMs(r9,sSTORE))
		assignSt(sST,[:]+mMs(r9,sST))
		if(!bIs(r9,sDID3OR5)) assignSt(sTRC,mMs(r9,sTRC))
	}

//	Long el4=elapseT(startTime)
//remove large stuff
	for(String foo in cleanData) r9.remove(foo)

	if(bIs(r9,sUPDDEVS))updateDeviceList(r9)
	r9[sDEVS]=[:]

//	Long el5=elapseT(startTime)
	Map<String,Map> tt=msMs(r9,sGVCACHE)
	if(tt!=null || r9[sGVSTOREC]!=null){
/*		if(tt!=null){
			String semName=sTGBL
			String wName=sMs(r9,spId)
			getTheLock(semName,sFINLZ)
			Map vars=globalVarsVFLD[wName]
			for(var in tt){
				String varName=(String)var.key
				if(varName && varName.startsWith(sAT) && vars[varName]){
					def val=var.value[sV]
					def oval=vars[varName][sV]
					if(val!=oval){
						vars[varName][sV]=val
						globalVarsVFLD=globalVarsVFLD
					}
				}
			}
			releaseTheLock(semName)
		} */
		r9[sPISTN]= [(sZ): sMs(r9,sPSTNZ)] as Map
		relaypCall(r9)
		r9.remove(sGVCACHE)
		r9.remove(sGVSTOREC)
		r9.remove(sGSTORE)
		r9[sINITGS]=false
	}else{
		// update Dashboard
		Map myRt=shortRtd(r9)
		relaypCall(myRt)
	}
//	Long el6=elapseT(startTime)
	r9[sPISTN]=null

	((Map)((Map)r9[sSTATS])[sTIMING])[sU]=elapseT(startTime)
//	doLog(sERROR,"el1: $el1  el2: $el2  el3: $el3 el4: $el4 el5: $el5 el6: $el6")

//update graph data
	Map stats
	String s; s=sSTATS
	if(myPep)stats=(Map)gtAS(s) else stats=(Map)gtSt(s)
	stats=stats ?: [:]

	List<Map> tlist
	tlist=liMs(stats,sTIMING) ?: (List<Map>)[]
	//square up the graph
	Map lastST=tlist.size() ? [:]+tlist.last():null
	Map newMap=[:]+mMs(mMs(r9,s),sTIMING)
	if(lastST && newMap){
		lastST[sT]=lMt(newMap)-10L
		tlist.push(lastST)
	}
	tlist.push(newMap)
	Integer t1,t2
	t1=iMs(r9,sMSTATS)
	t2=tlist.size()
	if(t2>t1)tlist=tlist[t2-t1..t2-i1]

	stats[sTIMING]=tlist
	if(myPep)assignAS(s,stats) else assignSt(s,stats)
	((Map)r9[s])[sTIMING]=null

	if(cacheExists!=null){

		s=sFINLZ+s1
		// lock only to get a consistent snapshot of nc; released immediately because
		// each updateCacheFld call acquires the lock independently for its own write
		getTheLock(myId,s)
		Map nc=theCacheVFLD[myId]
		releaseTheLock(myId)
		if(nc){
			t1=i20
			List hisList
			hisList=(List)nc[sRTHIS]
			hisList.push( elapseT(lMs(r9,sTMSTMP)).toInteger() )
			t2=hisList.size()
			if(t2>t1)hisList=hisList[t2-t1..t2-i1]
			updateCacheFld(r9,sRTHIS,hisList,s,false)
		}
		updateCacheFld(r9,sMEM,mem(),s,false)
		updateCacheFld(r9,sRUNS,[:]+mMs(r9,sCURS),s,false)
	}
}

/** Returns newly created schedules */
@CompileStatic
private static List<Map> sgtSch(Map r9){ return liMs(r9,sSCHS) }

/** Add to newly created schedules */
@CompileStatic
private static Boolean spshSch(Map r9,Map sch){ return liMs(r9,sSCHS).push(sch) }

/** Returns saved schedules */
@CompileStatic
private List<Map> sgetSchedules(String t,Boolean myPep){
	List<Map> schedules
	Map t0=getCachedMaps(t)
	if(t0!=null)schedules=(List<Map>)[]+liMs(t0,sSCHS)
	else schedules=(myPep ? (List<Map>)gtAS(sSCHS):(List<Map>)gtSt(sSCHS)) ?: ([] as List<Map>)
	return schedules
}

/** updates saved schedules */
@CompileStatic
private void updateSchCache(Map r9,List<Map> schedules,String t,String lt,Boolean myPep){
	if(myPep)assignAS(sSCHS,(List<Map>)[]+schedules) else assignSt(sSCHS,(List<Map>)[]+schedules)

	updateCacheFld(r9,sSCHS,(List<Map>)[]+schedules,t+lt,true)
}

private static LinkedHashMap initCncl(){ return [(sSTMTS):[],(sCONDITIONS):[],(sALL):false] as LinkedHashMap }

@Field static final String sPROCS='processSchedules'
@CompileStatic
private void processSchedules(Map r9,Boolean scheduleJob=false){
	//if automatic piston states set it based on the autoNew - if any
	if(!gtPOpt(r9,sMPS)) mMs(r9,sST)[sNEW]= sMs(mMs(r9,sST),sAUTONEW) ?: sTRUE

	Boolean myPep=isPep(r9)
	List<Map> schedules,ts
	schedules=sgetSchedules(sPROCS,myPep)
	ts=[]+schedules
	Boolean lg=isTrc(r9)

	if(ts){
		Map cncls=mMs(r9,sCNCLATNS)
		if(bIs(cncls,sALL)){
			//cancel all statement and any other pending -3,-5 events (device schedules); does not cancel EVERY blocks -1 iN1 or $:0 condition requests
			if(lg)whatCnclsA(r9)
			for(Map sch in ts){
				Integer i=iMs(sch,sI)
				if(i>iZ || i in ListIN35) schedules.remove(sch)
			}
			r9[sLSTPCQ]=null
			r9[sLSTPCSNT]=null
			ts=[]+schedules
		}

		//cancel statements
		if(ts){
			List<Map>cncStmts= liMs(cncls,sSTMTS)
			if(cncStmts){
				Set<Integer> cncIds=new HashSet<Integer>()
				Map<Integer,Set<String>> cncDataIds=[:]
				for(Map cancelation in cncStmts){
					Integer cid=iMs(cancelation,sID)
					String cdata=sMs(cancelation,sDATA)
					if(!cdata){ cncIds << cid }
					else{
						if(!cncDataIds[cid]) cncDataIds[cid]=new HashSet<String>()
						cncDataIds[cid] << cdata
					}
				}
				final String dtt = sD // compiler bug?
				schedules.removeAll{ Map schedule ->
					Integer sid=iMsS(schedule)
					if(cncIds.contains(sid)) return true
					Set<String> ds=(Set<String>)cncDataIds[sid]
					if(!ds) return false
					Object dval=schedule[dtt]
					return dval instanceof String && ds.contains((String)dval)
				}
			}
			ts=[]+schedules

			//cancel on conditions
			if(ts){
				for(Integer cid in (List<Integer>)cncls[sCONDITIONS]){
					ts=[]+schedules
					if(ts)
						for(Map sch in ts)
							if(cid in (List)sch[sCS]) schedules.remove(sch)
				}
				//cancel on piston state change
				ts=[]+schedules
				if(ts && bIs(r9,sPSTNSTC)){
					if(lg){
						updateSchCache(r9,schedules,sPROCS+s0,sT,myPep)
						whatCnclsP(r9)
					}
					for(Map sch in ts)
						if(iMs(sch,sPS)!=iZ) schedules.remove(sch)
				}
			}
		}
	}

	r9[sCNCLATNS]=initCncl()

	schedules=(schedules+sgtSch(r9))//.sort{ lMt(it) }
	updateSchCache(r9,schedules,sPROCS+s1,sT,myPep)

	if(scheduleJob){
		Long nextT=lZ
		Integer ssz=schedules.size()
		if(ssz>iZ){
			Map tnext=schedules.min{ Map it -> lMt(it) }
			nextT=lMt(tnext)
			Long sVariance=lMs(gtPLimits(),sSCHVARIANCE)
			Long t=nextT-wnow()
			t=Math.max(t,sVariance)
			wrunInMillis(t,sTIMHNDR,[(sDATA): tnext])
			if(isInf(r9))info 'Setting up scheduled job for '+formatLocalTime(r9,nextT)+' (in '+t.toString()+'ms)'+(ssz>i1 ? ', with '+(ssz-i1).toString()+' more job'+(ssz>i2 ? sS:sBLK)+' pending':sBLK),r9
		}
		if(nextT==lZ){
			if(lMs(r9,sNSCH)!=lZ)wunschedule(sTIMHNDR)
			clearReadFLDs(r9)
		}
		chgNextSch(r9,nextT)
	}
	r9[sSCHS]=[]
}

@Field static final String sUPDL='updateLogs'
/** store this run logs to persistent state and update caches */
private void updateLogs(Map r9,Long lastExecute=null){
	if(!r9)return

	String id=sMs(r9,snId)

	Map cacheMap=getCachedMaps(sUPDL)
	getTheLock(id,sE)

	Map nc=theCacheVFLD[id]

	if(lastExecute!=null){
		assignSt(sLEXEC,lastExecute)
		if(nc && cacheMap!=null){
			nc[sLEXEC]=lastExecute
			nc[sTEMP]=[:]+mMs(r9,sTEMP)
			nc[sPCACHE]=[:]+mMs(r9,sPCACHE)
			theCacheVFLD[id]=nc
			theCacheVFLD=theCacheVFLD
		}
	}

	String s=sLOGS
	if(r9[s] && liMs(r9,s).size()>i1){
		Boolean myPep=isPep(r9)
		Integer lim=iMs(r9,sMLOGS)

		List t0
		if(cacheMap!=null)t0=[]+(List)cacheMap[s]
		else t0=(myPep ? (List)gtAS(s):(List)gtSt(s)) ?: []
		List logs
		logs=[]+(List)r9[s]+t0
		if(lim>=iZ){
			Integer lsz; lsz=logs.size()
			if(lim==iZ || lsz==iZ)logs=[]
			else{
				if(lim< lsz-i1){
					logs=logs[iZ..<lim]
					lsz=logs.size()
				}
				if(lsz>i50){
					assignSt(s,logs) //	this mixes state and AS
					if(state.toString().length()>75000){
						lim-=Math.min(i50,(lim/d2).toInteger())
						logs=logs[iZ..<lim]
					}
				}
			}
		}
		if(myPep)assignAS(s,logs) else assignSt(s,logs)
		if(nc && cacheMap!=null){
			nc[s]=logs
			theCacheVFLD[id]=nc
			theCacheVFLD=theCacheVFLD
		}
	}
	r9[s]=[]
	releaseTheLock(id)
}

@CompileStatic
private Boolean executeStatements(Map r9,List<Map>statements,Boolean async=false){
	if(statements==null)return true
	Integer t=iMs(r9,sSTMTL)
	r9[sSTMTL]=t+i1
	for(Map statement in statements){
		//only execute statements that are enabled
		Boolean disab=statement[sDI]!=null && bIs(statement,sDI)
		if(!disab && !executeStatement(r9,statement,async)){
			r9[sSTMTL]=t
			//stop processing
			return false
		}
	}
	r9[sSTMTL]=t
	//continue processing
	return true
}

@Field static final String sRUN='running'
@Field static final String sFFT='ffTo'
/** ffto == 0 */
@CompileStatic
private static Boolean prun(Map r9){ bIs(r9,sRUN) }
/** ffto != 0 */
@CompileStatic
private static Boolean ffwd(Map r9){ !prun(r9) }
/** get ffto */
@CompileStatic
private static Integer currun(Map r9){ iMs(r9,sFFT) }
@CompileStatic
private static void chgRun(Map r9,Integer num){
	r9[sFFT]=num
	r9[sRUN]=num==iZ
}

@CompileStatic
private static Integer stmtNum(Map stmt){ return stmt?.$!=null ? iMs(stmt,sDLR):iZ }

@Field static final String sEXST='executeStatement '

@CompileStatic
private static Integer pushStk(Map r9,Integer stmtNm,Boolean stacked){
	Map stk=mMs(r9,sSTACK)
	((List<Integer>)stk[sSS]).push(iMsS(stk))
	stk[sS]=stmtNm
	Integer c=iMs(stk,sC)
	if(stacked) ((List<Integer>)stk[sCS]).push(c)
	//r9[sSTACK]=stk
	return c
}

@CompileStatic
private static void popStk(Map r9,Integer v,Boolean stacked){
	Map stk=mMs(r9,sSTACK)
	stk[sC]=v
	if(stacked) ((List<Integer>)stk[sCS]).pop()
	stk[sS]=(Integer)((List<Integer>)stk[sSS]).pop()
	//r9[sSTACK]=stk
}

@Field static List<String> are_async=[]
@Field static List<String> loops=[]

@CompileStatic
private static void fill_are_async(){
	if(!are_async){ are_async=[sEVERY,sON]; if(!loops)loops=[sWHILE,sREPEAT,sFOR,sEACH] }
}

@CompileStatic
private Boolean executeStatement(Map r9,Map statement,Boolean asynch=false){
	//if r9.ffTo is a positive non-zero number, we need to fast forward through all branches
	//until we locate statement with a matching id, then we continue
	if(statement==null)return false
	Boolean lgt=isTrc(r9)
	Boolean lg=isDbg(r9)
	Integer stmtNm=stmtNum(statement)
	Boolean cchg=bIs(r9,sCNDTNSTC)
	// Task Execution Policy - only execute on: ""- always (def), c-condition state change, p- piston state change, b-condition or piston change
	String tep=sMs(statement,sTEP)
	if(tep && prun(r9)){
		Boolean pchg=bIs(r9,sPSTNSTC)
		String s = lg && (!cchg || !pchg) ? "Skipping execution for statement #${stmtNm} because " : sBLK
		switch(tep){
			case sC:
				if(!cchg){
					if(lg)debug s+"condition state did not change",r9
					return true
				}
				break
			case sP:
				if(!pchg){
					if(lg)debug s+"piston state did not change",r9
					return true
				}
				break
			case sB:
				if(!cchg && !pchg){
					if(lg)debug s+"neither condition state nor piston state changed",r9
					return true
				}
				break
		}
	}
	String stateType=sMt(statement)
	String mySt; mySt=sNL
	Boolean lge=lg && isEric(r9)
	if(lge){
		mySt=sEXST+("#${stmtNm}"+sffwdng(r9)+stateType+sSPC+"async: $asynch").toString()
		myDetail r9,mySt,i1
	}
	Long t=wnow()

	Boolean stacked=true /* cancelable on condition change */
	Integer c=pushStk(r9,stmtNm,stacked)

	Boolean svCSC=cchg
	Boolean value; value=true
	Map<String,Map>sysV=msMs(r9,sSYSVARS)
	Double svIndex=(Double)oMv(sysV[sDLLRINDX])
	List svDevice=liMv(sysV[sDLLRDEVICE])

	Boolean selfAsync=sMa(statement)==s1 || stateType in are_async // execution method (async)
	Boolean async=asynch||selfAsync

	Boolean myPep=isPep(r9)
	List<Map> r=liMs(statement,sR)
	Boolean allowed; allowed=true
	if(stateType!=sEVERY)
		allowed=!r || r.size()==iZ || evaluateConditions(r9,statement,sR,async)
	if(allowed || ffwd(r9)){
		String evntName; evntName=sMs(mMs(r9,sEVENT),sNM)
		Boolean perform,repeat,isIf,isEach
		perform=false
		repeat=true
		isIf=false
		isEach=false
		Double index; index=null
		while(repeat){
			switch(stateType){
				case sACTION:
					value=executeAction(r9,statement,async)
					break
				case sIF:
					isIf=true
				case sWHILE:
					//check condition for if and while
					perform=evaluateConditions(r9,statement,sC,async)
					//override current condition so child statements can cancel on it
					((Map)r9[sSTACK])[sC]=stmtNm
					if(isIf && prun(r9) && !gtPOpt(r9,sMPS) && iMs(r9,sSTMTL)==i1){
						//automatic piston state
						((Map)r9[sST])[sAUTONEW]= perform ? sTRUE:sFALSE
					}
					if(perform || ffwd(r9)){
						if(!executeStatements(r9,liMs(statement,sS),async)){
							//stop processing
							value=false
							if(prun(r9))break
						}
						value=true
						if(prun(r9))break
					}
					if((!perform || ffwd(r9)) && isIf){
						//look for else-if
						if(statement[sEI]){
							Integer tstmtNm=stmtNum(liMs(statement,sEI)[iZ])
							String mySt1; mySt1=sNL
							if(lge){
								mySt1=sEXST+("#${tstmtNm}"+sffwdng(r9)+'elseif'+sSPC+"async: $async").toString()
								myDetail r9,mySt1,i1
							}

							Integer cc=pushStk(r9,tstmtNm,stacked)
							Integer st=iMs(r9,sSTMTL)
							r9[sSTMTL]=st+i1
							Boolean cchg1=bIs(r9,sCNDTNSTC)

							for(Map elseIf in liMs(statement,sEI)){
								perform=evaluateConditions(r9,elseIf,sC,async)
								//override current condition so child statements can cancel on it
								((Map)r9[sSTACK])[sC]=tstmtNm
								if(perform || ffwd(r9)){
									if(!executeStatements(r9,liMs(elseIf,sS),async)){
										//stop processing
										value=false
										if(prun(r9))break
									}
									value=true
									if(prun(r9))break
								}
							}

							r9[sCNDTNSTC]=cchg1
							r9[sSTMTL]=st
							popStk(r9,cc,stacked)

							Boolean ret=value || ffwd(r9)
							if(lge)myDetail r9,mySt1+" result:"+ret.toString()
						}
						//look for else
						if((!perform || ffwd(r9)) && !executeStatements(r9,liMs(statement,sE),async)){
							//stop processing
							value=false
							if(prun(r9))break
						}
					}
					break
				case sEVERY:
					//only execute the every if i=-1 (for rapid timers with large restrictions i.e. every second, but only on Mondays)
					Map es=mMs(mMs(r9,sEVENT),sSCH)
					Boolean ownEvent=evntName==sTIME && es!=null && iMsS(es)==stmtNm && iMs(es,sI)==iN1
					if(ownEvent){
						chgRun(r9,iZ)
						allowed=!r || r.size()==iZ || evaluateConditions(r9,statement,sR,async)
					}

					//ensure every timer is scheduled
					scheduleTimer(r9,statement,ownEvent ? lMt(es):lZ,myPep)

					if(ffwd(r9) || ownEvent){
						Boolean canR=ownEvent && allowed && !bIs(r9,sRESTRICD) // honor restrictions
						if(ffwd(r9) || canR){
							//override current condition so child statements can cancel on it
							((Map)r9[sSTACK])[sC]=stmtNm
							// note we can end ffwding in the timer block on scheduled task
							executeStatements(r9,liMs(statement,sS),async)
						}else if(ownEvent && !canR && lgt)trace 'Piston Every timer execution aborted due to restrictions in effect',r9
						//if we wanted to / ran any timer block statements, exit
						if(prun(r9) || ownEvent){
							r9[sTERM]=true
							if(lgt)trace "Exiting piston at end of Every timer block",r9
						}
						value=false
						break
					}
					value=true
					break
				case sREPEAT:
					//override current condition so child statements can cancel on it
					((Map)r9[sSTACK])[sC]=stmtNm
					if(!executeStatements(r9,liMs(statement,sS),async)){
						//stop processing
						value=false
						if(prun(r9))break
					}
					value=true
					perform=!evaluateConditions(r9,statement,sC,async)
					break
				case sON:
					perform=false
					if(prun(r9)){
						//look to see if any of the events match
						Map ce=mMs(r9,sCUREVT)
						String deviceId= sMs(ce,sDEV) ?: sNL
						evntName=sMs(ce,sNM)
						List<Map>evts= liMs(statement,sC)
						if(evts){
							for(Map event in evts){
								Map operand=(Map)event?.lo
								if(operand!=null && sMt(operand)){
									switch(sMt(operand)){
										case sP:
											if(deviceId!=sNL && evntName==sMa(operand) && liMd(operand) && deviceId in expandDeviceList(r9,liMd(operand),true))
												perform=true
											break
										case sV:
											if(evntName==sMv(operand))
												perform=true
											break
										case sX:
											String operX=sMs(operand,sX)
											if(ce[sVAL]==operX && evntName==sMs(r9,sINSTID)+sDOT+operX)
												perform=true
											break
									}
								}
								if(perform)break
							}
						}
					}
					value=ffwd(r9) || perform ? executeStatements(r9,liMs(statement,sS),async):true
					break
				case sEACH:
					isEach=true
				case sFOR:
					Double startValue,endValue,stepValue
					startValue=dZ
					stepValue=d1
					List devices; devices=[]
					Integer dsiz; dsiz=iZ
					if(isEach){
						List t0=liMv(mevaluateOperand(r9,mMs(statement,sLO)))
						devices=t0 ?: []
						dsiz=devices.size()
						endValue=dsiz-d1
					}else{
						startValue=evalDecimalOperand(r9,mMs(statement,sLO))
						endValue=evalDecimalOperand(r9,mMs(statement,sLO2))
						Double t0=evalDecimalOperand(r9,mMs(statement,sLO3))
						stepValue=t0 ?: d1
					}
					String ts=sMs(statement,sX)
					String cntrVar=ts && !isErr(getVariable(r9,ts)) ? ts:sNL
					String sidx='f:'+stmtNm.toString()
					if( ffwd(r9) || (startValue<=endValue && stepValue>dZ) || (startValue>=endValue && stepValue<dZ) ){
						//initialize the for loop
						if(ffwd(r9))index=dcast(r9,mMs(r9,sCACHE)[sidx])
						if(index==null){
							index=dcast(r9,startValue)
							((Map)r9[sCACHE])[sidx]=index
						}
						stSysVarVal(r9,sDLLRINDX,index)
						List dvc; dvc=[]
						if(isEach){
							dvc= index<dsiz ? [devices[index.toInteger()]]:[]
							if(currun(r9) in ListIZIN9)stSysVarVal(r9,sDLLRDEVICE,dvc)
						}
						if(cntrVar!=sNL && prun(r9)) setVariable(r9,cntrVar,isEach ? dvc:index)
						//do the loop
						perform=executeStatements(r9,liMs(statement,sS),async)
						if(!perform){
							//stop processing
							value=false
							if(isBrk(r9)){
								//reached a break continue execution outside of the for
								value=true
								r9[sBREAK]=false
								//perform=false
							}
							break
						}
						//don't do the rest if fast forwarding
						if(ffwd(r9))break
						index=index+stepValue
						stSysVarVal(r9,sDLLRINDX,index)
						dvc=[]
						if(isEach){
							dvc= index<dsiz ? [devices[index.toInteger()]]:[]
							if(prun(r9))stSysVarVal(r9,sDLLRDEVICE,dvc)
						}
						if(cntrVar!=sNL && prun(r9)) setVariable(r9,cntrVar,isEach ? dvc:index)
						((Map)r9[sCACHE])[sidx]=index
						if((stepValue>dZ && index>endValue) || (stepValue<dZ && index<endValue)){
							perform=false
							break
						}
					}
					break
				case sSWITCH:
					Map lo=[(sOPERAND): mMs(statement,sLO),(sVALUES): levaluateOperand(r9,statement,mMs(statement,sLO))]
					Boolean fnd,fallThru
					fnd=false
					Boolean implctBr=sMs(statement,sCTP)!=sE // case traversal policy, i- autobreak (def), e- fall thru
					fallThru=!implctBr
					perform=false
					if(lg)debug "Evaluating switch statement with values $lo.values",r9
					//go through all cases
					for(Map _case in liMs(statement,sCS)){
						Map ro=[(sOPERAND): mMs(_case,sRO),(sVALUES): levaluateOperand(r9,_case,mMs(_case,sRO))]
						Boolean isR=sMt(_case)==sR // _case.t - r- range, s- single value
						Map ro2=isR ? [(sOPERAND): mMs(_case,sRO2),(sVALUES): levaluateOperand(r9,_case,mMs(_case,sRO2),null,false,true)]:null
						perform=perform || evaluateComparison(r9,(isR ? sISINS:sIS),lo,ro,ro2)
						fnd=fnd || perform
						if(perform || (fnd && fallThru) || ffwd(r9)){
							Integer ffTo=currun(r9)
							if(!executeStatements(r9,liMs(_case,sS),async)){
								//stop processing
								value=false
								if(isBrk(r9)){
									//reached a break continue execution outside of switch
									value=true
									fnd=true
									fallThru=false
									r9[sBREAK]=false
								}
								if(prun(r9))break
							}
							//if fast forwarding ended during execution, assume fnd is true
							fnd=fnd || ffTo!=currun(r9)
							value=true
							//if implicit breaks
							if(implctBr && prun(r9)){
								fallThru=false
								break
							}
						}
					}
					List<Map> e=liMs(statement,sE)
					if(e && e.size() && (value || ffwd(r9)) && (!fnd || fallThru || ffwd(r9))){
						//no case found, do the default
						if(!executeStatements(r9,e,async)){
							//stop processing
							value=false
							if(isBrk(r9)){
								//reached a break, want to continue execution outside of switch
								value=true
								r9[sBREAK]=false
							}
							if(prun(r9))break
						}
					}
					break
				case sDO:
					Integer ov=iMs(r9,sSTMTL)
					r9[sSTMTL]=ov-i1
					value=executeStatements(r9,liMs(statement,sS),async)
					r9[sSTMTL]=ov
					break
				case sBREAK:
					if(prun(r9))r9[sBREAK]=true
					value=false
					break
				case sEXIT:
					if(prun(r9)){
						def ss=oMv(mevaluateOperand(r9,mMs(statement,sLO)))
						vcmd_setState(r9,null,[scast(r9,ss)])
						r9[sTERM]=true
						if(lg)debug "Exiting piston due to exit statement",r9
					}
					value=false
					break
			}
			if(ffwd(r9) || isIf)perform=false

			Boolean loop=(stateType in loops)
			//break the loop
			if(loop && !value && isBrk(r9)){
				//someone requested a break from loop
				r9[sBREAK]=false
				//allowing rest to continue
				value=true
				perform=false
			}
			//repeat the loop?
			repeat=perform && value && loop && prun(r9)

			if(prun(r9)){
				Long overBy=checkForSlowdown(r9)
				if(overBy>lZ){
					Long delay=calcDel(overBy)
					String mstr=sEXST+":Execution time exceeded by ${overBy}ms, ".toString()
					if(repeat && overBy>lMs(gtPLimits(),sEXCTIME)){
						r9[sTERM]=true
						error mstr+'Terminating',r9
						repeat=false
					}else doPause(mstr+'Waiting for '+delay+sMS,delay,r9)
				}
			}
		}
	}
	Map sch;sch=null
	if(stateType==sEVERY){
		sch=sgtSch(r9).find{ Map it -> iMsS(it)==stmtNm}
		if(sch==null){
			List<Map> schs=sgetSchedules(sEXST+s1,myPep)
			sch=schs.find{ Map it -> iMsS(it)==stmtNm }
		}
	}
	String myS="s:${stmtNm}".toString()
	Long myL=elapseT(t)
	if(sch!=null){ //timers need to show the remaining time
		Long v=elapseT(lMt(sch))
		tracePoint(r9,myS,myL,v)
	}else if(prun(r9))
		tracePoint(r9,myS,myL,value)

	if(selfAsync){ //if in async mode return true (to continue execution)
		// if resumed from a timed event; only execute sub tasks / statements during the resume as other statements already ran
		value=!bIs(r9,sRESUMED)
		r9[sRESUMED]=false
	}
	if(bIs(r9,sTERM))value=false

	//restore current condition
	popStk(r9,c,stacked)
	stSysVarVal(r9,sDLLRINDX,svIndex)
	stSysVarVal(r9,sDLLRDEVICE,svDevice)
	r9[sCNDTNSTC]=svCSC

	Boolean ret=value || ffwd(r9)
	if(lge)myDetail r9,mySt+" result:"+ret.toString()
	return ret
}

@CompileStatic
private static Long calcDel(Long overBy){
	Map pl=gtPLimits()
	return overBy>lMs(pl,sLONGLIMTIME) ? lMs(pl,sLONGDEL):lMs(pl,sSHORTDEL)
}

@Field static final String sTPAUSE='tPause'
@Field static final String sLSTPAUSE='lastPause'

@CompileStatic
private Long checkForSlowdown(Map r9){
	//return how long over the time limit
	Long t2; t2=lMs(r9,sTPAUSE)
//	t2=t2!=null ? t2: lZ
	Long t1= lMs(gtPLimits(),sSHLIMTIME)
	Long t0= lMs(r9,sLSTPAUSE)
	Long overby= t0==null || (wnow()-t0)>t1 ? elapseT(lMs(r9,sTMSTMP))-t2-t1 : lZ
	return overby>lZ ? overby:lZ
}

@CompileStatic
private void doPause(String mstr,Long delay,Map r9,Boolean ign=false){
	Long actDelay,t1,t2
	Long t0; t0=wnow()
	if(lMs(r9,sLSTPAUSE)==null || ign || (t0-lMs(r9,sLSTPAUSE))>lMs(gtPLimits(),sSHLIMTIME)){
		if(isInf(r9)){
			if(isTrc(r9))trace mstr+'; lastPause: '+r9[sLSTPAUSE],r9
			else info mstr,r9
		}
		r9[sLSTPAUSE]=t0
		t0=wnow()
		Long mdel= delay>25L ? delay-25L : delay
		wpauseExecution(mdel)

		t1=wnow()
		actDelay=t1-t0
		t2=lMs(r9,sTPAUSE)
//		t2=t2!=null ? t2:lZ
		r9[sTPAUSE]=t2+actDelay
		r9[sLSTPAUSE]=t1

		t2=lMs(gtState(),sPAUSES)
		t2=t2!=null ? t2:lZ
		assignSt(sPAUSES,t2+l1)
	}
}

@CompileStatic
private Boolean executeAction(Map r9,Map statement,Boolean async){
	String mySt; mySt=sNL
	Integer stmtNm=stmtNum(statement)
	Boolean lge=isEric(r9)
	if(lge){
		mySt='executeAction '+("#${stmtNm}"+sffwdng(r9)+"async: ${async} ").toString()
		myDetail r9,mySt,i1
	}
	List svDevices=(List)gtSysVarVal(r9,sDLLRDEVS)
	Boolean res,isCurEvtDev
	res=true
	List<String> sd= statement[sD] ? liMd(statement):[]
	List<String> deviceIds; deviceIds=expandDeviceList(r9,sd)
	List devices; devices= deviceIds ? deviceIds.collect{ String it -> getDevice(r9,it)}:[]

	def device=devices[iZ]
	Boolean allowMul=sMs(statement,sTSP)==sA // Task scheduling policy - a- allow multiple schedules, ""-override existing (def)
	isCurEvtDev= sd.size()==i1 && sLi(sd,iZ)==sCURDEV && device
	String data= isCurEvtDev && allowMul ? "s:${stmtNm}:"+ ( !isDeviceLocation(device) ? dvStr(device):"Loc" ) : sNL // make cancel per device
	if(prun(r9) && (data || !allowMul))
		cancelStatementSchedules(r9,stmtNm,data)

	r9[sCURACTN]=statement
	List<Map>sk=liMs(statement,sK)
	if(sk){
		for(Map task in sk){
			Integer tskNm=stmtNum(task)
			if(tskNm!=null && tskNm==currun(r9)){
				//resuming a waiting task need to bring back the devices
				Map e=mMs(r9,sEVENT) ?: [:]
				Map es=(Map)e[sSCH]
				Map ess=(Map)e[sSTACK]
				if(es && ess){
					stSysVarVal(r9,sDLLRINDX,(Double)ess[sINDX])
					stSysVarVal(r9,sDLLRDEVICE,(List)ess[sDEV])
					if(ess[sDEVS] instanceof List){
						deviceIds=(List<String>)ess[sDEVS]
						devices= deviceIds ? deviceIds.collect{ String it -> getDevice(r9,it)}:[]
					}
				}
			}
			stSysVarVal(r9,sDLLRDEVS,deviceIds)
			r9[sCURTSK]=[(sDLR):stmtNum(task)] as LinkedHashMap
			res=executeTask(r9,devices,statement,task,async,data)
			r9.remove(sCURTSK)
			if(!res && prun(r9))break
		}
	}
	r9.remove(sCURACTN)
	stSysVarVal(r9,sDLLRDEVS,svDevices)
	if(lge)myDetail r9,mySt+"resumed: ${bIs(r9,sRESUMED)} result:$res".toString()
	return res
}

// device commands with added parameters by webCoRE (have cmd_ wrapper)
@Field static List<String> LWCMDS=[]
private static void fill_WCMDS(){
	if(LWCMDS.size()==iZ)
		LWCMDS= [sSTLVL,sSTIFLVL,sSTHUE,sSTSATUR,sSTCLRTEMP,sSTCLR,'setAdjustedColor','setAdjustedHSLColor','setLoopDuration','setVideoLength']
}

@CompileStatic
private Boolean executeTask(Map r9,List devices,Map statement,Map task,Boolean async,String data){
	Long t=wnow()
	Integer tskNm=stmtNum(task)
	String myS='t:'+tskNm.toString()
	if(ffwd(r9)){
		if(tskNm==currun(r9)){
			//finally reached the resuming point play nicely from hereon
			tracePoint(r9,myS,elapseT(t),null)
			chgRun(r9,iZ)
			r9[sRESUMED]=true
		}
		//not doing anything we are fast forwarding
		return true
	}
	Boolean lg=isDbg(r9)
	Boolean lge=lg && isEric(r9)

	List<String> mds=(List<String>)task[sM]
	if(mds?.size()>iZ){
		String m; m=sMs(r9,sLOCMODEID)
		if(m==sNL){
			Map mode=getDeviceAttribute(r9,sMs(r9,sLOCID),sMODE)
			m=sMv(mode)
			r9[sLOCMODEID]=m
		}
		if(!(m in mds)){
			if(lg)debug "Skipping task ${tskNm} because of mode restrictions",r9
			return true
		}
	}

	String tskC=sMs(task,sC)
	String mySt; mySt=sNL
	if(lge){
		mySt=("executeTask #${tskNm} "+tskC+" async:${async} devices: ${devices.size()} ").toString()
		myDetail r9,mySt,i1
	}

	// the command r: is replaced with command c.
	//handle duplicate command "push" which was replaced with fake command "pushMomentary"
	Map.Entry<String,Map> override=CommandsOverrides.find{ (String)it.value.r==tskC }
	String command=override ? sMs(override.value,sC):tskC
	if(override && lg)debug "Overriding command ${tskC} with ${command}",r9

	//parse parameter
	List prms=[]
	Boolean emptyIndex
	List<Map>iprms=liMs(task,sP) ?: []
	for(Map prm in iprms){
		def p; p=null
		String vt=sMvt(prm)
		switch(vt){
			case sVARIABLE: // vcmd_setVariable command, first argument is the variable name
				if(sMt(prm)==sX){
					p=prm[sX] instanceof List ? (List)prm[sX] : sMs(prm,sX)+(sMs(prm,sXI)!=sNL ? sLB+sMs(prm,sXI)+sRB:sBLK)
				}
				break
			default:
				Map v=mevaluateOperand(r9,prm)
				String tt1=vt.replace(sLRB,sBLK)
				emptyIndex= tt1!=vt &&
						command==sSTVAR &&
						prms[iZ] instanceof String && !( sLi(prms,iZ).contains(sRB) )
				def t0=oMv(v)
				//if not selected, return the null to fill in parameter
				p=t0==null || emptyIndex || matchCast(t0,tt1) ? t0:oMv(evaluateExpression(r9,v,tt1))
		}
		//ensure value type is successfully passed through
		prms.push(p)
	}

	Map vcmd=VirtualCommands()[command]
	Boolean aggregate= vcmd && bIs(vcmd,sA) //aggregate commands only run once for all devices at the same time
	Boolean voverride= vcmd && bIs(vcmd,sO) // virtual command overrides device command

	Long delay; delay=lZ
	if(lge)myDetail r9,mySt+"prms: $prms",iN2

	def virtualDevice=devices.size()!=iZ ? null:gtLocation()
	for(device in (virtualDevice!=null ? [virtualDevice]:devices)){
		if(virtualDevice==null && wdeviceHascommand(device,command) && !voverride){
			if(command in LWCMDS){ // device commands with added parameters by webCoRE (have cmd_ wrapper (11))
				Boolean doL= isInf(r9) && !isTrc(r9)
				Map msg; msg=null
				if(doL) msg=timer "Executed [$device].${command}",r9
				try{
					delay= callCmdWrap(r9,device,prms,'cmd_'+command)
					if(doL && delay!=lZ) msg[sM]=sMs(msg,sM)+" W"
				}catch(e){
					if(doL) msg[sM]=sMs(msg,sM)+" SKIP"
					error "Error while executing command $device.$command($prms):",r9,iN2,e
				}
				if(doL)info msg,r9
			}else
				executePhysicalCommand(r9,device,command,prms)
		}else{
			if(vcmd!=null){
				delay=executeVirtualCommand(r9,aggregate ? devices:device,command,prms)
				if(aggregate)break
			}else warn mySt+"$device command $command not found, prms: $prms",r9
		}
	}
	//negative delays force us to reschedule
	Boolean reschedule=delay<lZ
	delay=reschedule ? -delay:delay

	//if we don't have to wait, home free
	String pStr="executeTask: Waiting for "
	if(delay!=lZ){
		//get remaining piston time
		if(reschedule || async || delay>lMs(gtPLimits(),sTPAUSELIM)){
			//schedule a wake up
			String msg= isTrc(r9) ? "Requesting":sNL
			tracePoint(r9,myS,elapseT(t),-delay) //timers need to show the remaining time
			requestWakeUp(r9,statement,task,delay,data,true,tskC,sNL,msg) //allow statement cancels to work
			if(lge)myDetail r9,mySt+"result:FALSE"
			return false
		}else doPause(pStr+"${delay}ms",delay,r9,true)
	}
	tracePoint(r9,myS,elapseT(t),delay)

	//get remaining piston time
	Long overBy=checkForSlowdown(r9)
	if(overBy>lZ){
		Long mdelay=calcDel(overBy)
		doPause(pStr+"${mdelay}ms, Execution time exceeded by ${overBy}ms",mdelay,r9)
	}
	if(lge)myDetail r9,mySt+"result:TRUE"
	return true
}

Long callCmdWrap(Map r9,device,List prms,String command){
	return (Long)"${command}"(r9,device,prms)
}

private Long executeVirtualCommand(Map r9,devices,String command,List prms){
	Map msg=timer "Executed virtual command ${devices ? (devices instanceof List ? "$devices.":"[$devices]."):sBLK}${command}",r9
	Long delay
	try{
		delay=callCmdWrap(r9,devices,prms,'vcmd_'+command)
		if(isInf(r9)){
			if(command.contains('wait')) msg[sM]+=" $prms"
			if(isTrc(r9))trace msg,r9
			else if(delay>lZ)info msg,r9
		}

	}catch(all){
		msg[sM]="Error executing virtual command ${devices instanceof List ? "$devices":"[$devices]"}.${command}:"
		error msg,r9,iN2,all
		delay=lZ
	}
	return delay
}

@CompileStatic
private static String gTCP(Map statement){ return sMs(statement,sTCP) ?: sC }

@CompileStatic
private static List<Integer> svCS(Map r9,Map statement){
	// cancel on "" == c- condition state change (def), p- piston state change, b- condition or piston state change, sN- never cancel
	List<Integer> cs=(List<Integer>)[]+ (gTCP(statement) in ListBC ? (List<Integer>)mMs(r9,sSTACK)[sCS]:(List<Integer>)[] ) // task cancelation policy TCP
	cs.removeElement(iZ)
	return cs
}

@CompileStatic
private static Integer svPS(Map statement){ return gTCP(statement) in ListBP ? i1:iZ }

@CompileStatic
private static Long cedIs(Map r9){
	Integer a=gtPOpt(r9,sCED) // command execution delay
	Long ced; ced= a ? a.toLong():lZ
	if(ced>lZ){
		Long t1=lMs(gtPLimits(),sDEVMAXDEL)
		ced=Math.min(ced,t1)
	}
	return ced
}

@CompileStatic
private String gtSwitch(Map r9,device){ return (String)getDeviceAttributeValue(r9,device,sSWITCH) }

@CompileStatic
private void executePhysicalCommand(Map r9,device,String command,prms=[],Long idel=lZ,String isched=sNL,Boolean dco=false,Boolean doced=true,Boolean canq=true){
	Long delay
	delay=idel
	String s,s1,scheduleDevice
	scheduleDevice=isched
	Boolean willQ,ignRest
	willQ=delay!=lZ && scheduleDevice!=sNL

	Boolean doL=isTrc(r9)
	Boolean doI=(doL||isInf(r9))
	//delay on device commands is not supported in hubitat; using schedules instead
	s=sBLK
	s1=sBLK
	if(doL && delay)s1="wait before command delay: $delay "
	ignRest=false
	Long ced=cedIs(r9)
	if(doced && canq){
		if(ced>lZ){
			Boolean lge=isEric(r9)
			Long cmdqt=lMs(r9,sLSTPCQ) ?: lZ
			Long cmdsnt=lMs(r9,sLSTPCSNT) ?: lZ
			Long lastcmdSent=cmdqt&&cmdsnt ? Math.max(cmdqt,cmdsnt):(cmdqt ?: cmdsnt)
			Long waitT=ced+lastcmdSent-wnow()
			String sst; sst=sBLK
			if(lge)sst=s1+"cmdqt: $cmdqt cmdsnt: $cmdsnt waitT: $waitT lastcmdSent: $lastcmdSent ced: $ced "
			if(doL)s="No command execution delay required "+s1
			if(waitT>ced/i4){
				Long t1=delay
				ignRest=!willQ
				delay= Math.max(waitT,delay)
				scheduleDevice=scheduleDevice ?: hashD(r9,device)
				willQ=true
				if(doI && waitT>t1)s="Injecting command execution delay of ${waitT-t1}ms before [$device].$command() added schedule "
			}
			if(lge)s+=sst+"updated delay: $delay ignore restrictions: $ignRest"
		}
	}

	if(willQ && canq){
		Map statement=mMs(r9,sCURACTN)
		Map task=mMs(r9,sCURTSK)
		List<Integer> cs=svCS(r9,statement)
		Integer ps=svPS(statement)
		Long ttt=Math.round(wnow()*d1+delay)
		if(ced)r9[sLSTPCQ]=ttt
		Map schedule=[
			(sT):ttt,
			(sS):stmtNum(statement),
			(sI):iN3,
			(sCS):cs,
			(sPS):ps,
			(sD):[
				(sD):scheduleDevice,
				(sC):command,
				(sP):prms,
				(sTASK):stmtNum(task)
			]
		]
		if(ignRest){
			mMs(schedule,sD)['dc']=dco
			mMs(schedule,sD)['ig']=ignRest
		}
		if(doI){
			if(doL)debug s+wakeS(r9,'Requesting a device command',schedule),r9
			else info s,r9
		}
		spshSch(r9,schedule)
	}else{
		List nprms=(prms instanceof List) ? (List)prms:(prms!=null ? [prms]:[])
		try{
			//cleanup the prms so that SONOS works
			Integer psz; psz=nprms.size()
			while (psz>iZ && nprms[psz-i1]==null){ nprms.pop(); psz=nprms.size() }

			String tailStr; tailStr=sNL
			if(!canq && delay>lZ){
				Long t1=lMs(gtPLimits(),sDEVMAXDEL)
				delay=Math.min(delay,t1)
				doPause("PAUSE wait before device command: Waiting for ${delay}ms",delay,r9,true)
				if(doI)tailStr="[delay: $delay])".toString()
			}
			Map msg; msg=null
			if(doI)msg=timer sBLK,r9

			Boolean skip; skip=false
			// disableCommandOptimization
			// [sSTCLRTEMP,sSTCLR,sSTHUE,sSTSATUR,sPUSH,sRELEASE,sHOLD,sDOUBLETAP] // commands that do not allow command optimization
			if(!gtPOpt(r9,sDCO) && !dco && !(command in ListDCO)){
				Map cmd=PhysicalCommands()[command]
				if(cmd!=null && sMa(cmd)!=sNL){
					String attr= sMa(cmd)
					Map attribute= Attributes()[attr]
					if( attribute==null || attribute[sM]==null || !bIs(attribute,sM) ){ // not momentary attribute
						if(oMv(cmd)!=null && psz==iZ) {
							//commands with no parameter that set an attribute to a preset value
							if ((String)getDeviceAttributeValue(r9,device,attr) == sMv(cmd))
								skip= true
						} else if(psz==i1) {
							if(getDeviceAttributeValue(r9,device,attr) == nprms[iZ])
								skip= (command in ListSLVLSIFLVL ? gtSwitch(r9,device)==sON : true)
						}
					}
				}
			}

			if(doI){
				String tstr
				tstr=' device command ['+gtLbl(device)+'].'+command+'('
				if(psz>iZ) tstr+=nprms.join(sCOMMA)+"${tailStr ? sCOMMA+tailStr:')'}"
				else tstr+="${tailStr ?: ')'}"
				if(skip) msg[sM]='Command optimization: Skipped execution of'+tstr+' because it would make no change to the device.'+s
				else msg[sM]='Executed'+tstr
			}
			if(!skip){
				if(ced)r9[sLSTPCSNT]=wnow()
				pcmd(device,command,nprms)
			}
			if(doL)trace msg,r9
			else if(doI)info msg,r9
		}catch(all){
			error "Error while executing device command $device.$command($nprms):",r9,iN2,all
		}
	}
}

private static void pcmd(device,String cmd,List nprms=[]){
	if(nprms.size()>iZ) device."$cmd"(nprms as Object[])
	else device."$cmd"()
}

@Field static final String sOM='om'
@Field static final String sOH='oh'
@Field static final String sODW='odw'
@Field static final String sODM='odm'
@Field static final String sOMY='omy'
@Field static final String sOWM='owm'

/**
 * schedule EVERY timer
 */
@CompileStatic
private void scheduleTimer(Map r9,Map timer,Long lastRun=lZ,Boolean myPep){
	Boolean lg=isDbg(r9)
	Boolean lgt=isTrc(r9)
	Boolean lge=lg && isEric(r9)
	String mySt,mySt1; mySt=sBLK; mySt1= lg ? 'scheduleTimer ': sBLK
	Integer iTD=stmtNum(timer)
	Map tlo=mMs(timer,sLO)
	Map tlo2=mMs(timer,sLO2)
	Map tlo3=mMs(timer,sLO3)
	if(lge)
		mySt="stmt: ${iTD} lo:${tlo} lo2: ${tlo2} lo3: ${tlo3} lastRun: $lastRun "

	//if already scheduled once during run, don't do it again
	List<Map> schedules=sgetSchedules(sEXST,myPep)
	Boolean fnd=schedules.find{ Map it -> iMsS(it)==iTD } != null ||
			sgtSch(r9).find{ Map it -> iMsS(it)==iTD } != null
	if(fnd){
		if(lge) myDetail r9,mySt1+'FOUND EXISTING TIMER '+mySt,iN2
		return
	}
	if(lge) myDetail r9,mySt1+mySt,i1
	//complicated stuff follows
	// interval, day, hour, etc
	String tinterval="${oMv(mevaluateOperand(r9,tlo))}".toString()
	Boolean exitOut,priorActivity,hasPresetS
	exitOut=false
	Integer tintvl,level,cycles
	tintvl=iZ
	if(tinterval.isInteger()){
		tintvl=tinterval.toInteger()
		if(tintvl<=iZ)exitOut=true
	}else exitOut=true
	if(exitOut){
		if(lge)myDetail r9,mySt1+mySt+"Interval: $tinterval"
		return
	}
	Integer interval=tintvl
	String intervlUnit=sMvt(tlo)
	level=iZ

	Long delta,dtime,rightNow,nxtSchd
	delta=lZ
	switch(intervlUnit){
		case sMS: level=i1; delta=l1; break
		case sS: level=i2; delta=lTHOUS; break
		case sM: level=i3; delta=dMSMINT.toLong(); break
		case sH: level=i4; delta=dMSECHR.toLong(); break
		case sD: level=i5; break
		case sW: level=i6; break
		case sN: level=i7; break
		case sY: level=i8; break
	}
	if(lge) myDetail r9,mySt1+"interval: $interval delta: $delta level: $level intervlUnit: $intervlUnit",iN2
	dtime=lZ
	priorActivity= lastRun!=lZ
	hasPresetS= false
	rightNow= wnow()
	if(delta==lZ){ // [sD, sW, sN, sY]
		hasPresetS= hasPreset(tlo2)
		if(lge) myDetail r9,mySt1+"1 dtime: $dtime rightNow: $rightNow lastRun: $lastRun hasPreset: $hasPresetS",iN2
		dtime= evalRO1(r9,tlo2,rightNow,tlo3,false)
		// using sunrise,sunset presets, make sure in the future, without dst offsets
		if(hasPresetS){
			Long ndtime
			ndtime= priorActivity ? pushTimeAhead(r9,dtime,lastRun,false) : pushTimeAhead(r9,dtime,rightNow,false)
			if(ndtime!=dtime)
				if(lge) myDetail r9,mySt1+"2 dtime: $dtime rightNow: $rightNow lastRun: $lastRun",iN2
				dtime= evalRO1(r9,tlo2,ndtime,tlo3,false)
		}
		//if(lge) myDetail r9,mySt1+"3 dtime: $dtime rightNow: $rightNow lastRun: $lastRun",iN2
		if(!priorActivity) dtime=pushTimeAhead(r9,dtime,rightNow,!hasPresetS) // first run
		if(lge) myDetail r9,mySt1+"4 dtime: $dtime rightNow: $rightNow lastRun: $lastRun",iN2

	}else{
		delta=Math.round(delta*interval*d1)
		if(lge) myDetail r9,mySt1+"interval: $interval delta: $delta level: $level intervlUnit: $intervlUnit",iN2
	}

	Long lastR=priorActivity ? lastRun:rightNow
	nxtSchd=lastR

	if(lastR>rightNow) //sometimes timers run early, so make sure at least in the near future
		rightNow=Math.round(lastR+d1)

	if(intervlUnit==sH){
		Long min=lcast(r9,oMs(tlo,sOM))
		nxtSchd=Math.round(dMSECHR*Math.floor((nxtSchd/dMSECHR).toDouble())+(min*dMSMINT))
	}

	//next date
	cycles=i500
	Integer tcy=cycles+i1
	if(lge)
		myDetail r9,mySt1+"cycle: ${tcy-cycles} dtime: $dtime delta: $delta nxtSchd: $nxtSchd priorActivity: $priorActivity lastRun: $lastRun lastR: $lastR rightNow: $rightNow",iN2
	Double d7=7.0D
	while(cycles!=iZ){
		Long svNxtSchd= nxtSchd
		if(lge) myDetail r9,mySt1+"10 svNxtSchd: $svNxtSchd rightNow: $rightNow nxtSchd: $nxtSchd",iN2
		if(delta!=lZ){ // anything of [sMS, sS, sM, sH]
			if(nxtSchd<(rightNow-delta)){
				//behind, catch up to where the next future occurrence
				Long n=Math.floor(((rightNow-nxtSchd)/delta*d1).toDouble()).toLong()
				//if(lg)debug "Timer fell behind by $n interval${n>i1 ? sS:sBLK}, catching up",r9
				nxtSchd+=Math.round(delta*n*d1)
			}
			nxtSchd+=delta
		}else{ // [sD, sW, sN, sY]
			//if(lge) myDetail r9,mySt1+"11 dtime: $dtime rightNow: $rightNow nxtSchd: $nxtSchd",iN2
			//advance ahead of rightNow if in the past
			dtime=pushTimeAhead(r9,dtime,rightNow,!hasPresetS)
			Long lastDay=Math.floor((nxtSchd/dMSDAY).toDouble()).toLong()
			Long thisDay=Math.floor((dtime/dMSDAY).toDouble()).toLong()

			ZonedDateTime zdt,nzdt; zdt = localDate(r9,dtime)
			Integer dyYear= zdt.getYear()-i1900
			Integer dyMon= zdt.getMonth().getValue()-i1
			Integer dyDay= zdt.getDayOfWeek().getValue() % i7
			Integer dyMonDay= zdt.getDayOfMonth()
			if(lge) myDetail r9,mySt1+"12 dtime: $dtime rightNow: $rightNow  nxtSchd: $nxtSchd lastDay: $lastDay thisDay: $thisDay dyYear: $dyYear dyMon: $dyMon dyMonDay: $dyMonDay dyDay: $dyDay ZonedDate: $zdt",iN2

			//the repeating interval is not necessarily constant
			switch(intervlUnit){
				case sD:
					if(priorActivity){
						//add the required number of days
						nxtSchd=addTime(r9,dtime,Math.round(dMSDAY*(interval-(thisDay-lastDay))),level)
					}else nxtSchd=dtime
					break
				case sW:
					//figure out the first day of the week matching the requirement
					Long currentDay=dyDay
					Long requiredDay; requiredDay=lcast(r9,oMs(tlo,sODW))
					if(lge) myDetail r9,mySt1+"currentDay: $currentDay requiredDay: $requiredDay ",iN2
					if(currentDay>requiredDay)requiredDay+=i7
					//move to first matching day in future
					nxtSchd=addTime(r9,dtime,Math.round(dMSDAY*(requiredDay-currentDay)),level) // this is ahead of now on proper day (could be today)
					Integer myInterval; myInterval=interval
					if(requiredDay!=currentDay) myInterval-=i1 //if we changed the day adjust interval calculation
					if(priorActivity)nxtSchd=addTime(r9,nxtSchd,Math.round(604800000.0D*myInterval),level) // this is n weeks from now
					break
				case sN: // months
				case sY:
					//figure out the month matching the requirement
					Integer odm=icast(r9,oMs(tlo,sODM))
					def odw=oMs(tlo,sODW)
					Integer omy=intervlUnit==sY ? icast(r9,oMs(tlo,sOMY)):iZ
					Integer day,year,month
					year=dyYear
					month=Math.round((intervlUnit==sN ? dyMon /*date.month*/:omy)+(priorActivity ? interval:((nxtSchd<rightNow)? d1:dZ))*(intervlUnit==sN ? d1:i12)).toInteger()
					if(month>=i12){
						year+=Math.floor((month/i12).toDouble()).toInteger()
						month=month%i12
					}
					if(lge) myDetail r9,mySt1+"month: $month year: $year ",iN2
					nzdt= zdt.withDayOfMonth(i1)
					nzdt= nzdt.withMonth(month+i1)
					nzdt= nzdt.withYear(year+i1900)

					Integer lastDayOfMonth= nzdt.with(TemporalAdjusters.lastDayOfMonth())
							.getDayOfMonth()
					if(odw==sD){
						if(odm>iZ)day=(odm<=lastDayOfMonth)? odm:iZ
						else{
							day=lastDayOfMonth+i1+odm
							day=(day>=i1)? day:iZ
						}
					}else{
						Integer iodw=icast(r9,odw)
						//locate the nth week day of the month
						if(odm>iZ){
							//going forward
							Integer firstDayOfMonthDOW= nzdt.getDayOfWeek().getValue() % i7
							//locate the first matching day
							Integer firstMatch=Math.round(i1+iodw-firstDayOfMonthDOW+(iodw<firstDayOfMonthDOW ? d7:dZ)).toInteger()
							day=Math.round(firstMatch+d7*(odm-d1)).toInteger()
							day=(day<=lastDayOfMonth)? day:iZ
						}else{
							//going backwards
							Integer lastDayOfMonthDOW= nzdt.with(TemporalAdjusters.lastDayOfMonth())
									.getDayOfWeek().getValue() % i7
							//locate the first matching day
							Integer firstMatch=lastDayOfMonth+iodw-lastDayOfMonthDOW-(iodw>lastDayOfMonthDOW ? i7:iZ)
							day=Math.round(firstMatch+d7*(odm+i1)).toInteger()
							day=(day>=i1 && day<=lastDayOfMonth)? day:iZ
						}
					}
					if(lge)
						myDetail r9,mySt1+"odm: $odm odw: $odw omy: $omy day: $day month: $month year: $year",iN2
					if(day){
						nzdt= nzdt.withDayOfMonth(day)
						nxtSchd= nzdt.toInstant().toEpochMilli()
						//if(lge) myDetail r9,mySt1+"14 dtime: $dtime rightNow: $rightNow nxtSchd: $nxtSchd",iN2
					}
					break
			}
			//if(lge) myDetail r9,mySt1+"13 dtime: $dtime rightNow: $rightNow nxtSchd: $nxtSchd",iN2
			// if we have a sunrise/sunset preset, we need to get the sunrise/sunset as of the day we are evaulating in the future
			nxtSchd= hasPresetS && svNxtSchd!=nxtSchd ? evalRO1(r9,tlo2,nxtSchd,tlo3,false) : nxtSchd
		}
		if(lge) myDetail r9,mySt1+"15 dtime: $dtime rightNow: $rightNow nxtSchd: $nxtSchd",iN2
		//check to see if it fits the restrictions
		if(nxtSchd>=rightNow){
			Long offset=checkTimeRestrictions(r9,tlo,nxtSchd,level,interval)
			if(lge)myDetail r9,mySt1+"checking for schedule restrictions for $tlo interval: $interval level: $level RESULT: $offset",iN2
			if(offset==lZ){
				if(lge)
					myDetail r9,mySt1+"TIME RESTRICTION PASSED cycle: ${tcy-cycles} nxtSchd: $nxtSchd priorActivity: $priorActivity lastRun: $lastRun lastR: $lastR rightNow: $rightNow",iN2
				break
			}
			if(offset>lZ){
				//if(lge) myDetail r9,mySt1+"offset: $offset level: $level",iN2
				nxtSchd=addTime(r9,nxtSchd,offset,level)
				nxtSchd= hasPresetS ? evalRO1(r9,tlo2,nxtSchd,tlo3,false) : nxtSchd
			}
		}
		dtime=nxtSchd
		priorActivity=true
		cycles-=i1
		if(lge)
			myDetail r9,mySt1+"cycle: ${tcy-cycles} nxtSchd: $nxtSchd priorActivity: $priorActivity lastRun: $lastRun lastR: $lastR rightNow: $rightNow",iN2
	}

	if(nxtSchd>lastR){
		liMs(r9,sSCHS).removeAll{ Map it -> iMsS(it)==iTD }
		String msg= lgt ? mySt1+'Requesting ' + (cycles==iZ && lg ? 'interim ' : sBLK) +'every schedule':sNL
		requestWakeUp(r9,timer,[(sDLR):iN1],nxtSchd,sNL,false,sNL,msg)
	}
	if(lge)myDetail r9,mySt1+mySt
}

/**
 * return true if operand has sunrise or sunset
 */
@CompileStatic
static Boolean hasPreset(Map oper){ return (oper && sMt(oper)==sS && sMs(oper,sS) in [sSUNSET,sSUNRISE,'midnight','noon']) }

/**
 * Add time (mod DST)
 */
@CompileStatic
private static Long addTime(Map r9,Long pastTime,Long add,Integer level){
	Long t0,t1
	TimeZone mtz=rTZ(r9)
	t0=pastTime+add
	t1= level>=i5 ? Math.round( (t0+(mtz.getOffset(pastTime)-mtz.getOffset(t0))) *d1) : t0
	return t1
}

/**
 * Push pastTime head by 24 hours (mod DST) until >=curTime
 */
@CompileStatic
private static Long pushTimeAhead(Map r9,Long pastTime,Long curTime, Boolean dst=true){
	Long retTime,t0,t1
	retTime=pastTime
	TimeZone mtz=rTZ(r9)
	while(retTime<curTime){
		t0=Math.round(retTime+dMSDAY)
		t1= dst ? t0+(mtz.getOffset(retTime)-mtz.getOffset(t0)) : t0
		retTime=t1
	}
	return retTime
}

/**
 * evaluate time request with optional offset request; deal with sunset
 * @param iro - time map
 * @param dayBasis - day bias to start
 * @param operoffset - optional offset to time
 * @return long of datetime this calculated to
 */
@CompileStatic
Long evalRO1(Map r9,Map iro,Long dayBasis,Map operoffset,Boolean nextMidN=false){
	Boolean lge=isDbg(r9) && isEric(r9)
	String s; s=sNL
	if(lge){
		s= "evalRO1: ro: $iro dayBasis: $dayBasis operoffset: $operoffset"
		myDetail r9,s,i1
	}
	Map ro= [:]+iro
	Boolean roHasPreset= hasPreset(ro)
	String ty; ty= ro[sVT] ?: sTIME
	if(roHasPreset && ro[sVT]==sTIME){ // sunrise or sunset is a dtime and never a time
		ty= sDTIME
		ro[sVT]= ty
	}
	//let's get the at date/time/datetime and offset; this could be 10:00, or noon-10h, sunrise-5hr, midnight+10h, '4/1/2024, 8:00:00PM'
	Long t1; t1= longEvalExpr(r9,mevaluateOperand(r9,ro,null,false,nextMidN,dayBasis),ty)
	Long mn= getMidnightTime(r9,Math.max( (dayBasis ?: wnow()),t1))
	if( !(ty in [sDTIME,sDATE])){ // make a datetime if not already
		t1=addTime(r9,mn,t1,i5)
	}
	Long tzadjust; tzadjust= lZ
	Long offset; offset= lZ
	if(sMt(ro)!=sC){
		Map offsetMap= operoffset!=null ? mevaluateOperand(r9,operoffset) : null
		offset= offsetMap!=null ? longEvalExpr(r9,rtnMap1(offsetMap)) : lZ
	}
	if(!roHasPreset && offset!=lZ){ // with offset, we may have crossed a dst change today...
		TimeZone mtz=rTZ(r9)
		Long to= t1+offset
		Long pTime= Math.min(mn,to)
		Long fTime= Math.max(mn,to)
		tzadjust= (mtz.getOffset(pTime)-mtz.getOffset(fTime))
	}
	Long ret= t1+tzadjust+offset
	if(lge)
		myDetail r9,s+" $ret rets: ${formatLocalTime(r9,ret)}"
	return ret
}

@CompileStatic
Long evalRO2(Map r9,Boolean trigger,Integer pCnt,Long v1,Long v2,Map ro2,Long mnt,Map operoffset,Map cLO){
	Boolean lge=isDbg(r9) && isEric(r9)
	String s; s=sNL
	if(lge){
		s= "evalRO2: trigger: $trigger pCnt: $pCnt ro2: $ro2 v1: $v1 v2: $v2 operoffset: $operoffset"
		myDetail r9,s,i1
	}
	Long ret
	if(trigger) ret=v1
	else{
		if(pCnt>i1){
			ret= evalRO1(r9,ro2,v2,operoffset,true)
		}else{
			ret= sMv(cLO)==sTIME ? mnt:v1
		}
	}
	if(lge)
		myDetail r9,s+" ret: $ret rets: ${formatLocalTime(r9,ret)}"
	return ret
}

@CompileStatic
private void scheduleTimeCondition(Map r9,Map cndtn){
	String mySt; mySt=sNL
	Boolean lg=isDbg(r9)
	Boolean lgt=isTrc(r9)
	Boolean lge=lg && isEric(r9)
	if(lge){
		mySt='scheduleTimeCondition '
		myDetail r9,mySt,i1
	}
	Integer cndNm=stmtNum(cndtn)
	//if already scheduled once during run, don't do it again
	String i=sI; Integer iz=iZ // compiler bug
	if(sgtSch(r9).find{ Map it -> iMsS(it)==cndNm && iMs(it,i)==iz }){
		if(lge)myDetail r9,mySt+'FOUND EXISTING TIMER '
		return
	}
	String co=sMs(cndtn,sCO)
	Map comparison=(Map)AllComparisons()[co]
	if(comparison==null)return
	Boolean trigger=bIs(comparison,sTRIG)
	cancelStatementSchedules(r9,cndNm)
	Integer pCnt=comparison[sP]!=null ? iMs(comparison,sP):iZ
	if(!pCnt)return
	Map cLO=mMs(cndtn,sLO)

	Long v1,v2,n,n1
	Map ro,ro2

	Long now= wnow()
	ro=mMs(cndtn,sRO)
	Boolean roHasPreset= hasPreset(ro)
	v1= evalRO1(r9,ro,now,mMs(cndtn,sTO))

	ro2=mMs(cndtn,sRO2)
	Boolean ro2HasPreset= hasPreset(ro2)
	v2= evalRO2(r9,trigger,pCnt,v1,now,ro2,getMidnightTime(r9,now),mMs(cndtn,sTO2),cLO)

	n=Math.round(d1*now+2000L)
	if(sMv(cLO)==sTIME){
		Long tempv; tempv= v1
		v1=pushTimeAhead(r9,v1,n,!roHasPreset)
		if(roHasPreset && tempv!=v1){
			v1= evalRO1(r9,ro,v1,mMs(cndtn,sTO))
			v2= evalRO2(r9,trigger,pCnt,v1,v2,ro2,v2,mMs(cndtn,sTO2),cLO)
		}else{
			tempv= v2
			v2=pushTimeAhead(r9,v2,n,!ro2HasPreset)
			if(ro2HasPreset && tempv!=v2)
				v2= evalRO2(r9,trigger,pCnt,v1,v2,ro2,v2,mMs(cndtn,sTO2),cLO)
		}
	}

	//if(lge)myDetail r9,mySt+"BEFORE n: $n v1: $v1 v2: $v2",iN2
	//figure out the next time
	v1=v1<n ? v2:v1
	v2=v2<n ? v1:v2
	n=v1<v2 ? v1:v2
	//if(lge)myDetail r9,mySt+"AFTER n: $n v1: $v1 v2: $v2",iN2

	Long origN=n
	n1=n
	if(sMv(cLO)==sTIME && trigger){
		Integer iyr=1461 // 4 years
		Integer v; v=iyr
		if(lge)myDetail r9,mySt+"checking for schedule restrictions for $cLO",iN2
		Long n2
		while(v>iZ){
			//repeat until we find a day that's matching the restrictions
			Long t= checkTimeRestrictions(r9,cLO,n1,i5,i1)
			if(lge)myDetail r9,mySt+"checkTimeRestrisions returned $t",iN2
			if(t==lZ) break
			// deal with sunrise sunset future calculations
			if(roHasPreset || ro2HasPreset){
				n=Math.round(d1*n1+2000L)

				Long tempv; tempv= v1
				v1=pushTimeAhead(r9,v1,n,!roHasPreset)
				if(roHasPreset && tempv!=v1){
					v1= evalRO1(r9,ro,v1,mMs(cndtn,sTO))
					v2= evalRO2(r9,trigger,pCnt,v1,v2,ro2,v2,mMs(cndtn,sTO2),cLO)
				}else{
					tempv= v2
					v2= pushTimeAhead(r9,v2,n,!ro2HasPreset)
					if(ro2HasPreset && tempv != v2)
						v2= evalRO2(r9,trigger,pCnt,v1,v2,ro2,v2,mMs(cndtn,sTO2),cLO)
				}
				//figure out the next time
				v1=v1<n1 ? v2:v1
				v2=v2<n1 ? v1:v2
				n2=v1<v2 ? v1:v2
				n1= n2
			}else
				n1=pushTimeAhead(r9,n1,n1+l1)
			v-=i1
		}
		if(lg && v!=iyr)debug "Adding ${iyr-v} days, $origN >>> $n1" ,r9
		if(v==iZ)n1=origN
	}

	if(n1>wnow()){
		String msg= lgt ? "Requesting time schedule":sNL
		requestWakeUp(r9,cndtn,[(sDLR):iZ],n1,sNL,false,sNL,msg)
	}
	if(lge)myDetail r9,mySt
}

@CompileStatic
private static Boolean listWithSz(obj){ return obj instanceof List && ((List)obj).size()>iZ }

@CompileStatic
private static List<Integer>listInt(Boolean a,Map operand,String k){
	return a && listWithSz(operand[k]) ? (List<Integer>)operand[k]:null
}

@CompileStatic
private Long checkTimeRestrictions(Map r9,Map operand,Long time,Integer level,Integer interval){
	//returns 0 if restrictions are passed
	//returns a positive number as millisecond offset to apply to nextSchedule for fast forwarding
	//returns a negative number as a failed restriction with no fast forwarding offset suggestion

	// on minute of hour
	List<Integer> om= listInt((level<=i2),operand,sOM)
	// on hours
	List<Integer> oh= listInt((level<=i3),operand,sOH)
	// on day(s) of week
	List<Integer> odw= listInt((level<=i5),operand,sODW)
	// on day(s) of month
	List<Integer> odm
	odm= listInt((level<=i6),operand,sODM)
	// on weeks of month
	List<Integer> owm= listInt((level<=i6 && odm==null),operand,sOWM)
	// on month of year
	List<Integer> omy= listInt((level<=i7), operand,sOMY)

	if(om==null && oh==null && odw==null && odm==null && owm==null && omy==null)return lZ

	ZonedDateTime zdt, nzdt; zdt = localDate(r9,time)
	Integer dyYear= zdt.getYear()-i1900
	Integer dyMon= zdt.getMonth().getValue()-i1
	Integer dyDate= zdt.getDayOfMonth()
	Integer dyDay= zdt.getDayOfWeek().getValue() % i7
	Integer dyHr= zdt.getHour()
	Integer dyMins= zdt.getMinute()

	Double dminDay=1440.0D
	Double dsecDay=86400.0D

	Long res; res= -l1
	//month restrictions
	Integer dyMonPlus=dyMon+i1
	if(omy!=null && omy.indexOf(dyMonPlus)<iZ){
		List<Integer> tI=omy.sort{ Integer it -> it }
		Integer month
		month=(tI.find{ Integer it -> it>dyMonPlus } ?: i12+tI[iZ]) -i1
		Integer year=dyYear+(month>=i12 ? i1:iZ)
		month=(month>=i12 ? month-i12:month)
		nzdt= zdt.withYear(year+i1900)
		nzdt= nzdt.withMonth(month+i1)
		nzdt= nzdt.withDayOfMonth(i1)
		Long ms= nzdt.toInstant().toEpochMilli()-time
		switch(level){
			case i2: //by second
				Double tt=Math.floor((ms/(d1000*interval)).toDouble())
				res=Math.round(interval*(tt-d2)*d1000)
				break
			case i3: //by minute
				Double tt=Math.floor((ms/(dMSMINT*interval)).toDouble())
				res=Math.round(interval*(tt-d2)*dMSMINT)
				break
		}
		return pRes(res)
	}

	Double d7=7.0D
	//week of month restrictions
	if(owm!=null && !(owm.indexOf(getWeekOfMonth(zdt))>=iZ || owm.indexOf(getWeekOfMonth(zdt,true))>=iZ)){
		switch(level){
			case i2: //by second
				Double tt= Math.floor( ( ( (d7-dyDay)*dsecDay -dyHr*dSECHR -dyMins*d60)/interval ).toDouble() )
				res=Math.round(interval*(tt-d2)*d1000)
				break
			case i3: //by minute
				Double tt= Math.floor( ( ( (d7-dyDay)*dminDay -dyHr*d60 -dyMins)/interval).toDouble() )
				res=Math.round(interval*(tt-d2)*dMSMINT)
				break
		}
		return pRes(res)
	}

	//day of month restrictions
	if(odm!=null && odm.indexOf(dyDate)<iZ){
		Integer lastDayOfMonth= zdt.with(TemporalAdjusters.lastDayOfMonth())
				.getDayOfMonth()
		if(odm.find{ Integer it -> it<1 }){
			//we need to add the last days
			odm= []+odm as List<Integer> //copy the array
			if(odm.indexOf(iN1)>=iZ) odm.push(lastDayOfMonth)
			if(odm.indexOf(iN2)>=iZ) odm.push(lastDayOfMonth-i1)
			if(odm.indexOf(iN3)>=iZ) odm.push(lastDayOfMonth-i2)
			odm.removeAll{ Integer it -> it<1 }
		}
		List<Integer> tI=odm.sort{ Integer it -> it }
		switch(level){
			case i2: //by second
				Double tt= Math.floor( ((((tI.find{ Integer it -> it>dyDate } ?: lastDayOfMonth+tI[iZ])-dyDate)*dsecDay-dyHr*dSECHR-dyMins*d60)/interval).toDouble() )
				res=Math.round(interval*(tt-d2)*d1000)
				break
			case i3: //by minute
				Double tt= Math.floor(((((tI.find{ Integer it -> it>dyDate } ?: lastDayOfMonth+tI[iZ])-dyDate)*dminDay-dyHr*d60-dyMins)/interval).toDouble())
				res=Math.round(interval*(tt-d2)*dMSMINT)
				break
		}
		return pRes(res)
	}

	//day of week restrictions
	if(odw!=null && odw.indexOf(dyDay)<iZ ){
		List<Integer> tI=odw.sort{ Integer it -> it }
		switch(level){
			case i2: //by second
				Double tt= Math.floor(((((tI.find{ Integer it -> it>dyDay } ?: d7+tI[iZ])-dyDay)*dsecDay-dyHr*dSECHR-dyMins*d60)/interval).toDouble())
				res=Math.round(interval*(tt-d2)*d1000)
				break
			case i3: //by minute
				Double tt= Math.floor(((((tI.find{ Integer it -> it>dyDay } ?: d7+tI[iZ])-dyDay)*dminDay-dyHr*d60-dyMins)/interval).toDouble())
				res=Math.round(interval*(tt-d2)*dMSMINT)
				break
		}
		return pRes(res)
	}

	//hour restrictions
	if(oh!=null && oh.indexOf(dyHr)<iZ ){
		Double d24=24.0D
		List<Integer> tI=oh.sort{ Integer it -> it }
		switch(level){
			case i2: //by second
				Double tt= Math.floor(((((tI.find{ Integer it -> it>dyHr } ?: d24+tI[iZ])-dyHr)*dSECHR-dyMins*d60)/interval).toDouble())
				res=Math.round(interval*(tt-d2)*d1000)
				break
			case i3: //by minute
				Double tt= Math.floor(((((tI.find{ Integer it -> it>dyHr } ?: d24+tI[iZ])-dyHr)*d60-dyMins)/interval).toDouble())
				res=Math.round(interval*(tt-d2)*dMSMINT)
				break
		}
		return pRes(res)
	}

	//minute restrictions
	if(om!=null && om.indexOf(dyMins)<iZ ){
		//get the next highest minute
		//suggest an offset to reach the next minute
		List<Integer> tI=om.sort{ Integer it -> it }
		Double tt= Math.floor((((tI.find{ Integer it -> it>dyMins } ?: d60+tI[iZ])-dyMins-d1)*d60/interval).toDouble())
		res=Math.round(interval*(tt-d2)*d1000)
		return pRes(res)
	}
	return lZ
}

/** return r if r>0, otherwise -1 */
@CompileStatic
private static Long pRes(Long r){ return r>lZ ? r: -l1 }

/**
 * return the number of occurrences of same day of week up until the date or from the end of the month if backwards,i.e. last Sunday is -1, second-last Sunday is -2
 */
@CompileStatic
private static Integer getWeekOfMonth(ZonedDateTime zdt,Boolean backwards=false){
	Integer day= zdt.getDayOfMonth()
	if(backwards){
		Integer lastDayOfMonth= zdt.with(TemporalAdjusters.lastDayOfMonth())
					.getDayOfMonth()
		return -(i1+Math.floor( ((lastDayOfMonth-day)/i7).toDouble() ))
	}else return i1+Math.floor(((day-i1)/i7).toDouble()) //1 based
}

/**
 * setup a wakeup at task.$; toResume saves state to put back when timer fires
 */
@CompileStatic
private void requestWakeUp(Map r9,Map statement,Map task,Long timeOrDelay,String data=sNL,Boolean toResume=true, String reason=sNL,String msg=sNL,String tmsg=sNL){
	Long time=timeOrDelay>9999999999L ? timeOrDelay:wnow()+timeOrDelay
	List<Integer> cs=svCS(r9,statement)
	Integer ps=svPS(statement)
	Map mmschedule=[
		(sT):time,
		(sS):stmtNum(statement),
		(sI):stmtNum(task), // timers restart at .i
		(sCS):cs,
		(sPS):ps
	]
	if(reason!=sNL)mmschedule[sR]=reason
	if(data!=sNL)mmschedule[sD]=data
	Boolean fnd

	//not all wakeups are suspend/resume
	if(toResume){ // state to save across a sleep
		Map e=mMs(r9,sEVENT)
		Map es=mMs(e,sSCH)
		if(sMs(e,sNM)==sTIME && es!=null && iMsS(es) && stmtNum(task)>=iZ && data!=sNL && !data.startsWith(sCLN))
			mmschedule['svs']=iMsS(es) // dealing a sleep before r9.wakingUp

		fnd=false
		def myResp,myJson,a

		myResp=r9[sRESP]
		if(myResp.toString().size()>10000){ myResp=[:]; fnd=true } // state can only be total 100KB

		myJson=r9[sJSON]
		if(myJson.toString().size()>10000){ myJson=[:]; fnd=true }
		if(fnd)debug 'trimming from scheduled wakeup saved $response and/or $json due to large size',r9

		fnd=false
		Map mstk,evt
		mstk=[:]
		a=(Double)gtSysVarVal(r9,sDLLRINDX); if(a!=null)fnd=true
		mstk[sINDX]=a
		a=(List)gtSysVarVal(r9,sDLLRDEVICE); if(a!=null)fnd=true
		mstk[sDEV]=a
		a=(List)gtSysVarVal(r9,sDLLRDEVS); if(a)fnd=true
		mstk[sDEVS]=a
		if(myJson)fnd=true
		mstk[sJSON]=myJson
		if(myResp)fnd=true
		mstk[sRESP]=myResp
		if(fnd)mmschedule[sSTACK]=mstk
// what about previousEvent httpContentType httpStatusCode httpStatusOk iftttStatusCode iftttStatusOk "\$mediaId" "\$mediaUrl" "\$mediaType" mediaData (big)

		evt=[:]+mMs(r9,sCUREVT)
		if(evt)evt=cleanEvt(evt)
		mmschedule['evt']=evt

		a=gtSysVarVal(r9,sDARGS)
		if(a)mmschedule[sARGS]=a
	}
	spshSch(r9,mmschedule)
	if(msg||tmsg){
		String s= wakeS(r9,sBLK,mmschedule)
		if(msg)trace msg+s,r9 else trace tmsg+s,r9
	}
}

@CompileStatic
private String wakeS(Map r9,String m,Map sch){ Long t=lMt(sch); return m+" wake up at ${formatLocalTime(r9,t)} (in ${t-wnow()}ms) for "+cnlS(sch) }

/** Returns true if switch does not match mat */
@CompileStatic
private Boolean ntMatSw(Map r9,String mat,device,String cmd){
	if(mat!=sNL && gtSwitch(r9,device)!=mat){
		if(isTrc(r9))trace "Skipping ${cmd} as switch is not $mat",r9
		return true
	}
	return false
}

@CompileStatic
private Long do_setLevel(Map r9,device,List prms,String cmd,Integer val=null){
	Integer arg=val!=null ? val:icast(r9,prms[iZ])
	Integer psz=prms.size()
	String mat=psz>i1 ? sLi(prms,i1):sNL
	if(ntMatSw(r9,mat,device,cmd))return lZ
	Double delay; delay=-d1
	List<Object> larg
	larg= [arg] as List<Object>
	if(cmd==sSTLVL){ // setLevel takes seconds duration argument (optional)
		delay=psz>i2 && prms[i2]!=null ? dcast(r9,prms[i2]):-d1
	}else if(cmd==sSTCLRTEMP){ // setColorTemp takes level and seconds duration arguments (optional)
		if(psz>i2){
			Integer lvl=prms[i2]!=null ? icast(r9,prms[i2]):null
			larg.push(lvl)
			delay=psz>i3 && prms[i3]!=null ? dcast(r9,prms[i3]):-d1
		}
	}
	if(delay>=dZ) larg.push(delay.toBigDecimal())
	executePhysicalCommand(r9,device,cmd,larg)
	//if(delay>=dZ)return Math.round(delay*d1000)
	return lZ
}

// cmd_ are wrappers for device commands with added parameters by webCoRE - these are in LWCMDS
@CompileStatic
private Long cmd_setLevel(Map r9,device,List prms){ return do_setLevel(r9,device,prms,sSTLVL) }

private Long cmd_setInfraredLevel(Map r9,device,List prms){ return do_setLevel(r9,device,prms,sSTIFLVL) }

@Field static final Double d3d6=3.6D
private static Integer wcHue2DevHue(Integer v){ return Math.round(v/d3d6).toInteger() }
private static Integer devHue2WcHue(Integer v){ return Math.round(v*d3d6).toInteger() }

private Long cmd_setHue(Map r9,device,List prms){
	Integer hue=wcHue2DevHue(iLi(prms,iZ))
	return do_setLevel(r9,device,prms,sSTHUE,hue)
}

private Long cmd_setSaturation(Map r9,device,List prms){ return do_setLevel(r9,device,prms,sSTSATUR) }

private Long cmd_setColorTemperature(Map r9,device,List prms){ return do_setLevel(r9,device,prms,sSTCLRTEMP) }

@CompileStatic
private static Map gtColor(String colorValue){
	Map color; color=(colorValue=='Random')? getRandomColor():getColorByName(colorValue)
	if(color==null) color=hexToColor(colorValue)
	if(color!=null){
		color=[
			hex:sMs(color,'rgb'),
			(sHUE): wcHue2DevHue(iMs(color,sH)),
			(sSATUR):iMsS(color),
			(sLVL):iMs(color,sL)
		]
	}
	return color
}

private Long cmd_setColor(Map r9,device,List prms){
	Map color=gtColor(sLi(prms,iZ))
	if(!color){
		error "ERROR: Invalid color $prms",r9
		return lZ
	}
	Integer psz=prms.size()
	String mat=psz>i1 ? sLi(prms,i1):sNL
	if(ntMatSw(r9,mat,device,sSTCLR))return lZ
	Long delay=psz>i2 ? (Long)prms[i2]:lZ
	executePhysicalCommand(r9,device,sSTCLR,color,delay)
	if(delay>=lZ)return delay
	return lZ
}

private Long cmd_setAdjustedColor(Map r9,device,List prms){
	Map color=gtColor(sLi(prms,iZ))
	if(!color){
		error "ERROR: Invalid color $prms",r9
		return lZ
	}
	Integer psz=prms.size()
	String mat=psz>i2 ? sLi(prms,i2):sNL
	String cmd='setAdjustedColor'
	if(ntMatSw(r9,mat,device,cmd))return lZ
	Long duration=matchCastL(r9,prms[i1])
	Long delay=psz>i3 ? (Long)prms[i3]:lZ
	executePhysicalCommand(r9,device,cmd,[color,duration],delay)
	if(delay>=lZ)return delay
	return lZ
}

private Long cmd_setAdjustedHSLColor(Map r9,device,List prms){
	Integer psz=prms.size()
	String mat=psz>i4 ? sLi(prms,i4):sNL
	if(ntMatSw(r9,mat,device,'setAdjustedHSLColor'))return lZ

	Long duration=matchCastL(r9,prms[i3])
	Integer hue=wcHue2DevHue(iLi(prms,iZ))
	Integer saturation=iLi(prms,i1)
	Integer level=iLi(prms,i2)
	Map color=[
		(sHUE):hue,
		(sSATUR):saturation,
		(sLVL):level
	]
	Long delay=psz>i5 ? (Long)prms[i5]:lZ
	executePhysicalCommand(r9,device,'setAdjustedColor',[color,duration],delay)
	if(delay>=lZ)return delay
	return lZ
}

private Long cmd_setLoopDuration(Map r9,device,List prms){
	Integer duration=Math.round(matchCastL(r9,prms[iZ])/d1000).toInteger()
	executePhysicalCommand(r9,device,'setLoopDuration',duration)
	return lZ
}

private Long cmd_setVideoLength(Map r9,device,List prms){
	Integer duration=Math.round(matchCastL(r9,prms[iZ])/d1000).toInteger()
	executePhysicalCommand(r9,device,'setVideoLength',duration)
	return lZ
}

/* virtual commands */

private Long vcmd_log(Map r9,device,List prms){
	String command=prms[iZ] ? sLi(prms,iZ):sBLK
	String message=sLi(prms,i1)
	log(message,r9,iN2,null,command.toLowerCase().trim(),true)
	return lZ
}

@CompileStatic
private Long vcmd_setState(Map r9,device,List prms){
	String value=prms[iZ]
	if(gtPOpt(r9,sMPS)){
		Map t0=mMs(r9,sST)
		t0[sNEW]=value
		if(!bIs(r9,sPSTNSTC)){
			Boolean t= sMs(t0,sOLD)!=sMs(t0,sNEW)
			r9[sPSTNSTC]=t
			if(t && isDbg(r9)) debug "Piston state changed",r9
		}
	}else error "Cannot set the piston state while in automatic mode. Please edit the piston settings to disable the automatic piston state if you want to manually control the state.",r9
	return lZ
}

private static Long vcmd_setTileColor(Map r9,device,List prms){
	Integer index=matchCastI(r9,prms[iZ])
	if(index<i1 || index>i16)return lZ
	String sIdx=index.toString()
	Map t0=mMs(r9,sST)
	t0[sC+sIdx]=(String)gtColor((String)prms[i1])?.hex
	t0[sB+sIdx]=(String)gtColor((String)prms[i2])?.hex
	t0[sF+sIdx]=!!prms[i3]
	return lZ
}

private static Long vcmd_setTileTitle(Map r9,device,List prms){ return helper_setTile(r9,sI,prms) }

private static Long vcmd_setTileText(Map r9,device,List prms){ return helper_setTile(r9,sT,prms) }

private static Long vcmd_setTileFooter(Map r9,device,List prms){ return helper_setTile(r9,sO,prms) }

private static Long vcmd_setTileOTitle(Map r9,device,List prms){ return helper_setTile(r9,sP,prms) }

private static Long helper_setTile(Map r9,String typ,List prms){
	Integer index=matchCastI(r9,prms[iZ])
	if(index<i1 || index>i16)return lZ
	r9[sST]["${typ}$index".toString()]=sLi(prms,i1)
	return lZ
}

private static Long vcmd_setTile(Map r9,device,List prms){
	Integer index=matchCastI(r9,prms[iZ])
	if(index<i1 || index>i16)return lZ
	String sIdx=index.toString()
	Map t0=mMs(r9,sST)
	t0[sI+sIdx]=sLi(prms,i1)
	t0[sT+sIdx]=sLi(prms,i2)
	t0[sO+sIdx]=sLi(prms,i3)
	t0[sC+sIdx]=(String)gtColor(sLi(prms,i4))?.hex
	t0[sB+sIdx]=(String)gtColor(sLi(prms,i5))?.hex
	t0[sF+sIdx]=!!prms[i6]
	return lZ
}

private static Long vcmd_clearTile(Map r9,device,List prms){
	Integer index=matchCastI(r9,prms[iZ])
	if(index<i1 || index>i16)return lZ
	String sIdx=index.toString()
	Map t0=mMs(r9,sST)
	t0.remove(sI+sIdx)
	t0.remove(sT+sIdx)
	t0.remove(sC+sIdx)
	t0.remove(sO+sIdx)
	t0.remove(sB+sIdx)
	t0.remove(sF+sIdx)
	t0.remove(sP+sIdx)
	return lZ
}

/* wrappers */
private Long vcmd_setLocationMode(Map r9,device,List prms){
	String mIdOrNm=sLi(prms,iZ)
	Map mode=fndMode(r9,mIdOrNm)
	if(mode) location.setMode(sMs(mode,sNM))
	else error "Error setting location mode. Mode '$mIdOrNm' does not exist.",r9
	return lZ
}

/* wrappers */
private Long vcmd_setAlarmSystemStatus(Map r9,device,List prms){
	String sIdOrNm=sLi(prms,iZ)
	Map vd=VirtualDevices()[sALRMSSTATUS]
	Map<String,String> options=(Map<String,String>)vd?.ac
	List<Map<String,String>> status=options?.find{ it.key==sIdOrNm || it.value==sIdOrNm }?.collect{ [(sID):it.key,(sNM):it.value] }

	if(status && status.size()!=iZ){
		String v= status[iZ][sID]
		String s; s= "Sending hsmSetArm $v"
		Map data; data= [:]
		if(v in ['armAway','armHome','armNight']){ // optional - the number of seconds of delay
			Integer del= prms.size()>i1 ? iLi(prms,i1) : iZ
			if(del>iZ){
				data= [seconds: del]
				s+= " with delay $del"
			}
		}
		sendLocationEvent((sNM):sHSMSARM,(sVAL):v,(sDATA):data)
		if(isDbg(r9)) debug s,r9
	} else error "Error setting HSM status. Status '$sIdOrNm' does not exist.",r9
	return lZ
}

// todo: need commands to arm / disarm monitoring rules; need ability to list rules

private Long vcmd_sendEmail(Map r9,device,List prms){
	Map<String,String> data=[
		(sI):sMs(r9,sID),
		(sN):gtAppN(),
		(sT):sLi(prms,iZ),
		(sS):sLi(prms,i1),
		(sM):sLi(prms,i2)
	]

	Map requestParams=[
		uri: 'https://api.webcore.co/email/send/'+sMs(r9,sLOCID),
		query: null,
		headers: [:],
		requestContentType: sAPPJSON,
		body: data,
		timeout:i20
	]

	try{
		asynchttpPost('ahttpRequestHandler',requestParams,[command:sSENDE,em:data])
		return 24000L
	}catch(all){
		error "Error sending email to ${data.t}: Unknown error",r9,iN2,all
	}
	return lZ
}

private static Long vcmd_noop(Map r9,device,List prms){
	return lZ
}

@CompileStatic
private static Long vcmd_wait(Map r9,device,List prms){
	return matchCastL(r9,prms[iZ])
}

private static Long vcmd_waitRandom(Map r9,device,List prms){
	Long min,max
	min=matchCastL(r9,prms[iZ])
	max=matchCastL(r9,prms[i1])
	if(max<min){
		Long t=max
		max=min
		min=t
	}
	return min+Math.round((max-min)*Math.random())
}

private Long vcmd_waitForTime(Map r9,device,List prms){
	Long time; time=(Long)cast(r9,(Long)cast(r9,prms[iZ],sTIME),sDTIME,sTIME)
	Long rightNow=wnow()
	time=pushTimeAhead(r9,time,rightNow)
	return time-rightNow
}

private Long vcmd_waitForDateTime(Map r9,device,List prms){
	Long time=(Long)cast(r9,prms[iZ],sDTIME)
	Long rightNow=wnow()
	return time>rightNow ? time-rightNow:lZ
}

private Long vcmd_setSwitch(Map r9,device,List prms){
	executePhysicalCommand(r9,device,bcast(r9,prms[iZ]) ? sON:sOFF)
	return lZ
}

private Long vcmd_toggle(Map r9,device,List prms){
	smart_toggle(r9,device)
	return lZ
}

private Long vcmd_toggleRandom(Map r9,device,List prms){
	Integer probability; probability=matchCastI(r9,prms.size()==i1 ? prms[iZ]:i50)
	if(probability<=iZ)probability=i50
	smart_toggle(r9,device, Math.round(d100*Math.random()).toInteger()<=probability)
	return lZ
}

@Field static final String sOPEN='open'
@Field static final String sCLOSE='close'
@Field static final String sCLOSED='closed'
@Field static List<List<String>> cls1=[]
private static void fill_cls(){
	if(cls1.size()==iZ)
		cls1=[
			// attr		value		cmd1	cmd2
			[sSWITCH,	sOFF,		sON,   sOFF],
			['door',	sCLOSED,  	sOPEN, sCLOSE],
			['windowShade',	sCLOSED,	sOPEN, sCLOSE],
			['windowBlind',	sCLOSED,	sOPEN, sCLOSE],
			['valve',	sCLOSED,	sOPEN, sCLOSE],
			['alarm',	sOFF,		'siren', sOFF],
			['lock',	'unlocked',	'lock', 'unlock'],
			['mute',	'unmuted',	'mute', 'unmute']
		]
}

private void smart_toggle(Map r9,device,Boolean prob=null){
	Boolean fnd; fnd=false
	String a0,c; c= 'toggle'
	if(wdeviceHascommand(device,c)){
		fnd=true
	}else{
		List da= (List)device?.getSupportedAttributes()
		def b
		for(List<String> a in cls1){
			a0=a[iZ]
			b= da.find{ (String)it.getName()==a0 }
			if(b){
				Boolean t= prob==null ? (String)getDeviceAttributeValue(r9,device,a0)==a[i1] : !!prob
				c= t ? a[i2]:a[i3]
				if(wdeviceHascommand(device,c)){
					fnd=true
					break
				}
			}
		}
	}
	if(fnd) executePhysicalCommand(r9,device,c)
	else warn "toggle not found for device $device",r9
}

private Long vcmd_toggleLevel(Map r9,device,List prms){
	Integer level=iLi(prms,iZ)
	executePhysicalCommand(r9,device,sSTLVL,(Integer)getDeviceAttributeValue(r9,device,sLVL)==level ? iZ:level)
	return lZ
}

@CompileStatic
private Long do_adjustLevel(Map r9,device,List prms,String attr,String cmd,Integer val=null,Boolean big=false){
	Integer arg; arg=val!=null ? val:matchCastI(r9,prms[iZ])
	Integer psz=prms.size()
	String mat=psz>i1 ? sLi(prms,i1):sNL
	if(ntMatSw(r9,mat,device,cmd))return lZ
	Long delay=psz>i2 ? (Long)prms[i2]:lZ
	arg=arg+matchCastI(r9,getDeviceAttributeValue(r9,device,attr))
	Integer low=big ? i1000:iZ
	Integer hi=big ? 30000:i100
	arg=(arg<low)? low:((arg>hi)? hi:arg)
	executePhysicalCommand(r9,device,cmd,arg,delay)
	if(delay>=lZ)return delay
	return lZ
}

private Long vcmd_adjustLevel(Map r9,device,List prms){ return do_adjustLevel(r9,device,prms,sLVL,sSTLVL) }

private Long vcmd_adjustInfraredLevel(Map r9,device,List prms){ return do_adjustLevel(r9,device,prms,sIFLVL,sSTIFLVL) }

private Long vcmd_adjustSaturation(Map r9,device,List prms){ return do_adjustLevel(r9,device,prms,sSATUR,sSTSATUR) }

private Long vcmd_adjustHue(Map r9,device,List prms){
	Integer hue=wcHue2DevHue(iLi(prms,iZ))
	return do_adjustLevel(r9,device,prms,sHUE,sSTHUE,hue)
}

private Long vcmd_adjustColorTemperature(Map r9,device,List prms){ return do_adjustLevel(r9,device,prms,sCLRTEMP,sSTCLRTEMP,null,true) }

@CompileStatic
private Long do_fadeLevel(Map r9,device,List prms,String attr,String cmd,Integer val=null,Integer val1=null,Boolean big=false){
	Integer startLevel,endLevel
	if(val==null){
		def d=prms[iZ]
		def d1=d!=null ? d:getDeviceAttributeValue(r9,device,attr)
		startLevel=matchCastI(r9,d1)
		endLevel=matchCastI(r9,prms[i1])
	}else{
		startLevel=val
		endLevel=val1
	}
	String mat=prms.size()>i3 ? sLi(prms,i3):sNL
	if(ntMatSw(r9,mat,device,cmd))return lZ
	Long duration=matchCastL(r9,prms[i2])
	Integer low=big ? i1000:iZ
	Integer hi=big ? 30000:i100
	startLevel=(startLevel<low)? low:((startLevel>hi)? hi:startLevel)
	endLevel=(endLevel<low)? low:((endLevel>hi)? hi:endLevel)
	return vcmd_internal_fade(r9,device,cmd,startLevel,endLevel,duration)
}

private Long vcmd_fadeLevel(Map r9,device,List prms){ return do_fadeLevel(r9,device,prms,sLVL,sSTLVL) }

private Long vcmd_fadeInfraredLevel(Map r9,device,List prms){ return do_fadeLevel(r9,device,prms,sIFLVL,sSTIFLVL) }

private Long vcmd_fadeSaturation(Map r9,device,List prms){ return do_fadeLevel(r9,device,prms,sSATUR,sSTSATUR) }

private Long vcmd_fadeHue(Map r9,device,List prms){
	Integer startLevel=prms[iZ]!=null ? wcHue2DevHue(iLi(prms,iZ)) : matchCastI(r9,getDeviceAttributeValue(r9,device,sHUE))
	Integer endLevel=wcHue2DevHue(iLi(prms,i1))
	return do_fadeLevel(r9,device,prms,sHUE,sSTHUE,startLevel,endLevel)
}

private Long vcmd_fadeColorTemperature(Map r9,device,List prms){
	return do_fadeLevel(r9,device,prms,sCLRTEMP,sSTCLRTEMP,null,null,true)
}

@CompileStatic
private Long vcmd_internal_fade(Map r9,device,String command,Integer startLevel,Integer endLevel,Long idur){
	Long duration=idur

	Long minInterval,interval
	minInterval=l500
	//this attempts to adjust for command delays ced
	Long ced=cedIs(r9)
	if(ced>lZ) minInterval=ced>minInterval ? ced:minInterval
	if(startLevel==endLevel || duration<minInterval){
		//if the fade is too fast, or not changing anything, go to the end level directly
		executePhysicalCommand(r9,device,command,endLevel)
		return lZ
	}
	Integer delta=endLevel-startLevel
	//the max number of steps we can do
	Integer steps; steps=delta>iZ ? delta:-delta
	//figure out the interval
	interval=Math.round((duration/steps).toDouble())
	if(interval<minInterval){
		//interval is too small adjust to do one change per minInterval
		steps=Math.floor((d1*duration/minInterval).toDouble()).toInteger()
		interval=Math.round((d1*duration/steps).toDouble())
	}
	String scheduleDevice=hashD(r9,device)
	executePhysicalCommand(r9,device,command,startLevel)
	Map jq=fillJQ(
		i1,
		steps,
		command,
		startLevel,
		(delta*d1/steps).toDouble(),
		interval,
		interval,
		sNL,
		null,
		lZ,
		scheduleDevice,
		command,
		endLevel,
		l500,
		sNL,
		null,
		lZ
	)
	Long wt=stRepeat(r9,jq)
	return wt+750L
}

private Long vcmd_emulatedFlash(Map r9,device,List prms){ vcmd_flash(r9,device,prms) }


static Map fillJQ(Integer s,Integer cy, String f1C, f1P, Double f1Padd, Long f1ID, Long f1D, String s2C,
		   List s2P, Long s2D, sDev, String l1C, l1P, Long l1D, String l2C, List l2P, Long l2D){
	return [
		s:s,
		cy:cy,
		f1C:f1C,
		f1P: f1P,
		f1ID:f1ID,
		f1Padd: f1Padd,
		f1D:f1D,
		s2C:s2C,
		s2P: s2P,
		s2D:s2D,
		sDev:sDev,
		l1C:l1C,
		l1P: l1P,
		l1D:l1D,
		l2C:l2C,
		l2P: l2P,
		l2D:l2D
	]
}

private Long vcmd_flash(Map r9,device,List prms){
	Long onDuration=matchCastL(r9,prms[iZ])
	Long offDuration=matchCastL(r9,prms[i1])
	//if the flash is too fast, ignore it
	if((onDuration+offDuration)<l500)return lZ

	String mat=prms.size()>i3 ? sLi(prms,i3):sNL
	String currentState=gtSwitch(r9,device)
	if(mat!=sNL && currentState!=mat)return lZ

	Integer cycles=matchCastI(r9,prms[i2])
	Boolean firstOn=currentState!=sON
	String firstCmd=firstOn ? sON:sOFF
	Long firstDuration=firstOn ? onDuration:offDuration
	String secondCommand=firstOn ? sOFF:sON
	Long secondDuration=firstOn ? offDuration:onDuration
	String scheduleDevice=hashD(r9,device)
	Map jq=fillJQ(
		i1,
		cycles,
		firstCmd,
		null,
		null,
		lZ,
		firstDuration,
		secondCommand,
		null,
		secondDuration,
		scheduleDevice,
		currentState,
		[],
		l500,
		sNL,
		null,
		lZ
	)
	Long wt=stRepeat(r9,jq)
	return wt+750L
}

/** return duration estimate */
@CompileStatic
private Long stRepeat(Map r9,Map jq){
	Integer start=iMsS(jq)
	Integer cycles=iMs(jq,'cy')
	//String fCmd=sMs(jq,'f1C')
	Long firstDuration,secondDuration,dur
	firstDuration=lMs(jq,'f1D')
	String sCmd=sMs(jq,'s2C')
	secondDuration=lMs(jq,'s2D')

	//this attempts to add command delays ced
	Long ced=cedIs(r9)
	if(ced>lZ){
		firstDuration=ced>firstDuration ? ced:firstDuration
		if(sCmd)secondDuration=ced>secondDuration ? ced:secondDuration
	}

	dur=lMs(jq,'f1ID')
	Integer i
	for(i=start;i<=cycles;i++){
		dur+=firstDuration
		if(sCmd)dur+=secondDuration
	}
	dur+=lMs(jq,'l1D')+lMs(jq,'l2D')
	runRepeat(r9,jq)
	return dur
}

@CompileStatic
void runRepeat(Map r9,Map ijq){
	Map jq=[:]+ijq

	String scheduleDevice=sMs(jq,'sDev')
	def device=getDevice(r9,scheduleDevice)
	if(device!=null){
		Integer start
		start=iMsS(jq)
		Integer cycles=iMs(jq,'cy')

		def p1; p1=jq.f1P
		Boolean doNotSend; doNotSend=false
		Double i=(Double)jq.f1Padd
		if(i!=null){
			Integer p=(Integer)p1
			Integer oldL=Math.round(p+i*(start-i1)).toInteger()
			Integer newL=Math.round(p+i*start).toInteger()
			p1=newL
			if(oldL==newL)doNotSend=true
		}

		Long dur
		dur=start==i1 ? lMs(jq,'f1ID'):lZ
		if(start<=cycles){
			if(!doNotSend){
				String fCmd=sMs(jq,'f1C')
				executePhysicalCommand(r9,device,fCmd,p1,dur,scheduleDevice,true)
				dur+= lMs(jq,'f1D')
			}
			String sCmd=sMs(jq,'s2C')
			if(sCmd){
				executePhysicalCommand(r9,device,sCmd,jq.s2P,dur,scheduleDevice,true)
				dur+= lMs(jq,'s2D')
			}
			start++
		}
		if(start>cycles){
			String c
			c=sMs(jq,'l1C')
			if(c){
				executePhysicalCommand(r9,device,c,jq.l1P,dur,scheduleDevice,true)
				dur+= lMs(jq,'l1D')
			}
			c=sMs(jq,'l2C')
			if(c){
				executePhysicalCommand(r9,device,c,jq.l2P,dur,scheduleDevice,true)
			}
		}else{
			jq.s=start
			qrunRepeat(r9,dur,jq)
		}
	}
}

@CompileStatic
void qrunRepeat(Map r9,Long dur,Map jq){
	//void executePhysicalCo iN3
	Map statement=mMs(r9,sCURACTN)
	Map task=mMs(r9,sCURTSK)

	List<Integer> cs=svCS(r9,statement)
	LinkedHashMap jq1
	jq1=([ // items to save for later requeues
		(sDLR):stmtNum(statement),
		(sCS):cs,
		(sTASK):stmtNum(task)
	]+jq) as LinkedHashMap
	String s= sMs(statement,sTCP)
	if(s) jq1+=[(sTCP):s]

	Map schedule=[
		(sT): Math.round(wnow()*d1+dur),
		(sS):stmtNum(statement),
		(sI):iN5,
		(sCS):cs,
		(sPS):svPS(statement),
		('jq'):jq1,
	]
	if(isTrc(r9))trace wakeS(r9,'Requesting a repeat task',schedule),r9
	spshSch(r9,schedule)
}

private Long vcmd_flashLevel(Map r9,device,List prms){
	Integer level1=matchCastI(r9,prms[iZ])
	Long duration1=matchCastL(r9,prms[i1])
	Integer level2=matchCastI(r9,prms[i2])
	Long duration2=matchCastL(r9,prms[i3])
	Integer cycles=matchCastI(r9,prms[i4])
	String mat=prms.size()>i5 ? sLi(prms,i5):sNL
	String currentState=gtSwitch(r9,device)
	if(mat!=sNL && currentState!=mat)return lZ
	//if the flash is too fast, ignore it
	if((duration1+duration2)<l500)return lZ
	Integer currentLevel=(Integer)getDeviceAttributeValue(r9,device,sLVL)
	String scheduleDevice=hashD(r9,device)
	Map jq=fillJQ(
		i1,
		cycles,
		sSTLVL,
		[level1],
		null,
		lZ,
		duration1,
		sSTLVL,
		[level2],
		duration2,
		scheduleDevice,
		sSTLVL,
		[currentLevel],
		l500,
		currentState,
		[],
		200L
	)
	Long wt=stRepeat(r9,jq)
	return wt+750L
}

private Long vcmd_flashColor(Map r9,device,List prms){
	Map color1=gtColor(sLi(prms,iZ))
	Long duration1=matchCastL(r9,prms[i1])
	Map color2=gtColor(sLi(prms,i2))
	Long duration2=matchCastL(r9,prms[i3])
	Integer cycles=matchCastI(r9,prms[i4])
	String mat=prms.size()>i5 ? sLi(prms,i5):sNL
	String currentState=gtSwitch(r9,device)
	if(mat!=sNL && currentState!=mat)return lZ
	//if the flash is too fast, ignore it
	if((duration1+duration2)<l500)return lZ
	String scheduleDevice=hashD(r9,device)
	Map jq=fillJQ(
		i1,
		cycles,
		sSTCLR,
		[color1],
		null,
		lZ,
		duration1,
		sSTCLR,
		[color2],
		duration2,
		scheduleDevice,
		currentState,
		[],
		l500,
		sNL,
		[],
		lZ
	)
	Long wt=stRepeat(r9,jq)
	return wt+750L
}

private Long vcmd_sendNotification(Map r9,device,List prms){
	String message="Hubitat does not support sendNotification "+sLi(prms,iZ)
	log(message,r9,iN2,null,sWARN,true)
	//sendNotificationEvent(message)
	return lZ
}

private Long vcmd_sendPushNotification(Map r9,device,List prms){
	String message; message=sLi(prms,iZ)
	String initP='initPush'
	String pd='pushDev'
	if(r9[initP]==null){
		r9[pd]=wgetPushDev()
		if(r9[pd]!=null) r9[initP]=true
	}
	if(bIs(r9,initP)){
		List t0= (List)r9[pd]
		try{
			t0*.deviceNotification(message)
		}catch(ignored){
			r9[initP]=null
		}
	}
	if(!bIs(r9,initP)){
		message="Default push device not set properly in webCoRE "+message
		error message,r9
	}
	return lZ
}

private Long vcmd_sendSMSNotification(Map r9,device,List prms){
	String msg; msg=sLi(prms,iZ)
	msg="HE SMS notifications are being removed,please convert to a notification device "+msg
	warn msg,r9
	return lZ
}

private Long vcmd_sendNotificationToContacts(Map r9,device,List prms){
	// HE does not have Contact Book; falling back onto PUSH notifications
	String message=sLi(prms,iZ)
	Boolean save=!!prms[i2]
	return vcmd_sendPushNotification(r9,device,[message,save])
}

@CompileStatic
private Long vcmd_setVariable(Map r9,device,List prms){
	String name=sLi(prms,iZ)
	def value=prms[i1]
	setVariable(r9,name,value)
	return lZ
}

private Long vcmd_executePiston(Map r9,device,List prms){
	String selfId=sMs(r9,sID)
	String pistonId=sLi(prms,iZ)
	List<String> arguments= prms[i1] ? (prms[i1] instanceof List ? (List<String>)prms[i1]:prms[i1].toString().tokenize(sCOMMA)).unique() : []
	Boolean wait; wait=prms.size()>i2 ? bcast(r9,prms[i2]):false
	String desc="webCoRE: Piston ${gtAppN()} requested execution of piston $pistonId".toString()
	Map data=[:]
	for(String argument in arguments) if(argument)data[argument]=oMv(getVariable(r9,argument))
	if(wait){ if(!wexecutePiston(pistonId,data,selfId)) error desc+" Piston not found",r9 }
	else sendExecuteEvt(pistonId,selfId,desc,data)
	return lZ
}

private Long vcmd_pausePiston(Map r9,device,List prms){
	String selfId=sMs(r9,sID)
	String pistonId=sLi(prms,iZ)
	if(!wpausePiston(pistonId,selfId)){
		String message="Piston not found "+pistonId
		error message,r9
	}
	return lZ
}

private Long vcmd_resumePiston(Map r9,device,List prms){
	String selfId=sMs(r9,sID)
	String pistonId=sLi(prms,iZ)
	if(!wresumePiston(pistonId,selfId)){
		String message="Piston not found "+pistonId
		error message,r9
	}
	return lZ
}

/* wrappers */
private Long vcmd_executeRule(Map r9,device,List prms){
	String ruleId=sLi(prms,iZ)
	String action=sLi(prms,i1)
	//Boolean wait=(prms.size()>i2)? bcast(r9,prms[i2]):false
	String ruleAction
	ruleAction=sNL
	if(action=="Run")ruleAction="runRuleAct"
	if(action=="Stop")ruleAction="stopRuleAct"
	if(action=="Pause")ruleAction="pauseRule"
	if(action=="Resume")ruleAction="resumeRule"
	if(action=="Evaluate")ruleAction="runRule"
	if(action=="Set Boolean True")ruleAction="setRuleBooleanTrue"
	if(action=="Set Boolean False")ruleAction="setRuleBooleanFalse"
	if(!ruleAction){
		String message="No Rule action found "+action
		error message,r9
	}else{
		Boolean sent; sent=false
		for(String ver in ['4.1','5.0']){
			List<Map> rules=RMUtils.getRuleList(ver ?: sNL)
			List myRule; myRule=[]
			for(rule in rules){
				List t0=rule.find{ hashId(r9,(String)it.key)==ruleId }.collect{(String)it.key}
				myRule+=t0
			}
			if(myRule){
				RMUtils.sendAction(myRule,ruleAction,gtAppN(),ver ?: sNL)
				sent=true
			}
		}
		if(!sent){
			String message="Rule not found "+ruleId
			error message,r9
		}
	}
	return lZ
}

private Long vcmd_setHSLColor(Map r9,device,List prms){
	Integer hue=wcHue2DevHue(iLi(prms,iZ))
	Integer saturation=iLi(prms,i1)
	Integer level=iLi(prms,i2)
	Map color=[
		(sHUE): hue,
		(sSATUR): saturation,
		(sLVL): level
	]
	Integer psz=prms.size()
	String mat=psz>i3 ? sLi(prms,i3):sNL
	if(ntMatSw(r9,mat,device,'setHSLColor'))return lZ
	Long delay=psz>i4 ? (Long)prms[i4]:lZ
	executePhysicalCommand(r9,device,sSTCLR,color,delay)
	if(delay>=lZ)return delay
	return lZ
}

private Long vcmd_wolRequest(Map r9,device,List prms){
	String mac; mac=sLi(prms,iZ)
	String secureCode=sLi(prms,i1)
	mac=mac.replace(sCLN,sBLK).replace(sMINUS,sBLK).replace(sDOT,sBLK).replace(sSPC,sBLK).toLowerCase()

	sendHubCommand(HubActionClass().newInstance(
		"wake on lan $mac".toString(),
		HubProtocolClass().LAN,
		null,
		secureCode ? [secureCode: secureCode]:[:]
	))
	return lZ
}

private Long vcmd_iftttMaker(Map r9,device,List prms){
	String key; key=sNL
	if(r9[sSETTINGS]==null){
		error "no settings",r9
	}else{
		key=(sMs(mMs(r9,sSETTINGS),'ifttt_url') ?: sBLK).trim().replace('https://',sBLK).replace('http://',sBLK).replace('maker.ifttt.com/use/',sBLK)
	}
	if(!key){
		error "Failed to send IFTTT event, because the IFTTT integration is not properly set up. Please visit Settings in your webCoRE dashboard and configure the IFTTT integration.",r9
		return lZ
	}
	String event=prms[iZ]
	Integer psz=prms.size()
	def v1=psz>i1 ? prms[i1]:sBLK
	def v2=psz>i2 ? prms[i2]:sBLK
	def v3=psz>i3 ? prms[i3]:sBLK
	Map<String,Object> body=[:]
	if(v1)body.value1=v1
	if(v2)body.value2=v2
	if(v3)body.value3=v3
	Map data=[
		t:event,
		p1:v1,
		p2:v2,
		p3:v3
	]
	Map requestParams=[
		uri: "https://maker.ifttt.com/trigger/${URLEncoder.encode(event,sUTF8)}/with/key/".toString()+key,
		requestContentType: sAPPJSON,
		body: body,
		timeout:i20
	]
	try{
		asynchttpPost('ahttpRequestHandler',requestParams,[command:sIFTTM,em: data])
		return 24000L
	}catch(all){
		error "Error iftttMaker to ${requestParams.uri} ${data.t}: ${data.p1}, ${data.p2}, ${data.p3}",r9,iN2,all
	}
	return lZ
}


private Long do_lifx(Map r9,String cmd,String path,Map body,Long duration,String c){
	String token=mMs(r9,sSETTINGS)?.lifx_token
	if(!token){
		error "Sorry, enable the LIFX integration in the dashboard's Settings section before trying to execute a LIFX operation.",r9
		return lZ
	}
	Map requestParams=[
		uri: "https://api.lifx.com",
		path: path,
		headers: ["Authorization": "Bearer $token"],
		requestContentType: sAPPJSON,
		timeout:i10,
		body: body
	]
	try{
		if(isDbg(r9))debug "Sending lifx ${c} web request to: $path",r9
		callHttp("asynchttp${cmd}".toString(),'ahttpRequestHandler',requestParams,[command:sLIFX,em: [(sT):c]])
		Long ldur=duration ? Math.round(duration * d1000):lZ
		Long l=11000L
		return Math.max(ldur,l)
	}catch(all){
		error "Error while activating LIFX $c:",r9,iN2,all
	}
	return lZ
}

private Long vcmd_lifxScene(Map r9,device,List prms){
	String sceneId; sceneId=sLi(prms,iZ)
	Long duration=prms.size()>i1 ? Math.round( matchCastL(r9,prms[i1]) / d1000):lZ
	Map scn=(Map)mMs(r9,sLIFX)?.scenes
	if(!scn){
		error "Sorry, there seems to be no available LIFX scenes, please ensure the LIFX integration is working.",r9
		return lZ
	}
	sceneId=scn.find{ (String)it.key==sceneId || (String)it.value==sceneId }?.key
	if(!sceneId){
		error "Sorry, could not find the specified LIFX scene.",r9
		return lZ
	}
	String path="/v1/scenes/scene_id:${sceneId}/activate"
	Map body=duration ? [(sDURATION): duration]:null
	return do_lifx(r9,'Put',path,body,duration,'scene')
}

private Long lifxErr(Map r9){
	error "Sorry, could not find the specified LIFX selector.",r9
	return lZ
}

private static String getLifxSelector(Map r9,String selector){
	String selectorId=sBLK
	if(selector==sALL)return selector
	Integer i; i=iZ
	List<String> a=['scene_',sBLK,'group_','location_']
	for(String m in ['scenes','lights','groups','locations']){
		String obj=mMs(mMs(r9,sLIFX),m)?.find{ it.key==selector || it.value==selector }?.key
		if(obj)return "${a[i]}id:${obj}".toString()
		i+=i1
	}
	return selectorId
}

private Long vcmd_lifxState(Map r9,device,List prms){
	String selector=getLifxSelector(r9,sLi(prms,iZ))
	if(!selector)return lifxErr(r9)
	String power=sLi(prms,i1)
	Map color=gtColor(sLi(prms,i2))
	Integer level=iLi(prms,i3)
	Integer infrared=iLi(prms,i4)
	Long duration=Math.round( matchCastL(r9,prms[i5]) / d1000 )
	String path="/v1/lights/${selector}/state"
	Map body= [:]+(power ? ([power: power]) : [:])+(color ? ([color: color.hex]) : [:])+(level!=null ? ([brightness: level / d100]) : [:])+(infrared!=null ? [infrared: infrared] : [:])+(duration ? [(sDURATION): duration] : [:])
	return do_lifx(r9,'Put',path,body,duration,sST)
}

private Long vcmd_lifxToggle(Map r9,device,List prms){
	String selector=getLifxSelector(r9,sLi(prms,iZ))
	if(!selector)return lifxErr(r9)
	Long duration=Math.round( matchCastL(r9,prms[i1]) / d1000 )
	String path="/v1/lights/${selector}/toggle"
	Map body= [:]+(duration ? [(sDURATION): duration]:[:])
	return do_lifx(r9,'Post',path,body,duration,'toggle')
}

private Long lifxBreathePulse(Map r9,device,List prms,String meth){
	String selector=getLifxSelector(r9,sLi(prms,iZ))
	if(!selector)return lifxErr(r9)
	Map color=gtColor(sLi(prms,i1))
	Map fromColor= prms[i2]==null ? null:gtColor(sLi(prms,i2))
	Long period= prms[i3]==null ? null:Math.round( matchCastL(r9,prms[i3]) / d1000)
	Integer cycles=iLi(prms,i4)

	Integer idx; idx=i5
	Map peakres; peakres=[:]
	if(meth=='breathe'){
		Integer peak=iLi(prms,idx)
		peakres= peak!=null ? [peak: peak / i100] : [:]
		idx++
	}
	Boolean powerOn= prms[idx]==null ? null:bcast(r9,prms[idx])
	idx++
	Boolean persist= prms[idx]==null ? null:bcast(r9,prms[idx])
	String path="/v1/lights/${selector}/effects/"+meth
	Map body= [color: color.hex]+(fromColor ? ([from_color: fromColor.hex]) : [:])+(period!=null ? ([period: period]) : [:])+(cycles ? ([cycles: cycles]) : [:])+(powerOn!=null ? ([power_on: powerOn]) : [:])+(persist!=null ? ([persist:persist]) : [:])+peakres
	Long ldur=Math.round( (period ? period:i1) * (cycles ? cycles:i1) )
	return do_lifx(r9,'Post',path,body,ldur,meth)
}

private Long vcmd_lifxBreathe(Map r9,device,List prms){
	return lifxBreathePulse(r9,device,prms,'breathe')
}

private Long vcmd_lifxPulse(Map r9,device,List prms){
	return lifxBreathePulse(r9,device,prms,'pulse')
}

@CompileStatic
private Long vcmd_httpRequest(Map r9,device,List prms){
	String uri
	uri=(sLi(prms,iZ))?.replace(sSPC,"%20") // may be null
	if(!uri){
		error "Error executing external web request:no URI",r9
		return lZ
	}
	String method=sLi(prms,i1)
	Boolean useQryS= (method in [sGET,sDELETE,sHEAD])
	String reqBodyT=sLi(prms,i2)
	def variables=prms[i3]
	String auth,cntntT,requestBody
	auth=sNL
	cntntT=sNL
	requestBody=sNL
	if(prms.size()==i5){
		auth=sLi(prms,i4)
	}else if(prms.size()==i7){
		requestBody=sLi(prms,i4)
		cntntT=sLi(prms,i5) ?: 'text/plain'
		auth=sLi(prms,i6)
	}
	String reqCntntT=(method==sGET || reqBodyT=='FORM')? sAPPFORM : (reqBodyT=='JSON')? sAPPJSON:cntntT

	String protocol,userPart,func
	protocol="https"
	userPart=sBLK
	String[] uriParts
	String mat='://'
	uriParts=uri.split(mat)
	if(uriParts.size()>i2){
		String s= uri.substring(iZ,uri.size()>i15 ? i15: uri.size())
		if(!s.contains(mat)){
			uriParts=[]
			uriParts[iZ]=uri
		}else{
			List<String> s1; s1=[]
			String[] n1; n1=[sA,sB]
			Integer i
			for(i=i1;i<uriParts.size();i++){
				s1[i-i1]=uriParts[i]
			}
			n1[iZ]=uriParts[iZ]
			n1[i1]=s1.join(mat)
			uriParts=n1
		}
		if(isEric(r9))debug "found complex s: $s uri: $uri parts: $uriParts",r9

		//warn "Invalid URI for web request:$uri",r9
		//return lZ
	}
	if(uriParts.size()==i2){
		//remove the httpX:// from the uri
		protocol=uriParts[iZ].toLowerCase()
		uri=uriParts[i1]
	}
	//support for user:pass@IP (remove from URI for logging purposes, and other uri checks later)
	if(uri.contains(sAT)){
		String[] uriSubParts= uri.split(sAT as String)
		userPart=uriSubParts[iZ]+sAT
		uri=uriSubParts[i1]
	}

	Boolean internal; internal= uri.startsWith('10.') || uri.startsWith('192.168.')
	if(!internal && uri.startsWith('172.')){ //check for the 172.16.x.x/12 class
		String b; b=uri.tokenize(sDOT)[i1] //substring(4,6)
		if(b.isInteger()){
			Integer bi=b.toInteger()
			internal=(bi>=i16 && bi<=31)
		}
	}
/*
	String[] split_path
	split_path = uri.split('/')
	uri = protocol+mat+userPart+split_path[iZ]
	String path; path = '/'
	Integer i
	for(i=i1; i<split_path.size(); i++){
		if(i>i1) path += '/'
		path += split_path[i]
	}
	if(isEric(r9))debug "http uri: $uri",r9
	if(isEric(r9))debug "http path: $path",r9
*/
	def data; data=null
	if(reqBodyT=='CUSTOM' && !useQryS){
		data=requestBody
	}else if(variables instanceof List){
		for(String variable in ((List)variables).findAll{ !!it }){
			data= data ?: [:]
			data[variable]=oMv(getVariable(r9,variable))
		}
	}
	if(!useQryS && reqCntntT==sAPPFORM && data && data instanceof Map){
		String sdata
		sdata=((Map)data).collect{ k,v -> encodeURIComponent(k)+'='+encodeURIComponent(v) }.join(sAMP)
		data=sdata
	}

	Map headers; headers=[:]
	headers += auth ? (Map)(stJson(auth)? (new JsonSlurper().parseText(auth)):[Authorization: auth]):[:]
	headers += ['Accept-Encoding': 'gzip,deflate']

	try{
		Map requestParams=[
			uri: protocol+mat+userPart+uri,
			//uri: uri,
			//path: path,
			query: useQryS ? data:null,
			headers: headers,
			(sCONTENTT): '*/*',
			requestContentType: reqCntntT,
			body: !useQryS ? data:null,
			ignoreSSLIssues: internal || !!gtPOpt(r9,sISH), // ignore ssl issues (protocol=='https' && internal)
			followRedirects: true,
			timeout:i20
		]
		func=sBLK
		switch(method){
			case sGET:
				func='asynchttpGet'
				break
			case 'POST':
				func='asynchttpPost'
				break
			case 'PUT':
				func='asynchttpPut'
				break
			case sDELETE:
				func='asynchttpDelete'
				break
			case sHEAD:
				func='asynchttpHead'
				break
		}
		if(isEric(r9))debug "http request $requestParams",r9
		if(isDbg(r9))debug "Sending ${func} web request to: $uri",r9
		if(func!=sBLK){
			callHttp(func,'ahttpRequestHandler',requestParams,[command:sHTTPR])
			return 24000L
		}
	}catch(all){
		error "Error executing external web request:",r9,iN2,all
	}
	return lZ
}

private void callHttp(String func,String callBack,Map rprms, Map cbdata){
	(void)"$func"(callBack,rprms,cbdata)
}

void ahttpRequestHandler(resp,Map callbackData){
	Boolean binary; binary=false
	Map t0=resp.getHeaders()
	String t1=t0!=null ? sMs(t0,'Content-Type'):sNL
	String mediaType; mediaType=t1 ? t1.toLowerCase()?.tokenize(';')[iZ] :sNL
	switch(mediaType){
		case 'image/jpeg':
		case 'image/png':
		case 'image/gif':
			binary=true
	}
	def data,json; data=[:]; json=[:]
	Map setRtData; setRtData=[:]
	String callBackC=(String)callbackData?.command
	Integer rCode; rCode=(Integer)resp.status

	String erMsg; erMsg=sNL
	if(resp.hasError()){
		erMsg=" Response Status: ${resp.status} error Message: ${resp.getErrorMessage()}".toString()
		if(!rCode) rCode=i500
	}
	Boolean respOk=(rCode>=i200 && rCode<i300)
	Boolean respRedir=(rCode>=i300 && rCode<i400)

	Map em=(Map)callbackData?.em
	switch(callBackC){
		case sHTTPR:
			if(rCode==i204){ // no content
				mediaType=sBLK
			}else{
				try{
					//if((respOk || respRedir || rCode==i401) && resp.data) {
					if(respOk || respRedir || rCode==i401) {
						if(!binary){
							data=resp.data
							if(eric() && ((String)gtSetting(sLOGNG))?.toInteger()>i2) debug "http response $mediaType $rCode ${data} $t0",null
							if(data!=null && !(data instanceof Map) && !(data instanceof List)){
								def ndata=parseMyResp(data,mediaType)
								if(ndata!=null)
									data=ndata
							}
						}else{
							if(resp.data!=null && resp.data instanceof java.io.ByteArrayInputStream){
								setRtData[sMEDIATYPE]=mediaType
								setRtData[sMEDIADATA]=((String)resp.data).decodeBase64() // HE binary data is b64encoded resp.data.getBytes()
							}
						}
					}else {
						if(!rCode) rCode=i500
						erMsg = "http error rCode $rCode " + erMsg
					}
				}catch(all){
					erMsg= erMsg ?: " Response Status: ${resp.status} exception Message: ${all}".toString()
					erMsg= 'http error'+erMsg
					if(!rCode) rCode=i500
				}
			}
			break
		case sLIFX:
			if(!respOk) erMsg="lifx Error lifx sending ${em?.t}".toString()+erMsg
			break
		case sSENDE:
			String msg; msg='Unknown error'
			Boolean success; success=false
			if(respOk){
				data=resp.getJson()
				if(data!=null){
					if(sMs(data,sRESULT)=='OK') success=true
					else msg=sMs(data,sRESULT).replace('ERROR ',sBLK)
				}
			}
			if(!success) erMsg="Error sending email to ${em?.t}: ${msg}".toString()
			break
		case sIFTTM:
			if(!respOk) erMsg="ifttt Error iftttMaker to ${em?.t}: ${em?.p1},${em?.p2},${em?.p3} ".toString()+erMsg
			break
		case sSTOREM:
			String mediaId,mediaUrl
			mediaId=sNL; mediaUrl=sNL
			if(respOk){
				data=resp.getJson()
				if(sMs(data,sRESULT)=='OK' && data.url){
					mediaId=sMs(data,sID)
					mediaUrl=sMs(data,'url')
				}else if(sMs(data,'message')) erMsg=sSTOREM+" Error storing media item: $data.message"+erMsg
				data=null
			}else erMsg=sSTOREM+erMsg
			setRtData=[(sMEDIAID):mediaId,(sMEDIAURL):mediaUrl]
	}

	if(erMsg!=sNL) setRtData[sHTTPERR]=erMsg

	handleEvents([(sDATE):new Date(),(sDEV):gtLocation(),(sNM):sASYNCREP,(sVAL):callBackC,(sCONTENTT):mediaType,(sRESPDATA):data,(sJSOND):json,(sRESPCODE):rCode,(sSRTDATA):setRtData])
}

private parseMyResp(aa,String mediaType=sNL){
	def ret
	ret=null
	if(aa instanceof String || aa instanceof GString){
		String a=aa.toString() //.trim()
		Boolean expectJson= mediaType ? mediaType.contains(sJSON):false
		try{
			if(stJson(a)){
				ret=(LinkedHashMap)new JsonSlurper().parseText(a)
			}else if(stJson1(a)){
				ret=(List)new JsonSlurper().parseText(a)
			}else if(expectJson || (mediaType in ['application/octet-stream'] && a.size()%i4==iZ) ){ // HE can return data Base64
				String dec=new String(a.decodeBase64())
				if(dec!=sNL){
					def t0=parseMyResp(dec,sBLK)
					ret=t0==null ? dec:t0
				}
			}
		}catch(ignored){}
	}
	return ret
}

private Map securityLogin(String u, String p){
	Boolean res; res=false
	String cookie; cookie=sNL
	try{
		Map params= [
			uri: 'http://127.0.0.1:8080',
			path: "/login",
			query: [loginRedirect: "/"],
			body: [
				username: u,
				password: p,
				submit: "Login"
			],
			textParser: true,
			ignoreSSLIssues: true
		]
		httpPost(params){ resp ->
			if(resp.data?.text?.contains("The login information you supplied was incorrect."))
				res= false
			else{
				String[] resu= ((String)resp?.headers?.'Set-Cookie')?.split(';')
				cookie= resu.size() ? resu[0] : sNL
				res= true
			}
		}
	}catch (e){
		doLog(sERROR,"Error logging in: ${e}")
	}
	return [(sRESULT): res, cookie: cookie]
}

// used to avoid blowing stack
@Field static Map<String,String> readTmpFLD=[:]
@Field static Map<String,byte[]> readTmpBFLD=[:]
@Field static Map<String,String> readDataFLD=[:]
@Field static Map<String,List<Map>> fuelDataFLD=[:]

private static void clearReadFLDs(Map r9){
	String pNm=sMs(r9,snId)
	readDataFLD.put(pNm,sNL); readTmpFLD.put(pNm,sNL); readTmpBFLD.put(pNm,null)
	fuelDataFLD.put(pNm,[])
}

@Field static String minFwVersion="2.3.4.132"

private Boolean readFile(Map r9,List prms,Boolean data){
	String name=sLi(prms,iZ)
	String user=sLi(prms,i1)
	String pass=sLi(prms,i2)
	String pNm=sMs(r9,snId)

	if(data)readDataFLD[pNm]=sBLK else readTmpFLD[pNm]=sBLK

	Boolean res; res=false
	Boolean fwOk= ((String)location.hub.firmwareVersionString >= minFwVersion)
	try{
		if(fwOk){
			readTmpBFLD[pNm]=null
			readTmpBFLD[pNm]= (byte[])downloadHubFile(name)
			if(readTmpBFLD[pNm].size()){
				if(data) readDataFLD[pNm]=new String(readTmpBFLD[pNm])
				else readTmpFLD[pNm]=new String(readTmpBFLD[pNm])
				readTmpBFLD[pNm]=null
			}
			res=true

		}else{
			String cookie; cookie=sNL
			if(user && pass) cookie=securityLogin(user,pass).cookie
			String uri= "http://${location.hub.localIP}:8080/local/${name}".toString()

			Map params=[
				uri: uri,
				(sCONTENTT): "text/plain; charset=UTF-8",
				textParser: true,
				headers: ["Cookie": cookie, "Accept": 'application/octet-stream']
			]

			httpGet(params){ resp ->
				if(resp.status==i200 && resp.data){
					Integer i
					char c
					i=resp.data.read()
					while(i!=iN1){
						c=(char)i
						if(data) readDataFLD[pNm]+=c
						else readTmpFLD[pNm]+=c
						i=resp.data.read()
					}
					res=true
					//doLog(sWARN,"pNm: ${pNm} data: ${data} file: ${readDataFLD[pNm]}")
				}else{
					error "Read Response status $resp.status",r9
				}
			}
		}

		if(res) return true
	}catch(e){
		String s="Error reading file $name: "
		if( isFNF(r9,e) ){
			error s+"Not found",r9
		}else error s,r9,iN2,e
		readTmpBFLD[pNm]=null
	}
	if(data)readDataFLD[pNm]=sNL else readTmpFLD[pNm]=sNL
	return false
}

private Long vcmd_readFile(Map r9,device,List prms){
	readFile(r9,prms,true)
	return lZ
}

Boolean isFNF(Map r9,Exception ex){
	if(ex instanceof java.nio.file.NoSuchFileException) return true
	String file=(String)ex.message
	if(isEric(r9))doLog(sINFO,"isFNF ($file)")
	return file.contains("Not Found")
}

private Long vcmd_appendFile(Map r9,device,List prms){
	String name=sLi(prms,iZ)
	String user=sLi(prms,i2)
	String pass=sLi(prms,i3)
	String pNm=sMs(r9,snId)
	Boolean ws
	try{
		if(readFile(r9,[name,user,pass],false)){
			Integer sz=readTmpFLD[pNm].length()
			if(sz<=iZ) readTmpFLD[pNm]=sSPC
			readTmpFLD[pNm]+=sLi(prms,i1)
			writeFile(r9,[name,readTmpFLD[pNm],sNL,sNL])
		}else{
			ws=writeFile(r9,[name,sLi(prms,i1),sNL,sNL])
			if(eric())doLog(sINFO,"Append FNF write Status: $ws")
		}
	}catch(e){
		if( isFNF(r9,e) ){
			ws=writeFile(r9,[name,sLi(prms,i1),sNL,sNL])
			if(eric())doLog(sINFO,"Append FNF write Status: $ws")
		}else{
			error "Error appending file $name: ${e}",r9,iN2,e
		}
	}
	readTmpFLD[pNm]=sNL
	return lZ
}

private Boolean fileExists(Map r9,String name){
	Boolean res; res=false
	Boolean fwOk= ((String)location.hub.firmwareVersionString >= minFwVersion)
	String pNm=sMs(r9,snId)
	try{
		if(fwOk){
			readTmpBFLD[pNm]= (byte[])downloadHubFile(name)
			res= true
		}else{
			String uri="http://${location.hub.localIP}:8080/local/${name}".toString()
			Map params=[uri: uri]

			httpGet(params){ resp ->
				Boolean b= (resp.status==i200)
				res=b
			}
		}
	}catch(e){
		if( !isFNF(r9,e) )
			error "Error file exists $name: ",r9,iN2,e
		readTmpBFLD[pNm]=null
	}
	if(eric())doLog(sINFO,"File Exists $name: $res")
	return res
}

private Boolean writeFile(Map r9,List prms){
	String name=sLi(prms,iZ)
	//String user=sLi(prms,i2)
	//String pass=sLi(prms,i3)

	String pNm=sMs(r9,snId)
	Boolean fwOk= ((String)location.hub.firmwareVersionString >= minFwVersion)
	Boolean res; res=false
	try{
		if(fwOk){
			readTmpBFLD[pNm]= sLi(prms,i1).getBytes()
			uploadHubFile(name, readTmpBFLD[pNm])
			readTmpBFLD[pNm]=null
			res=true

		}else{
			Date d=new Date()
			String encodedString= "thebearmay$d".bytes.encodeBase64().toString()
			Map params= [
				uri		: 'http://127.0.0.1:8080',
				path	: '/hub/fileManager/upload',
				query	: ['folder': '/'],
				headers	: ['Content-Type': "multipart/form-data; boundary=$encodedString"],
				body	: """--${encodedString}
Content-Disposition: form-data; name="uploadFile"; filename="${name}"
contentType: "text/plain; charset=UTF-8"

${sLi(prms,i1)}

--${encodedString}
Content-Disposition: form-data; name="folder"


--${encodedString}--""",
				timeout	: i300,
				ignoreSSLIssues: true
			]
			//data=sNL
			httpPost(params){ resp ->
				if(resp.status!=i200){
					error "Write Response status $resp.status",r9
				} else res=true
			}
		}

		if(res) return true

	}catch(e){
		error "Error writing file $name: ${e}",r9,iN2,e
		readTmpBFLD[pNm]=null
	}
	return false
}

private Long vcmd_writeFile(Map r9,device,List prms){
	writeFile(r9,prms)
	return lZ
}

private Boolean deleteFile(Map r9,List prms){
	String fName= sLi(prms,iZ)

	Boolean fwOk= ((String)location.hub.firmwareVersionString >= minFwVersion)
	Boolean res; res=false
	try{
		if(fwOk){
			deleteHubFile(fName)
			res=true
		}else{
			String bodyText=JsonOutput.toJson(name:"$fName",type:"file")
			Map params= [
				uri: "http://127.0.0.1:8080",
				path: "/hub/fileManager/delete",
				contentType:'text/plain',
				requestContentType: sAPPJSON,
				body: bodyText
			]

			httpPost(params){ resp ->
				if(resp.status!=i200){
					error "Delete Response status $resp.status",r9
				} else res=true
			}
		}
		if(res)	return true

	}catch(e){
		error "Error deleting file $fName: ${e}",r9,iN2,e
	}
	return false
}

private Long vcmd_deleteFile(Map r9,device,List prms){
	deleteFile(r9,prms)
	return lZ
}

private static Map canisterMap(Map r9,String c,String n,s=null,d=null){
	Map req=[(sC):c,(sN):n,(sS):s,(sD):d,(sI):sMs(r9,sINSTID)]
	req
}

private Long vcmd_readFuelStream(Map r9,device,List prms){
	String canister=sLi(prms,iZ)
	String name=sLi(prms,i1)

	Map req=canisterMap(r9,canister,name)
	String pNm=sMs(r9,snId)
	fuelDataFLD[pNm]=[]
	if(bIs(r9,sUSELFUELS) && name!=sNL) fuelDataFLD[pNm]=(List)parent.readFuelStream(req) // store in $fuel
	return lZ
}

private Long vcmd_writeFuelStream(Map r9,device,List prms){
	String canister=sLi(prms,iZ)
	String name=sLi(prms,i1)
	//def data=prms[i2]
	// blow the stack??
	def source=prms[i3]

	Map req=canisterMap(r9,canister,name,source,prms[i2])
	if(bIs(r9,sUSELFUELS) && name!=sNL) parent.writeFuelStream(req)
	return lZ
}

private Long vcmd_clearFuelStream(Map r9,device,List prms){
	String canister=sLi(prms,iZ)
	String name=sLi(prms,i1)
	def source=prms[i2]

	Map req=canisterMap(r9,canister,name,source)
	if(bIs(r9,sUSELFUELS) && name!=sNL) parent.clearFuelStream(req)
	return lZ
}
/* wrappers */
private Long vcmd_writeToFuelStream(Map r9,device,List prms){
	String canister=sLi(prms,iZ)
	String name=sLi(prms,i1)
	def data=prms[i2]
	def source=prms[i3]

	Map req=canisterMap(r9,canister,name,source,data)
	if(bIs(r9,sUSELFUELS) && name!=sNL) parent.writeToFuelStream(req)
	else{
		Map requestParams=[
			uri: "https://api-"+sMs(r9,sREGION)+'-'+sMs(r9,sINSTID)[i32]+".webcore.co:9247",
			path: "/fuelStream/write",
			headers: ['ST': sMs(r9,sINSTID)],
			body: req,
			(sCONTENTT): sAPPJSON,
			requestContentType: sAPPJSON,
			timeout:i20
		]
		asynchttpPut('asyncFuel',requestParams,[bbb:iZ])
	}
	return lZ
}

void asyncFuel(response,data){
	if(response.status!=i200) error "Error storing fuel stream: $response?.data?.message",null
}

private Long vcmd_storeMedia(Map r9,device,List prms){
	if(!r9[sMEDIADATA] || !r9[sMEDIATYPE] || ((byte[])r9[sMEDIADATA]).size()<=iZ){
		error 'No media is available to store; operation aborted.',r9
		return lZ
	}
	String data=new String((byte[])r9[sMEDIADATA],'ISO_8859_1')
	Map requestParams=[
		uri: "https://api-"+sMs(r9,sREGION)+'-'+sMs(r9,sINSTID)[i32]+".webcore.co:9247",
		path: "/media/store",
		headers: [
			'ST':sMs(r9,sINSTID),
			'media-type': sMs(r9,sMEDIATYPE)
		],
		body: data,
		requestContentType: sMs(r9,sMEDIATYPE),
		timeout:i20
	]
	asynchttpPut('asyncRequestHandler',requestParams,[command:sSTOREM])
	return 24000L
}

private String canisterS(Map r9,device,List prms){ return (prms.size()>i1 ? scast(r9,prms[i1])+sCLN:sBLK)+hashD(r9,device)+sCLN }

private Long vcmd_saveStateLocally(Map r9,device,List prms,Boolean global=false){
	List<String> attributes=scast(r9,prms[iZ]).tokenize(sCOMMA)
	String canister=canisterS(r9,device,prms)
	Boolean overwrite=!(prms.size()>i2 ? bcast(r9,prms[i2]):false)
	if(global && !bIs(r9,sINITGS)){
		r9[sGSTORE]=wgetGStore()
		r9[sINITGS]=true
	}
	for(String attr in attributes){
		String n=canister+attr
		def value; value=getDeviceAttributeValue(r9,device,attr,true)
		if(attr==sHUE && value!=null && value!=sBLK) value=devHue2WcHue(value as Integer)
		def curVal= global ? mMs(r9,sGSTORE)[n] : mMs(r9,sSTORE)[n]
		String msg; msg=sBLK
		if(isEric(r9) || isTrc(r9))msg=" ${gtLbl(device)} $attr ($value) to ${global ? 'global': 'local'} store $n curVal: $curVal overwrite: $overwrite"
		if(overwrite || curVal==null){
			if(global){
				r9[sGSTORE][n]=value
				LinkedHashMap cache= (LinkedHashMap)r9[sGVSTOREC] ?: [:] as LinkedHashMap
				cache[n]=value
				r9[sGVSTOREC]=cache
			}else r9[sSTORE][n]=value
			if(isEric(r9))doLog(sINFO, 'stored'+msg)
		}else
			if(isTrc(r9)) warn 'Could not store'+msg,r9
	}
	return lZ
}

private Long vcmd_saveStateGlobally(Map r9,device,List prms){ return vcmd_saveStateLocally(r9,device,prms,true) }

// attr -> (v -> cmdName) first-wins; attr -> cmdName (v==null) last-wins; v -> cmdName last-wins
@Field static Map<String,Map<String,String>> physExactFLD=[:]
@Field static Map<String,String> physFuzzyAttrFLD=[:]
@Field static Map<String,String> physFuzzyValFLD=[:]

private static void buildPhysLookups(){
	Map<String,Map<String,String>> byAttrVal=[:]
	Map<String,String> byAttr=[:]
	Map<String,String> byVal=[:]
	for(Map.Entry<String,Map> entry in PhysicalCommands()){
		String k=(String)entry.key
		Map cv=(Map)entry.value
		String a=sMa(cv)
		String v=(String)cv.v
		if(a && v==null){
			byAttr[a]=k
		} else if(v!=null){
			if(a){
				if(!byAttrVal[a]) byAttrVal[a]=[:]
				if(!((Map)byAttrVal[a]).containsKey(v)) ((Map)byAttrVal[a])[v]=k
			}
			byVal[v]=k
		}
	}
	physExactFLD=byAttrVal
	physFuzzyAttrFLD=byAttr
	physFuzzyValFLD=byVal
}

private Long vcmd_loadStateLocally(Map r9,device,List prms,Boolean global=false){
	List<String> attributes=scast(r9,prms[iZ]).tokenize(sCOMMA)
	String canister=canisterS(r9,device,prms)
	Boolean empty=prms.size()>i2 ? bcast(r9,prms[i2]):false

	Map svd=[:]
	List<String> newattrs=[]
	List vals=[]

	if(global && !bIs(r9,sINITGS)){
		r9[sGSTORE]=wgetGStore()
		r9[sINITGS]=true
	}
	for(String attr in attributes){
		String n=canister+attr
		def value; value=global ? mMs(r9,sGSTORE)[n]: mMs(r9,sSTORE)[n]
		if(empty){
			if(global){
				mMs(r9,sGSTORE).remove(n)
				Map cache=mMs(r9,sGVSTOREC) ?: [:]
				cache[n]=null
				r9[sGVSTOREC]=cache
			}else mMs(r9,sSTORE).remove(n)
		}
		if(value==null){
			if(isTrc(r9))warn "Could not load ${gtLbl(device)} $attr ($value) from ${global ? 'global': 'local'} store $n",r9
			continue
		}

		if(attr==sHUE && value!=sBLK) value=devHue2WcHue(value as Integer)
		if(attr in [sSWITCH,sLVL,sSATUR,sHUE,sCLRTEMP]) svd[attr]=value
		else{
			newattrs.push(attr)
			vals.push(value)
		}
	}

	Boolean lg=isDbg(r9)
	String msg='loadState '
	if(isEric(r9)&& svd)debug msg+"${svd}",r9
	Boolean wOn= svd.containsKey(sSWITCH) ? sMs(svd,sSWITCH)==sON :null
	Boolean isOn; isOn= svd.containsKey(sSWITCH) ? gtSwitch(r9,device)==sON :null
	Boolean chgHSL= svd.containsKey(sLVL) && svd.containsKey(sSATUR) && svd.containsKey(sHUE)
	Boolean chgLvl= !chgHSL && svd.containsKey(sLVL) ? true : null
	Boolean chgCtemp= svd.containsKey(sCLRTEMP) ?: null
	String scheduleDevice= hashD(r9,device)
	Long del; del=lZ
	if((chgLvl || chgCtemp) && isOn==false){
		if(lg)debug "Turning on ${gtLbl(device)}",r9
		executePhysicalCommand(r9,device,sON)
		isOn=true
		del=1500L
	}
	if(chgHSL){
		if(lg)debug "Restoring setColor ${gtLbl(device)}",r9
		executePhysicalCommand(r9,device,sSTCLR,[(sHUE):svd[sHUE],(sSATUR):svd[sSATUR],(sLVL):svd[sLVL]],del,scheduleDevice)
		del+=l100
	}
	if(chgLvl){
		List larg=[svd[sLVL]]
		if(lg)debug "Restoring level ${gtLbl(device)}",r9
		executePhysicalCommand(r9,device,sSTLVL,larg,del,scheduleDevice)
		del+=l100
	}
	if(chgCtemp){
		List larg=[svd[sCLRTEMP]]
		if(lg)debug "Restoring color Temperature ${gtLbl(device)}",r9
		executePhysicalCommand(r9,device,sSTCLRTEMP,larg,del,scheduleDevice)
		del+=l100
	}

	if(physExactFLD.isEmpty()) buildPhysLookups()
	def value
	Integer n; n=iZ
	String exactCommand,fuzzyCommand,fuzzyCommand1,t0
	for(String attr in newattrs){
		value=vals[n]
		n+=i1
		exactCommand= fuzzyCommand= fuzzyCommand1=sNL
		t0="Restoring ${gtLbl(device)} : '$attr' to value '$value'".toString()
		Map attrExact=(Map)physExactFLD[attr]
		exactCommand=attrExact!=null ? (String)(attrExact[(String)value] ?: sNL) : sNL
		if(exactCommand!=sNL) t0+=" using command".toString()
		fuzzyCommand=(String)(physFuzzyAttrFLD[attr] ?: sNL)
		fuzzyCommand1=(String)(physFuzzyValFLD[(String)value] ?: sNL)
		if(exactCommand!=sNL){
			if(lg)debug "${t0} $exactCommand()",r9
			executePhysicalCommand(r9,device,exactCommand,null,del,scheduleDevice)
			del+=l100
			continue
		}
		if(fuzzyCommand!=sNL){
			if(lg)debug "${t0} $fuzzyCommand($value)",r9
			executePhysicalCommand(r9,device,fuzzyCommand,value,del,scheduleDevice)
			del+=l100
			continue
		}
		if(fuzzyCommand1!=sNL){
			if(lg)debug "${t0} $fuzzyCommand1()",r9
			executePhysicalCommand(r9,device,fuzzyCommand1,null,del,scheduleDevice)
			del+=l100
			continue
		}
		warn "Could not find a command to set attribute '$attr' to value '$value'",r9
	}
	if(wOn!=null && wOn!=isOn){
		del+= wOn ? lZ:1200L
		executePhysicalCommand(r9,device,sMs(svd,sSWITCH),null,del,scheduleDevice)
	}
	return del
}

private Long vcmd_loadStateGlobally(Map r9,device,List prms){ return vcmd_loadStateLocally(r9,device,prms,true) }

private Long vcmd_parseJson(Map r9,device,List prms){
	String data=sLi(prms,iZ)
	try{
		if(stJson(data)){
			r9[sJSON]=(LinkedHashMap)new JsonSlurper().parseText(data)
		}else if(stJson1(data)){
			r9[sJSON]=(List)new JsonSlurper().parseText(data)
		}else r9[sJSON]=[:]
	}catch(all){
		error "Error parsing JSON data $data",r9,iN2,all
	}
	return lZ
}

private static Long vcmd_cancelTasks(Map r9,device,List prms){
	((Map)r9[sCNCLATNS])[sALL]=true
	return lZ
}

@Field static final String sFF=' ffwd: '
private static String sffwdng(Map r9){ return prun(r9) ? sSPC : sFF+sTRUE+": ${currun(r9)} " }

@Field static final String sFLWBY='followed by'
@Field static final String sC_COL='c:'

@CompileStatic
private Boolean evaluateConditions(Map r9,Map cndtns,String collection,Boolean async){
	String myS; myS=sBLK
	Integer myC=stmtNum(cndtns)
	Boolean lg=isDbg(r9)
	Boolean lgt=isTrc(r9)
	Boolean lge=lg && isEric(r9)
	if(lge){
		String s,s1
		s= "$cndtns".toString()
		s1= s.substring(iZ,Math.min(340,s.length()))
		s1= s1!=s ? s1+' TRUNCATED' : s1
		myS=("evaluateConditions #${myC}"+sffwdng(r9)+s1+sSPC).toString()
		myDetail r9,myS,i1
	}
	Long t; t=wnow()
	Map msg; msg=null
	if(lg)msg=timer sBLK,r9
	//override condition id
	Integer c=iMs(mMs(r9,sSTACK),sC)
	((Map)r9[sSTACK])[sC]=myC
	Boolean collC= collection==sC // collection is sR or sC
	Boolean not= collC ? !!cndtns[sN]:!!cndtns[sRN]
	String grouping= collC ? sMs(cndtns,sO):sMs(cndtns,sROP) // operator, restriction operator
	Boolean value; value= grouping!=sOR
	List<Map> cndtnsCOL=cndtns[collection] ? liMs(cndtns,collection) : []

	Boolean isFlwby= grouping==sFLWBY
	Boolean runThru= currun(r9)==iN9 && isFlwby
	if(isFlwby && collC && !runThru){
		if(prun(r9) || currun(r9)==myC){
			//dealing with a followed by condition
			Integer steps= cndtnsCOL.size()
			String sidx='c:fbi:'+myC.toString()
			Integer ladderIndex
			ladderIndex= matchCastI(r9,mMs(r9,sCACHE)[sidx]) // gives back iZ if null
			String sldt='c:fbt:'+myC.toString()
			Long ladderUpdated
			ladderUpdated=(Long)cast(r9,mMs(r9,sCACHE)[sldt],sDTIME) // gives back current dtime if null
			//Boolean didC; didC=false
			if(ladderIndex>=steps) value=false
			else{
				t=wnow()
				Map cndtn; cndtn=cndtnsCOL[ladderIndex]
				Long duration; duration=lZ
				if(ladderIndex){
					Map tv=mevaluateOperand(r9,mMs(cndtn,sWD))
					duration=longEvalExpr(r9,rtnMap1(tv))
				}
				// wt: l- loose (ignore unexpected events), s- strict, n- negated (lack of requested event continues group)
				String wt=sMs(cndtn,sWT)
				if(ladderUpdated && duration!=lZ && (ladderUpdated+duration)<t){
					//time has expired
					value=(wt==sN)
					if(!value)
						if(lg)debug "Conditional ladder step failed due to a timeout",r9
				}else{

					value=evaluateCondition(r9,cndtn,sC,async)
					//didC=true

					if(wt==sN){
						if(value) value=false
						else value=null
					}
					//we allow loose matches to work even if other events happen
					if(wt==sL && !value)value=null // loose
				}
				if(value){
					//successful step, move on
					ladderIndex+= i1
					//didC=false
					ladderUpdated=t
					cancelStatementSchedules(r9,myC)
					String ms; ms=sBLK
					if(lg)ms="Condition group #${myC} made progress up the ladder; currently at step $ladderIndex of $steps, "
					if(ladderIndex<steps){
						//delay decision, there are more steps to go through
						value=null
						cndtn=cndtnsCOL[ladderIndex]
						Map tv=mevaluateOperand(r9,mMs(cndtn,sWD))
						duration=longEvalExpr(r9,rtnMap1(tv))
						if(lgt)ms+="Requesting timed"
						requestWakeUp(r9,cndtns,cndtns,duration,sNL,true,sNL,ms)
					}else if(ms)debug ms,r9
				}
			}

			switch(value){
				case null:
					//Integer st=ladderIndex +(didC?i1:iZ)
					//we need to exit time events set to work out the timeouts
					if(currun(r9)==myC)r9[sTERM]=true
					break
				case false:
				case true:
					//ladder either collapsed or finished, reset data
					ladderIndex=iZ
					ladderUpdated=lZ
					cancelStatementSchedules(r9,myC)
					break
			}
			if(currun(r9)==myC)chgRun(r9,iZ)
			((Map)r9[sCACHE])[sidx]=ladderIndex
			((Map)r9[sCACHE])[sldt]=ladderUpdated
		}
	}else{
		if(cndtnsCOL){
			Boolean canopt
			canopt= !gtPOpt(r9,sCTO) && grouping in ListORAND //cto == disable condition traversal optimizations
			if(canopt){
				Integer i; i=iZ
				for(Map cndtn in cndtnsCOL){
					if( i!=iZ && (sMt(cndtn)==sGROUP || cndtn[sCT]==sT || cndtn[sS]) ){ canopt=false; break }
					i++
				}
			}
			if(lge) myS+="cto: $canopt "
			Boolean isOR= grouping==sOR
			Boolean res
			for(Map cndtn in cndtnsCOL){
				res=evaluateCondition(r9,cndtn,collection,async) //run through all to update stuff
				value= isOR ? value||res : value&&res
				if(prun(r9) && canopt && ((value && isOR) || (!value && !isOR)))break
			}
		}
	}

	Boolean res; res=false //null
	if(value!=null) res= not ? !value:!!value
	if((value!=null && myC!=iZ) || runThru){
		if(!runThru){
			String mC= sC_COL+myC.toString()
			if(prun(r9))tracePoint(r9,mC,elapseT(t),res)
			Boolean oldResult= !!bIs(mMs(r9,sCACHE),mC)
			Boolean a= oldResult!=res
			r9[sCNDTNSTC]= a
			if(a) //condition change, perform Task Cancellation Policy TCP
				cancelConditionSchedules(r9,myC)
			((Map)r9[sCACHE])[mC]=res
		}
		//true/false actions
		if(collC){
			List<Map> ts= cndtns[sTS]!=null ? liMs(cndtns,sTS):[]
			if(ts.size()!=iZ && (res || ffwd(r9))) executeStatements(r9,ts,async)
			List<Map> fs= cndtns[sFS]!=null ? liMs(cndtns,sFS):[]
			if(fs.size()!=iZ && (!res || ffwd(r9))) executeStatements(r9,fs,async)
		}
		if(prun(r9) && lg){
			msg[sM]="Condition group #${myC} evaluated $res (condition ".toString()+(bIs(r9,sCNDTNSTC) ? 'changed':'did not change')+')'
			debug msg,r9
		}
	}
	//restore condition id
	((Map)r9[sSTACK])[sC]=c
	if(lge)myDetail r9,myS+"result:$res"
	return res
}

@CompileStatic
private List levaluateOperand(Map r9,Map node, Map oper,Integer index=null,Boolean trigger=false,Boolean nextMidnight=false){
	return (List)evaluateOperand(r9,node,oper,index,trigger,nextMidnight,null)
}

@CompileStatic
private Map mevaluateOperand(Map r9,Map oper,Integer index=null,Boolean trigger=false,Boolean nextMidnight=false,Long dayBasis=null){
	return (Map)evaluateOperand(r9,null,oper,index,trigger,nextMidnight,dayBasis)
}

@CompileStatic
private Double evalDecimalOperand(Map r9,Map operand){
	Map value=mevaluateOperand(r9,operand)
	return dcast(r9,value ? oMv(value):sBLK)
}

@CompileStatic
private evaluateOperand(Map r9,Map node,Map oper,Integer index=null,Boolean trigger=false,Boolean nextMidnight=false,Long dayBasis=null){
	String myS,nodeI
	myS=sBLK
	Boolean lge=isEric(r9)
	if(lge){
		myS="evaluateOperand:"+sffwdng(r9)+"trigger: $trigger dayBasis: $dayBasis oper: $oper "
		myDetail r9,myS,i1
	}
	List<LinkedHashMap> vals; vals=[]
	Map operand,movt,mv
	operand=oper
	if(!operand)operand=[(sT):sC] //older pistons don't have the to: operand (time offset), simulating an empty one
	String ovt=sMvt(operand)
	movt=(ovt ? [(sVT):ovt] : [:]) as LinkedHashMap
	String nD="${node?.$}:".toString()
	nodeI=nD+"$index:0".toString()
	Long t=wnow()
	String LID=sMs(r9,sLOCID)
	mv=null
	switch(sMt(operand)){
		case sBLK: //optional, nothing selected
			mv=rtnMap(ovt,null)
			break
		case sP: //physical device
			String operA=sMa(operand)
			Map attribute=operA ? Attributes()[operA]:[:]
			Map aM=(attribute && bIs(attribute,sP) ? [(sP): (sMs(operand,sP)?:sA) ] :[:]) as LinkedHashMap // .p - device support p- physical vs. s- digital, a-any
			for(String d in expandDeviceList(r9,liMd(operand))){
				Map value=[(sI): d+sCLN+operA,(sV):getDeviceAttribute(r9,d,operA,operand[sI],trigger)+movt+aM]
				//updateCache(r9,value,t)
				vals.push(value)
			}
			//if we have multiple values and a grouping other than any or all we need to apply that function
			// avg,median,least,most,sum,variance,stdev,min,max,count,size etc
			String g=sMs(operand,sG)
			if(vals.size()>i1 && !(g in ListANYALL)){
				try{
					mv=callFunc(r9,g,vals*.v)+movt
				}catch(ignored){
					error "Error applying grouping method ${g}",r9
				}
			}
			break
		case sD: //devices
			List deviceIds=[]
			for(String d in expandDeviceList(r9,liMd(operand))){
				if(getDevice(r9,d)) deviceIds.push(d)
			}
			nodeI=nD+sD
			mv=rtnMap(sDEV,deviceIds.unique())
			break
		case sV: //virtual devices
			Map ce=mMs(r9,sCUREVT)
			String rEN=sMs(ce,sNM)
			String evntVal="${ce[sVAL]}".toString()
			nodeI=nD+sV
			String oV; oV=fixAttr(sMv(operand))
			switch(oV){
				case sTIME:
				case sDATE:
				case sDTIME:
					mv= rtnMap(oV,(Long)cast(r9,t,oV,sLONG))
					break
				case sMODE:
				case sPWRSRC:
				case sHSMSTS:
					nodeI=LID+sCLN+oV
					mv=getDeviceAttribute(r9,LID,oV,null,trigger)
					break
			// for the rest of these, add in the eXcluded, a: markers
				case sHSMALRT:
					String valStr= evntVal+(evntVal==sRULE ? sCOMMA+sMs(ce,sDESCTXT) : sBLK)
					mv= gtVdevRes(r9,rEN,oV,valStr,!trigger)
					break
				case sHSMSARM:
				case sHSMRULE:
				case sHSMRULES:
				case sPSTNRSM:
				case 'cloudBackup':
				case 'lowMemory':
				case 'manualReboot':
				case 'update':
				case 'systemStart':
				case 'severeLoad':
				case 'zigbeeOff':
				case 'zigbeeOn':
				case 'zwaveCrashed':
				case 'sunriseTime':
				case 'sunsetTime':
				case sTILE:
					mv= gtVdevRes(r9,rEN,oV,evntVal,!trigger)
					break
				case 'ifttt':
				case 'email':
					mv= gtVdevRes(r9,rEN,oV+sDOT+evntVal,evntVal,!trigger)
					mv[sT]= oV=='email' ? oV:sMt(mv)
					break
				case 'routine':
					oV='routineExecuted'
					mv= gtVdevRes(r9,rEN,oV,hashId(r9,evntVal),!trigger)
					break
			}
			break
		case sS: //preset
			switch(ovt){
				case sTIME:
				case sDTIME:
					Long v; v=lZ
					switch(sMs(operand,sS)){
						case sSUNSET: v= getSunsetTime(r9,dayBasis); break
						case sSUNRISE: v= getSunriseTime(r9, dayBasis); break
						case 'midnight': v=nextMidnight ? getNextMidnightTime(r9,dayBasis):getMidnightTime(r9,dayBasis); break
						case 'noon': v=getNoonTime(r9,dayBasis); break
					}
					if(ovt==sTIME && v)v=(Long)cast(r9,v,ovt,sDTIME)
					mv=rtnMap(ovt,v)
					break
				default:
					mv=rtnMap(ovt,operand[sS])
					break
			}
			break
		case sX: //variable
			if(ovt==sDEV && operand[sX] instanceof List){
				//we could have multiple devices selected
				List asum; asum=[]
				for(String x in (List)oMs(operand,sX)){
					def tmp=oMv(getVariable(r9,x))
					if(tmp instanceof List){
						asum+= (List)tmp
					}else asum.push(tmp)
				}
				mv=rtnMap(sDEV,asum)+movt
			}else{
				Boolean hasI=sMs(operand,sXI)!=sNL
				if(hasI)movt=ovt ? [(sVT):ovt.replace(sLRB,sBLK)]:[:]
				String operX= sMs(operand,sX)
				mv=getVariable(r9,operX+(hasI ? sLB+sMs(operand,sXI)+sRB:sBLK))+movt
				if(operX && operX.startsWith(sAT)){
					if(operX.startsWith(sAT2)){
						String vn=operX.substring(i2)
						nodeI=sVARIABLE+sCLN+vn
					}else{
						nodeI= sMs(r9,sINSTID)+sDOT+operX
					}
				}
			}
			break
		case sC: //constant
			switch(ovt){
				case sTIME:
					Long offset= operand[sC] instanceof Integer ? iMs(operand,sC).toLong():lcast(r9,operand[sC])
					mv=rtnMap(ovt,(offset%1440L)*60000L)	//convert mins to time
					break
				case sDATE:
				case sDTIME:
					mv=rtnMap(ovt,operand[sC])
					break
				default:
					Map e= mMs(operand,sEXP) ?: [:]
					List<Map>i= liMs(e,sI) ?: []
					if(sMt(e)==sEXPR && i.size()==i1){
						Map val=i[iZ]
						String ty; ty= sMt(val)
						if(!(ty in ListNOOPT)){ //[sVARIABLE,sFUNC,sDEV,sOPERAND,sDURATION]
							def v; v= oMv(val)
							v= ty==sBOOLN ? bcast(r9,v):v
							if(ovt==sDEC){
								v=dcast(r9,v)
								ty=sDEC
							}
							mv=movt+rtnMap(ty,v)
						}
					}
			}
			if(mv)break
		case sE: //expression
			mv=movt+evaluateExpression(r9,mMs(operand,sEXP))
			break
		case sU: //argument
			mv=getArgument(r9,sMs(operand,sU))
			break
	}
	if(mv)vals=[[(sI):nodeI,(sV):mv]] as List<LinkedHashMap>

	if(node==null){ // return a Map instead of a List
		Map ret= vals.size() ? mMv(vals[iZ]) :rtnMap(sDYN,null)
		if(lge)myDetail r9,myS+"result:$ret"
		return ret
	}
	if(lge)myDetail r9,myS+"result:$vals"
	return vals
}

private static Map gtVdevRes(Map r9,String rEN, String attr,String v,Boolean eXcluded){
	return rEN==attr ? rtnMapS(v)+addVDevFlds(r9,attr,eXcluded) : rtnMapS(sNL)+addVDevFlds(r9,attr,true)
}

private static Map addVDevFlds(Map r9,String attr,Boolean eXcluded){
	return [(sA):attr, (sD):sMs(r9,sLOCID), /*(sI):subDeviceIndex,*/ (sX):eXcluded]
}

private Map callFunc(Map r9,String func,List p){
	return (Map)"func_${func}"(r9,p)
}


@Field volatile static Map<String,Boolean> needUpdateFLD=[:]
/** deal with cache for triggers refreshing */
void stNeedUpdate(){
	String myId=sAppId()
	if(!myId)return
	needUpdateFLD.put(myId,true)
	needUpdateFLD=needUpdateFLD
}

@Field static final List<String> lNTRK=['happens_daily_at','arrives','event_occurs','executes','gets','gets_any','receives']

@Field static final String sEVCN='evaluateCondition '
@CompileStatic
private Boolean evaluateCondition(Map r9,Map cndtn,String collection,Boolean async){
	String myS; myS=sBLK
	Integer cndNm=stmtNum(cndtn)
	Boolean lg=isDbg(r9)
	Boolean lge=lg && isEric(r9)
	if(lge){
		myS=sEVCN+("#${cndNm}"+sffwdng(r9)+"$cndtn async: ${async}").toString()
		myDetail r9,myS,i1
	}

	Long t=wnow()
	Boolean res; res=false

	if(sMt(cndtn)==sGROUP){
		res=evaluateConditions(r9,cndtn,collection,async)
		if(lge)myDetail r9,myS+" result:$res"
		return res
	}

	Map msg;msg=null
	if(lg)msg=timer sBLK,r9
	//override condition id
	Integer c=iMs(mMs(r9,sSTACK),sC)
	((Map)r9[sSTACK])[sC]=cndNm
	String sIndx=sC_COL+cndNm.toString()
	Boolean oldResult=!!bIs(mMs(r9,sCACHE),sIndx)

	Boolean not=!!cndtn[sN]
	String co=sMs(cndtn,sCO)
	Map comparison=(Map)AllComparisons()[co]
	Boolean trigger=comparison!=null && bIs(comparison,sTRIG)
	Map e=mMs(r9,sEVENT)
	Map es=mMs(e,sSCH)
	String rEN=sMs(e,sNM)
	Boolean w=rEN==sTIME && es!=null && iMsS(es)==cndNm
	r9[sWUP]=w
	if(w && lge)myDetail r9,sEVCN+"WAKING UP",iN2
	if(ffwd(r9) || comparison!=null){
		Boolean isStays=co.startsWith(sSTAYS)
		if(currun(r9) in ListIZIN9){
			Integer pCnt=comparison?.p!=null ? iMs(comparison,sP):iZ
			Map lo,ro,ro2,loOp
			lo=null; ro=null; ro2=null
			Integer i
			for(i=iZ; i<=pCnt; i++){
				Map operand=(i==iZ ? mMs(cndtn,sLO):(i==i1 ? mMs(cndtn,sRO):mMs(cndtn,sRO2)))
				//parse the operand
				List vals=levaluateOperand(r9,cndtn,operand,i,trigger)
				switch(i){
					case iZ:
						lo=[(sOPERAND):operand,(sVALUES):vals]
						break
					case i1:
						ro=[(sOPERAND):operand,(sVALUES):vals]
						break
					case i2:
						ro2=[(sOPERAND):operand,(sVALUES):vals]
						break
				}
			}

			//we now have all the operands,their values, and the comparison, let's get to work
			Boolean compt= sMt(comparison)!=sNL
			Boolean t_and_compt=(trigger && compt)
			loOp=mMs(lo,sOPERAND)
			String dmv=sMs(loOp,'dm')
			String dnv=sMs(loOp,'dn')
			LinkedHashMap options=[
				//we ask for matching/non-matching devices if the user requested it or if the trigger is timed
				//setting matches to true will force the condition group to evaluate all members (disables evaluation optimizations)
				(sDEVS): [:],
				(sMATCHES): t_and_compt || !!dmv || !!dnv,
				(sFRCALL): t_and_compt
			] as LinkedHashMap
			Map to=(compt || (ro!=null && sMt(loOp)==sV && sMv(loOp)==sTIME && sMt(mMs(ro,sOPERAND))!=sC)) && cndtn[sTO]!=null ? [(sOPERAND): mMs(cndtn,sTO),(sVALUES): mevaluateOperand(r9,mMs(cndtn,sTO))]:null
			Map to2=ro2!=null && sMt(loOp)==sV && sMv(loOp)==sTIME && sMt(mMs(ro2,sOPERAND))!=sC && cndtn[sTO2]!=null ? [(sOPERAND): mMs(cndtn,sTO2),(sVALUES): mevaluateOperand(r9,mMs(cndtn,sTO2))]:null

			res=evaluateComparison(r9,co,lo,ro,ro2,to,to2,options)

			String myId=sAppId()
			//save new values to cache
			Boolean isTracking= trigger && !(co in lNTRK)
			if(lo)for(Map value in liMs(lo,sVALUES)){
				Map tm= mMv(value)
				if(isTracking){
					Boolean isd= tm[sD] && sMa(tm) //if has d and a its a device
					Boolean ok; ok=true
					if(isd){
						Boolean eXcluded=bIs(tm,sX)
			//x=eXclude- if a trigger comparison or a momentary device/attribute is looked for,
			//  and the device/attr does not match the current event device/attr,
			// then we must ignore the result during comparisons
						ok= bIs(cndtn,sS) && (!isStays || (isStays && co==sSTAYUNCH)) &&
							(ffwd(r9) || !eXcluded || needUpdateFLD[myId]!=false)
					}
					if(lge)myDetail r9,"cache check ${co} tm: $tm isDevice: $isd ok: $ok",iN2
					if(ok)updateCache(r9,value,t)
				}
			}
			loOp=mMs(lo,sOPERAND)
			Map ods=mMs(options,sDEVS)
			List om=(List)ods[sMATCHED]
			List oum=(List)ods[sUNMATCHED]
			if(ods){
				if(dmv) setVariable(r9,dmv,om)
				if(dnv) setVariable(r9,dnv,oum)
			}

			//do the stays logic here
			if(t_and_compt && prun(r9)){
				//trigger on device:attribute and timed trigger
				if(lg)myDetail r9,"stays check ${co} isStays: $isStays result: $res options: $options",iN2
				if(to!=null){
					Map tvalue=mMs(to,sOPERAND) && mMs(to,sVALUES) ? mMs(to,sVALUES)+[(sF): mMs(to,sOPERAND)[sF]]:null
					if(tvalue!=null){
						Long delay=longEvalExpr(r9,rtnMap1(tvalue))

						List<Map> schedules=sgetSchedules(sEVCN,isPep(r9))

						if(sMt(loOp)==sP && sMs(loOp,sG)==sANY && liMs(lo,sVALUES).size()>i1){
							List<String> chkList=om
							if(lge)myDetail r9,"$co stays check device options: $options",iN2
							//if(!isStays) chkList=oum
							for(value in liMs(lo,sVALUES)){
								String dev=(String)mMv(value)?.d
								doStaysProcess(r9,schedules,co,cndtn,cndNm,delay,(dev in chkList),dev)
							}
						}else{
							if(lge)myDetail r9,"$co stays check",iN2
							doStaysProcess(r9,schedules,co,cndtn,cndNm,delay,res,sNL)
						}
					}else{ error "expecting time for stay and value not found $to $tvalue",r9 }	//; res=false }
				}else{ error "expecting time for stay and operand not found $to",r9 } //;	res=false }
				if(isStays)res=false
			}
			res=not ? !res:res
		}else if(rEN==sTIME && currun(r9)==cndNm){ // we are ffwd & stays timer fired, pickup at result of if statement
			chgRun(r9,iZ)
			r9[sRESUMED]=true
			if(isStays)res=!not
		}else{ // continue ffwd
			res=oldResult
		}
	}
	if(prun(r9))tracePoint(r9,sIndx,elapseT(t),res)

	r9[sWUP]=false
	Boolean a= oldResult!=res
	r9[sCNDTNSTC]= a
	if(a) //cndtn change, perform Task Cancellation Policy TCP
		cancelConditionSchedules(r9,cndNm)
	((Map)r9[sCACHE])[sIndx]=res

	//true/false actions
	List<Map> ts= cndtn[sTS]!=null ? liMs(cndtn,sTS):[]
	Boolean wasFwd=ffwd(r9)
	if(ts.size()!=iZ && (res || wasFwd)) executeStatements(r9,ts,async)
	List<Map> fs= cndtn[sFS]!=null ? liMs(cndtn,sFS):[]
	if(fs.size()!=iZ && (!res || (wasFwd && ffwd(r9)))) executeStatements(r9,fs,async)

	//restore condition id
	((Map)r9[sSTACK])[sC]=c
	if(prun(r9) && lg){
		msg[sM]="Condition #${cndNm} evaluated $res"
		debug msg,r9
	}
	if(currun(r9)<=iZ && bIs(cndtn,sS) && sMt(cndtn)==sCONDITION && cndtn[sLO]!=null && sMt(mMs(cndtn,sLO))==sV){
		if(sMv(mMs(cndtn,sLO)) in LT1) scheduleTimeCondition(r9,cndtn)
	}
	if(lge)myDetail r9,myS+" resumed: ${bIs(r9,sRESUMED)} result:$res"
	return res
}

@CompileStatic
void doStaysProcess(Map r9,List<Map>schedules,String co,Map cndtn,Integer cndNm,Long delay,Boolean result,String dev){
	Boolean canc,schd
	canc=false; schd=false
	Boolean isStaysUnchg= co==sSTAYUNCH
	Boolean isStays=co.startsWith(sSTAYS)
	Boolean lg=isDbg(r9)
	Boolean lgt=isTrc(r9)
	String s; s=sBLK
	String d=sD
	if(isStays && result){
		//if we find the comparison true (ie reason to time stays has begun) set a timer if we haven't already
		if(lg)s= dev ? " $co match in list":" $co result $result"
		if(!schedules.find{ Map it -> iMsS(it)==cndNm && (!dev || sMs(it,d)==dev) }){
			//schedule a wake up if there's none otherwise just move on
			if(lgt)s+= " scheduling timer "
			schd=true
		}else s+= " found timer "
	}else{ // the comparison failed, normally cancel except for stays_unchanged
		if(lg)s= dev ? " $co device did not match":" $co result $result"
		if(isStaysUnchg){
			if(lg)s+= " $co result $result (it changed)"
			if(!schedules.find{ Map it -> iMsS(it)==cndNm && (!dev || sMs(it,d)==dev) }){
				if(lgt)s+= " no timer found creating timer "
				schd=true
			}else{
				if(lg)s+= " with timer active, cancel timer and create new timer"
				canc=true
				schd=true
			}
		}else{
			//cancel any schedule
			if(lgt)s+= " cancel any timers "
			canc=true
		}
	}
	if(lgt){
		String d1= dev ? "for device $dev ":sBLK
		s="timed trigger schedule${s}${d1}for condition ${cndNm}"
	}
	if(canc){
		if(lgt)trace "Cancel any $s",r9
		cancelStatementSchedules(r9,cndNm,dev)
	}
	if(schd){
		String msg= lgt ? "Adding a "+s : sNL
		requestWakeUp(r9,cndtn,cndtn,delay,dev,true,sNL,msg)
	}
	if(!schd && !canc){
		if(lg)debug "Doing nothing found $s",r9
	}
}

@Field static final List<String> lGENERIC=['event_occurs','gets_any']
@Field static final List<String> lSPECIFIC=['receives','gets']
@Field static final String sDNM= ' (event device/attr did not match)'

@CompileStatic
private Boolean evaluateComparison(Map r9,String comparison,Map lo,Map ro=null,Map ro2=null,Map to=null,Map to2=null,Map options=[:]){
	String mySt; mySt=sBLK
	Boolean lg=isDbg(r9)
	Boolean lge=lg && isEric(r9)
	if(lge){
		mySt="evaluateComparison"+sffwdng(r9)+"$comparison "
		String s1="lo: $lo ro: $ro ro2: $ro2 to: $to to2: $to2 options: $options"
		myDetail r9,mySt+s1,i1
	}
	String fn="comp_"+comparison
	Map loOperMap=mMs(lo,sOPERAND)
	String loG= sMs(loOperMap,sG) ?: sANY
	Boolean result,res
	result= loG!=sANY
	Boolean oM=bIs(options,sMATCHES)
	if(oM) options[sDEVS]=[(sMATCHED): [],(sUNMATCHED): []] as LinkedHashMap
	//if multiple left values go through each
	Map tvalue=to && to[sOPERAND] && to[sVALUES] ? mMs(to,sVALUES)+[(sF): mMs(to,sOPERAND)[sF]]:null
	Map tvalue2=to2 && to2[sOPERAND] && to2[sVALUES]? mMs(to2,sVALUES):null
	Boolean fa=bIs(options,sFRCALL) // force all to be evaluated
	for(Map<String,Map> value in liMs(lo,sVALUES)){
		res=false
		//x=eXclude- if a trigger comparison or a momentary device/attribute is looked for,
		//  and the device/attr does not match the current event device/attr,
		// then we must ignore the result during comparisons, unless forceAll
		Map vvalMap; vvalMap= value ? (mMv(value) ?: null) : null
		Boolean eXcluded= (vvalMap && bIs(vvalMap,sX))
		if(vvalMap && (!eXcluded || fa)){
			Map msg; msg=[:]
			try{
				//physical support
				//value.p=lo.operand.p
				if(vvalMap && sMt(vvalMap)==sDEV) value[sV]=evaluateExpression(r9,vvalMap,sDYN)
				vvalMap= mMv(value)
				String m1; m1=sNL
				if(lg) m1= "Comparison (${vvalMap[sT]}) ${vvalMap[sV]} $comparison "
				String compS; compS= fixAttr(sMv(loOperMap))
				Map ce= mMs(r9,sCUREVT) ?: [:]
				String rEN= sMs(ce,sNM)
				if(!ro){
					if(lg)msg= timer sBLK,r9
					if(comparison in lGENERIC){ // generic trigger event_occurs, gets_any
						if(sMt(loOperMap)==sV && rEN==compS){
							res= true
						}else if(sMs(vvalMap,sD)==sMs(ce,sDEV) && sMa(vvalMap)==rEN){
							res= true
							compS= sMa(vvalMap)
						}
						if(lg)msg[sM]= "Comparison (string) ${compS} $comparison = $res" + (!res ? sDNM:sBLK)
					}else{
						res= callComp(r9,fn,value,null,null,tvalue,tvalue2)
						if(lg)msg[sM]= m1+"= $res"
					}
					if(lg)debug msg,r9
				}else{
					Boolean isSpecific= comparison in lSPECIFIC // specific receives, gets
					Boolean ok= isSpecific ? ( (sMt(loOperMap)==sV && rEN==compS) ||
								((sMs(vvalMap,sD)==sMs(ce,sDEV) && sMa(vvalMap)==rEN)) ) : true
					Boolean rres
					String roG= sMs(mMs(ro,sOPERAND),sG) ?: sANY
					res= roG!=sANY
					//if multiple right values go through each
					for(Map<String,Map> rvalue in liMs(ro,sVALUES)){
						if(rvalue && sMt(mMv(rvalue))==sDEV) rvalue[sV]= evaluateExpression(r9,mMv(rvalue),sDYN)
						String m2; m2=sNL
						if(lg) m2= m1+"(${rvalue?.v?.t}) ${rvalue?.v?.v} "
						if(!ro2){
							if(lg)msg= timer sBLK,r9
							rres= ok ? callComp(r9,fn,value,rvalue,null,tvalue,tvalue2) : ok
							if(lg){
								msg[sM]= m2+"= $rres" + (!rres && !ok ? sDNM:sBLK)
								debug msg,r9
							}
						}else{
							String ro2G= sMs(mMs(ro2,sOPERAND),sG) ?: sANY
							rres= ro2G!=sANY
							//if multiple right2 values go through each
							for(Map<String,Map> r2value in liMs(ro2,sVALUES)){
								if(r2value && sMt(mMv(r2value))==sDEV) r2value[sV]= evaluateExpression(r9,mMv(r2value),sDYN)
								if(lg)msg= timer sBLK,r9
								Boolean r2res= ok ? callComp(r9,fn,value,rvalue,r2value,tvalue,tvalue2) : false
								if(lg){
									msg[sM]= m2+".. (${r2value?.v?.t}) ${r2value?.v?.v} = $r2res"
									debug msg,r9
								}
								rres= ro2G==sANY ? rres || r2res : rres && r2res
								if(!fa && ((ro2G==sANY && rres) || (ro2G!=sANY && !rres))) break
							}
						}
						res= (roG==sANY ? res || rres : res && rres)
						if(!fa && ((roG==sANY && res) || (roG!=sANY && !res))) break
					}
				}
			}catch (all){
				error "Error calling comparison $fn:",r9,iN2,all
				res= false
			}

			if(res && sMt(loOperMap)==sV && sMv(loOperMap) in LT1){
				Boolean pass= (checkTimeRestrictions(r9,loOperMap,wnow(),i5,i1)==lZ)
				if(lg)debug "Time restriction check ${pass ? 'passed' : 'failed'}",r9
				if(!pass) res= false
			}
		}else if(eXcluded){
			if(lg)debug "Comparison $comparison = $res (event device/attr eXcluded)",r9
		}
		result= loG==sANY ? result||res : result&&res
		if(oM){
			String vVD=sMs(mMv(value),sD)
			if(vVD){
				Map ods=mMs(options,sDEVS)
				((List)oMs(ods, res ? sMATCHED:sUNMATCHED)).push(vVD)
			}
		}else if(!fa){ // if not matching, evaluation optimization
			//logical OR if using the ANY keyword
			if(loG==sANY && res) break
			//logical AND if using the ALL keyword
			if(loG==sALL && !result) break
		}
	}
	if(lge)myDetail r9,mySt+"result:$result"
	return result
}

private Boolean callComp(Map r9,String fn,Map lv,Map rv,Map rv2,Map tv,Map tv2){
	Boolean lge=isDbg(r9) && isEric(r9)
	String s; s=sBLK
	if(lge){
		s= "callComp $fn $lv $rv $rv2 $tv $tv2"
		myDetail r9,s,i1
	}

	Boolean a=(Boolean)"$fn"(r9,lv,rv,rv2,tv,tv2)

	if(lge)myDetail r9,s+ " RESULT: $a ${myObj(lv?.v?.v)} ${myObj(rv?.v?.v)}"
	return a
}

@CompileStatic
private static String cnlS(Map sch){ return "${sch[sS]} (st:${sch[sI]}"+(sch[sD] ? " / ${sch[sD]}":sBLK)+') ' }

private void whatCnclsA(Map r9){
	List<Map> schedules=sgetSchedules(sPROCS,isPep(r9))
	String s; s=sBLK
	for(Map sch in schedules){
		Integer i=iMs(sch,sI)
		if(i>iZ || i in ListIN35) s+= cnlS(sch)
	}
	if(s)trace "Cancel ALL task schedules..."+s,r9
}

/** log what timers will be canceled due to piston state change from saved schedules */
private void whatCnclsP(Map r9){
	List<Map> schedules=sgetSchedules(sPROCS,isPep(r9))
	String s; s=sBLK
	for(Map sch in schedules)
		if(iMs(sch,sPS)!=iZ) s+= cnlS(sch)
	if(s)trace "Cancel piston state changed schedules..."+s,r9
}

/** log what statements timers will be canceled from saved schedules */
private void whatStatementsCncl(Map r9,Integer stmtId, String data=sNL){
	List<Map> schedules=sgetSchedules(sPROCS,isPep(r9))
	String s; s=sBLK
	for(Map sch in schedules)
		if(stmtId==iMsS(sch) && (!data || data==sMs(sch,sD))) s+= cnlS(sch)
	if(s)trace "Cancel statement #${stmtId}'s schedules..."+s,r9
}

@CompileStatic
private void cancelStatementSchedules(Map r9,Integer stmtId,String data=sNL){
	// data=null - cancel all schedules that are pending for statement stmtId
	Boolean fnd; fnd=false
	for(Map item in liMs(mMs(r9,sCNCLATNS),sSTMTS)){
		fnd=(stmtId==iMs(item,sID) && (!data || data==sMs(item,sDATA)))
		if(fnd)break
	}
	if(isTrc(r9))whatStatementsCncl(r9,stmtId,data)
	// if not already in list, add to list
	if(!fnd) liMs(mMs(r9,sCNCLATNS),sSTMTS).push([(sID): stmtId,(sDATA): data])
}

/** log what condition timers will be canceled from saved schedules */
private void whatConditionsCncl(Map r9,Integer cndtnId){
	List<Map> schedules=sgetSchedules(sPROCS,isPep(r9))
	String s; s=sBLK
	for(Map sch in schedules)
		if(cndtnId in (List)sch[sCS]) s+= cnlS(sch)
	if(s)trace "Cancel condition #${cndtnId}'s schedules..."+s,r9
}

@CompileStatic
private void cancelConditionSchedules(Map r9,Integer cndtnId){
	//cancel all schedules that are pending for condition cndtnId
	if(isTrc(r9))whatConditionsCncl(r9,cndtnId)
	if(!(cndtnId in (List<Integer>)mMs(r9,sCNCLATNS)[sCONDITIONS]))
		((List<Integer>)mMs(r9,sCNCLATNS)[sCONDITIONS]).push(cndtnId)
}
/*
private static Boolean matchDeviceSubIndex(list,deviceSubIndex){
	if(!list || !(list instanceof List) || list.size()==iZ)return true
	return list.collect{ "$it".toString() }.indexOf("$deviceSubIndex".toString())>=iZ
	return true
} */

/**
 * physical support
 */
@CompileStatic
private static Boolean matchDeviceInteraction(Map lv,Map r9){
	String option= sMs(mMv(lv),sP) // lv.v.p
	Map ce=mMs(r9,sCUREVT) ?: [:]
	Boolean isPhysical=bIs(ce,sPHYS)
	// device support p- physical vs. s- digital, a-any
	return !((option==sP && !isPhysical) || (option==sS && isPhysical))
}

private List<Map> listPreviousStates(Map r9,device,String attr,Long threshold,Boolean excludeLast){
	String mySt; mySt=sBLK
	Boolean lge=isDbg(r9) && isEric(r9)
	if(lge){
		mySt="listPreviousStates"+sffwdng(r9)+"$attr "
		String s1="threshold: $threshold excludeLast: $excludeLast"
		myDetail r9,mySt+s1,i1
	}
	List<Map> res=[]
	List events=((List)device.events([all: true,max: i100])).findAll{ it -> (String)it.getName()==attr}
	//if we need to exclude last event we start at the second event as the first one is the event that triggered execution.
	// The attribute's value has to be different from the current one to qualify for quiet
	Integer sz=events.size()
	if(lge)myDetail r9,mySt+"found $sz events",iN2
	String s='startTime'
	if(sz!=iZ){
		Long thresholdTime=elapseT(threshold)
		Long endTime; endTime=wnow()
		Integer i
		def curEvt
		for(i=iZ; i<sz; i++){
			curEvt=events[i]
			Long startTime=((Date)curEvt[sDATE]).getTime()
			Long duration=endTime-startTime
			if(duration>=10L && (i>iZ || !excludeLast)) // lTHOUS
				res.push([(sVAL):curEvt[sVAL],(s):startTime,(sDURATION):duration])
			if(startTime<thresholdTime) break
			endTime=startTime
		}
	}
	if(res.size()==iZ){
		def currentState=device.currentState(attr,true)
		if(currentState){
			Long startTime=((Date)currentState.getDate()).getTime()
			res.push([(sVAL):currentState[sVAL],(s):startTime,(sDURATION):elapseT(startTime)])
		}
	}
	if(lge)myDetail r9,mySt+"result:$res"
	return res
}

@CompileStatic
private static Boolean isEntryInCache(Map r9,Map value){
	String n=sMs(value,sI)
	return msMs(r9,sNWCACHE)[n]!=null
}

@CompileStatic
private static void updateCache(Map r9,Map value,Long t){
	String n=sMs(value,sI)
	Map oldValue=mMs(mMs(r9,sCACHE),n)
	Map valueV=[:]+mMv(value)
	if(oldValue==null || sMt(oldValue)!=sMt(valueV) || "${oldValue[sV]}"!="${valueV[sV]}"){
		if(valueV[sD]!=null && valueV[sD] instanceof Long) valueV.remove(sD)
		if(valueV[sVT]!=null) valueV.remove(sVT)
		if(valueV[sX]!=null) valueV.remove(sX)
		if(valueV[sP]!=null) valueV.remove(sP)
		msMs(r9,sNWCACHE)[n]=valueV+( [(sS):t] as Map)
	}
}

@CompileStatic
private Map valueCacheChanged(Map r9,Map comparisonValue){
	Boolean lg=isDbg(r9)
	String n=sMs(comparisonValue,sI)
	def oV=mMs(r9,sCACHE)[n]
	Map newValue=mMv(comparisonValue)
	Map oldValue= oV instanceof Map ? oV:null
	Map res= (oldValue!=null && (sMt(oldValue)!=sMt(newValue) || "${oldValue[sV]}"!="${newValue[sV]}")) ? [(sI):n,(sV):oldValue] :null
	if(lg)
		debug "Previous value ${res!=null ? "changed from ${oldValue[sV]} (${sMt(oldValue)}) to ${newValue[sV]} (${sMt(newValue)})}" : "was not found, or did not change (${oldValue} -> ${newValue})" }",r9
	return res
}

@CompileStatic
private static Boolean okComp(Map compV,Map timeValue){
	Map cv= compV!=null ? mMv(compV):null
	return !(cv==null || !sMs(cv,sD) || !sMa(cv) || timeValue==null || timeValue[sV]==null || !sMvt(timeValue))
}

@CompileStatic
private Boolean valueWas(Map r9,Map comparisonValue,Map rightValue,Map rightValue2,Map timeValue,String func){
	Boolean res; res= null
	Boolean lg=isDbg(r9)
	if(okComp(comparisonValue,timeValue)){
		Map cv=mMv(comparisonValue)
		String t=sMs(cv,sD)
		def device=t?getDevice(r9,t):null
		if(device){
			res= false
			String attr=sMa(cv)
			Long threshold=longEvalExpr(r9,rtnMap1(timeValue))

			Map e=mMs(r9,sEVENT)
			String nattr=fixAttr(attr)
			Boolean thisEventWokeUs=(sMs(e,sDEV)==hashD(r9,device) && sMs(e,sNM)==nattr)
			// todo need to check waking up?

			List<Map> states=listPreviousStates(r9,device,nattr,threshold,thisEventWokeUs)
			Long duration; duration=lZ
			String comp_t=sMt(cv)
			def v
			Boolean needFix= (nattr==sTHREAX && nattr!=attr)
			for(Map stte in states){
				v= stte[sVAL]
				if(needFix) v= gtThreeAxisVal(v,attr)
				if(!callComp(r9,"comp_$func", [(sI):sMs(comparisonValue,sI),(sV):rtnMap(comp_t,cast(r9,v,comp_t))],
						rightValue,rightValue2,timeValue,null))break
				duration+= lMs(stte,sDURATION)
			}
			Boolean fisg=sMs(timeValue,sF)==sG // 'l' or 'g'
			String s; s='No'
			if(duration>lZ){
				res= fisg ? duration>=threshold:duration<threshold
				s= 'Found'
			}
			if(lg)
				debug s+" matching value, duration ${duration}ms for ${func.replace('is_','was_')} ${fisg ? sGTHE:sLTH} ${threshold}ms threshold = ${res}",r9
		}
	}
	if(res==null){
		res=false
		error "valueWas: bad parameters $res",r9
	}
	return res
}

@CompileStatic
private Boolean valueChanged(Map r9,Map comparisonValue,Map timeValue){
	Boolean res; res= null
	if(okComp(comparisonValue,timeValue)){
		Map cv=mMv(comparisonValue)
		String t=sMs(cv,sD)
		def device=t?getDevice(r9,t):null
		if(device){
			res= false
			String attr=sMa(cv)
			Long threshold=longEvalExpr(r9,rtnMap1(timeValue))

			String nattr=fixAttr(attr)
			Boolean needFix= (nattr==sTHREAX && nattr!=attr)

			List<Map> states=listPreviousStates(r9,device,nattr,threshold,false)
			if(states.size()!=iZ){
				def value,v
				value=states[iZ][sVAL]
				if(needFix) value= gtThreeAxisVal(value,attr)
				for(Map tstate in states){
					v= tstate[sVAL]
					if(needFix) v= gtThreeAxisVal(v,attr)
					if(v!=value){ res= true; break }
				}
			}
		}
	}
	if(res==null){
		res=false
		error "valueChanged: bad parameters $res",r9
	}else if(isDbg(r9) && isEric(r9)) myDetail r9,"valueChanged: $res",iN2
	return res
}

private static Boolean match(String str,String pattern){
	Integer sz=pattern.size()
	if(sz>i2 && pattern.startsWith(sDIV) && pattern.endsWith(sDIV)){
		def ppattern= ~pattern.substring(i1,sz-i1)
		return !!(str =~ ppattern)
	}
	return str.contains(pattern)
}

//comparison low level functions
private Boolean comp_is					(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){
	Map l= mMv(lv) ?: [:]
	Map r= mMv(rv) ?: [:]
	return strEvalExpr(r9,l)==strEvalExpr(r9,r) || (l[sN] && scast(r9,l[sN])==scast(r9,oMv(r)))
}
private Boolean comp_is_not				(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_is(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_is_equal_to		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){
	Map l= mMv(lv) ?: [:]
	Map r= mMv(rv) ?: [:]
	String lt=sMt(l); String rt=sMt(r)
	String dt= lt==sDEC || rt==sDEC ? sDEC:(lt==sINT || rt==sINT ? sINT:sDYN)
	return oMv(evaluateExpression(r9,l,dt))==oMv(evaluateExpression(r9,r,dt))
}
private Boolean comp_is_not_equal_to	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_is_equal_to(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_is_different_than	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_not_equal_to(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_is_less_than		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return dblEvalExpr(r9,mMv(lv))<dblEvalExpr(r9,mMv(rv)) }
private Boolean comp_is_greater_than_or_equal_to	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_is_less_than(r9,lv,rv) }
private Boolean comp_is_greater_than	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return dblEvalExpr(r9,mMv(lv))>dblEvalExpr(r9,mMv(rv)) }
private Boolean comp_is_less_than_or_equal_to	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_is_greater_than(r9,lv,rv) }
private Boolean comp_is_even			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return intEvalExpr(r9,mMv(lv)) % i2==iZ }
private Boolean comp_is_odd				(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return intEvalExpr(r9,mMv(lv)) % i2!=iZ }
private Boolean comp_is_true			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return boolEvalExpr(r9,mMv(lv)) }
private Boolean comp_is_false			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !boolEvalExpr(r9,mMv(lv)) }
private Boolean comp_is_inside_of_range		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Double v=dblEvalExpr(r9,mMv(lv)); Double v1=dblEvalExpr(r9,mMv(rv)); Double v2=dblEvalExpr(r9,mMv(rv2)); return (v1<v2) ? (v>=v1 && v<=v2):(v>=v2 && v<=v1)}
private Boolean comp_is_outside_of_range	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_is_inside_of_range(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_is_any_of			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){
	String v=strEvalExpr(r9,mMv(lv))
	Map r=[:]+mMv(rv)
	String s=sMv(r)
	List<String> parts=anyOfCacheFLD[s]
	if(parts==null){ parts=s.tokenize(sCOMMA).collect{ ((String)it).trim() }; if(anyOfCacheFLD.size()>500) anyOfCacheFLD=[:]; anyOfCacheFLD[s]=parts; anyOfCacheFLD=anyOfCacheFLD }
	for(String vi in parts){
		r[sV]=vi
		if(v==strEvalExpr(r9,r))return true
	}
	return false
}
private Boolean comp_is_not_any_of		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_is_any_of(r9,lv,rv,rv2,tv,tv2)}

private Boolean comp_was				(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,sIS)}
private Boolean comp_was_not			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_not')}
private Boolean comp_was_equal_to		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_equal_to')}
private Boolean comp_was_not_equal_to	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_not_equal_to')}
private Boolean comp_was_different_than		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_different_than')}
private Boolean comp_was_less_than		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_less_than')}
private Boolean comp_was_less_than_or_equal_to		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_less_than_or_equal_to')}
private Boolean comp_was_greater_than	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_greater_than')}
private Boolean comp_was_greater_than_or_equal_to	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_greater_than_or_equal_to')}
private Boolean comp_was_even			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_even')}
private Boolean comp_was_odd			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_odd')}
private Boolean comp_was_true			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_true')}
private Boolean comp_was_false			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_false')}
private Boolean comp_was_inside_of_range		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,sISINS)}
private Boolean comp_was_outside_of_range		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_outside_of_range')}
private Boolean comp_was_any_of			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_any_of')}
private Boolean comp_was_not_any_of		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueWas(r9,lv,rv,rv2,tv,'is_not_any_of')}

private Boolean comp_changed			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,tv2=null){ return valueChanged(r9,lv,tv)}
private Boolean comp_did_not_change		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !valueChanged(r9,lv,tv)}

private static Boolean comp_is_any		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return true }
private Boolean comp_is_before			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){
	Map l=mMv(lv)
	String lvt= sMt(l) in LT1 ? sMt(l) : sNL

	Long ttv= longEvalExpr(r9,l,sDTIME)/*+2000L*/
	Long v= lvt ? (Long)cast(r9,ttv,lvt) : ttv

	Long offset1=tv ? longEvalExpr(r9,rtnMap1(tv)) :lZ
	Long ttv1= longEvalExpr(r9,mMv(rv),sDTIME)+offset1
	Long v1= lvt ? (Long)cast(r9,ttv1,lvt) : ttv1

	return v<v1
}
private Boolean comp_is_after			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_is_before(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_is_between			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){
	Map l=mMv(lv)
	String lvt= sMt(l) in LT1 ? sMt(l) : sNL

	Long ttv= longEvalExpr(r9,l,sDTIME)/*+2000L*/
	Long v= lvt ? (Long)cast(r9,ttv,lvt) : ttv

	Long offset1=tv ? longEvalExpr(r9,rtnMap1(tv)) :lZ
	Long ttv1= longEvalExpr(r9,mMv(rv),sDTIME)+offset1
	Long v1= lvt ? (Long)cast(r9,ttv1,lvt) : ttv1

	Long offset2=tv2 ? longEvalExpr(r9,rtnMap1(tv2)) :lZ
	Long ttv2= longEvalExpr(r9,mMv(rv2),sDTIME)+offset2
	Long v2= lvt ? (Long)cast(r9,ttv2,lvt) : ttv2

	return v1<v2 ? v>=v1 && v<v2 : v<v2 || v>=v1
}
private Boolean comp_is_not_between		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_is_between(r9,lv,rv,rv2,tv,tv2)}

/*triggers*/
private Boolean comp_gets				(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return scast(r9,oMvv(lv))==scast(r9,oMvv(rv)) /* && matchDeviceSubIndex(mMv(lv).i,iMs(r9[sCUREVT],sINDX))*/ }
private static Boolean comp_receives	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return "${oMvv(lv)}"=="${oMvv(rv)}" && matchDeviceInteraction(lv,r9)}
private static Boolean comp_gets_any	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return false }
private static Boolean comp_event_occurs		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return false }
private Boolean comp_executes			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_arrives			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return (String)r9[sEVENT][sNM]=='email' && match(r9[sEVENT]?.jsonData?.from ?: sBLK,strEvalExpr(r9,mMv(rv))) && match(r9[sEVENT]?.jsonData?.message ?: sBLK,strEvalExpr(r9,mMv(rv2)))}
private static Boolean comp_happens_daily_at		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ bIs(r9,sWUP) }
private Boolean comp_changes		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueCacheChanged(r9,lv)!=null && matchDeviceInteraction(lv,r9)}
private Boolean comp_changes_to	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueCacheChanged(r9,lv)!=null && comp_receives(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_changes_away_from		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); return oldValue!=null && String.valueOf(oMvv(oldValue))==String.valueOf(oMvv(rv)) && matchDeviceInteraction(lv,r9)}
private Boolean comp_drops				(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); return oldValue!=null && dcast(r9,oMvv(oldValue))>dcast(r9,oMvv(lv))}
private Boolean comp_does_not_drop		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_drops(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_drops_below		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); Double v1=dcast(r9,oMvv(rv)); return oldValue!=null && dcast(r9,oMvv(oldValue))>=v1 && dcast(r9,oMvv(lv))<v1}
private Boolean comp_drops_to_or_below	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); Double v1=dcast(r9,oMvv(rv)); return oldValue!=null && dcast(r9,oMvv(oldValue))>v1 && dcast(r9,oMvv(lv))<=v1}
private Boolean comp_rises				(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); return oldValue!=null && dcast(r9,oMvv(oldValue))<dcast(r9,oMvv(lv))}
private Boolean comp_does_not_rise		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_rises(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_rises_above		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); Double v1=dcast(r9,oMvv(rv)); return oldValue!=null && dcast(r9,oMvv(oldValue))<=v1 && dcast(r9,oMvv(lv))>v1}
private Boolean comp_rises_to_or_above	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); Double v1=dcast(r9,oMvv(rv)); return oldValue!=null && dcast(r9,oMvv(oldValue))<v1 && dcast(r9,oMvv(lv))>=v1}
private Boolean comp_remains_below		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); Double v1=dcast(r9,oMvv(rv)); return oldValue!=null && dcast(r9,oMvv(oldValue))<v1 && dcast(r9,oMvv(lv))<v1}
private Boolean comp_remains_below_or_equal_to		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); Double v1=dcast(r9,oMvv(rv)); return oldValue!=null && dcast(r9,oMvv(oldValue))<=v1 && dcast(r9,oMvv(lv))<=v1}
private Boolean comp_remains_above		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); Double v1=dcast(r9,oMvv(rv)); return oldValue!=null && dcast(r9,oMvv(oldValue))>v1 && dcast(r9,oMvv(lv))>v1}
private Boolean comp_remains_above_or_equal_to		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); Double v1=dcast(r9,oMvv(rv)); return oldValue!=null && dcast(r9,oMvv(oldValue))>=v1 && dcast(r9,oMvv(lv))>=v1}
private Boolean comp_enters_range		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); if(oldValue==null)return false; Double ov=dcast(r9,oMvv(oldValue)); Double v=dcast(r9,oMvv(lv)); Double v1; v1=dcast(r9,oMvv(rv)); Double v2; v2=dcast(r9,oMvv(rv2)); if(v1>v2){ Double vv=v1; v1=v2; v2=vv }; return (ov<v1 || ov>v2) && v>=v1 && v<=v2}
private Boolean comp_exits_range		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); if(oldValue==null)return false; Double ov=dcast(r9,oMvv(oldValue)); Double v=dcast(r9,oMvv(lv)); Double v1; v1=dcast(r9,oMvv(rv)); Double v2; v2=dcast(r9,oMvv(rv2)); if(v1>v2){ Double vv=v1; v1=v2; v2=vv }; return ov>=v1 && ov<=v2 && (v<v1 || v>v2)}
private Boolean comp_remains_inside_of_range		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); if(oldValue==null)return false; Double ov=dcast(r9,oMvv(oldValue)); Double v=dcast(r9,oMvv(lv)); Double v1; v1=dcast(r9,oMvv(rv)); Double v2; v2=dcast(r9,oMvv(rv2)); if(v1>v2){ Double vv=v1; v1=v2; v2=vv }; return ov>=v1 && ov<=v2 && v>=v1 && v<=v2}
private Boolean comp_remains_outside_of_range		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); if(oldValue==null)return false; Double ov=dcast(r9,oMvv(oldValue)); Double v=dcast(r9,oMvv(lv)); Double v1; v1=dcast(r9,oMvv(rv)); Double v2; v2=dcast(r9,oMvv(rv2)); if(v1>v2){ Double vv=v1; v1=v2; v2=vv }; return (ov<v1 || ov>v2) && (v<v1 || v>v2)}
private Boolean comp_becomes_even		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv);return oldValue!=null && icast(r9,oMvv(oldValue))%i2!=iZ && icast(r9,oMvv(lv))%i2==iZ}
private Boolean comp_becomes_odd		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv);return oldValue!=null && icast(r9,oMvv(oldValue))%i2==iZ && icast(r9,oMvv(lv))%i2!=iZ}
private Boolean comp_remains_even		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv);return oldValue!=null && icast(r9,oMvv(oldValue))%i2==iZ && icast(r9,oMvv(lv))%i2==iZ}
private Boolean comp_remains_odd		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv);return oldValue!=null && icast(r9,oMvv(oldValue))%i2!=iZ && icast(r9,oMvv(lv))%i2!=iZ}

private Boolean comp_changes_to_any_of			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return valueCacheChanged(r9,lv)!=null && comp_is_any_of(r9,lv,rv,rv2,tv,tv2) && matchDeviceInteraction(lv,r9)}
private Boolean comp_changes_away_from_any_of		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ Map oldValue=valueCacheChanged(r9,lv); return oldValue!=null && comp_is_any_of(r9,oldValue,rv,rv2) && matchDeviceInteraction(lv,r9)}

private Boolean comp_stays				(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is(r9,lv,rv,rv2,tv,tv2)}
//private Boolean comp_stays_unchanged			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return true }
private Boolean comp_stays_unchanged			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return !comp_changes(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_not				(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_not(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_equal_to			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_equal_to(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_different_than		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_different_than(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_less_than			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_less_than(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_less_than_or_equal_to	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_less_than_or_equal_to(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_greater_than			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_greater_than(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_greater_than_or_equal_to	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_greater_than_or_equal_to(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_even			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_even(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_odd			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_odd(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_true			(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_true(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_false		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_false(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_inside_of_range		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_inside_of_range(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_outside_of_range		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_outside_of_range(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_any_of		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_any_of(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_away_from	(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_not_equal_to(r9,lv,rv,rv2,tv,tv2)}
private Boolean comp_stays_away_from_any_of		(Map r9,Map lv,Map rv=null,Map rv2=null,Map tv=null,Map tv2=null){ return comp_is_not_any_of(r9,lv,rv,rv2,tv,tv2)}

private void traverseStatements(node,Closure closure,parentNode=null,Map<String,Boolean> data=null,Map<String,Integer> lvl=null){
	if(!node)return
	//if a statements element, go through each item
	if(node instanceof List){
		Integer lastlvl= iMs(lvl,sV)
		lvl[sV]=lastlvl+i1
		Boolean lastRes= data!=null && bIs(data,sRESACT)
		for(Map item in (List<Map>)node)
			if(!item[sDI]){
				Boolean lastTimer= data!=null && bIs(data,sTIMER)
				if(data!=null && sMt(item)==sEVERY) data[sTIMER]=true // force downgrade of triggers
				traverseStatements(item,closure,parentNode,data,lvl)
				if(data!=null) data[sTIMER]=lastTimer
			}
		lvl[sV]=lastlvl
		if(data!=null) data[sRESACT]=lastRes
		return
	}

	//got a statement
	if(closure instanceof Closure) closure(node,parentNode,data,lvl)

	Boolean lastTimer= data!=null && bIs(data,sTIMER)
	String ty=sMt(node)
	if(ty==sON && data!=null) data[sTIMER]=true // force downgrade of triggers
	if(!data[sINMEM] && ty in [sIF,sWHILE,sREPEAT]){
		Integer n=doCcheck(sMs(node,sO),liMs(node,sC))
		if(n>i1)
			addWarning(node,'Found multiple trigger comparisons ANDed together')
		if(ty==sIF && node[sEI]){
			Integer bnt
			for(Map ei in liMs(node,sEI)){
				if(ei[sC]){
					bnt=doCcheck(sMs(ei,sO),liMs(ei,sC))
					if(bnt>i1)
						addWarning(node,'Found multiple trigger comparisons ANDed together in else if num: '+"${stmtNum(ei)}")
				}
			}
		}
	}

	Integer lastlvl= iMs(lvl,sV)
	if(ty==sDO) lvl[sV]=lastlvl-i1 // do does not increase the level
	//if the statement has substatements go through them
	if(node[sS] instanceof List) traverseStatements(liMs(node,sS),closure,node,data,lvl)
	if(ty==sDO) lvl[sV]=lastlvl

	if(data!=null) data[sTIMER]=lastTimer

	if(node[sE] instanceof List) traverseStatements(liMs(node,sE),closure,node,data,lvl)
}

@CompileStatic
private static Integer doCcheck(String grouping,List<Map> cndtns){
	Integer n
	n=iZ
	if(cndtns){
		Boolean isAND=grouping==sAND
		String ty
		for(Map cndtn in cndtns){
			ty=sMt(cndtn)
			if(ty==sGROUP){
				Integer i=doCcheck(sMs(cndtn,sO),liMs(cndtn,sC))
				n+=i
				if(!isAND && i==i1)n--
			}else if(ty==sCONDITION && sMs(cndtn,sCT)==sT && isAND)n++
		}
	}
	return n
}

private void traverseEvents(node,Closure closure,parentNode=null){
	if(!node)return
	//if a statements element, go through each item
	if(node instanceof List){
		for(item in (List)node) traverseEvents(item,closure,parentNode)
		return
	}
	if(closure instanceof Closure) closure(node,parentNode)
}

private void traverseConditions(node,Closure closure,parentNode=null,Map<String,Boolean> data=null){
	if(!node)return
	//if a statements element, go through each item
	if(node instanceof List){
		for(item in (List)node) traverseConditions(item,closure,parentNode,data)
		return
	}
	//got a condition
	if(node[sT]==sCONDITION && (closure instanceof Closure)) closure(node,parentNode)
	//if the statement has subcondition go through them
	if(node[sC] instanceof List){
		if(closure instanceof Closure)closure(node,parentNode)
		traverseConditions(liMs(node,sC),closure,node,data)
	}
}

private void traverseRestrictions(node,Closure closure,parentNode=null,Map<String,Boolean> data=null){
	if(!node)return
	if(data!=null)data[sRESACT]=parentNode!=null || bIs(data,sRESACT)
	//if a statements element, go through each item
	if(node instanceof List){
		for(item in (List)node) traverseRestrictions(item,closure,parentNode,data)
		return
	}
	//got a restriction
	if(node[sT]==sRESTRIC && (closure instanceof Closure)) closure(node,parentNode,data)
	//if the statement has subrestrictions go through them
	if(node[sR] instanceof List){
		if(closure instanceof Closure)closure(node,parentNode,data)
		traverseRestrictions(liMs(node,sR),closure,node,data)
	}
}

private void traverseExpressions(node,Closure closure,prm,Boolean isTrk,parentNode=null){
	if(!node)return
	//if a statements element, go through each item
	if(node instanceof List){
		for(item in (List)node) traverseExpressions(item,closure,prm,isTrk,parentNode)
		return
	}
	//got a statement
	if(closure instanceof Closure) closure(node,parentNode,prm,isTrk)
	//if the statement has subexpression go through them
	if(node[sI] instanceof List) traverseExpressions(liMs(node,sI),closure,prm,isTrk,node)
}

private void updateDeviceList(Map r9){
	List a=((List)mMs(r9,sDEVS)*.value.id).unique()
	if(a){
		wappUpdateSetting(sDV, [(sTYPE): 'capability', (sVAL): a])
		// settings update do not happen till next execution
		updateCacheFld(r9,sDEVS,[:]+mMs(r9,sDEVS),'updateDeviceList', true)
	}
	r9[sUPDDEVS]=false
}

@CompileStatic
private void updateCacheFld(Map r9,String nm,Object v,String s,Boolean gm){
	if(!gm || getCachedMaps(s)!=null){
		String id=sMs(r9,snId)
		getTheLock(id,s)
		Map nc=theCacheVFLD[id]
		if(nc){
			nc[nm]=v
			theCacheVFLD[id]=nc
			theCacheVFLD=theCacheVFLD
			mb()
		}
		releaseTheLock(id)
	}
}

@CompileStatic
private static addWarning(Map node,String msg){
	if(!node)return
	node[sW]=node[sW] ? (List)node[sW]:[]
	((List)node[sW]).push(msg) // modifies the code
}

@Field volatile static Map<String,List<String>> TRKINGFLD=[:]

@CompileStatic
/**
 * these are caching tracking triggers like device:attribute or variables (@, @@)
 * to automatcially update the tracking cache as events occur
 */
void addTrk(Map r9,String deviceId, String attr){
	if(deviceId.startsWith(sCLN) && attr!=sNL){
		String appStr= sAppId()
		List<String> trk= TRKINGFLD[appStr] ?: []
		Boolean isVar= attr.contains(sCLN) || attr.contains(sVARIABLE) || attr.contains(sAT)
		String tdev; tdev=deviceId
		if(isVar && deviceId==sMs(r9,sLOCID)) tdev=sBLK
		String chk= tdev+attr
		Boolean lge=isEric(r9)
		if(!fndTrk(tdev,attr)){
			trk << chk
			TRKINGFLD[appStr]= trk
			TRKINGFLD=TRKINGFLD
			if(lge)doLog(sDBG,"subscribeAll added track $chk to $trk")
		}else if(lge)doLog(sDBG,"subscribeAll found variable $isVar track $chk in $trk")
	}
}

/**
 * is Tracking trigger?
 */
@CompileStatic
Boolean fndTrk(String dev, String attr){
	String chk= dev+attr
	String appStr= sAppId()
	List<String> trk= TRKINGFLD[appStr] ?: []
	return (chk in trk)
}

/**
 * save tracking trigger value if cache does not have an entry
 */
@CompileStatic
void updTrkVal(Map r9,String dev, String attr,Long t){
	Boolean isVar= attr.contains(sVARIABLE+sCLN) || attr.contains(sAT)
	String tdev; tdev=dev
	if(isVar && dev==sMs(r9,sLOCID)) tdev=sBLK
	if(fndTrk(tdev,attr)){ // always save tracking trigger value for dev:attr or @, @@ variables
		// this is matching what evaluateOperand/evaluateComparison is doing
		String i= (!isVar ? dev+sCLN+attr : attr)
		Map val
		if(!isVar) val= [(sI):i, (sV): getDeviceAttribute(r9,dev,attr)]
		else{
			String vn= attr.contains(sVARIABLE+sCLN) ? sAT2+(attr.split(sCLN as String)[i1]) : sAT+(attr.split(sAT)[i1])
			val= [(sI):i, (sV): getVariable(r9,vn)]
		}
		if(!isEntryInCache(r9,val)) updateCache(r9,val,t)
		if(isEric(r9))
			myDetail r9,"found ${isVar ? sVARIABLE:sDEV} ${tdev}${attr}, updating cache with value $val  ${t}",iN2
	}
}

/**
 * update all trackers
@CompileStatic
void updTrkrs(Map r9){
	String dev,attr
	String appStr= sAppId()
	List<String> trk= TRKINGFLD[appStr] ?: []
	for(String tmp in trk){
		Boolean isVar= attr.contains(sVARIABLE+sCLN) || attr.contains(sAT)
		String[] a= tmp.split(sCLN)
		dev= !isVar ? sCLN+a[1]+sCLN : sMs(r9,sLOCID)
		attr= !isVar ? a[2] : tmp
		updTrkVal(r9,dev,attr,wnow())
	}
}
 */

@Field static final String sALLOWA = 'allowA'
@Field static final String sAVALS = 'avals'

/**
 * modify piston for subscriptions, warnings if !inMem, tracking in use devices, setup tracking trigger variables
 * @param doit - do subscriptions
 * @param inMem
 */
private void subscribeAll(Map r9,Boolean doit,Boolean inMem){
	String s='subscribeAll '
	Integer lg=iMs(r9,sLOGNG)
	Boolean lge= isEric(r9)
	if(eric())doLog(sDBG,s+"doit: $doit")
	try{
		if(!r9){ doLog(sERROR,s+"no r9"); return }
		Map<String,Integer> ss=[
			events  :iZ,
			controls:iZ,
			(sDEVS) :iZ,
		]
		String appStr= sAppId()
		TRKINGFLD[appStr]= []
		TRKINGFLD=TRKINGFLD

		assignSt(sALLOWR,false)
		String LID=sMs(r9,sLOCID)
		List<String> oLIDS= (List<String>)r9[sOLDLOC]
		if(doit){
			wremoveAllInUseGlobalVar()
			wunsubscribe()
			r9[sDEVS]=[:]
			updateCacheFld(r9,sDEVS,[:],s,true)
			if(lg>i1)trace "Subscribing to devices...",r9,i1
		}
		Map<String,Map> devices=[:]
		Map rawDevices,curStatement,curCondition
		rawDevices=[:]
		Map<String,Map> subsMap=[:]
		Boolean hasTriggers,dwnGrdTrig
		hasTriggers=false
		Map<String,Boolean> stmtData=[(sTIMER):false,(sINMEM):inMem] // downGrade of triggers
		Map<String,Integer> stmtLvl=[(sV):iZ]
		dwnGrdTrig=false // EVERY statement only has timer trigger, ON only has its event
		curStatement=null
		curCondition=[:]
		String never='never'
		String always='always'

		Closure incrementDevices
		incrementDevices={String deviceId, String cmpTyp, String attr ->
			if(isWcDev(deviceId)){
				Map d_did=devices[deviceId] ?: [:]
				List<String> attl=d_did[sA] ? (List<String>)d_did[sA] : []
				List<String> ladd= attr && attr!=sTIME && !(attr in attl) ? [attr] :[]
				devices[deviceId]= [
					(sC): (cmpTyp? i1:iZ) +(d_did[sC]? iMs(d_did,sC) :iZ),
					(sR): i1+(d_did[sR] ? iMs(d_did,sR) :iZ),
					(sA): attl+ladd
				]
				if(doit && !(deviceId in [LID]) && !rawDevices[deviceId])
					rawDevices[deviceId]= getDevice(r9,deviceId) // add in use device
			}
		}

		//traverse all statements
		Closure expressionTraverser
		Closure operandTraverser
		Closure eventTraverser
		Closure conditionTraverser
		Closure restrictionTraverser
		Closure statementTraverser

		expressionTraverser={ Map expression,parentExpression,String cmpTyp,Boolean isTrk ->
			String subsId,deviceId,attr,exprID
			subsId=sNL
			deviceId=sNL
			attr=sNL
			exprID=sMs(expression,sID)
			if(sMt(expression)==sDEV && exprID){
				if(exprID in oLIDS) exprID=LID
				//if(eric())doLog(sDBG,s+"expressionTrav checking expression: $expression")
				deviceId=exprID
				//incrementDevices(deviceId,cmpTyp)
				attr=sMa(expression)
				subsId=deviceId+attr
			}
			String exprX=sMs(expression,sX)
			if(sMt(expression)==sVARIABLE && exprX && exprX.startsWith(sAT)){
				deviceId=LID
				if(exprX.startsWith(sAT2)){
					String vn=exprX.substring(i2)
					Map hg=wgetGlobalVar(vn) // check if it exists
					waddInUseGlobalVar(r9,vn,true,!!hg)
					if(hg){
						subsId=vn
						attr=sVARIABLE+sCLN+vn
					}else warn "hub varible not found while subscribing: $vn",r9
				}else{
					subsId=exprX
					attr=sMs(r9,sINSTID)+sDOT+exprX
					waddInUseGlobalVar(r9,exprX,false)
				}
			}
			if(subsId!=sNL && deviceId!=sNL){
				subsMap[subsId]= subsMap[subsId] ?: [(sD):deviceId, (sA):attr, (sC):[]]
				String ct; ct= sMt(subsMap[subsId]) ?: sNL
				if(ct==sTRIG || cmpTyp==sTRIG){
					ct=sTRIG
				}else ct=ct ?: cmpTyp
				subsMap[subsId]= [(sD):deviceId, (sA):attr, (sT):ct, (sC): (List)subsMap[subsId][sC] + (cmpTyp && curCondition ? [curCondition] : []) ]
				if(ct==sTRIG && isTrk){ addTrk(r9,deviceId,attr) }
				incrementDevices(deviceId,cmpTyp,attr)
			}
		}

		operandTraverser={ Map node,Map operand,Map value,String cmpTyp, Boolean isTrk ->
			if(!operand)return
			switch(sMt(operand)){
				case sP: //physical device
					String deviceId
					for(String mdeviceId in expandDeviceList(r9,liMd(operand),true)){
						deviceId=mdeviceId
						if(deviceId in oLIDS) deviceId=LID
						String attr=sMa(operand)
						incrementDevices(deviceId,cmpTyp,attr)
						String subsId=deviceId+attr
						subsMap[subsId]= subsMap[subsId] ?: [(sD):deviceId, (sA):attr, (sC):[]]
						//if we have any trigger it takes precedence over anything else
						String ct; ct= sMt(subsMap[subsId]) ?: sNL

						Boolean allowAval
						allowAval= subsMap[subsId][sALLOWA]!=null ? bIs(subsMap[subsId],sALLOWA) : (Boolean)null
						List<String> avals; avals= allowAval && subsMap[subsId][sAVALS] ? (List)subsMap[subsId][sAVALS] : []
						String msgVal; msgVal=sNL

						if(ct==sTRIG || cmpTyp==sTRIG){
							ct=sTRIG
							hasTriggers=true
							String attrVal; attrVal=sNL
							allowAval= allowAval==(Boolean)null ? true : allowAval

							if(allowAval && (sMs(node,sCO) in lSPECIFIC) && value && sMt(value)==sC && value[sC]){
								attrVal=sMs(value,sC)
							}else allowAval=false
							if(allowAval && attrVal!=sNL){
								if(!(attrVal in avals)) avals<<attrVal
								msgVal="Attempting Attribute $attr value "+avals
							}else{
								allowAval=false
								msgVal="Using Attribute $attr"
								avals=[]
							}
						}else ct=ct?:cmpTyp
						subsMap[subsId]= [(sD):deviceId, (sA):attr, (sT):ct, (sC): (List)subsMap[subsId][sC] + (cmpTyp ? [node] : []), (sALLOWA):allowAval, (sAVALS):avals]

						if(ct==sTRIG && isTrk){ addTrk(r9,deviceId,attr) }

						if(doit){
							def a; a=null
							if(deviceId!=LID && deviceId.startsWith(sCLN)){
								a=rawDevices[deviceId]
								if(!a && eric())doLog(sDBG,s+"operandTrav device not found deviceId: $deviceId")
							}
							if(lg>i2 && msgVal){
								msgVal+=' subscription'
								if(a)msgVal+=" for device: $a"
								debug msgVal,r9
							}
						}
					}
					break
				case sV: //virtual device
					String deviceId=LID
					//if we have any trigger, it takes precedence over anything else
					String subsId,attr,ct
					subsId=sNL
					attr=sNL
					String operV=fixAttr(sMv(operand))
					String tsubId=deviceId+operV
					switch(operV){
						case sPSTNRSM:
							assignSt(sALLOWR,true)

						case sTIME:
						case sDATE:
						case sDTIME:
						case sMODE:
						case sTILE:
						case sPWRSRC:
						case 'cloudBackup':
						case 'lowMemory':
						case 'systemStart':
						case 'severeLoad':
						case 'zigbeeOff':
						case 'zigbeeOn':
						case 'zwaveCrashed':
						case 'sunriseTime':
						case 'sunsetTime':
						case sHSMSTS:
						case sHSMALRT:
						case sHSMSARM:
						case sHSMRULE:
						case sHSMRULES:
							subsId=tsubId
							attr=operV
							break
						case 'email':
							subsId="$deviceId${operV}".toString()+sMs(r9,sID)
							attr='email.'+sMs(r9,sID) // receive email does not work
							break
						case 'ifttt':
							if(value && sMt(value)==sC && value[sC]){
								Map<String,String> options= (Map)VirtualDevices()[operV]?.o
								String item= options ? options[sMs(value,sC)] : sMs(value,sC)
								if(item){
									subsId= "$deviceId${operV}${item}".toString()
									attr= "${operV}.${item}".toString()
								}
							}
							break
					}
					incrementDevices(deviceId,cmpTyp,attr)
					if(subsId!=sNL && attr!=sNL){
						subsMap[subsId]= subsMap[subsId] ?: [(sD):deviceId, (sA):attr, (sC):[]]
						ct= sMt(subsMap[subsId]) ?: sNL
						if(ct==sTRIG || cmpTyp==sTRIG){
							ct=sTRIG
							hasTriggers=true
						}else ct=ct?:cmpTyp
						subsMap[subsId]= [(sD):deviceId, (sA):attr, (sT):ct, (sC): (List)subsMap[subsId][sC] + (cmpTyp ?[node]:[])]
						if(ct==sTRIG && isTrk){ addTrk(r9,deviceId,attr) }
						break
					}
					break
				case sX: // variable
					String operX=sMs(operand,sX)
					if(operX && operX.startsWith(sAT)){
						String subsId,attr,ct
						subsId=sNL
						attr=sNL
						if(operX.startsWith(sAT2)){
							String vn=operX.substring(i2)
							Map hg=wgetGlobalVar(vn) // check if it exists
							waddInUseGlobalVar(r9,vn,true,!!hg)
							if(hg){
								subsId=vn
								attr=sVARIABLE+sCLN+vn
							}else warn "hub varible not found while subscribing: $vn",r9
						}else{
							subsId=operX
							attr=sMs(r9,sINSTID)+sDOT+operX
							waddInUseGlobalVar(r9,operX,false)
						}
						if(subsId!=sNL && attr!=sNL){
							subsMap[subsId]= subsMap[subsId] ?: [(sD):LID, (sA):attr, (sC):[]]
							ct= sMt(subsMap[subsId]) ?: sNL
							if(ct==sTRIG || cmpTyp==sTRIG){
								ct=sTRIG
								hasTriggers=true
							}else ct=ct?:cmpTyp
							subsMap[subsId]= [(sD):LID, (sA):attr, (sT):ct, (sC):(List)subsMap[subsId][sC] + (cmpTyp ? [node]:[])]
							if(ct==sTRIG && isTrk){ addTrk(r9,LID,attr) }
						}
					}
					break
				case sC: //constant
				case sE: //expression
					traverseExpressions(mMs(operand,sEXP)?.i,expressionTraverser,cmpTyp,isTrk)
					break
			}
		}

		eventTraverser={ Map event,parentEvent ->
			if(event[sLO]){
				String cmpTyp=sTRIG
				operandTraverser(event,mMs(event,sLO),null,cmpTyp,false)
			}
		}

		List<String> ltsub=['happens_daily_at']
		conditionTraverser={ Map cndtn,parentCondition ->
			Map svCond=curCondition
			curCondition=cndtn
			String co=sMs(cndtn,sCO)
			if(co){
				Map comparison=(Map)AllComparisons()[co]
				String cmpTyp; cmpTyp=sCONDITION
				Boolean isTrig=comparison!=null && bIs(comparison,sTRIG)
				// sSM subscription method, always, never, auto/''
				if(comparison!=null){
					Boolean isTracking= isTrig && !(co in lNTRK)
					if(isTrig){
						Boolean didDwnGrd= dwnGrdTrig || sMs(cndtn,sSM)==never // not force condition
						if(!didDwnGrd){
							hasTriggers=true
							cmpTyp=sTRIG //subscription method
						}
						if(!inMem){
							String m,tm
							m=sNL
							String tm1="trigger comparison type"
							tm=tm1
							String tn=" that relies on runtime "
							Boolean isSub= co in ltsub // ['happens_daily_at']
							Boolean isStays= co.startsWith(sSTAYS)
							Map lo=mMs(cndtn,sLO) ?: [:]
							Integer dsz= lo[sD] ? liMd(lo).size():iZ
							Boolean isAll= isTracking && !isStays && lo && sMa(lo) && sMs(lo,sG)==sALL &&
									(dsz>i1 || (dsz==i1 && !isWcDev(liMd(lo)[iZ])))
							if(didDwnGrd) m= "downgraded "+tm+" not subscribed to in EVERY or ON statement, or forced never subscribe - should use condition comparison rather than trigger"
							else{
								Boolean isbadVar
								isbadVar= isTracking && lo && sMt(lo)==sX && !(sMs(lo,sX).startsWith(sAT))
								/*if(isbadVar){
									Map a=getVariable(r9,sMs(lo,sX))
									if(!isErr(a)) isbadVar=sMt(a)!=sDEV
								}*/
								Boolean localTracking; localTracking=isTracking
								if(isbadVar) tm += " using a non global or non device variable,"
								if(isAll){
									tm += " using multiple devices with ALL (ANDed trigger),"
								}else{
									if(isTracking){
										if(tm==tm1){ tm=sBLK; localTracking=false }
										else tm+=tn+"event tracking,"
									}else
										if(isSub) tm+=tn+"timer scheduling,"
								}
								if(localTracking || isSub || isbadVar){
									if(isbadVar) m=tm
									if(isAll) m=tm
									else if(stmtLvl[sV]>i2) m="nested (level ${stmtLvl[sV]}) "+tm
									else if(bIs(stmtData,sRESACT)) m="nested by restriction "+tm
									if(m) m +=" that may cause errors"
								}
							}
							if(m) addWarning(curStatement,'Found '+m+"; comparison: $co  num: ${stmtNum(cndtn)}")
						}
					}
					if(cndtn.containsKey(sS)) cndtn.remove(sS) // modifies the code
					cndtn[sCT]=(String)cmpTyp.take(i1) // modifies the code
					Integer pCnt= comparison[sP]!=null ? iMs(comparison,sP):iZ
					Integer i
					for(i=iZ;i<=pCnt;i++){
						//get the operand to parse
						Map operand=(i==iZ ? mMs(cndtn,sLO) : (i==i1 ? mMs(cndtn,sRO) : mMs(cndtn,sRO2)))
						operandTraverser(cndtn,operand,mMs(cndtn,sRO),cmpTyp,isTracking)
					}
				}
			}
			if(cndtn[sTS] instanceof List)traverseStatements(liMs(cndtn,sTS),statementTraverser,cndtn,stmtData,stmtLvl)
			if(cndtn[sFS] instanceof List)traverseStatements(liMs(cndtn,sFS),statementTraverser,cndtn,stmtData,stmtLvl)
			//noinspection GrReassignedInClosureLocalVar
			curCondition=svCond
		}

		restrictionTraverser={ Map restriction,Map parentRestriction,Map data ->
			String rco=sMs(restriction,sCO)
			if(rco){
				Map comparison=(Map)AllComparisons()[rco]
				if(comparison!=null){
					Integer pCnt= comparison[sP]!=null ? iMs(comparison,sP):iZ
					Integer i
					for(i=iZ;i<=pCnt;i++){
						//get the operand to parse
						Map operand= (i==iZ ? mMs(restriction,sLO) : (i==i1 ? mMs(restriction,sRO) : mMs(restriction,sRO2)))
						operandTraverser(restriction,operand,null,sNL,false)
					}
				}
			}
		}

		List<String> lsub=[sIF,sFOR,sWHILE,sREPEAT,sSWITCH,sON,sEACH,sEVERY]
		statementTraverser={ Map node,parentNode,Map<String,Boolean>data,Map<String,Integer>lvl ->
			dwnGrdTrig= data!=null && bIs(data,sTIMER)
			if(node[sR])traverseRestrictions(node[sR],restrictionTraverser,node,data)

			String t=sMt(node)
			if(t!=sACTION){
				String attr=sMa(node)
				for(String mdeviceId in liMd(node)){
					String deviceId; deviceId=mdeviceId
					if(deviceId in oLIDS)deviceId=LID
					if(deviceId) incrementDevices(deviceId,sNL,attr)
				}
			}

			Integer lastlvl
			lastlvl=null
			Map lastStatement; lastStatement=null
			if(t?.length()>i1){
				lastStatement=curStatement
				curStatement=node
				node.remove(sW) // modifies the code
				lastlvl=lvl[sV]
				if(!inMem && lastlvl>i1){
					String m= ' are designed to be top-level statements and should not be used inside other statements.'
					switch(t){
						case sEVERY: addWarning(node,'Every timers'+m+' If you need a conditional timer, please look into using a while loop instead.'); break
						case sON: addWarning(node,'On event statements'+m); break
					}
				}
				//doLog(sWARN,"found statement $t level ${lvl.v}")
				if(t in lsub) lvl[sV]=lastlvl+i1
			}
			switch(t){
				case sACTION:
					for(String mdeviceId in expandDeviceList(r9,liMd(node),true)){
						String deviceId; deviceId=mdeviceId
						if(deviceId in oLIDS)deviceId=LID
						incrementDevices(deviceId,sNL,sNL)
					}
					if(node[sK]){
						for(Map k in liMs(node,sK)){
							String kc=sMs(k,sC)
							if(kc in ['setLocationMode', 'setAlarmSystemStatus'] || kc.contains('Tile') ||
										kc.contains(sLIFX) || kc.contains('Rule') ||
										kc.contains('Piston')){
								if(lge)myDetail r9,"Found location command $kc",iN2
								incrementDevices(LID,sNL,kc)
							}

							List<Map> prms= liMs(k,sP)
							if(kc==sSTVAR && prms && prms.size()==i2){
								Map v=prms[iZ]
								String vn=sMs(v,sX)
								if(sMt(v)==sX && vn){
									//get variable {t:type,v:value, ic: isConst, fx: isFixed, vn: name}
									Map a=getVariable(r9,vn,true)
									String m= bIs(a,'ic') ? 'const' : ( bIs(a,'fx') ? 'pre initialized' : sNL)
									if(!inMem && m) addWarning(node,'Found Set variable to a '+m+" variable: $vn num: ${stmtNum(k)}")
								}
							}

							traverseStatements(k[sP] ?: [],statementTraverser,k,data,lvl)
						}
					}
					break
				case sIF:
					if(node[sEI]){
						Integer lastlvl1=lvl[sV]
						lvl[sV]=lastlvl1+i1
						for(Map ei in liMs(node,sEI)){
							traverseConditions(ei[sC] ?: [],conditionTraverser,ei,data)
							traverseStatements(ei[sS] ?: [],statementTraverser,ei,data,lvl)
						}
						lvl[sV]=lastlvl1
					}
				case sWHILE:
				case sREPEAT:
					traverseConditions(node[sC],conditionTraverser,node,data)
					break
				case sON:
					List<Map>evts= liMs(node,sC)
					if(evts) traverseEvents(evts, eventTraverser)
					else if(!inMem) addWarning(node,'On event statement without events')
					break
				case sSWITCH:
					if(node.containsKey(sS)) node.remove(sS) // modifies the code
					if(node.containsKey(sCT)){ node.remove(sCT) } // modifies the code
					operandTraverser(node,mMs(node,sLO),null,sCONDITION,false)
					for(Map c in liMs(node,sCS)){
						operandTraverser(c,mMs(c,sRO),null,sNL,false)
						//if case is a range traverse the second operand too
						if(sMt(c)==sR) operandTraverser(c,mMs(c,sRO2),null,sNL,false)
						if(c[sS] instanceof List) traverseStatements(liMs(c,sS),statementTraverser,node,data,lvl)
					}
					break
				case sEVERY:
					hasTriggers=true
					break
			}
			if(t?.length()>i1){
				lvl[sV]=lastlvl
				//noinspection GrReassignedInClosureLocalVar
				curStatement=lastStatement
			}
		}

		Map r9p=mMs(r9,sPISTN)

		if(r9p[sR])traverseRestrictions(liMs(r9p,sR),restrictionTraverser,null,stmtData)

		if(r9p[sS])traverseStatements(liMs(r9p,sS),statementTraverser,null,stmtData,stmtLvl)

		//device variables could be device type variable, or another type using device attributes to fill in
		for(Map variable in ((List<Map>)oMv(r9p)).findAll{ Map it -> /*(String)it.t==sDEV && */ it.v!=null && mMv(it).d!=null && mMv(it).d instanceof List }){
			//if(eric())doLog(sDBG,s+"checking variable ${variable}")
			for(String mdeviceId in liMd(mMv(variable))){
				String deviceId; deviceId=mdeviceId
				if(deviceId in oLIDS) deviceId=LID
				//if(eric())doLog(sDBG,s+"checking variable deviceId: $deviceId")
				if(isWcDev(deviceId)){
					if(sMt(variable)!=sDEV) // variable definitions raise ref count except device variables
						incrementDevices(deviceId,sNL,sNL)
					else if(doit && deviceId!=LID && !rawDevices[deviceId]) // device variable - just add in use device
						rawDevices[deviceId]=getDevice(r9,deviceId)
				}else{
					operandTraverser(variable,mMv(variable),null,sNL,false)
					break
				}
			}
		}
		Map<String,Integer> dds=[:]
		List<String>nosub=[sTILE,sPSTNRSM]
		Boolean des=!!gtPOpt(r9,sDES)
		if(doit && lg>i2){
			String m
			m= des ? 'disable event subscriptions':sBLK
			m += !des && !hasTriggers ? 'no triggers, promoting conditions':sBLK
			if(m)debug 'subscriptions: '+m,r9
		}
		if(doit&&lge)myDetail r9,"START devices: $devices",iN2
		Integer logcnt; logcnt=iZ
		String dh='deviceHandler'
		for(Map.Entry<String,Map>subscription in subsMap){
			Map sub=(Map)subscription.value
			List<Map> subconds=liMs(sub,sC)
			String sst=sMt(sub)
			String devStr=sMs(sub,sD)
			String altSub; altSub=never
			String st, stt; st= sBLK; stt=sBLK
			if(lg>i2){
				st= (String)subscription.key
				stt= " piston resume event due to subscription control: ${st}"
			}
			if(doit && lge && logcnt>iZ)myDetail r9,"devices: $devices",iN2
			logcnt++
			if(doit && lge)myDetail r9,"evaluating sub: $subscription",iN2
			for(Map cndtn in subconds){
				if(cndtn){
					if(cndtn.containsKey(sS)) cndtn.remove(sS) // modifies the code
					String tt0=sMs(cndtn,sSM)
					altSub= tt0==always ? tt0:(altSub!=always && tt0!=never ? tt0:altSub)
				}
			}
			if(doit && lg>i2){
				if(!des && altSub in [never,always])debug 'subscribe override: '+altSub+" ${st}",r9
			}
			Boolean skip,allowA
			skip=true
			allowA=bIs(sub,sALLOWA)
			String a; a=sMa(sub)
			// check for disabled event subscriptions
			Boolean isSkipA= a in [sLSTACTIVITY,sSTS,sROOMID,sROOMNM] // these do not generate events
			if(!des && !isSkipA && sst && !!subconds && altSub!=never && (sst==sTRIG || altSub==always || !hasTriggers)){
				def device= devStr.startsWith(sCLN)? getDevice(r9,devStr):null
				allowA=!!allowA
				String nattr=fixAttr(a)
				if(a!=nattr){
					a=nattr
					allowA=false
				}
				if(device!=null){
					for(Map cndtn in subconds){
						if(cndtn){
							String ct,t1
							ct=sMs(cndtn,sCT)
							t1=sMs(cndtn,sSM)
							if(ct==sNL){
								if(sMt(cndtn)==sSWITCH){ cndtn[sCT]=sC; ct=sC } // modifies the code
								if(sMt(cndtn)==sEVENT){ cndtn[sCT]=sT; ct=sT } // modifies the code
							}
							cndtn[sS]= t1!=never && (ct==sT || t1==always || !hasTriggers) // modifies the code
							if(bIs(cndtn,sS) && a==sPSTNRSM && !bIs(gtState(),sALLOWR)){
								assignSt(sALLOWR,true)
								if(lg>i2)debug "Enabling"+stt,r9
							}
							if(doit && lge)myDetail r9,"processed condition: $cndtn",iN2
							if(devices[devStr]){
								Integer c=iMs(devices[devStr],sC)
								devices[devStr][sC]=c>iZ ? c-i1:iZ
								devices[devStr][sR]=iMs(devices[devStr],sR)-i1
							}
						}
					}
					Integer n
					n=ss.events
					if(!(a in (LT1+nosub))){ // timers, tile, pistonResume events don't have hub subscription
						List<String> avals=(List)sub[sAVALS]
						if(allowA && avals.size()<i9){
							for(String aval in avals){
								String myattr=a+sDOT+aval
								if(doit){
									if(lg>iZ)info "Subscribing to $device.${myattr}...",r9
									wsubscribe(device,myattr,dh)
									skip=false
								}
								n+=i1
							}
						}else{
							if(doit){
								if(lg>iZ)info "Subscribing to $device.${a}...",r9
								wsubscribe(device,a,dh)
								skip=false
							}
							n+=i1
						}
					}
					if(a in nosub) n+=i1

					ss.events=n
					String didS=dvStr(device)
					if(!dds[didS]){
						ss[sDEVS]=ss[sDEVS]+i1
						dds[didS]=i1
					}
				}else{
					if(doit)error "Failed subscribing to $devStr.${a}, device not found",r9
				}
			}else{
				for(Map cndtn in subconds){
					if(cndtn){
						cndtn[sS]=false // modifies the code
						if(a==sPSTNRSM && bIs(gtState(),sALLOWR)){
							assignSt(sALLOWR,false)
							if(lg>i2)debug "Disabled"+stt,r9
						}
						if(devices[devStr]){ //incrementDevices
							Integer c=iMs(devices[devStr],sC)
							devices[devStr][sC]=c>iZ ? c-i1:iZ
							devices[devStr][sR]=iMs(devices[devStr],sR)-i1
						}
					}
				}
			}
			if(skip && doit && lg>i2) debug "SKIPPING sub: ${st}",r9
		}
		if(doit && lge)myDetail r9,"END devices: $devices",iN2

		//not using fake subscriptions; piston has devices inuse in settings
		for(d in devices.findAll{ (des || (iMs(it.value,sR)-iMs(it.value,sC))>iZ) }){
		//incrementDevices
			def device
			String d1=(String)d.key
			device= isWcDev(d1) ? getDevice(r9,d1):null
			if(device!=null){
				String didS=dvStr(device)
				if(lg>i1 && doit)trace "Piston utilizes ${gtLbl(device)} ${d.value.a}...",r9
				ss.controls+=i1
				if(!dds[didS]){
					ss[sDEVS]=ss[sDEVS]+i1
					dds[didS]=i1
				}
			}
		}
		if(doit){
			//save in use devices
			List deviceList=rawDevices.collect{ it.value }
			deviceList.removeAll{ it==null }
			r9[sDEVS]=deviceList.collectEntries{ it -> [(hashD(r9,it)):it] }
			r9[sDEVS]=r9[sDEVS] ?:[:]
			updateDeviceList(r9)
			rawDevices=null

			assignSt(sSUBS,ss)
			if(lg>i1)trace msg,r9

			//subscribe(app,appHandler)
			String t,t0,e
			e='executeHandler'
			t=sMs(r9,sID)
			wsubscribe(gtLocation(),t,e)
			if(lg>i2)debug "Subscribing to ${gtLname()}.pistonID...($t)",r9
			t0=hashId(r9,r9[snId])
			if(t0!=t){
				wsubscribe(gtLocation(),t0,e) //backwards
				if(lg>i2)debug "Subscribing to ${gtLname()}.oldpistonID...($t0)",r9
			}

			Long n=wnow()
			Map event=[(sT):n,(sDEV):cvtLoc(),(sNM):sTIME,(sVAL):n,(sSCH):[(sT):lZ,(sS):iZ,(sI):iN9]]
			r9.put(sCACHE,[:]) // reset followed by
			assignSt(sCACHE,[:])
			stNeedUpdate()
			updateCacheFld(r9,sCACHE,[:],s,true)

			executeEvent(r9,event)
			processSchedules r9,true
			//save cache collected through dummy run
			for(Map.Entry<String,Map> item in msMs(r9,sNWCACHE)){
				((Map)r9[sCACHE])[(String)item.key]=item.value
			}
			assignSt(sCACHE,mMs(r9,sCACHE))
			updateCacheFld(r9,sCACHE,[:]+mMs(r9,sCACHE),s,true)

			Map myRt=shortRtd(r9)
			myRt[sT]=n
			relaypCall(myRt)
		}

	}catch(all){
		error "An error has occurred while subscribing: ",r9,iN2,all
	}
}

@CompileStatic
private List<String> expandDeviceList(Map r9,List<String> devs,Boolean localVarsOnly=false){
	Boolean mlocalVars=false	//allowing global vars
	List<String>devices=devs
	List<String> res; res=[]
	if(devices){
		for(String deviceId in devices){
			if(deviceId){
				if(isWcDev(deviceId)) res.push(deviceId)
				else{
					if(mlocalVars){
						//during subscriptions we can use local vars only to make sure we don't subscribe to "variable" lists of devices
						Map var=mMs(mMs(r9,sLOCALV),deviceId)
						if(var && sMt(var)==sDEV && oMv(var) instanceof Map){
							Map m=mMv(var)
							if(sMt(m)==sD && m[sD] instanceof List)res+=liMd(m)
						}
					}else{
						Map var=getVariable(r9,deviceId)
						if(sMt(var)==sDEV)
							res+=(oMv(var) instanceof List) ? liMv(var):[]
						else{
							//if(oMv(var) && eric())doLog(sDBG,"expandDeviceList: checking variable sMv(var): ${sMv(var)}")
							def device=oMv(var) ? getDevice(r9,scast(r9,sMv(var))):null
							if(device!=null)res+= [hashD(r9,device)]
						}
					}
				}
			}
		}
	}
	return res.unique()
}

//def appHandler(evt){
//}

/** return a device or location object for idOrName */
@CompileStatic
private getDevice(Map r9,String idOrName, Boolean rptMissing=true){
	if(idOrName in (List<String>)r9[sALLLOC])return gtLocation()
	if(!idOrName)return null
	String d=sDEVS
	r9[d]=r9[d] ?:[:]
	Map<String,Object> dM=mMs(r9,d)
	def t0=dM[idOrName]
	def device
	if(t0!=null){
		device=t0
	}else{
		Map.Entry<String,Object> found=dM.find{Map.Entry<String,Object> it -> gtLbl(it.value)==idOrName }
		if(found!=null){
			device=found.value
			dM[idOrName]=device // cache label as alias so future lookups are O(1)
		}
	}
	if(device==null){
		String aD=sALLDEVS
		if(r9[aD]==null){
			Map msg=timer "Device missing from piston. Loading all from parent",r9
			r9[aD]=wlistAvailableDevices(true)
			if(isDbg(r9))debug msg,r9
		}
		if(r9[aD]!=null){
			Map.Entry<String,Object> deviceEnt=mMs(r9,aD).find{ Map.Entry<String,Object> it -> idOrName==(String)it.key || idOrName==gtLbl(it.value) }
			if(deviceEnt!=null){
				device=deviceEnt.value
				r9[sUPDDEVS]=true
				r9[d][(String)deviceEnt.key]=device
			}
		}
		if(device==null && rptMissing){
			error "Device (${idOrName}) was not found. Please review your piston and webCoRE device access settings.",r9
		}
	}
	return device
}

@Field static List<String> LTHR=[]
@Field static List<String> FATTRS=[]
private static void fill_FATTRS(){
	if(LTHR.size()==iZ)
		LTHR= [sORIENT,sAXISX,sAXISY,sAXISZ]
	if(FATTRS.size()==iZ)
		FATTRS= LTHR+[sALRMSSTATUS,sALRMSYSALRT,sALRMSYSEVT,sALRMSYSRULE, sALRMSYSRULES]
}

private static String fixAttr(String attr){
	if(!(attr in FATTRS)) return attr
	if(attr in LTHR) return sTHREAX
	switch(attr){
		case sALRMSSTATUS:
			return sHSMSTS
		case sALRMSYSALRT:
			return sHSMALRT
		case sALRMSYSEVT:
			return sHSMSARM
		case sALRMSYSRULE:
			return sHSMRULE
		case sALRMSYSRULES:
			return sHSMRULES
		default:
			return attr
	}
}

private static Map fixVector(String s1){
	String s; s=s1
	s=s.trim()
	s=s.replace(sSPC,sBLK)
	Map xyz; xyz=[:]
	if(stJson1(s)){
		s=s.replace(sLB,sOB)
		s=s.replace(sRB,sCB)
		s=s.replace('x:','"x":')
		s=s.replace('y:','"y":')
		s=s.replace('z:','"z":')
	}
	if(stJson(s))xyz= (Map)new JsonSlurper().parseText(s)
	return xyz
}

/**
 *
 * @param ixyz - may be string or a map
 */
private static gtThreeAxisVal(ixyz, String attr){
	Map xyz
	if(ixyz instanceof String){
		xyz= fixVector((String)ixyz)
	} else xyz= ixyz instanceof Map ? (Map)ixyz : [:]
	switch(attr){
		case sORIENT:
			return getThreeAxisOrientation(xyz)
		case sAXISX:
			return xyz[sX]
		case sAXISY:
			return xyz[sY]
		case sAXISZ:
			return xyz[sZ]
		case sTHREAX:
			return xyz
	}
}

private static String getThreeAxisOrientation(Map m /*, Boolean getIndex=false */){
	if(m && m[sX]!=null && m[sY]!=null && m[sZ]!=null){
		Integer dx,dy,dz,x,y,z,side
		dx=iMs(m,sX)
		dy=iMs(m,sY)
		dz=iMs(m,sZ)
		x=Math.abs(dx*d1).toInteger()
		y=Math.abs(dy*d1).toInteger()
		z=Math.abs(dz*d1).toInteger()
		side=(x>y ? (x>z ? iZ:i2):(y>z ? i1:i2))
		side+=(side==iZ && dx<iZ) || (side==i1 && dy<iZ) || (side==i2 && dz<iZ) ? i3:iZ
//		if(getIndex)return side
		List<String> orientations=['rear','down','left','front','up','right']
		return orientations[side]+' side up'
	}
	return sNL
}


/**
 * get current attribute value for device
 * @param skipCurEvt - false means we can use current event attribute value if device and attribute match
 */
private getDeviceAttributeValue(Map r9,device,String attr,Boolean skipCurEvt=false){
	Map ce=mMs(r9,sEVENT)
	String r9EvN=ce!=null ? sMs(ce,sNM):sBLK
	Boolean r9EdID=ce!=null ? sMs(ce,sDEV)==hashD(r9,device):false

	String s; s=sBLK
	Boolean lge=isEric(r9)
	if(lge){
		s= "getDeviceAttributeValue device: $device attr: $attr skipCurEvt: $skipCurEvt"
		myDetail r9,s+" ce: ${ce}",i1
	}
	def res; res=null
	if(!skipCurEvt && r9EvN==attr && r9EdID){
		res= ce[sVAL]
		if(lge)myDetail r9,s+" event $res (${myObj(res)})",iN2
	}else{
		String nattr=fixAttr(attr)
		try{
			if(nattr in ListLSTSTSTHREAX){
				switch(nattr){
					case sLSTACTIVITY:
						res= ((Date)device.getLastActivity())?.getTime()
						break
					case sSTS:
						res= (String)device.getStatus() // ACTIVE/INACTIVE
						break
					case sROOMID:
						res= (Long)device.getRoomId()
						break
					case sROOMNM:
						res= (String)device.getRoomName()
						break
					case sTHREAX:
						def xyz
						xyz= !skipCurEvt && r9EvN==sTHREAX && r9EdID && ce && ce[sVAL] ? ce[sVAL] :null
						if(lge && xyz)myDetail r9,s+" event $xyz (${myObj(xyz)})",iN2
						Boolean errmsg; errmsg=false
						if(!xyz){
							try{
								xyz=device.currentValue(sTHREAX,true)
								if(lge && xyz)myDetail r9,s+" read $xyz (${myObj(xyz)})",iN2
							}catch(al){
								error gtAttrErr(device)+sTHREAX+sCLN,r9,iN2,al
								errmsg=true
							}
						}
						if(xyz){
							res= gtThreeAxisVal(xyz,attr)
						}else if(!errmsg) error gtAttrErr(device)+sTHREAX+sCLN,r9,iN2
						break
				}
			}else{
				res=device.currentValue(attr,true)
			}
		}catch(all){
			error gtAttrErr(device)+attr+sCLN,r9,iN2,all
		}
	}

	if(lge)myDetail r9,s+" result $res (${myObj(res)})"
	return res!=null ? res:sBLK
}

static String gtAttrErr(device){
	return "Error reading current value for ${device}:".toString()
}

@Field static final String sCNUMBER='NUMBER'
/**
 * Return a Map of the attribute details, will return map with type String if no attribute is found
 */
private static Map devAttrT(String attr,device){
	Map attribute, a1; attribute=[(sT):sSTR]; a1=null
	if(attr){
		a1=Attributes()[attr] // check db first
		if(a1==null && device){
			// ask the device what is the type
			def at=device.getSupportedAttributes().find{ (String)it.getName()==attr }
			// enum,string,json_object -> string; number, date?, vector3?
			if(at && at.getDataType()==sCNUMBER) attribute=[(sT):sDEC]
		}
	}
	Map res= a1 ?: attribute
	return res
}

/**  get device attr value
 * trigger means current comparison is a trigger comparison
 * If no attribute return device name if device exists
 */
@CompileStatic
private Map getDeviceAttribute(Map r9,String ideviceId,String attr,subDeviceIndex=null,Boolean trigger=false){
	String deviceId; deviceId=ideviceId
	Map ce=mMs(r9,sCUREVT)
	String cdev
	cdev= ce? (sMs(ce,sDEV) ?: sNL) : sNL

	if(isEric(r9))myDetail r9,"getDeviceAttribute deviceId: $deviceId attr: $attr  trigger: $trigger ce: ${ce}",iN2

	Map mv; mv=[:]
	Map attribute; attribute=[:]
	def device

	if(deviceId in (List<String>)r9[sALLLOC]){ //backward compatibility; we have the location device
		device=gtLocation()
		deviceId=sMs(r9,sLOCID)
		Boolean useEvt; useEvt=false
		String v; v=sNL
		if(cdev==sMs(r9,sLOCID)){
			String r9EvN=sMs(ce,sNM)
			v=sMs(ce,sVAL)
			List<String> l=[sHSMSTS,sALRMSSTATUS]
			useEvt= attr && v!=sNL && (r9EvN==attr || (r9EvN in l && attr in l))
		}
		switch(attr){
			case sMODE:
				Map mode; mode= useEvt ? fndMode(r9,v):null
				mode= mode ?: gtCurrentMode()
				if(mode) mv= rtnMap(sENUM,hashId(r9,lMs(mode,sID))) + ([(sN):sMs(mode,sNM)] as Map<String,Object>)
				break
			case sHSMSTS:
			case sALRMSSTATUS:
				String inm= useEvt ? v : gtLhsmStatus()
				String n=VirtualDevices()[sALRMSSTATUS]?.o[inm]
				mv= rtnMap(sENUM,inm) + ([(sN):n] as Map<String,Object>)
				break
			case sPWRSRC:
				mv=rtnMap(sENUM,r9[sPWRSRC])
				break
		}
		if(!mv) mv=rtnMapS(gtLname())

	}else{

		device=getDevice(r9,deviceId)
		if(device!=null){
			def value; value=gtLbl(device)
			attribute=devAttrT(attr,device)
			String atT=sMt(attribute)
			if(attr!=sNL){
				def t0
				t0=getDeviceAttributeValue(r9,device,attr)//,!trigger)
				if(attr==sHUE && t0!=null && t0!=sBLK) t0=devHue2WcHue(t0 as Integer)
				if(t0 instanceof BigDecimal){
					if(atT==sINT) t0=t0 as Integer
					else if(atT==sDEC) t0=t0 as Double
				}
				value= matchCast(t0,atT) ? t0:cast(r9,t0,atT)
			}
			mv= rtnMap(atT,value)
		}else
			return rtnMapE("Device '${deviceId}':${attr} not found".toString())
	}

//x=eXclude- if a trigger comparison or a momentary device/attribute is looked for,
//   and the device/attr does not match the current event device/attr,
// then we must ignore the result during comparisons
	Boolean eXclude; eXclude=false
	if( attr!=sNL && (trigger || (attribute[sM]!=null && bIs(attribute,sM)) ) ){
		//have to compare ids and type for hubitat since the locationid can be the same as the deviceid
		Boolean isLoc= isDeviceLocation(device)
		cdev=cdev ?: sMs(r9,sLOCID)
		Boolean deviceMatch= (!isLoc && hashD(r9,device)==cdev) || (isLoc && cdev in (List<String>)r9[sALLLOC])
		eXclude=  (!deviceMatch || (ce && fixAttr(attr)!=sMs(ce,sNM)))
	}
	return mv + ([
		(sD):deviceId,
		(sA):attr,
//		(sI):subDeviceIndex,
		(sX):eXclude
	] as Map<String,Object>)
}

@CompileStatic
private Map<String,Object> getJsonData(Map r9,data,String name,String feature=sNL){
	if(data){
		String mpart; mpart=sNL
		try{
			List<String> parts=name.replace('][','].[').tokenize(sDOT)
			def args
			args=(data instanceof Map ? [:]+(Map)data : (data instanceof List ? []+(List)data : new JsonSlurper().parseText((String)data)))
			Integer partIndex; partIndex=iN1
			String part
			Boolean lge=isEric(r9)
			for(ipart in parts){
				part=ipart
				mpart=part
				partIndex+=i1
				if(lge)myDetail r9,"getJsonData part: ${part} parts: ${parts} args: (${myObj(args)}) ${args}",iN2
				if(args instanceof String || args instanceof GString){
					def narg=parseMyResp(args.toString())
					if(narg){
						args=narg
						if(lge)myDetail r9,"getJsonData updated args: (${myObj(args)}) ${args}",iN2
					}
				}
				if(args instanceof List){
					List largs=(List)args
					Integer sz=largs.size()
					switch(part){
						case 'length':
							return rtnMapI(sz)
						case 'first':
							args=sz>iZ ? largs[iZ]:sBLK
							continue
						case 'second':
							args=sz>i1 ? largs[i1]:sBLK
							continue
						case 'third':
							args=sz>i2 ? largs[i2]:sBLK
							continue
						case 'fourth':
							args=sz>i3 ? largs[i3]:sBLK
							continue
						case 'fifth':
							args=sz>i4 ? largs[i4]:sBLK
							continue
						case 'sixth':
							args=sz>i5 ? largs[i5]:sBLK
							continue
						case 'seventh':
							args=sz>i6 ? largs[i6]:sBLK
							continue
						case 'eighth':
							args=sz>i7 ? largs[i7]:sBLK
							continue
						case 'ninth':
							args=sz>i8 ? largs[i8]:sBLK
							continue
						case 'tenth':
							args=sz>i9 ? largs[i9]:sBLK
							continue
						case 'last':
							args=sz>iZ ? largs[sz-i1]:sBLK
							continue
					}
				}
				if(!(args instanceof Map) && !(args instanceof List))return rtnMap(sDYN,sBLK)
				Boolean overrideArgs=false

				def idx; idx=iZ
				String newPart; newPart=part
				Boolean ins= (partIndex<parts.size()-i1)
				if(ins && !part.endsWith(sRB) && args[newPart] instanceof List){
					if( !(
						(parts[partIndex+i1] in ['length','first','second','third','fourth','fifth','sixth','seventh','eighth','ninth','tenth','last'])
						)
					){
						part+='[0]'
						if(lge)myDetail r9,"getJsonData adjusted for list part: ${part}",iN2
					}
				}
				if(part.endsWith(sRB)){
					//array index
					Integer start=part.indexOf(sLB)
					if(start>=iZ){
						idx=part.substring(start+i1,part.size()-i1)
						newPart=part.substring(iZ,start)
						if(idx.isInteger()) idx=idx.toInteger()
						else{
							Map var=getVariable(r9,"$idx".toString())
							idx=!isErr(var) ? oMv(var):idx
						}
					}
					if(!overrideArgs && !!newPart)args=args ? args[newPart] :args
					if(args){
						if(args instanceof List){
							Integer i= idx instanceof Integer ? idx:icast(r9,idx)
							args=((List)args)[i]
							if(lge)myDetail r9,"getJsonData found list $i args: (${myObj(args)}) ${args}",iN2
						}else args=((Map)args)[idx as String]
					}
					continue
				}
				if(!overrideArgs)args=args ? args[newPart] : args
			}
			if(lge)myDetail r9,"getJsonData return args: (${myObj(args)})",iN2
			return rtnMap(sDYN,"$args".toString())
		}catch(all){
			error "Error retrieving JSON data part $mpart",r9,iN2,all
		}
	}
	rtnMap(sDYN,sBLK)
}

private Map<String,Object> getArgument(Map r9,String name){
	def ttt=gtSysVarVal(r9,sDARGS)
	return getJsonData(r9,ttt,name)
}

private Map<String,Object> getJson(Map r9,String name){ return getJsonData(r9,r9[sJSON],name) }

private Map<String,Object> getPlaces(Map r9,String name){ return getJsonData(r9,mMs(r9,sSETTINGS)?.places,name) }

private Map<String,Object> getResponse(Map r9,String name){ return getJsonData(r9,r9[sRESP],name) }

private Map<String,Object> getWeather(Map r9,String name){
	String s=sWEAT
	if(r9[s]==null){
		Map t0=wgetWData()
		r9[s]=t0!=null ? t0:[:]
	}
	return getJsonData(r9,r9[s],name)
}

private Map<String,Object> getRooms(Map r9,String name){ return getJsonData(r9,gtRooms(r9),name) }

private Map getNFLDataFeature(String dataFeature){
	Map requestParams=[
			uri: "https://api.webcore.co/nfl/$dataFeature".toString(),
			//query: method==sGET ? data:null,
			query: null,
			timeout:i20
	]
	httpGet(requestParams){ response ->
		if(response.status==i200 && response.data){
			try{
				return response.data instanceof Map ? response.data:(LinkedHashMap)new JsonSlurper().parseText((String)response.data)
			}catch(ignored){}
		}
		return null
	}
}

private Map getNFL(Map r9,String name){
	List parts=name.tokenize(sDOT)
	String s='nfl'
	r9[s]=r9[s]!=null?r9[s]: [:]
	if(parts.size()>iZ){
		String dataFeature= sLi(parts,iZ).tokenize(sLB)[iZ]
		if(r9[s][dataFeature]==null){
			r9[s][dataFeature]=getNFLDataFeature(dataFeature)
		}
	}
	return getJsonData(r9,r9[s],name,'NFL')
}

private Map<String,Object> getIncidents(Map r9,String name){
	return getJsonData(r9,r9[sINCIDENTS],name)
}

@Field volatile static Map<String,Boolean> initGlobalVFLD=[:]
@Field volatile static Map<String,Map<String,Map>> globalVarsVFLD=[:]

void updateGblCache(String lockT,Map v,Boolean v1){
	String semName=sTGBL
	String wName=sPAppId()
	getTheLock(semName,lockT)
	globalVarsVFLD[wName]=v
	globalVarsVFLD=globalVarsVFLD
	initGlobalVFLD[wName]=v1
	initGlobalVFLD=initGlobalVFLD
	releaseTheLock(semName)
	//if(eric())doLog(sDBG,lockT)
}

void clearGlobalCache(String meth=sNL){ updateGblCache('clearGlobalCache '+meth,null,false) }

private void loadGlobalCache(){
	String wName=sPAppId()
	if(!initGlobalVFLD[wName])
		updateGblCache('loadGlobalCache',wlistAvailableVariables(),true)
}

@CompileStatic
private static Map<String,String> parseVariableName(String name){
	Map res; res=[
			(sNM): name,
			(sINDX): sNL
	]
	if(name!=sNL && !name.startsWith(sDLR) && name.endsWith(sRB)){
		List<String> parts=name.replace(sRB,sBLK).tokenize(sLB)
		if(parts.size()==i2){
			res=[
					(sNM): parts[iZ],
					(sINDX): parts[i1]
			]
		}
	}
	return res
}

@CompileStatic
private static String sanitizeVariableName(String name){
	String rname=name!=sNL ? name.trim().replace(sSPC,sUNDS):sNL
	return rname
}

@CompileStatic
private Map<String,Object> getVariable(Map r9,String name, Boolean rtnL=false){
	Map<String,String> var=parseVariableName(name)
	String tn,mySt,rt
	tn=sanitizeVariableName(var[sNM])

	mySt="get Variable ${name} ${var} ${tn} "
	Boolean lge=isEric(r9)
	if(lge) myDetail r9,mySt,i1
	Map<String,Object> res
	if(tn==sNL){
		res=rtnMapE('Invalid empty variable name')
		error mySt+sMv(res),r9
		if(lge)myDetail r9,mySt+" result:$res"
		return res
	}
	Map err=rtnMapE(mySt+"not found".toString())

	Boolean rtnVarN,rtnLCL,isConst,isFixed
	rtnVarN=false // return variable name
	rtnLCL=false  // return LCL markers
	isFixed=false // fixed (pre-assigned)value
	isConst=false // const

	if(tn.startsWith(sAT)){
		if(tn.startsWith(sAT2)){
			tn=var[sNM] // allow spaces todo
			String vn=tn.substring(i2)
			//get a variable
			Map hg=wgetGlobalVar(vn)
			waddInUseGlobalVar(r9,vn,true,!!hg)
			if(hg){
				String typ; typ=sNL
				def vl; vl=null
				Map ta=fixHeGType(r9,false,sMs(hg,sTYPE),hg[sVAL])
				for(t in ta){
					typ=(String)t.key
					vl=t.value
				}
				rtnVarN=true
				res=rtnMap(typ,vl)
			}else res=err
			if(eric())debug "getVariable hub variable (${vn}) returning ${res} to webcore",r9
		}else{
			loadGlobalCache()
			waddInUseGlobalVar(r9,tn,false)
			String wName=sMs(r9,spId)
			def tresult=globalVarsVFLD[wName][tn]
			if(!(tresult instanceof Map))res=err
			else{
				res=(Map)tresult
				String t=sMt(res)
				def v=oMv(res)
				res[sV]= matchCast(v,t) ? v: cast(r9,v,t)
				rtnVarN=true
			}
		}
	}else{
		if(tn.startsWith(sDLR)){
			Integer t0=tn.size()
			if(tn.startsWith(sDARGS+sDOT) && t0>i6){ // '$args.'
				res=getArgument(r9,tn.substring(i6))
			}else if(tn.startsWith(sDARGS+sLB) && t0>i6){ //'$args['
				res=getArgument(r9,tn.substring(i5))
			}else if(tn.startsWith(sDRESP+sDOT) && t0>i10){
				res=getResponse(r9,tn.substring(i10))
			}else if(tn.startsWith(sDRESP+sLB) && t0>i10){
				res=getResponse(r9,tn.substring(i9))
			}else if(tn.startsWith(sDLRWEAT+sDOT) && t0>i9){
				res=getWeather(r9,tn.substring(i9))
			}else if(tn.startsWith(sROOMS+sDOT) && t0>i7){
				res=getRooms(r9,tn.substring(i7))
			}else if(tn.startsWith(sDJSON+sDOT) && t0>i6){
				res=getJson(r9,tn.substring(i6))
			}else if(tn.startsWith(sDJSON+sLB) && t0>i6){
				res=getJson(r9,tn.substring(i5))
			}else if(tn.startsWith(sDLRINCIDENTS+sDOT) && t0>i11){
				res=getIncidents(r9,tn.substring(i11))
			}else if(tn.startsWith(sDLRINCIDENTS+sLB) && t0>i11){
				res=getIncidents(r9,tn.substring(i10))
			}else if(tn.startsWith('$nfl'+sDOT) && t0>i5){
				res=getNFL(r9,tn.substring(i5))
			}else if(tn.startsWith(sPLACES+sDOT) && t0>i8){
				res=getPlaces(r9,tn.substring(i8))
			}else if(tn.startsWith(sPLACES+sLB) && t0>i8){
				res=getPlaces(r9,tn.substring(i7))
			}else{
				def tres=r9[sSYSVARS][tn]
				if(!(tres instanceof Map))res=err
				else{
					res=(Map)tres
					if(res!=null)res=rtnMap(sMt(res),gtSysVarVal(r9,tn))
				}
			}
		}else{

//			if(eric())doLog(sDBG,"getVariable ${r9.localVars}")
			Map tlocV=mMs(mMs(r9,sLOCALV),tn)
			if(!tlocV)res=err
			else{
				rtnLCL= rtnL
				rtnVarN=true
				isConst=sMs(tlocV,sA)==sS // const
				isFixed=bIs(tlocV,sF) // fixed value
				res=rtnMap(sMt(tlocV),oMv(tlocV))
				def tmp=oMv(res)
				if(tmp instanceof List)
					res[sV]=[]+(List)tmp //make a local copy of the list
				if(tmp instanceof Map)
					res[sV]=[:]+mMv(res) //make a local copy of the map
			}
		}
	}
	if(res==null)res=err

	rt= sMt(res)
	if(rt.endsWith(sRB)){
		rtnLCL=false
		String sindx=sMs(var,sINDX)
		if( (oMv(res) instanceof List || oMv(res) instanceof Map) && sindx!=sNL && sindx!=sBLK){
			if(!sindx.isNumber()){
				//indirect variable addressing
				Map indirectVar=getVariable(r9,sindx)
				if(!isErr(indirectVar)){
					String t=sMt(indirectVar)
					def v=oMv(indirectVar)
					def value= t==sDEC ? icast(r9,v):v
					String dt= t==sDEC ? sINT:t
					var[sINDX]=(String)cast(r9,value,sSTR,dt)
				}
			}
			res[sT]= rt.replace(sLRB,sBLK)
			res[sV]=res[sV][var[sINDX]]
		}
	}else{
		if(oMv(res) instanceof Map){
			res=mevaluateOperand(r9,mMv(res))
			res=(rt && rt==sMt(res)) ? res:evaluateExpression(r9,res,rt)
		}
	}
	def v; v=oMv(res)
	if(isTrc(r9) && isErr(res)){ // some apps handle missing variable, so require trace or higher to log error
		error sMv(res),r9
	}else{
		rt=sMt(res)
		if(rt==sDEC && v instanceof BigDecimal)v=v.toDouble()
		res=rtnMap(rt,v) + ((rtnVarN ? [vn: tn] : [:]) as Map<String, Object>)
		res= res + ((rtnLCL ? [ic: isConst, fx: isFixed] : [:]) as Map<String, Object>)
	}
	if(lge)myDetail r9,mySt+"result:$res (${myObj(v)})"
	res
}

@CompileStatic
private Map setVariable(Map r9,String name,value){
	Map<String,String> var=parseVariableName(name)
	String tn,mySt
	tn=sanitizeVariableName(var[sNM])
	mySt="set Variable '${tn}' "
	Map res; res=null
	Map err; err=rtnMapE(mySt+'not found ')
	Boolean lge=isEric(r9)
	if(tn==sNL){
		res=rtnMapE(mySt+'Invalid empty variable name')
		error sMv(res),r9
		return res
	}
	if(lge)myDetail r9,mySt+"value: ${value} (${myObj(value)})",iN2
	if(tn.startsWith(sAT)){
		if(tn.startsWith(sAT2)){
			tn=var[sNM] // allow spaces
			String vn=tn.substring(i2)
			Map hg=wgetGlobalVar(vn)
			waddInUseGlobalVar(r9,vn,true,!!hg)
			if(hg){ // we know it exists and if it has a value we can know its type (overloaded String, datetime)
				String typ,wctyp
				wctyp=sNL
				def vl
				Map tb=fixHeGType(r9,false,sMs(hg,sTYPE),hg[sVAL])
				for(t in tb){
					wctyp=(String)t.key
				}
				if(wctyp){ // if we know current type
					Map ta=fixHeGType(r9,true,wctyp,value)
					for(t in ta){
						typ=(String)t.key
						vl=t.value
						if(lge)myDetail r9,"setVariable setting Hub ($vn) to $vl with type ${typ} wc original type ${wctyp}",iN2
						Boolean a; a=false
						try{
							a=wsetGlobalVar(vn,vl)
						}catch(all){
							error 'An error occurred while executing set hub variable',r9,iN2,all
						}
						if(a){
							res=rtnMap(wctyp,value)
							if(lge)myDetail r9,"setVariable returning ${res} to webcore",iN2
						}else err[sV]=mySt+'setGlobal failed'
					}
					if(res)return res
				}else err[sV]=mySt+'setGlobal unknown wctyp'
			}
		}else{
			loadGlobalCache()
			String lockTyp='setGlobalvar'
			String semName=sTGBL
			String wName=sMs(r9,spId)
			waddInUseGlobalVar(r9,tn,false)
			getTheLock(semName,lockTyp)
			def tvariable=globalVarsVFLD[wName][tn]
			if(tvariable instanceof Map){
				Map variable=(Map)tvariable
				variable[sV]=cast(r9,value,sMt(variable))
				globalVarsVFLD=globalVarsVFLD
				Map<String,Map> cache=r9[sGVCACHE]!=null ? msMs(r9,sGVCACHE):[:]
				cache[tn]=variable
				r9[sGVCACHE]=cache
				releaseTheLock(semName)
				return variable
			}
			releaseTheLock(semName)
		}
		// hubvars are removed via HE console -> settings
		// global vars are removed by setting them to null via webcore dashboard
	}else{
		// local vars are removed by 'clear all data' via HE console
//		if(eric())doLog(sDBG,"setVariable ${r9.localVars}")
		Map tvariable=mMs(mMs(r9,sLOCALV),tn)
//		if(eric())doLog(sDBG,"setVariable tvariable ${tvariable}")
		if(tvariable){
			Map variable=tvariable
			String t=sMt(variable)
//			if(eric())doLog(sDBG,"setVariable found variable ${variable}")
			if(t.endsWith(sRB)){
				//dealing with a list
				variable[sV]= oMv(variable) instanceof Map || oMv(variable) instanceof List ? oMv(variable):[:]
				String sindx=var[sINDX]
				if(sindx=='*CLEAR') variable[sV]=[:] //((Map)variable.v).clear() // this modifies r9[sLOCALV]
				else{
					if(sindx!=null){
						if(!sindx.isNumber()){
							//indirect variable addressing
							Map indirectVar=getVariable(r9,sindx)
							if(!isErr(indirectVar)){
								def a=oMv(indirectVar)
								var[sINDX]=(a instanceof String)? (String)a:(String)cast(r9,a,sSTR,sMt(indirectVar))
							}
						}
						String at=t.replace(sLRB,sBLK)
						variable[sV][var[sINDX]]= matchCast(value,at)?value:cast(r9,value,at) // this modifies r9[sLOCALV]
					}else{
						//list of numbers, spread into multiple prms
						def nvalue
						nvalue=value
						if(nvalue instanceof String){
							String s= (String)nvalue
							if(stJson1(s)){
								try{
									List l= (List)new JsonSlurper().parseText(s)
									nvalue= l
								}catch(ignored){}
							}
						}
						if(nvalue instanceof List){
							if(lge)myDetail r9,"setVariable list found ${variable} value: ${nvalue}",iN2
							variable[sV]= nvalue // this modifies r9[sLOCALV]
						}else{
							if(isInf(r9))error sMv(err),r9
							return err
						}
					}
				}
			}else{
				def v=(value instanceof GString)? "$value".toString():value
				if(!sMs(variable,sA)){ // cannot change const; sNL means dynamic, sS means static/const
					variable[sV]=matchCast(v,t) ? v : cast(r9,v,t) // this modifies r9[sLOCALV]
					if(bIs(variable,sF) && isTrc(r9))
						warn mySt+'changing initialized variable', r9
				}else{
					err= rtnMapE(mySt+'attempting to set a const')
					error sMv(err),r9
					return err
				}
			}
			if(!bIs(variable,sF) || !sMs(variable,sA)){ // save local variables except constants
				updateVariable(r9,tn,variable)
			}
			return variable
		}
	}
	error sMv(err),r9 // if you are trying to set something that does not exist, log error
	return err
}

/**
 *  unpersist local variable value
 */
private void clearVariable(Map r9,String n){
	updateVariable(r9,n,[:],true)
}

/**
 *  persist local variable value
 */
@CompileStatic
private void updateVariable(Map r9,String n, Map variable, Boolean clear=false){
	Map<String,Object> vars
	Map t0=getCachedMaps(sSTVAR)
	if(t0!=null)vars=mMs(t0,sVARS)
	else vars=isPep(r9) ? (Map)gtAS(sVARS):(Map)gtSt(sVARS)

	if(clear) vars.remove(n)
	else vars[n]=oMv(variable)
	mb()

	if(t0!=null){
		// Cache is warm: update only the in-memory cache; the state write at execution end persists it.
		updateCacheFld(r9,sVARS,vars,sV,false)
	} else {
		// Cache is cold (recovery/first-run path): must write through to state immediately.
		if(isPep(r9))assignAS(sVARS,vars)
		else assignSt(sVARS,vars)
	}
}

@CompileStatic
private static Integer matchCastI(Map r9,v){ Integer res=matchCast(v,sINT) ? (Integer)v:icast(r9,v); return res }
@CompileStatic
private static Long matchCastL(Map r9,v){ Long res=matchCast(v,sLONG) ? (Long)v:lcast(r9,v); return res }

@CompileStatic
private static Boolean matchCastD(v,String t){
	Boolean match= v!=null && t in LT1 && (v instanceof Long)
	return match
}

@Field static Set<String> mL=[] as Set<String>
@Field static Set<String> mL1=[] as Set<String>
private static void fill_mL(){
	if(mL.size()==iZ){
		mL1=([sDYN]+LS) as Set<String>
		mL=(mL1+([sLONG,sDEC,sINT,sBOOLN,sDEV,sVEC] as Set<String>)) as Set<String>
	}
}

@CompileStatic
private static Boolean matchCast(v,String t){
	Boolean match= v!=null && t in mL && (
		(v instanceof String && t in mL1)||
		(t==sDEC && v instanceof Double) ||
		(t==sLONG && v instanceof Long)||
		(t==sINT && v instanceof Integer)||
		(t==sBOOLN && v instanceof Boolean)||
		(t==sDEV && v instanceof List) ||
		(t==sVEC && v instanceof Map)
	)
	return match
}

Map setLocalVariable(String name,value){ // called by parent (IDE) to set a variable
	String tn=sanitizeVariableName(name)
	if(tn==sNL || tn.startsWith(sAT))return [:]
	Map vars; vars=(Map)gtAS(sVARS)
	vars=vars!=null ? vars:[:]
	vars[tn]=value
	assignAS(sVARS,vars)
	clearMyCache('setLocalVariable')
	return vars
}

/** EXPRESSION FUNCTIONS							**/

/**
 * called by parent to evaluate on the fly for IDE
 */
@CompileStatic
Map proxyEvaluateExpression(LinkedHashMap mr9,Map expression,String dataType=sNL, List<Map>vars=null){
	LinkedHashMap r9; r9=getRunTimeData(mr9)
	resetRandomValues(r9)
	r9[sEVENT]=[:]
	r9[sCUREVT]=[:]
	try{
		if(vars){
			Map aS
			aS=getCachedMaps('proxyEvaluateExpression glv')
			aS=aS!=null?aS:[:]
			getLocalVariables(r9,aS,true,true, vars)
		}

		Map res; res=evaluateExpression(r9,expression,dataType)
		if(sMt(res)==sDEV && sMa(res)!=sNL){
			def device=getDevice(r9, sLi(liMv(res),iZ) )
			Map attr=devAttrT(sMa(res),device)
			res=evaluateExpression(r9,res,sMt(attr) ?: sSTR)
		}
		r9=null
		return res
	}catch(all){
		error 'An error occurred while executing the expression',r9,iN2,all
	}
	return rtnMapE('expression error')
}

@CompileStatic
private static Map simplifyExpression(Map express){
	Map expression; expression=express
	List<Map> expi; expi= liMs(expression,sI)
	while(expi && sMt(expression)==sEXPR && expi.size()==i1){ expression=expi[iZ]; expi= liMs(expression,sI) }
	return expression
}

@Field static List<String> L1opt=[]
@Field static List<String> lPLSMIN=[]
@Field static List<String> LN=[]
@Field static List<String> LD=[]
@Field static List<String> LT2=[]
@Field static List<String> tL2=[]
@Field static List<String> tL4=[]
@Field static List<String> tL6=[]
@Field static List<String> tL7=[]
@Field static List<String> tL8=[]
@Field static List<String> tL9=[]
@Field static List<String> tL10=[]
@Field static List<String> tL11=[]
@Field static List<String> tL12=[]
@Field static List<String> tL13=[]
@Field static List<String> tL14=[]
@Field static List<String> pn1=[]
@Field static List<String> pn2=[]
@Field static List<String> pn3=[]
@Field static List<String> pn4=[]
@Field static Map<String,Integer> opPriorityFLD=[:]

@Field static List<String> LS=[]
private static void fill_LS(){
	if(LS.size()==iZ)
		LS= [sSTR,sENUM,sERROR,sPHONE,sURI,sTEXT]
}

@Field static List<String> LT1=[]
private static void fill_TIM(){
	if(LT1.size()==iZ)
		LT1= [sDTIME,sTIME,sDATE]
	if(L1opt.size()==iZ){
		L1opt=[sPLUS,sMINUS,sPWR,sAMP,sBOR,sBXOR,sBNOT,sBNAND,sBNOR,sBNXOR,sLTH,sGTH,sLTHE,sGTHE,sEQ,sNEQ,sNEQA,sSBL,sSBR,sNEG,sDNEG,sQM]
		lPLSMIN=[sPLUS,sMINUS]
		LN=[sNUMBER,sINT,sLONG] // number is ambiguous for devices
		LD=[sDEC,sFLOAT]
		LT2=[sDEV,sVARIABLE]
		tL2=[sNEG,sDNEG,sBNOT]
		tL4=[sMULP,sDIV,sMOD1,sMOD]
		tL6=[sSBL,sSBR]
		tL7=[sGTH,sLTH,sGTHE,sLTHE]
		tL8=[sEQ,sNEQ,sNEQA]
		tL9=[sAMP,sBNAND]
		tL10=[sBXOR,sBNXOR]
		tL11=[sBOR,sBNOR]
		tL12=[sLAND,sLNAND]
		tL13=[sLXOR,sLNXOR]
		tL14=[sLOR,sLNOR]
		pn1=[sMULP,sDIV,sPWR,sMINUS] // number fixes
		pn2=[sMOD1,sMOD,sAMP,sBOR,sBXOR,sBNAND,sBNOR,sBNXOR,sSBL,sSBR] // int fixes
		pn3=[sLAND,sLOR,sLXOR,sLNAND,sLNOR,sLNXOR,sNEG,sDNEG] // bool fixes
		pn4=[sEQ,sNEQ,sLTH,sGTH,sLTHE,sGTHE,sNEQA]
		opPriorityFLD=[(sNEG):i2,(sDNEG):i2,(sBNOT):i2,
				(sPWR):i3,
				(sMULP):i4,(sDIV):i4,(sMOD1):i4,(sMOD):i4,
				(sPLUS):i5,(sMINUS):i5,
				(sSBL):i6,(sSBR):i6,
				(sGTH):i7,(sLTH):i7,(sGTHE):i7,(sLTHE):i7,
				(sEQ):i8,(sNEQ):i8,(sNEQA):i8,
				(sAMP):i9,(sBNAND):i9,
				(sBXOR):i10,(sBNXOR):i10,
				(sBOR):i11,(sBNOR):i11,
				(sLAND):i12,(sLNAND):i12,
				(sLXOR):i13,(sLNXOR):i13,
				(sLOR):14,(sLNOR):14]
	}
}

/** return Double for given expression */
@CompileStatic
private Double dblEvalExpr(Map r9,Map express,String rtndataType=sDEC){
	return oMv(evaluateExpression(r9,express,rtndataType)) as Double
}

/** return Long for given expression */
@CompileStatic
private Long longEvalExpr(Map r9,Map express,String rtndataType=sNL){
	//String rtn= rtndataType!=sSTR ? rtndataType : sNL
	return oMv(evaluateExpression(r9,express,rtndataType)) as Long
}

/** return Integer for given expression */
@CompileStatic
private Integer intEvalExpr(Map r9,Map express,String rtndataType=sINT){
	return oMv(evaluateExpression(r9,express,rtndataType)) as Integer
}

/** return Boolean for given expression */
@CompileStatic
private Boolean boolEvalExpr(Map r9,Map express,String rtndataType=sBOOLN){
	return !!oMv(evaluateExpression(r9,express,rtndataType))
}

/** return string for given expression */
@CompileStatic
private String strEvalExpr(Map r9,Map express,String rtndataType=sSTR){
	return sMv(evaluateExpression(r9,express,rtndataType))
}

@CompileStatic
private Map evaluateExpression(Map r9,Map express,String rtndataType=sNL){
	//if dealing with an expression that has multiple items let's evaluate each item one by one
	if(!express)return rtnMapE('Null expression')
	Long time=wnow()
	Map expression=simplifyExpression(express)
	String mySt; mySt=sNL
	Boolean lge=isEric(r9)
	if(lge){
		mySt="evaluateExpression $expression rtndataType: $rtndataType".toString()
		myDetail r9,mySt,i1
	}
	Map result
	result=expression
	String exprType=sMt(expression)
	def exprV=oMv(expression)
	switch(exprType){
		case sINT:
		case sLONG:
		case sDEC:
			result=rtnMap(exprType,exprV)
			break
		case sTIME:
		case sDTIME:
			String st0="$exprV".toString()
			try{
				if(st0.isNumber()){
					Double aa= st0 as Double
					Long tl=aa.toLong()
					if( (tl>=lMSDAY && exprType==sDTIME) || (tl<lMSDAY && exprType==sTIME) ){
						result=rtnMap(exprType,tl)
						break
					}
				}
			}catch(ignored){}
		case sINT32:
		case sINT64:
		case sDATE:
			result=rtnMap(exprType,cast(r9,exprV,exprType,exprType))
			break
		case sBOOL:
		case sBOOLN:
			result=rtnMapB(bcast(r9,exprV))
			break
		case sDYN:
			if(rtndataType!=sSTR)break
		case sSTR:
		case sENUM:
		case sERROR:
		case sPHONE:
		case sURI:
		case sTEXT:
			result=rtnMapS(scast(r9,exprV))
			break
		case sNUMBER:
		case sFLOAT:
		case sDBL:
			result=rtnMapD(dcast(r9,exprV))
			break
		case sDURATION:
			String t0=sMvt(expression)
			if(t0==sNL && exprV instanceof Long) result=rtnMap(sLONG,(Long)exprV)
			else result=rtnMap(sLONG,(Long)cast(r9,exprV,t0!=sNL ? t0:sLONG,sINT))
			break
		case sVARIABLE:
			//get variable {t:type,v:value}
			result=getVariable(r9,sMs(expression,sX)+(sMs(expression,sXI)!=sNL ? sLB+sMs(expression,sXI)+sRB:sBLK))
			break
		case sDEV:
			if(exprV instanceof List){
				//already parsed
				result=expression
			}else{
				Boolean err; err=false
				def eid=expression[sID]
				List deviceIds; deviceIds= eid instanceof List ? (List)eid : (eid ? [eid] : [])
				if(deviceIds.size()==iZ){
					//get variable {t:type,v:value}
					Map var=getVariable(r9,sMs(expression,sX))
					if(!isErr(var)){
						if(sMt(var)==sDEV)
							deviceIds=liMv(var)
						else{
							def device=oMv(var) ? getDevice(r9,sMv(var)):null
							if(device!=null)deviceIds=[hashD(r9,device)]
						}
					}else{
						err=true
						result=var // Invalid variable
					}
				}
				if(!err) result=rtnMap(sDEV,deviceIds)+([(sA):sMa(expression)] as LinkedHashMap)
			}
			break
		case sOPERAND:
			result=rtnMapS(scast(r9,exprV))
			break
		case sFUNC:
			String fn=sMs(expression,sN)
			//in a function, we look for device parameter,they may be lists- we need to reformat all parameter to send them to the function
			String myStr; myStr=sNL
			try{
				List prms=[]
				List<Map> t0=liMs(expression,sI)
				if(t0 && t0.size()!=iZ){
					Map prm
					Boolean a
					for(Map i in t0){
						prm=simplifyExpression(i)
						if(sMt(prm) in LT2){ // sDEV or sVARIABLE
							prm=evaluateExpression(r9,prm)
							Integer sz
							switch(fn){
								case sJSON: sz=i1; break
								default:
									//if multiple devices, or list of numbers, spread into multiple prms
									if(sMt(prm) in ListSTRDYN && oMv(prm) instanceof String){
										String s= sMv(prm)
										if(stJson1(s)){
											try{
												List l= (List)new JsonSlurper().parseText(s)
												prm[sV]= l
											}catch(ignored){}
										}
									}
									sz=oMv(prm) instanceof List ? liMv(prm).size():i1
							}
							switch(sz){
								case iZ: break
								case i1: prms.push(prm); break
								default:
									String t= sMt(prm).replace(sLRB,sBLK)
									String s= sMa(prm)
									for(v in liMv(prm)){
										if(s || v instanceof String)
											prms.push( rtnMap(t,[v]) + ((s ? [(sA):s] :[:]) as LinkedHashMap) )
										else
											prms.push( rtnMap(t,v) )
									}
							}
						}else prms.push(prm)
					}
				}
				if(lge){
					myStr='calling function '+fn+" $prms"
					myDetail r9,myStr,i1
				}
				result=callFunc(r9,fn,prms)
			}catch(all){
				error "Error executing $fn: ",r9,iN2,all
				result=rtnMapE("${all}".toString())
			}
			if(lge)myDetail r9,myStr+sSPC+"${result}".toString()
			break
		case sEXPR:
			//if we have a single item, we simply traverse the expression
			List<Map> items; items=[]
			Integer operand,lastOperand
			operand=iN1
			lastOperand=iN1

			if(expression[sI]){
				for(Map item in liMs(expression,sI)){
					if(sMt(item)==sOPER){
						String ito=sMs(item,sO)
						Map mito=[(sO):ito] as LinkedHashMap
						if(operand<iZ){
							if(ito in L1opt){
								items.push(rtnMapI(iZ)+mito)
							}else switch(ito){
								case sCLN:
									if(lastOperand>=iZ){
										//groovy-style support for object ?: value
										items.push(items[lastOperand]+mito)
									}else items.push(rtnMapI(iZ)+mito)
									break
								case sMULP:
								case sDIV:
									items.push(rtnMapI(i1)+mito)
									break
								case sLAND:
								case sLNAND:
									items.push(rtnMapB(true)+mito)
									break
								case sLOR:
								case sLNOR:
								case sLXOR:
								case sLNXOR:
									items.push(rtnMapB(false)+mito)
									break
							}
						}else{
							items[operand][sO]=ito
							operand=iN1
						}
					}else{
						//Map tmap= [:]+evaluateExpression(r9,item,rtndataType)
						Map tmap= [:]+evaluateExpression(r9,item)
						items.push(tmap)
						operand=items.size()-i1
						lastOperand=operand
					}
				}
			}
			//clean up operators, ensure there's one for each
			Integer idx,itmSz
			idx=iZ
			itmSz=items.size()-i1
			if(items){
				for(Map item in items){
					if(!sMs(item,sO)){
						switch(sMt(item)){
							case sINT:
							case sFLOAT:
							case sDBL:
							case sDEC:
							case sNUMBER:
								String nTyp; nTyp=sSTR
								if(idx<itmSz)nTyp=sMt(items[idx+i1])
								item[sO]= nTyp in LS ? sPLUS:sMULP // Strings
								break
							default:
								item[sO]=sPLUS
								break
						}
					}
					idx++
				}
			}
			//do the job
			idx=iZ
			itmSz=items.size()

			while (itmSz>i1){
				//ternary
				if(itmSz==i3 && sMs(items[iZ],sO)==sQM && sMs(items[i1],sO)==sCLN){
					//we have a ternary operator
					if(boolEvalExpr(r9,items[iZ])) items=[items[i1]]
					else items=[items[i2]]
					items[iZ][sO]=sNL
					break
				}
				//order of operations — single pass: find leftmost item with highest-priority operator
				// tiers 2–14 match original precedence; tier 2 = highest, 14 = lowest
				// unary minus (null-typed item with sMINUS) is treated as tier 2
				// stop at itmSz-1: last item has no right operand and must never be selected
				idx=iZ
				int ii=iZ; int bestTier=i15
				int scanLimit=itmSz-i1
				for(Map item in items){
					if(ii>=scanLimit) break
					String t0=sMs(item,sO)
					int tier=i15
					if(t0!=sNL){
						if(t0==sMINUS && sMt(item)==sNL){ tier=i2 }
						else{ Integer tp=(Integer)opPriorityFLD[t0]; if(tp!=null) tier=(int)tp }
					}
					if(tier<bestTier){ bestTier=tier; idx=ii }
					if(bestTier==i2) break
					ii++
				}

				String o=sMs(items[idx],sO)

				String a1,a2,t1,t2,t
				def v1,v2,v
				a1=sMa(items[idx])
				t1=sMt(items[idx])
				v1=oMv(items[idx])

				Integer idxPlus=idx+i1
				a2=sMa(items[idxPlus])
				t2=sMt(items[idxPlus])
				v2=oMv(items[idxPlus])

				v=null
				t=t1

				//fix-ups
				if(t1==sDEV && a1!=sNL && a1.length()>iZ){
					List lv1=(v1 instanceof List)? (List)v1:[v1]
					String tm=sLi(lv1,iZ)
					def device= tm?getDevice(r9,tm):null
					Map attr=devAttrT(a1,device)
					t1=sMt(attr)
				}
				if(t2==sDEV && a2!=sNL && a2.length()>iZ){
					List lv2=(v2 instanceof List)? (List)v2:[v2]
					String tm=sLi(lv2,iZ)
					def device= tm?getDevice(r9,tm):null
					Map attr=devAttrT(a2,device)
					t2=sMt(attr)
				}
				if(t1==sDEV && t2==sDEV && o in lPLSMIN){
					List lv1=(v1 instanceof List)? (List)v1:[v1]
					List lv2=(v2 instanceof List)? (List)v2:[v2]
					v= o==sPLUS ? lv1+lv2:lv1-lv2
					//set the results
					items[idxPlus][sT]=sDEV
					items[idxPlus][sV]=v
				}else{
					Boolean t1d,t2d,t1i,t2i,t1f,t2f,t1n,t2n
					t1d= t1 in LT1
					t2d= t2 in LT1
					t1i= t1 in LN //[sNUMBER,sINT,sLONG]
					t2i= t2 in LN
					t1f= t1 in LD //[sDEC,sFLOAT]
					t2f= t2 in LD
					t1n=t1i||t1f
					t2n=t2i||t2f
					//warn "Precalc ($t1) $v1 $o ($t2) $v2 >>> t1d=$t1d, t2d=$t2d, t1n=$t1n, t2n=$t2n",r9
					if(o in lPLSMIN && (t1d || t2d) && (t1d || t1n) && (t2d || t2n)){
						//if dealing with date +/- date/numeric then
						t=sLONG
						if(t1n){
							t= o==sPLUS && t2 in ListDATEDTIME ? sDTIME:t // dtime -> number+dtime number+date
						}else if(t2n){
							t= o==sPLUS && t1 in ListDATEDTIME ? sDTIME:t // dtime -> dtime+number date+number
						}else{
							t1d= t1==sDATE
							Boolean t2t= t2==sTIME
							Boolean t1dt= t1==sDTIME
							t= (t1d||t1dt) && t2t ? sDTIME:t // dtime -> date+/-time dtime+/-time
						}
					}else{
						if(o in lPLSMIN){
							//devices and others play nice
							if(t1==sDEV){
								t=t2
								t1=t2
							}else if(t2==sDEV){
								t=t1
								t2=t1
							}
						}
						t1d= t1 in LT1
						t2d= t2 in LT1
						t1i= t1 in LN
						t2i= t2 in LN
						t1f= t1 in LD
						t2f= t2 in LD
						t1n=t1i||t1f
						t2n=t2i||t2f
						//warn "Precalc ($t1) $v1 $o ($t2) $v2 >>> t1d=$t1d, t2d=$t2d, t1n=$t1n, t2n=$t2n",r9

						// *,/ ** require decimals
						if(o in pn1){ //[sMULP,sDIV,sPWR,sMINUS] number fixes
							t= t1i&&t2i && rtndataType!=sDEC ? typIL(t1,t2):sDEC
							t1=t
							t2=t
						}else if(o in pn2){ //[sMOD1,sMOD,sAMP,sBOR,sBXOR,sBNAND,sBNOR,sBNXOR,sSBL,sSBR] int fixes
							t=typIL(t1,t2)
							t1=t
							t2=t
						}else if(o in pn3){ //[sLAND,sLOR,sLXOR,sLNAND,sLNOR,sLNXOR,sNEG,sDNEG] bool fixes
							t=sBOOLN
							t1=t
							t2=t
						}else if(o==sPLUS && (t1 in LS || t2 in LS)){ // string fixes
							t=sSTR
							t1=t
							t2=t
						}
						t1i=(t1 in LN)
						t2i=(t2 in LN)
						t1f=(t1 in LD)
						t2f=(t2 in LD)
						t1n=t1i || t1f
						t2n=t2i || t2f
						//integer with decimal gives decimal
						if(t1n&&t2n){
							t= t1i&&t2i ? typIL(t1,t2):sDEC
							t1=t
							t2=t
						}
						if(o in pn4){ //[sEQ,sNEQ,sLTH,sGTH,sLTHE,sGTHE,sNEQA]
							if(t1==sDEV)t1=sSTR
							if(t2==sDEV)t2=sSTR
							t1=t1==sSTR ? t2:t1
							t2=t2==sSTR ? t1:t2
							t=sBOOLN
						}
					}

					//v1=evaluateExpression(r9,(Map)items[idx],t1).v
					//v1=((Map)items[idx]).v   // already done
					String tt1
					tt1=sMt(items[idx])
					if(!(v1!=null && tt1!=sNL && (t1==sNL || tt1==t1) && matchCast(v1,tt1))) v1=oMv(evaluateExpression(r9,items[idx],t1))
					v1=v1==sSNULL ? null:v1
					//v2=evaluateExpression(r9,(Map)items[idxPlus],t2).v
					//v2=((Map)items[idxPlus]).v // already done
					tt1=sMt(items[idxPlus])
					if(!(v2!=null && tt1!=sNL && (t2==sNL || tt1==t2) && matchCast(v2,tt1))) v2=oMv(evaluateExpression(r9,items[idxPlus],t2))
					v2=v2==sSNULL ? null:v2

					Boolean err;err=false
					try{
						v= doExprMath(r9,o,t,v1,v2)
					}catch(ignored){
						err=true
					}
					if(err || isDbg(r9)){
						String s
						s= "Calculating ($t1)$v1 $o ($t2)$v2 >> ($t)$v"
						if(err){
							s= 'Error '+s
							result=rtnMapE(s)
							error s,r9
							break
						}else{
							debug s,r9
						}
					}

					//set the results
					items[idxPlus][sT]=t
					v=(v instanceof GString)? "$v".toString():v
					items[idxPlus][sV]=matchCast(v,t) ? v:cast(r9,v,t)
				}

				items.remove(idx)

				itmSz=items.size()
			}
			if(!isErr(result)){
				result=items[iZ] ? (sMt(items[iZ])==sDEV ? items[iZ]:evaluateExpression(r9,items[iZ])):rtnMap(sDYN,null)
			}
			break
	}

	if(rtndataType && !isErr(result)){
		String ra=sMa(result)
		def ri=result[sI]
		//when dealing with devices they need to be "converted" unless the request is to return devices
		if(rtndataType!=sDEV && sMt(result)==sDEV){
			def tmp=oMv(result)
			List atL= (tmp instanceof List)?(List)tmp:[tmp]
			switch(atL.size()){
				case iZ: result=rtnMapE('Empty device list'); break
				case i1: result=getDeviceAttribute(r9,sLi(atL,iZ),ra,ri); break
				default: result=rtnMapS(buildDeviceAttributeList(r9,atL,ra)); break
			}
		}

		if(!isErr(result)){
			String t0=sMt(result)
			def t1; t1=oMv(result)
			Boolean match
			match=(rtndataType in LS && t0 in LS && t1 instanceof String)
			if(!match){
				if(!t0 || rtndataType==t0) match=matchCast(t1,rtndataType)
				if(!match && t0 && rtndataType==t0) match=matchCastD(t1,rtndataType)
				if(!match)t1=cast(r9,t1,rtndataType,t0)
			}
			result=rtnMap(rtndataType,t1)+((ra ? [(sA):ra]:[:]) as Map)+((ri!=null ? [(sI):ri]:[:]) as Map)
		}
	}
	result[sD]=elapseT(time)
	if(lge)myDetail r9,mySt+" result:$result".toString()
	return result
}

private doExprMath(Map r9,String o,String t,v1,v2){
	def v; v=null
	//if(isEric(r9)) myDetail r9,"doExprMath: o: $o type: $t, v1: $v1 (${myObj(v1)}) v2: $v2 (${myObj(v2)})",iN2
	switch(o){
		case sQM:
		case sCLN:
			error "Invalid ternary operator. Ternary operator's syntax is (condition ? trueValue:falseValue ). Please check your syntax.",r9
			v=sBLK
			break
		case sMINUS:
			v=v1 - v2
			break
		case sMULP:
			v=v1 * v2
			break
		case sDIV:
			v=(v2!=dZ ? v1/v2:dZ)
			break
		case sMOD1:
			v=Math.floor(v2!=iZ ? v1/v2:dZ).toLong()
			break
		case sMOD:
			v=(Long)(v2!=iZ ? v1%v2:lZ)
			break
		case sPWR:
			v=v1 ** v2
			break
		case sAMP:
			v=v1 & v2
			break
		case sBOR:
			v=v1 | v2
			break
		case sBXOR:
			v=v1 ^ v2
			break
		case sBNAND:
			v=~(v1 & v2)
			break
		case sBNOR:
			v=~(v1 | v2)
			break
		case sBNXOR:
			v=~(v1 ^ v2)
			break
		case sBNOT:
			v=~v2
			break
		case sSBL:
			v=v1 << v2
			break
		case sSBR:
			v=v1 >> v2
			break
		case sLAND:
			v=!!v1 && !!v2
			break
		case sLOR:
			v=!!v1 || !!v2
			break
		case sLXOR:
			v=!v1!=!v2
			break
		case sLNAND:
			v=!(!!v1 && !!v2)
			break
		case sLNOR:
			v=!(!!v1 || !!v2)
			break
		case sLNXOR:
			v=!(!v1!=!v2)
			break
		case sEQ:
			v=v1==v2
			break
		case sNEQ:
		case sNEQA:
			v=v1!=v2
			break
		case sLTH:
			v=v1<v2
			break
		case sGTH:
			v=v1>v2
			break
		case sLTHE:
			v=v1<=v2
			break
		case sGTHE:
			v=v1>=v2
			break
		case sNEG:
			v=!v2
			break
		case sDNEG:
			v=!!v2
			break
		case sPLUS:
		default:
			v=t==sSTR ? "$v1$v2".toString():v1+v2
			break
	}
	return v
}

@CompileStatic
private static String typIL(String t1,String t2){ return t1==sLONG || t2==sLONG ? sLONG:sINT }

@CompileStatic
private static String buildList(List list,String suffix=sAND){
	if(!list)return sBLK
	Integer n,t0,t1
	n=i1
	t0=list.size()
	t1=t0-i1
	String res; res=sBLK
	String a=sCOMMA+sSPC
	for(item in list){
		res+=item.toString()+(n<t0 ? (n==t1 ? sSPC+suffix+sSPC:a):sBLK)
		n++
	}
	return res
}

@CompileStatic
private String buildDeviceList(Map r9,devices,String suffix=sAND){
	if(!devices)return sBLK
	List nlist=(devices instanceof List)? devices:[devices]
	List list=[]
	def dev
	for(String device in nlist){
		dev=getDevice(r9,device)
		if(dev!=null)list.push(dev)
	}
	return buildList(list,suffix)
}

@CompileStatic
private String buildDeviceAttributeList(Map r9,List<String> devices,String attr,String suffix=sAND){
	if(!devices)return sBLK
	List list=[]
	Map value
	def v
	for(String device in devices){
		value=getDeviceAttribute(r9,device,attr)
		v= !isErr(value) ? oMv(value) : sBLK
		list.push(v)
	}
	return buildList(list,suffix)
}

/** roundTimeToMinutes
 * Usage: roundTimeToMinutes(time, mins, roundup) */
private Map func_roundtimetominutes(Map r9,List<Map> prms){
	String err='roundTimeToMinutes(time,mins,roundup)'
	if(badParams(prms,i3))return rtnErr(err)
	Long value= longEvalExpr(r9,prms[iZ],sDTIME)
	Integer mins; mins= intEvalExpr(r9,prms[i1])
	Boolean rndUp= boolEvalExpr(r9,prms[i2])

	if(mins<i1 || mins>i60) return rtnErr(err)

	ZonedDateTime zdt, nzdt; zdt = localDate(r9,value)
	nzdt= zdt.withNano(iZ)
	nzdt= nzdt.withSecond(iZ) // this is rounding down

	Integer mod= nzdt.getMinute() % mins
	Integer addm; addm=iZ
	if(mod!=iZ){ addm= mins-mod	}
	if(rndUp){
		nzdt= nzdt.plusMinutes(addm.toLong())
	} else nzdt= nzdt.minusMinutes(mod.toLong())

	rtnMap(sDTIME, nzdt.toInstant().toEpochMilli())
}

/** setVariable assigns a variable
 * Usage: setVariable(variablename, value) */
private Map func_setvariable(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('setVariable(variablename,value)')
	Map t0=setVariable(r9,strEvalExpr(r9,prms[iZ]),oMv(evaluateExpression(r9,prms[i1])))
	if(!isErr(t0)) return rtnMapB(true)
	rtnErr(sMv(t0))
}

/** dewPoint returns the calculated dew point temperature
 * Usage: dewPoint(temperature,relativeHumidity[, scale]) */
private Map func_dewpoint(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('dewPoint(temperature,relativeHumidity[, scale])')
	Double t,rh,res
	t=dblEvalExpr(r9,prms[iZ])
	rh=dblEvalExpr(r9,prms[i1])
	//if no temperature scale is provided we assume the location's temperature scale
	Boolean fahrenheit= (prms.size()>i2 ? strEvalExpr(r9,prms[i2]) :gtLtScale()).toUpperCase()=='F'
	if(fahrenheit) t=(t-32.0D)*5.0D/9.0D
	//convert rh to percentage
	if((rh>dZ) && (rh<d1)) rh=rh*d100
	Double b=(Math.log(rh/d100)+((17.27D*t)/(237.3D+t)))/17.27D
	res=(237.3D*b)/(d1-b)
	if(fahrenheit) res=res*9.0D/5.0D+32.0D
	rtnMapD(res)
}

/** celsius converts temperature from Fahrenheit to Celsius
 * Usage: celsius(temperature)							*/
private Map func_celsius(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('celsius(temperature)')
	Double t=dblEvalExpr(r9,prms[iZ])
	rtnMapD((Double)((t-32.0D)*5.0D/9.0D))
}

/** fahrenheit converts temperature from Celsius to Fahrenheit			**/
/** Usage: fahrenheit(temperature)						**/
private Map func_fahrenheit(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('fahrenheit(temperature)')
	Double t=dblEvalExpr(r9,prms[iZ])
	rtnMapD((Double)(t*9.0D/5.0D+32.0D))
}

/** fahrenheit converts temperature between Celsius and Fahrenheit if the	**/
/** units differ from location.temperatureScale					**/
/** Usage: convertTemperatureIfNeeded(temperature,unit)			**/
private Map func_converttemperatureifneeded(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('convertTemperatureIfNeeded(temperature,unit)')
	String u=strEvalExpr(r9,prms[i1]).toUpperCase()
	Map a=prms[iZ]
	switch(gtLtScale()){
		case u: // matches return value
			return rtnMapD( dblEvalExpr(r9,a) )
		case 'F': return func_celsius(r9,[a])
		case 'C': return func_fahrenheit(r9,[a])
	}
	return [:]
}

/** integer converts a decimal to integer value			**/
/** Usage: integer(decimal or string)				**/
private Map func_integer(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('integer(decimal or string)')
	rtnMapI(intEvalExpr(r9,prms[iZ]))
}
private Map func_int(Map r9,List<Map> prms){ return func_integer(r9,prms)}

/** decimal/float converts an integer value to it's decimal value		**/
/** Usage: decimal(integer or string)						**/
private Map func_decimal(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('decimal(integer or string)')
	rtnMapD(dblEvalExpr(r9,prms[iZ]))
}
private Map func_float(Map r9,List<Map> prms){ return func_decimal(r9,prms)}
private Map func_number(Map r9,List<Map> prms){ return func_decimal(r9,prms)}

/** string converts an value to it's string value				**/
/** Usage: string(anything)							**/
private Map func_string(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('string(anything)')
	String res; res=sBLK
	for(Map prm in prms) res+=strEvalExpr(r9,prm)
	rtnMapS(res)
}
private Map func_concat(Map r9,List<Map> prms){ return func_string(r9,prms)}
private Map func_text(Map r9,List<Map> prms){ return func_string(r9,prms)}

/** Boolean converts a value to it's Boolean value				**/
/** Usage: boolean(anything)							**/
private Map func_boolean(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('boolean(anything)')
	rtnMapB(boolEvalExpr(r9,prms[iZ]))
}
private Map func_bool(Map r9,List<Map> prms){ return func_boolean(r9,prms)}

/** sqr converts a decimal to square decimal value			**/
/** Usage: sqr(integer or decimal or string)				**/
private Map func_sqr(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('sqr'+sINTDECSTR)
	rtnMapD(dblEvalExpr(r9,prms[iZ])**i2)
}

/** sqrt converts a decimal to square root decimal value		**/
/** Usage: sqrt(integer or decimal or string)				**/
private Map func_sqrt(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('sqrt'+sINTDECSTR)
	rtnMapD(Math.sqrt(dblEvalExpr(r9,prms[iZ])))
}

/** ispistonpaused returns true if piston is paused			**/
/** Usage: pistonPaused(pistonName)			**/
private Map func_ispistonpaused(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('ispistonpaused(pistonName)')
	String s=strEvalExpr(r9,prms[iZ])
	Boolean r= wisPisPaused(s)
	if(r==(Boolean)null)
		return rtnErr('ispistonpaused(pistonName) piston not found')
	rtnMapB(r)
}

/** power converts a decimal to power decimal value			**/
/** Usage: power(integer or decimal or string, power)			**/
private Map func_power(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('power'+sINTDECSTR)
	rtnMapD(Math.pow(dblEvalExpr(r9,prms[iZ]), dblEvalExpr(r9,prms[i1]) ))
}
private Map func_pow(Map r9,List<Map> prms){ return func_power(r9,prms)}

/** sin converts a decimal to sine decimal value		**/
/** Usage: sin(integer or decimal or string)			**/
private Map func_sin(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('sin'+sINTDECSTR)
	rtnMapD(Math.sin(dblEvalExpr(r9,prms[iZ]) ))
}

/** asin converts a decimal to inverse sine decimal value in radians		**/
/** Usage: asin(integer or decimal or string)			**/
private Map func_asin(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('asin'+sINTDECSTR)
	rtnMapD(Math.asin(dblEvalExpr(r9,prms[iZ]) ))
}

/** cos converts a decimal to cosine decimal value			**/
/** Usage: cos(integer or decimal or string)			**/
private Map func_cos(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('cos'+sINTDECSTR)
	rtnMapD(Math.cos(dblEvalExpr(r9,prms[iZ]) ))
}

/** tan converts a decimal to tangent decimal value			**/
/** Usage: tan(integer or decimal or string)			**/
private Map func_tan(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('tan'+sINTDECSTR)
	rtnMapD(Math.tan(dblEvalExpr(r9,prms[iZ]) ))
}

/** atan2 converts a decimal to angle theta decimal value			**/
/** Usage: atan2(integer or decimal or string)			**/
private Map func_atan2(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('atan2'+sINTDECSTR)
	rtnMapD(Math.atan2(dblEvalExpr(r9,prms[iZ]) ))
}

/** log converts a decimal to natural logarithm decimal value		**/
/** Usage: log(integer or decimal or string)			**/
private Map func_log(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('log'+sINTDECSTR)
	rtnMapD(Math.log(dblEvalExpr(r9,prms[iZ]) ))
}

/**  converts a decimal degrees to radians decimal value		**/
/** Usage: toradians(integer or decimal or string)			**/
private Map func_toradians(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('toradians'+sINTDECSTR)
	rtnMapD(Math.toRadians(dblEvalExpr(r9,prms[iZ]) ))
}

/** todegrees converts a decimal radians to degrees decimal value		**/
/** Usage: todegrees(integer or decimal or string)			**/
private Map func_todegrees(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('todegrees'+sINTDECSTR)
	rtnMapD(Math.toDegrees(dblEvalExpr(r9,prms[iZ]) ))
}

/** round converts a decimal to rounded decimal value			**/
/** Usage: round(decimal or string[, precision])		**/
private Map func_round(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('round(decimal or string[, precision])')
	Integer precision=prms.size()>i1 ? intEvalExpr(r9,prms[i1]):iZ
	rtnMapD(Math.round(dblEvalExpr(r9,prms[iZ]) * (i10 ** precision))/(i10 ** precision))
}

/** floor converts a decimal to closest lower integer value		**/
/** Usage: floor(decimal or string)					**/
private Map func_floor(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('floor(decimal or string)')
	rtnMapI(icast(r9,Math.floor(dblEvalExpr(r9,prms[iZ]))))
}

/** ceiling converts a decimal to closest higher integer value	**/
/** Usage: ceiling(decimal or string)						**/
private Map func_ceiling(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('ceiling(decimal or string)')
	rtnMapI(icast(r9,Math.ceil(dblEvalExpr(r9,prms[iZ]))))
}
private Map func_ceil(Map r9,List<Map> prms){ return func_ceiling(r9,prms)}


/** sprintf converts formats a series of values into a string			**/
/** Usage: sprintf(format, arguments)						**/
private Map func_sprintf(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('sprintf(format, arguments)')
	String format; format=sNL
	List args=[]
	try{
		format=strEvalExpr(r9,prms[iZ])
		Integer sz=prms.size()
		Integer x
		for(x=i1; x<sz; x++) args.push( oMv(evaluateExpression(r9,prms[x])) )
		return rtnMapS(sprintf(format,args))
	}catch(all){
		return rtnErr("$all $format $args".toString())
	}
}
private Map func_format(Map r9,List<Map> prms){ return func_sprintf(r9,prms)}

/** left returns a substring of a value					**/
/** Usage: left(string, count)						**/
private Map func_left(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('left(string, count)')
	String value=strEvalExpr(r9,prms[iZ])
	Integer n,sz
	n=intEvalExpr(r9,prms[i1])
	sz=value.size()
	if(n>sz || n < 0)n=sz
	rtnMapS(value.substring(iZ,n))
}

/** right returns a substring of a value				**/
/** Usage: right(string, count)						**/
private Map func_right(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('right(string, count)')
	String value=strEvalExpr(r9,prms[iZ])
	Integer n,sz
	n=intEvalExpr(r9,prms[i1])
	sz=value.size()
	if(n>sz || n < 0)n=sz
	rtnMapS(value.substring(sz-n,sz))
}

/** strlen returns the length of a string value				**/
/** Usage: strlen(string)						**/
private Map func_strlen(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('strlen(string)')
	String value=strEvalExpr(r9,prms[iZ])
	rtnMapI(value.size())
}
private Map func_length(Map r9,List<Map> prms){ return func_strlen(r9,prms)}

/** coalesce returns the first non-empty parameter				**/
/** Usage: coalesce(value1[, value2[, ..., valueN]])				**/
private Map func_coalesce(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('coalesce'+sVALUEN)
	Integer sz,i
	sz=prms.size()
	for(i=iZ; i<sz; i++){
		Map value=evaluateExpression(r9,prms[i])
		def v= oMv(value)
		if(!(v==null || (v instanceof List ? v==[null] || v==[] || v==[sSNULL]:false) || isErr(value) || v==sSNULL || scast(r9,v)==sBLK)){
			return value
		}
	}
	rtnMap(sDYN,null)
}

/** trim removes leading and trailing spaces from a string			**/
/** Usage: trim(value)								**/
private Map func_trim(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('trim(value)')
	String t0=strEvalExpr(r9,prms[iZ])
	String value=t0.trim()
	rtnMapS(value)
}

/** trimleft removes leading spaces from a string				**/
/** Usage: trimLeft(value)							**/
private Map func_trimleft(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('trimLeft(value)')
	String t0=strEvalExpr(r9,prms[iZ])
	String value=t0.replaceAll('^\\s+',sBLK)
	rtnMapS(value)
}
private Map func_ltrim(Map r9,List<Map> prms){ return func_trimleft(r9,prms)}

/** trimright removes trailing spaces from a string				**/
/** Usage: trimRight(value)							**/
private Map func_trimright(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('trimRight(value)')
	String t0=strEvalExpr(r9,prms[iZ])
	String value=t0.replaceAll('\\s+$',sBLK)
	rtnMapS(value)
}
private Map func_rtrim(Map r9,List<Map> prms){ return func_trimright(r9,prms)}

/** substring returns a substring of a value					**/
/** Usage: substring(string, start, count)					**/
private Map func_substring(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('substring(string, start, count)')
	String value,res
	value=strEvalExpr(r9,prms[iZ])
	Integer start,n
	start=intEvalExpr(r9,prms[i1])
	n=prms.size()>i2 ? intEvalExpr(r9,prms[i2]):null
	//def end=null
	res=sBLK
	Integer t0=value.size()
	if(start<t0 && start>-t0){
		if(n!=null){
			if(n<iZ){
				//reverse
				start=start<iZ ? -start:t0-start
				n=-n
				value=value.reverse()
			}
			if(start>=iZ){
				if(n>t0-start)n=t0-start
			}else if(n>-start)n=-start
		}
		start=start>=iZ ? start:t0+start
		if(n>t0-start)n=t0-start
		res=n==null ? value.substring(start):value.substring(start,start+n)
	}
	rtnMapS(res)
}
private Map func_substr(Map r9,List<Map> prms){ return func_substring(r9,prms)}
private Map func_mid(Map r9,List<Map> prms){ return func_substring(r9,prms)}

/** replace replaces a search text inside of a value				**/
/** Usage: replace(string, search, replace[, [..],search, replace])		**/
private Map func_replace(Map r9,List<Map> prms){
	Integer sz,i,n
	sz=prms.size()
	if(badParams(prms,i3) || sz%i2!=i1)return rtnErr('replace(string, search, replace[, [..],search, replace])')
	String value,search,replace
	value=strEvalExpr(r9,prms[iZ])
	n=Math.floor((sz-i1)/i2).toInteger()
	for(i=iZ; i<n; i++){
		search=strEvalExpr(r9,prms[i*i2+i1])
		replace=strEvalExpr(r9,prms[i*i2+i2])
		sz=search.size()
		if((sz>i2)&& search.startsWith(sDIV)&& search.endsWith(sDIV)){
			def ssearch= ~search.substring(i1,sz-i1)
			value=value.replaceAll(ssearch,replace)
		}else value=value.replace(search,replace)
	}
	rtnMapS(value)
}

/** rangeValue returns the matching value in a range					**/
/** Usage: rangeValue(input, defaultValue,point1, value1[, [..],pointN, valueN])	**/
private Map func_rangevalue(Map r9,List<Map> prms){
	Integer sz=prms.size()
	if(badParams(prms,i2) || sz%i2!=iZ)return rtnErr('rangeValue(input, defaultValue,point1, value1[, [..],pointN, valueN])')
	Double input=dblEvalExpr(r9,prms[iZ])
	Map value; value=prms[i1]
	Integer n=Math.floor((sz-i2)/i2).toInteger()
	Double point
	Integer i
	for(i=iZ; i<n; i++){
		point=dblEvalExpr(r9,prms[i*i2+i2])
		if(input>=point)value=prms[i*i2 +i3]
	}
	return value
}

/** rainbowValue returns the matching value in a range				**/
/** Usage: rainbowValue(input, minInput, minColor,maxInput, maxColor)		**/
private Map func_rainbowvalue(Map r9,List<Map> prms){
	if(badParams(prms,i5))return rtnErr('rainbowValue(input, minColor,minValue,maxInput, maxColor)')
	Integer input,minInput,maxInput
	input=intEvalExpr(r9,prms[iZ])
	minInput=intEvalExpr(r9,prms[i1])
	Map minColor,maxColor
	minColor=gtColor(strEvalExpr(r9,prms[i2]))
	maxInput=intEvalExpr(r9,prms[i3])
	maxColor=gtColor(strEvalExpr(r9,prms[i4]))
	if(minInput>maxInput){
		Integer x=minInput
		minInput=maxInput
		maxInput=x
		Map x1=minColor
		minColor=maxColor
		maxColor=x1
	}
	input=(input<minInput ? minInput:(input>maxInput ? maxInput:input))
	String hx='hex'
	if((input==minInput)|| (minInput==maxInput))return rtnMapS(sMs(minColor,hx))
	if(input==maxInput)return rtnMapS(sMs(maxColor,hx))
	List<Integer> start=hexToHsl(sMs(minColor,hx))
	List<Integer> end=hexToHsl(sMs(maxColor,hx))
	Double alpha=d1*(input-minInput)/(maxInput-minInput+i1)
	Integer h=Math.round(start[iZ]-((input-minInput)*(start[iZ]-end[iZ])/(maxInput-minInput))).toInteger()
	Integer s=Math.round(start[i1]+(end[i1]-start[i1])*alpha).toInteger()
	Integer l=Math.round(start[i2]+(end[i2]-start[i2])*alpha).toInteger()
	rtnMapS(hslToHex(h.toDouble(),s.toDouble(),l.toDouble()))
}

/** indexOf finds the first occurrence of a substring in a string		**/
/** Usage: indexOf(stringOrDeviceOrList, substringOrItem)			**/
private Map func_indexof(Map r9,List<Map> prms){
	Integer sz=prms.size()
	if(badParams(prms,i2) /*|| (sMt(prms[iZ])!=sDEV && sz!=i2)*/)return rtnErr('indexOf(stringOrDeviceOrList, substringOrItem)')
	if(/*sMt(prms[iZ])==sDEV &&*/ sz>i2){
		Integer t0=sz-i1
		String item=strEvalExpr(r9,prms[t0])
		Integer idx
		for(idx=iZ; idx<t0; idx++){
			Map it=evaluateExpression(r9,prms[idx],sSTR)
			if(sMv(it)==item)return rtnMapI(idx)
		}
		return rtnMapI(iN1)
	}else if(oMv(prms[iZ]) instanceof Map){
		def item= oMv(evaluateExpression(r9,prms[i1],sMt(prms[iZ])))
		String key=mMv(prms[iZ]).find{ it.value==item }?.key
		return rtnMapS(key)
	}else{
		String value=strEvalExpr(r9,prms[iZ])
		String substring=strEvalExpr(r9,prms[i1])
		return rtnMapI(value.indexOf(substring))
	}
}

/** lastIndexOf finds the last occurrence of a substring in a string		**/
/** Usage: lastIndexOf(string, substring)					**/
private Map func_lastindexof(Map r9,List<Map> prms){
	Integer sz=prms.size()
	if(badParams(prms,i2) /*|| (sMt(prms[iZ])!=sDEV && sz!=i2)*/)return rtnErr('lastIndexOf(string, substring)')
	if(/*sMt(prms[iZ])==sDEV &&*/ sz>i2){
		String item=strEvalExpr(r9,prms[sz-i1])
		Integer idx
		for(idx=sz-i2; idx>=iZ; idx--){
			if(strEvalExpr(r9,prms[idx])==item){ return rtnMapI(idx) }
		}
		return rtnMapI(iN1)
	}else if(oMv(prms[iZ]) instanceof Map){
		def item= oMv(evaluateExpression(r9,prms[i1],sMt(prms[iZ])))
		String key; key=sNL
		mMv(prms[iZ]).each{ if(it.value==item) key=it.key }
		return rtnMapS(key)
	}else{
		String value=strEvalExpr(r9,prms[iZ])
		String substring=strEvalExpr(r9,prms[i1])
		return rtnMapI(value.lastIndexOf(substring))
	}
}


/** lower returns a lower case value of a string				**/
/** Usage: lower(string)							**/
private Map func_lower(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('lower(string)')
	String res; res=sBLK
	for(Map prm in prms) res+=strEvalExpr(r9,prm)
	rtnMapS(res.toLowerCase())
}

/** upper returns a upper case value of a string				**/
/** Usage: upper(string)							**/
private Map func_upper(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('upper(string)')
	String res; res=sBLK
	for(Map prm in prms) res+=strEvalExpr(r9,prm)
	rtnMapS(res.toUpperCase())
}

/** title returns a title case value of a string				**/
/** Usage: title(string)							**/
private Map func_title(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('title(string)')
	String res; res=sBLK
	for(Map prm in prms) res+=strEvalExpr(r9,prm)
	rtnMapS(res.tokenize(sSPC)*.toLowerCase()*.capitalize().join(sSPC))
}

@CompileStatic
static Boolean isLiStr(String t, String typ, Integer sz){
	return (sz==i1 && (t!=typ || t in ListSTRDYN))
}

/** try to convert string, list, or map to list */
@CompileStatic
private List listIt(Map r9,List<Map> prms,String t,String typ){
	def a; a= oMv(prms[iZ])
	if(t==typ){ //input is a list or map type
		if(t in [sDYN] && a instanceof String){
			String s; s= (String)a
			if(!stJson1(s) && !stJson(s)) s= sLB+s+sRB
			if(stJson1(s) || stJson(s)){
				try{
					a= new JsonSlurper().parseText(s)
				}catch(ignored){}
			}
		}
	}
	List res
	res= a instanceof List ? (List)a : []
	if(a instanceof Map){
		Map m= (Map)a
		if(m) for(j in m){ res << j.value }
	}
	if(!res) res= strEvalExpr(r9,prms[iZ]).split(sCOMMA) as List
	return res
}

/** avg calculates the average of a series of numeric values			**/
/** Usage: avg(values)								**/
private Map func_avg(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr(sAVG+sVALUEN)
	Double s; s=dZ

	String t= sMt(prms[iZ])
	String typ; typ=t.replace(sLRB,sBLK)
	Integer i,sz; sz=prms.size()
	if(isLiStr(t,typ,sz)){
		List res= listIt(r9,prms,t,typ)
		sz=res.size()
		if(sz){
			for(i=iZ;i<sz;i++){ s+=res[i] }
			return rtnMapD(s/sz)
		}else return rtnErr(sAVG+sVALUEN)
	}
	for(Map prm in prms) s+=dblEvalExpr(r9,prm)
	rtnMapD(s/sz)
}

/** median returns the value in the middle of a sorted array of numeric values			**/
/** Usage: median(values)							**/
private Map func_median(Map r9,List<Map> prms){

	String s='median'+sVALUEN
	String t= sMt(prms[iZ])
	String typ; typ=t.replace(sLRB,sBLK)
	Integer i,sz; sz=prms.size()
	if(isLiStr(t,typ,sz)){
		List res= listIt(r9,prms,t,typ).sort()
		sz=res.size()
		if(sz>i1){
			i=Math.floor(sz/i2).toInteger()
			return rtnMap(sDYN, sz%i2==iZ ? (res[i-i1]+res[i])/i2 : res[i])
		}else return rtnErr(s)
	}

	if(badParams(prms,i2))return rtnErr(s)
	List<Map> data=prms.collect{ Map it -> evaluateExpression(r9,it,sDYN)}.sort{ Map it -> oMv(it) }
	i=Math.floor(sz/i2).toInteger()
	if(data){ return sz%i2==iZ ? rtnMap(sMt(data[i]),( oMv(data[i-i1]) + oMv(data[i]) )/i2) : data[i] }
	rtnMap(sDYN,sBLK)
}

private Map mostleast(Map r9,List<Map> prms, String s, Boolean least){
	if(badParams(prms,i1))return rtnErr(s+sVALUEN)
	Map<Object,Map> data=[:]

	String t= sMt(prms[iZ])
	String typ; typ=t.replace(sLRB,sBLK)
	Integer i,sz; sz=prms.size()
	if(isLiStr(t,typ,sz)){
		List res= listIt(r9,prms,t,typ)
		sz=res.size()
		if(sz){
			for(i=iZ;i<sz;i++){
				def v=res[i]
				data[v]=rtnMap(sDYN,v)+[(sC):(data[v]?.c ?: iZ)+i1]
			}
		}else return rtnErr(s+sVALUEN)
	}else{

		for(Map prm in prms){
			Map value=evaluateExpression(r9,prm,sDYN)
			def v= oMv(value)
			data[v]=rtnMap(sMt(value),v)+[(sC):(data[v]?.c ?: iZ)+i1]
		}
	}

	Map value=data.sort{ least ? it.value.c : -it.value.c }.collect{ it.value }[iZ]
	rtnMap(sMt(value),oMv(value))
}

/** least returns the value that is least found a series of numeric values	**/
/** Usage: least(values)							**/
private Map func_least(Map r9,List<Map> prms){
	return mostleast(r9,prms,'least',true)
}

/** most returns the value that is most found a series of numeric values	**/
/** Usage: most(values)								**/
private Map func_most(Map r9,List<Map> prms){
	return mostleast(r9,prms,'most',false)
}

/** sum calculates the sum of a series of numeric values			**/
/** Usage: sum(values)								**/
private Map func_sum(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('sum'+sVALUEN)
	Double s; s=dZ

	String t= sMt(prms[iZ])
	String typ; typ=t.replace(sLRB,sBLK)
	Integer i,sz; sz=prms.size()
	if(isLiStr(t,typ,sz)){
		List res= listIt(r9,prms,t,typ)
		sz=res.size()
		if(sz){
			for(i=iZ;i<sz;i++){ s+=res[i] }
			return rtnMapD(s)
		}else return rtnMapD(s)
	}
	for(Map prm in prms) s+=dblEvalExpr(r9,prm)
	rtnMapD(s)
}

/** variance calculates the [population] variance of a series of numeric values	**/
/** Usage: variance(values)							**/
private Map func_variance(Map r9,List<Map> prms){
	Double sum,value
	sum=dZ
	List values=[]

	String s='variance'+sVALUEN
	String t= sMt(prms[iZ])
	String typ; typ=t.replace(sLRB,sBLK)
	Integer i,sz; sz=prms.size()
	if(isLiStr(t,typ,sz)){
		List res= listIt(r9,prms,t,typ)
		sz=res.size()
		if(sz>i1){
			for(i=iZ;i<sz;i++){
				def v=res[i]
				values.push(v)
				sum+=v
			}
		}else return rtnErr(s)
	}else{

		if(badParams(prms,i2))return rtnErr(s)
		for(Map prm in prms){
			value=dblEvalExpr(r9,prm)
			values.push(value)
			sum+=value
		}
	}

	Double avg=sum/sz
	sum=dZ
	for(i=iZ;i<sz;i++) sum+=((values[i] as Double) -avg)**i2
	rtnMapD(sum/sz)
}

/** stdev calculates the [population] standard deviation of a series of numeric values	**/
/** Usage: stdev(values)							**/
private Map func_stdev(Map r9,List<Map> prms){
	Map res=func_variance(r9,prms)
	if(isErr(res)) return rtnErr('stdev'+sVALUEN)
	rtnMapD(Math.sqrt((Double)oMv(res)))
}

/** sort a series of numeric values			**/
/** Usage: sort(values,reverse)						**/
@CompileStatic
private Map func_sort(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('sort(value1, [value2,..., valueN,] reverse)')
	Integer t0=prms.size()-i1
	Boolean rev= boolEvalExpr(r9,prms[t0])
	List res; res=[]
	String t= sMt(prms[iZ])
	String typ; typ=t.replace(sLRB,sBLK)

	if(isLiStr(t,typ,t0)){
		res= listIt(r9,prms,t,typ).sort()
	}else{
		Integer i
		for(i=iZ; i<t0; i++){ if(sMt(prms[i]).replace(sLRB,sBLK)!=typ){ typ=sNL; break } }
		typ= typ?:sDYN
		List<Map>nprms; nprms=[]
		for(i=iZ; i<t0; i++){ nprms[i]=prms[i] }
		List<Map> data=nprms.collect{ Map it -> evaluateExpression(r9,it,typ)}.sort{ Map it -> oMv(it) }
		for(Map m in data){ res << oMv(m) }
	}
	if(rev)res= res.reverse()
	rtnMap(typ+sLRB,res)
}

@CompileStatic
private Map minmax(Map r9,List<Map>prms,String m,Boolean first=true){
	if(badParams(prms,i1))return rtnErr(m+sVALUEN)
	String t= sMt(prms[iZ])
	String typ; typ=t.replace(sLRB,sBLK)
	Integer sz
	if(isLiStr(t,typ,prms.size())){
		List res= listIt(r9,prms,t,typ).sort()
		sz=res.size()
		if(sz) return rtnMap(sDYN,res[ (first ? iZ:sz-i1)])
	}
	typ= sDYN
	List<Map> data=prms.collect{ Map it -> evaluateExpression(r9,it,typ)}.sort{ Map it -> oMv(it) }
	sz=data.size()
	if(sz)return data[ (first ? iZ:sz-i1)]
	rtnMap(typ,sBLK)
}

/** min calculates the minimum of a series of numeric values			**/
/** Usage: min(values)								**/
private Map func_min(Map r9,List<Map> prms){
	return minmax(r9,prms,'min',true)
}

/** max calculates the maximum of a series of numeric values			**/
/** Usage: max(values)								**/
private Map func_max(Map r9,List<Map> prms){
	return minmax(r9,prms,'max',false)
}

/** abs calculates the absolute value of a number				**/
/** Usage: abs(number)								**/
private Map func_abs(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('abs(value)')
	Double value=dblEvalExpr(r9,prms[iZ])
	String dataType=(value==Math.round(value).toDouble() ? sINT:sDEC)
	rtnMap(dataType,cast(r9,Math.abs(value),dataType,sDEC))
}

/** hslToHex converts a hue/saturation/level trio to it hex #rrggbb representation
 Usage: hslToHex(hue,saturation,level)						*/
private Map func_hsltohex(Map r9,List<Map> prms){
	if(badParams(prms,i3))return rtnErr('hsl(hue,saturation, level)')
	Double hue=dblEvalExpr(r9,prms[iZ])
	Double saturation=dblEvalExpr(r9,prms[i1])
	Double level=dblEvalExpr(r9,prms[i2])
	rtnMapS(hslToHex(hue,saturation,level))
}

/** count calculates the number of true/non-zero/non-empty items in a series of numeric values		**/
/** Usage: count(values)										**/
private Map func_count(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnMapI(iZ)
	Integer n,i
	Integer sz; sz=prms.size()
	n=iZ
	if(sz==i1){
		String t= sMt(prms[iZ])
		String tt1=t.replace(sLRB,sBLK)

		if(t!=tt1 || t in ListSTRDYN){
			Map m; m=null
			List list
			def a= oMv(prms[iZ])
			if(t!=tt1){ //input is a list or map type
				list= a instanceof List ? (List)a : null
				m= a instanceof Map ? (Map)a : null
			}else
				list=strEvalExpr(r9,prms[iZ]).split(sCOMMA)

			Boolean t1
			if(m){
				for(Map.Entry j in m){
					//if(isEric(r9)) myDetail r9,"j.value is $j.value ${myObj(j.value)}",iN2
					t1=bcast(r9,j.value)
					n+=t1 ? i1:iZ
				}
			}else{
				sz=list.size()
				for(i=iZ; i<sz; i++){
					t1=bcast(r9,list[i])
					n+=t1 ? i1:iZ
				}
			}
			return rtnMapI(n)
		}
	}
	for(Map prm in prms) n+=boolEvalExpr(r9,prm) ? i1:iZ
	rtnMapI(n)
}

/** size returns the number of values provided				**/
/** Usage: size(values)							**/
private Map func_size(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnMapI(iZ)
	Integer sz=prms.size()
	if(sz==i1){
		Integer n
		String t= sMt(prms[iZ])
		String tt1=t.replace(sLRB,sBLK)
		//if(eric1())myDetail r9,"size t: ${t} tt1: $tt1",iN2
		if(t!=tt1 || t in ListSTRDYN){
			Map m; m=null
			List list
			def a= oMv(prms[iZ])
			//if(eric1())myDetail r9,"a is ${myObj(a)}",iN2
			if(t!=tt1){ //input is a list or map type
				list= a instanceof List ? (List)a : null
				m= a instanceof Map ? (Map)a : null
			}else
				list=strEvalExpr(r9,prms[iZ]).split(sCOMMA)
			if(m) n=m.size()
			else n=list.size()
			return rtnMapI(n)
		}
	}
	rtnMapI(sz)
}

/** age returns the number of milliseconds an attribute had the current value	**/
/** Usage: age([device:attribute])						**/
private Map func_age(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('age'+sDATTRH)
	Map prm=evaluateExpression(r9,prms[iZ],sDEV)
	if(sMt(prm)==sDEV && sMa(prm) && liMv(prm).size()){
		def device=getDevice(r9,sLi(liMv(prm),iZ))
		if(device!=null){
			def dstate=device.currentState(sMa(prm),true)
			if(dstate){
				Long res=elapseT(((Date)dstate.getDate()).getTime())
				return rtnMap(sLONG,res)
			}
		}
	}
	rtnMapE('Invalid device')
}

/** previousAge returns the number of milliseconds an attribute had the previous value		**/
/** Usage: previousAge([device:attribute])							**/
private Map func_previousage(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('previousAge'+sDATTRH)
	Map prm=evaluateExpression(r9,prms[iZ],sDEV)
	if(sMt(prm)==sDEV && sMa(prm) && liMv(prm).size()){
		def device=getDevice(r9,sLi(liMv(prm),iZ))
		if(device!=null && !isDeviceLocation(device)){
			List states=device.statesSince(sMa(prm),new Date(elapseT(604500000L)),[max:i5])
			Integer sz=states.size()
			if(sz>i1){
				def newValue=states[iZ].getValue()
				//some events get duplicated look for the last "different valued" state
				Integer i
				for(i=i1; i<sz; i++){
					if(states[i].getValue()!=newValue){
						Long res=elapseT(((Date)states[i].getDate()).getTime())
						return rtnMap(sLONG,res)
					}
				}
			}
			//saying 7 days though it may be wrong- but we have no data
			return rtnMap(sLONG,604800000L)
		}
	}
	rtnMapE('Invalid device')
}

/** previousValue returns the previous value of the attribute				**/
/** Usage: previousValue([device:attribute])						**/
private Map func_previousvalue(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('previousValue'+sDATTRH)
	Map prm=evaluateExpression(r9,prms[iZ],sDEV)
	if(sMt(prm)==sDEV && sMa(prm) && liMv(prm).size()){
		def device=getDevice(r9,sLi(liMv(prm),iZ))
		Map attribute=devAttrT(sMa(prm),device)
		if(device!=null && !isDeviceLocation(device)){
			List states=device.statesSince(sMa(prm),new Date(elapseT(604500000L)),[max:i5])
			Integer sz=states.size()
			if(sz>i1){
				def newValue=states[iZ].getValue()
				//some events get duplicated want to look for the last "different valued" state
				Integer i
				for(i=i1; i<sz; i++){
					def res=states[i].getValue()
					if(res!=newValue){
						String t=sMt(attribute)
						return rtnMap(t,cast(r9,res,t))
					}
				}
			}
			//saying no value- we have no data
			return rtnMapS(sBLK)
		}
	}
	rtnMapE('Invalid device')
}

/** newer returns the number of devices whose attribute had the current		**/
/** value for less than the specified number of milliseconds			**/
/** Usage: newer([device:attribute] [,.., [device:attribute]],threshold)	**/
private Map func_newer(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('newer'+sDATTRHT)
	Integer t0=prms.size()-i1
	Long threshold=longEvalExpr(r9,prms[t0],sLONG)
	Integer res,i
	res=iZ
	for(i=iZ; i<t0; i++){
		Map age=func_age(r9,[prms[i]])
		if(!isErr(age) && lMs(age,sV)<threshold)res++
	}
	rtnMapI(res)
}

/** older returns the number of devices whose attribute had the current		**/
/** value for more than the specified number of milliseconds			**/
/** Usage: older([device:attribute] [,.., [device:attribute]],threshold)	**/
private Map func_older(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('older'+sDATTRHT)
	Integer t0=prms.size()-i1
	Long threshold=longEvalExpr(r9,prms[t0],sLONG)
	Integer res,i
	res=iZ
	for(i=iZ; i<t0; i++){
		Map age=func_age(r9,[prms[i]])
		if(!isErr(age) && lMs(age,sV)>=threshold)res++
	}
	rtnMapI(res)
}

/** startsWith returns true if a string starts with a substring			**/
/** Usage: startsWith(string, substring)					**/
private Map func_startswith(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('startsWith(string, substring)')
	String string=strEvalExpr(r9,prms[iZ])
	String substring=strEvalExpr(r9,prms[i1])
	rtnMapB(string.startsWith(substring))
}

/** endsWith returns true if a string ends with a substring				**/
/** Usage: endsWith(string, substring)							**/
private Map func_endswith(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('endsWith(string, substring)')
	String string=strEvalExpr(r9,prms[iZ])
	String substring=strEvalExpr(r9,prms[i1])
	rtnMapB(string.endsWith(substring))
}

/** contains returns true if a string contains a substring				**/
/** Usage: contains(string, substring)							**/
private Map func_contains(Map r9,List<Map> prms){
	Integer t0,idx
	t0=prms.size()
	if(badParams(prms,i2) /*|| (sMt(prms[iZ])!=sDEV && t0!=i2)*/)return rtnErr('contains(string, substring)')
	if(/*sMt(prms[iZ])==sDEV &&*/ t0>i2){
		t0=t0-i1
		String item=strEvalExpr(r9,prms[t0])
		for(idx=iZ; idx<t0; idx++){
			Map it=evaluateExpression(r9,prms[idx],sSTR)
			if(oMv(it)==item)return rtnMapB(true)
		}
		return rtnMapB(false)
	}else{
		String string=strEvalExpr(r9,prms[iZ])
		String substring=strEvalExpr(r9,prms[i1])
		rtnMapB(string.contains(substring))
	}
}

/** matches returns true if a string matches a pattern					**/
/** Usage: matches(string, pattern)							**/
private Map func_matches(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('matches(string, pattern)')
	String string=strEvalExpr(r9,prms[iZ])
	String pattern=strEvalExpr(r9,prms[i1])
	Boolean r=match(string,pattern)
	rtnMapB(r)
}

/** exists returns true if file exists					**/
/** Usage: exists(value1)							**/
private Map func_exists(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('exists(filename)')
	String string=strEvalExpr(r9,prms[iZ])
	rtnMapB(fileExists(r9,string))
}

/** eq returns true if two values are equal					**/
/** Usage: eq(value1, value2)							**/
private Map func_eq(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('eq(value1, value2)')
	String t=sMt(prms[iZ])==sDEV ? sMt(prms[i1]):sMt(prms[iZ])
	Map value1=evaluateExpression(r9,prms[iZ],t)
	Map value2=evaluateExpression(r9,prms[i1],t)
	rtnMapB(oMv(value1)==oMv(value2))
}

/** lt returns true if value1<value2						**/
/** Usage: lt(value1, value2)							**/
private Map func_lt(Map r9,List<Map> prms,Boolean not=false){
	if(badParams(prms,i2))return rtnErr('lt(value1, value2)')
	Map value1=evaluateExpression(r9,prms[iZ])
	Map value2=evaluateExpression(r9,prms[i1],sMt(value1))
	Boolean a= (oMv(value1)<oMv(value2))
	rtnMapB( not ? !a:a)
}

/** ge returns true if value1>=value2						**/
/** Usage: ge(value1, value2)							**/
private Map func_ge(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('ge(value1, value2)')
	func_lt(r9,prms,true)
}

/** gt returns true if value1>value2						**/
/** Usage: gt(value1, value2)							**/
private Map func_gt(Map r9,List<Map> prms,Boolean not=false){
	if(badParams(prms,i2))return rtnErr('gt(value1, value2)')
	Map value1=evaluateExpression(r9,prms[iZ])
	Map value2=evaluateExpression(r9,prms[i1],sMt(value1))
	Boolean a= (oMv(value1)>oMv(value2))
	rtnMapB( not ? !a:a)
}

/** le returns true if value1<=value2						**/
/** Usage: le(value1, value2)							**/
private Map func_le(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('le(value1, value2)')
	func_gt(r9,prms,true)
}

/** not returns the negative Boolean value					**/
/** Usage: not(value)								**/
private Map func_not(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('not(value)')
	Boolean value=boolEvalExpr(r9,prms[iZ])
	rtnMapB(!value)
}

/** if evaluates a Boolean and returns value1 if true,otherwise value2		**/
/** Usage: if(condition, valueIfTrue,valueIfFalse)				**/
private Map func_if(Map r9,List<Map> prms){
	if(badParams(prms,i3))return rtnErr('if(condition, valueIfTrue,valueIfFalse)')
	Boolean value=boolEvalExpr(r9,prms[iZ])
	Integer i= value ? i1:i2
	return evaluateExpression(r9,prms[i])
}

/** isEmpty returns true if the value is empty					**/
/** Usage: isEmpty(value)							**/
private Map func_isempty(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('isEmpty(value)')
	Map value=evaluateExpression(r9,prms[iZ])
	def v=oMv(value)
	Boolean res=v==null || (v instanceof List ? v==[null] || v==[] || v==[sSNULL]:false) || isErr(value) || v==sSNULL || scast(r9,v)==sBLK || "$v".toString()==sBLK
	rtnMapB(res)
}

/** datetime returns the value as a datetime type				**/
/** Usage: datetime([value])							**/
private Map func_datetime(Map r9,List<Map> prms){
	Integer sz=prms.size()
	if(badParams(prms,iZ) || sz>i1)return rtnErr('datetime([value])')
	Long value=sz>iZ ? longEvalExpr(r9,prms[iZ],sDTIME) :wnow()
	rtnMap(sDTIME,value)
}

/** date returns the value as a date type					**/
/** Usage: date([value])							**/
private Map func_date(Map r9,List<Map> prms){
	Integer sz=prms.size()
	if(badParams(prms,iZ) || sz>i1)return rtnErr('date([value])')
	Long value=sz>iZ ? longEvalExpr(r9,prms[iZ],sDATE) :(Long)cast(r9,wnow(),sDATE,sDTIME)
	rtnMap(sDATE,value)
}

/** time returns the value as a time type					**/
/** Usage: time([value])							**/
private Map func_time(Map r9,List<Map> prms){
	Integer sz=prms.size()
	if(badParams(prms,iZ) || sz>i1)return rtnErr('time([value])')
	Long value=sz>iZ ? longEvalExpr(r9,prms[iZ],sTIME) : (Long)cast(r9,wnow(),sTIME,sDTIME)
	rtnMap(sTIME,value)
}

private Map addtimeHelper(Map r9,List<Map> prms,Long mulp,String msg){
	Integer sz=prms.size()
	if(badParams(prms,i1) || sz>i2)return rtnErr(msg)
	Long value=sz==i2 ? longEvalExpr(r9,prms[iZ],sDTIME) : wnow()
	Long deltaMS=longEvalExpr(r9,(sz==i2 ? prms[i1]:prms[iZ]),sLONG) *mulp
	Long res; res=value+deltaMS
	TimeZone mtz=rTZ(r9)
	res+=Math.round((mtz.getOffset(value)-mtz.getOffset(res))*d1)
	return rtnMap(sDTIME,res)
}

/** addSeconds returns the value as a dateTime type						**/
/** Usage: addSeconds([dateTime,]seconds)						**/
private Map func_addseconds(Map r9,List<Map> prms){ return addtimeHelper(r9,prms,lTHOUS,'addSeconds([dateTime,]seconds)') }

/** addMinutes returns the value as a dateTime type						**/
/** Usage: addMinutes([dateTime,]minutes)						**/
private Map func_addminutes(Map r9,List<Map> prms){ return addtimeHelper(r9,prms,dMSMINT.toLong(),'addMinutes([dateTime,]minutes)') }

/** addHours returns the value as a dateTime type						**/
/** Usage: addHours([dateTime,]hours)							**/
private Map func_addhours(Map r9,List<Map> prms){ return addtimeHelper(r9,prms,dMSECHR.toLong(),'addHours([dateTime,]hours)') }

/** addDays returns the value as a dateTime type						**/
/** Usage: addDays([dateTime,]days)							**/
private Map func_adddays(Map r9,List<Map> prms){ return addtimeHelper(r9,prms,lMSDAY,'addDays([dateTime,]days)') }

/** addWeeks returns the value as a dateTime type						**/
/** Usage: addWeeks([dateTime,]weeks)							**/
private Map func_addweeks(Map r9,List<Map> prms){ return addtimeHelper(r9,prms,604800000L,'addWeeks([dateTime,]weeks)') }

/** weekDayName returns the name of the week day					**/
/** Usage: weekDayName(dateTimeOrWeekDayIndex)						**/
private Map func_weekdayname(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('weekDayName(dateTimeOrWeekDayIndex)')
	Long value=longEvalExpr(r9,prms[iZ],sLONG)
	Integer index= (value>=lMSDAY ? utcToLocalDate(r9,value).getDayOfWeek().getValue() : value.toInteger()) % i7
	rtnMapS(weekDaysFLD[index])
}

/** monthName returns the name of the month						**/
/** Usage: monthName(dateTimeOrMonthNumber)						**/
private Map func_monthname(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('monthName(dateTimeOrMonthNumber)')
	Long value=longEvalExpr(r9,prms[iZ],sLONG)
	Integer index= (value>=lMSDAY ? utcToLocalDate(r9,value).getMonth().getValue() : value.toInteger()) % i13
	rtnMapS(yearMonthsFLD[index])
}

/** arrayItem returns the nth item in the parameter list				**/
/** Usage: arrayItem(index, item0[, item1[, .., itemN]])				**/
private Map func_arrayitem(Map r9,List<Map> prms){
	if(badParams(prms,i2))return rtnErr('arrayItem(index, item0[, item1[, .., itemN]])')
	Map serr=rtnMapE('Array item index is outside of bounds.')
	Integer index=intEvalExpr(r9,prms[iZ])
	Integer sz=prms.size()
	if(sz==i2){
		String t= sMt(prms[i1])
		String tt1=t.replace(sLRB,sBLK)
		if(t!=tt1 || t in ListSTRDYN){
			List list
			def a= oMv(prms[iZ])
			if(t!=tt1 && a instanceof List) //input is a list type
				list= (List)a
			else
				list=strEvalExpr(r9,prms[i1]).split(sCOMMA)
			if(index<iZ || index>=list.size())return serr
			String rtnT= t!=tt1 ? tt1 : sSTR
			return rtnMap(rtnT,list[index])
		}
	}
	if(index<iZ || index>=sz-i1)return serr
	return prms[index+i1]
}

/** isBetween returns true if value>=startValue and value<=endValue		**/
/** Usage: isBetween(value,startValue,endValue)				**/
private Map func_isbetween(Map r9,List<Map> prms){
	if(badParams(prms,i3))return rtnErr('isBetween(value,startValue,endValue)')
	Map value=evaluateExpression(r9,prms[iZ])
	Map startValue=evaluateExpression(r9,prms[i1],sMt(value))
	Map endValue=evaluateExpression(r9,prms[i2],sMt(value))
	rtnMapB((oMv(value)>=oMv(startValue) && oMv(value)<=oMv(endValue)))
}

/** formatDuration returns a duration in a readable format					**/
/** Usage: formatDuration(value[, friendly=false[, granularity='s'[, showAdverbs=false]]])	**/
private Map func_formatduration(Map r9,List<Map> prms){
	Integer sz
	sz=prms.size()
	if(badParams(prms,i1) || sz>i4)return rtnErr("formatDuration(value[, friendly=false[, granularity='s'[, showAdverbs=false]]])")
	Long value
	value=longEvalExpr(r9,prms[iZ],sLONG)
	Boolean friendly=sz>i1 ? boolEvalExpr(r9,prms[i1]):false
	String granularity=sz>i2 ? strEvalExpr(r9,prms[i2]):sS
	Boolean showAdverbs=sz>i3 ? boolEvalExpr(r9,prms[i3]):false

	Integer sign=(value>=iZ)? i1:iN1
	if(sign<iZ)value=-value
	Integer ms=(value%i1000).toInteger()
	value=Math.floor((value-ms)/d1000).toLong()
	Integer s=(value%i60).toInteger()
	value=Math.floor((value-s)/d60).toLong()
	Integer m=(value%i60).toInteger()
	value=Math.floor((value-m)/d60).toLong()
	Integer h=(value%24).toInteger()
	value=Math.floor((value-h)/24.0D).toLong()
	Integer d=value.toInteger()

	Integer parts
	String partName
	switch(granularity){
		case sD: parts=i1; partName='day'; break
		case sH: parts=i2; partName='hour'; break
		case sM: parts=i3; partName='minute'; break
		case sMS: parts=i5; partName='millisecond'; break
		default:parts=i4; partName='second'; break
	}
	parts=friendly ? parts:(parts<i3 ? i3:parts)
	String res
	if(friendly){
		List p=[]
		if(d) p.push("$d day"+(d>i1 ? sS:sBLK))
		if(parts>i1 && h) p.push("$h hour"+(h>i1 ? sS:sBLK))
		if(parts>i2 && m) p.push("$m minute"+(m>i1 ? sS:sBLK))
		if(parts>i3 && s) p.push("$s second"+(s>i1 ? sS:sBLK))
		if(parts>i4 && ms) p.push("$ms millisecond"+(ms>i1 ? sS:sBLK))
		sz=p.size()
		switch(sz){
			case iZ:
				res=showAdverbs ? 'now':'0 '+partName+sS
				break
			case i1:
				res=p[iZ]
				break
			default:
				res=sBLK
				Integer i
				for(i=iZ; i<sz; i++){
					res+=(i ? (sz>i2 ? sCOMMA:sSPC):sBLK)+(i==sz-i1 ? sAND+sSPC:sBLK)+p[i]
				}
				res=(showAdverbs && (sign>iZ)? 'in ':sBLK)+res+(showAdverbs && (sign<iZ)? ' ago':sBLK)
				break
		}
	}else{
		res=(sign<iZ ? sMINUS:sBLK)+(d>iZ ? sprintf("%dd ",d):sBLK)+sprintf("%02d:%02d",h,m)+(parts>i3 ? sprintf(":%02d",s):sBLK)+(parts>i4 ? sprintf(".%03d",ms):sBLK)
	}
	rtnMapS(res)
}

/** parseDateTime returns a datetime				**/
/** Usage: parseDateTime(value[, format])						**/
private Map func_parsedatetime(Map r9,List<Map> prms){
	Integer sz=prms.size()
	if(badParams(prms,i1) || sz>i2)return rtnErr('parseDateTime(value[, format])')
	String value=strEvalExpr(r9,prms[iZ])
	String format=sz>i1 ? strEvalExpr(r9,prms[i1]):sNL
	Long res; res=lZ
	try{
		if(format){
			SimpleDateFormat formatter= new SimpleDateFormat(format)
			res= formatter.parse(value).getTime()
		}else res= stringToTime(r9,value)
	}catch(all){
		return rtnErr("$all $format $value".toString())
	}
	return rtnMap(sDTIME,res)
}

/** settzid returns previous tzid as string	and sets timezone to new timezone based on tzid			**/
/** Usage: setTzid(tzid)						**/
private Map func_settzid(Map r9,List<Map> prms){
	Integer sz=prms.size()
	String msg= 'setTzid(tzid)'
	if(badParams(prms,i1) || sz>i1)return rtnErr(msg)
	String id= strEvalExpr(r9,prms[iZ])
	if(!id) return rtnErr(msg)

	String sv= TZID(rTZ(r9))
	if(id!=sv){
		Boolean a=loadTZs(r9,id)
		if(!a) return rtnErr(msg+" bad tzid $id $sv".toString())
	}
	rtnMapS(sv)
}

/** formatDateTime returns a datetime in a readable format				**/
/** Usage: formatDateTime(value[, format [, tzid]])						**/
private Map func_formatdatetime(Map r9,List<Map> prms){
	Integer sz=prms.size()
	String msg= 'formatDateTime(value[, format [, tzid]])'
	if(badParams(prms,i1) || sz>i3)return rtnErr(msg)
	Long value=longEvalExpr(r9,prms[iZ],sDTIME)
	String format=sz>i1 ? strEvalExpr(r9,prms[i1]):sNL
	String tzid=sz>i2 ? strEvalExpr(r9,prms[i2]):sNL

	String sv= TZID(rTZ(r9))
	Boolean a; a=false

	if(tzid && tzid!=sv){
		a=loadTZs(r9,tzid)
		if(!a) return rtnErr(msg+" could not load tzid $format $value $tzid $sv".toString())
	}
	String rtn= format!=sNL ? formatLocalTime(r9,value,format):formatLocalTime(r9,value)
	if(a) loadTZs(r9,sv)
	rtnMapS(rtn)
}

/** random returns a random value						**/
/** Usage: random([range | value1, value2[, ..,valueN]])			**/
private Map func_random(Map r9,List<Map> prms){
	Integer sz=prms!=null && (prms instanceof List) ? prms.size():iZ
	switch(sz){
		case iZ:
			return rtnMapD(Math.random())
		case i1:
			Double range=dblEvalExpr(r9,prms[iZ])
			return rtnMapI(Math.round(range*Math.random()).toInteger())
		case i2:
			List<String> n=[sINT,sDEC]
			if((sMt(prms[iZ]) in n) && (sMt(prms[i1]) in n)){
				Double min,max,swap
				min=dblEvalExpr(r9,prms[iZ])
				max=dblEvalExpr(r9,prms[i1])
				if(min>max){
					swap=min
					min=max
					max=swap
				}
				return rtnMapI(Math.round(min+(max-min)*Math.random()).toInteger())
			}
	}
	Integer choice
	choice=Math.round((sz-i1)*Math.random()).toInteger()
	if(choice>=sz)choice=sz-i1
	return prms[choice]
}

/** distance returns a distance measurement							**/
/** Usage: distance((device | latitude,longitude),(device | latitude,longitude)[, unit])	**/
@SuppressWarnings('GroovyVariableNotAssigned')
private Map func_distance(Map r9,List<Map> prms){
	Integer sz=prms.size()
	if(badParams(prms,i2) || sz>i5)return rtnErr('distance((device | latitude,longitude),(device | latitude,longitude)[, unit])')
	Double lat1,lng1,lat2,lng2
	String unit
	Integer idx,pidx
	idx=pidx=iZ
	String errMsg; errMsg=sBLK
	while (pidx<sz){
		if(sMt(prms[pidx])!=sDEV || (sMt(prms[pidx])==sDEV && !!sMa(prms[pidx]))){
			//a decimal or device attribute is provided
			switch(idx){
				case iZ:
					lat1=dblEvalExpr(r9,prms[pidx])
					break
				case i1:
					lng1=dblEvalExpr(r9,prms[pidx])
					break
				case i2:
					lat2=dblEvalExpr(r9,prms[pidx])
					break
				case i3:
					lng2=dblEvalExpr(r9,prms[pidx])
					break
				case i4:
					unit=strEvalExpr(r9,prms[pidx])
			}
			idx+=i1
			pidx+=i1
			continue
		}else{
			switch(idx){
				case iZ:
				case i2:
					prms[pidx].a='latitude'
					Double lat=dblEvalExpr(r9,prms[pidx])
					prms[pidx].a='longitude'
					Double lng=dblEvalExpr(r9,prms[pidx])
					if(idx==iZ){
						lat1=lat
						lng1=lng
					}else{
						lat2=lat
						lng2=lng
					}
					idx+=i2
					pidx+=i1
					continue
				default:
					errMsg="Invalid parameter order. Expecting parameter #${idx+i1} to be a decimal, not a device.".toString()
					pidx=iN1
					break
			}
		}
		if(pidx==iN1)break
	}
	if(errMsg!=sBLK)return rtnMapE(errMsg)
	if(idx<i4 || idx>i5)return rtnMapE('Invalid parameter combination. Expecting either two devices, a device and two decimals, or four decimals, followed by an optional unit.')
	Double earthRadius=6371000.0D //meters
	Double dLat=Math.toRadians(lat2-lat1)
	Double dLng=Math.toRadians(lng2-lng1)
	Double a=Math.sin(dLat/d2)*Math.sin(dLat/d2)+
		Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*
		Math.sin(dLng/d2)*Math.sin(dLng/d2)
	Double c=d2*Math.atan2(Math.sqrt(a),Math.sqrt(d1-a))
	Double dist=earthRadius*c
	switch(unit!=null ? unit:sM){
		case 'km':
		case 'kilometer':
		case 'kilometers':
			return rtnMapD(dist/d1000)
		case 'mi':
		case 'mile':
		case 'miles':
			return rtnMapD(dist/1609.3440D)
		case 'ft':
		case 'foot':
		case 'feet':
			return rtnMapD(dist/0.3048D)
		case 'yd':
		case 'yard':
		case 'yards':
			return rtnMapD(dist/0.9144D)
	}
	rtnMapD(dist)
}

/** json encodes data as a JSON string							**/
/** Usage: json(value[, pretty])							**/
private static Map func_json(Map r9,List<Map> prms){
	if(badParams(prms,i1) || prms.size()>i2)return rtnErr('json(value[, format])')
	JsonBuilder builder=new JsonBuilder( [ oMv(prms[iZ]) ] )
	String op=prms.size()>i1 && prms[i1] ? 'toPrettyString':'toString'
	String json=builder."${op}"()
	rtnMapS(json[i1..-i2].trim())
}

/** urlencode encodes data for use in a URL						**/
/** Usage: urlencode(value)								**/
private Map func_urlencode(Map r9,List<Map> prms){
	if(badParams(prms,i1))return rtnErr('urlencode(value])')
	String t0=strEvalExpr(r9,prms[iZ])
	String value=(t0!=sNL ? t0:sBLK)
	rtnMapS(encodeURIComponent(value))
}
private Map func_encodeuricomponent(Map r9,List prms){ return func_urlencode(r9,prms)}

/** COMMON PUBLISHED METHODS							**/

private String mem(Boolean showBytes=true){
	Integer bytes=state.toString().length()
	return Math.round(d100*(bytes/100000.0D))+"%${showBytes ? " ($bytes bytes)".toString():sBLK}"
}

private static String srunTimeHis(Map r9){
	String myId=sMs(r9,snId)
	Map nc=theCacheVFLD[myId]
	return 'Total run history: '+((List)nc[sRTHIS]).toString()+'<br>' +
			'Last run details: '+((Map)nc[sRUNS]).toString()
}

/** UTILITIES									**/

private static String encodeURIComponent(value){
	// URLEncoder converts spaces to + which is then indistinguishable from any
	// actual + characters in the value. Match encodeURIComponent in ECMAScript
	// which encodes "a+b c" as "a+b%20c" rather than URLEncoder's "a+b+c"
	String holder='__wc_plus__'
	return URLEncoder.encode(
		"${value}".toString().replaceAll('\\+',holder),
		sUTF8
	).replaceAll('\\+','%20').replaceAll(holder,'+')
}

@CompileStatic
private Long gtWCTimeToday(Map r9,Long time){
	TimeZone tz= rTZ(r9)
	Long t0=getMidnightTime(r9)
	Long res=time+t0
	//we need to adjust for time overlapping during DST changes
	return Math.round( (res+(tz.getOffset(t0)-tz.getOffset(res)) ) *d1)
}

@Field static final List<String> trueStrings= [ '1','true', "on", "open",  "locked",  "active",  "wet",           "detected",    "present",    "occupied",    "muted",  "sleeping"]
@Field static final List<String> falseStrings=[ '0','false',"off","closed","unlocked","inactive","dry","clear",   "not detected","not present","not occupied","unmuted","not sleeping","null"]

@CompileStatic
private static Map dataT(ival,String isrcDT){
	def value; value=ival
	String srcDt; srcDt=isrcDT
	Boolean isfbd; isfbd=false
	value=(value instanceof GString)? "$value".toString():value //get rid of GStrings
	if(srcDt==sNL || srcDt.length()==iZ || srcDt in [sBOOLN,sDYN]){
		if(value instanceof List)srcDt=sDEV
		else if(value instanceof Boolean)srcDt=sBOOLN
		else if(value instanceof String)srcDt=sSTR
		else if(value instanceof Integer)srcDt=sINT
		else if(value instanceof Long || value instanceof BigInteger)srcDt=sLONG
		else if(value instanceof Double)srcDt=sDEC
		else if(value instanceof BigDecimal || value instanceof Float){srcDt=sDEC; isfbd=true}
		else if(value instanceof Map){
			Map m=(Map)value
			if(m && m[sX]!=null && m[sY]!=null && m[sZ]!=null)srcDt=sVEC
		}else{ value="$value".toString(); srcDt=sSTR }
	}
	//overrides
	switch(srcDt){
		case sBOOL: srcDt=sBOOLN; break
		case sNUMBER: srcDt=sDEC; break
		case sENUM: srcDt=sSTR; break
	}
	return [(sS):srcDt,(sV):value,(sT):isfbd]
}

@CompileStatic
private static objVal(ival, String rtype){
	Map rr=dataT(ival,sNL)
	return com_cast(oMv(rr), rtype, sMs(rr,sS))
}

@CompileStatic
private static Long lcast(Map r9,ival){
	if(ival instanceof Long) return (Long)ival
	if(ival instanceof Integer) return ((Integer)ival).toLong()
	if(ival instanceof Double) return Math.round((Double)ival)
	if(ival instanceof Boolean) return (Boolean)ival ? l1:lZ
	if(ival instanceof BigDecimal) return ((BigDecimal)ival).toLong()
	return (Long)objVal(ival,sLONG)
}

@CompileStatic
private static Double dcast(Map r9,ival){
	if(ival instanceof Double) return (Double)ival
	if(ival instanceof Integer) return ((Integer)ival).toDouble()
	if(ival instanceof Long) return ((Long)ival).toDouble()
	if(ival instanceof Boolean) return (Boolean)ival ? d1:dZ
	if(ival instanceof BigDecimal) return ((BigDecimal)ival).toDouble()
	return (Double)objVal(ival,sDEC)
}

@CompileStatic
private static Integer icast(Map r9,ival){
	if(ival instanceof Integer) return (Integer)ival
	if(ival instanceof Long) return ((Long)ival).toInteger()
	if(ival instanceof Double) return ((Double)ival).toInteger()
	if(ival instanceof Boolean) return (Boolean)ival ? i1:iZ
	if(ival instanceof BigDecimal) return ((BigDecimal)ival).toInteger()
	return (Integer)objVal(ival,sINT)
}

@CompileStatic
private static Boolean bcast(Map r9,ival){
	if(ival instanceof Boolean) return (Boolean)ival
	if(ival instanceof Integer) return (Integer)ival != iZ
	if(ival instanceof Long) return (Long)ival != lZ
	if(ival instanceof Double) return (Double)ival != dZ
	return objVal(ival,sBOOLN) as Boolean
}

@CompileStatic
private String scast(Map r9,v){
	if(v==null) return sBLK
	if(v instanceof String) return (String)v
	if(v instanceof Integer || v instanceof Long) return v.toString()
	if(v instanceof Boolean) return (Boolean)v ? sTRUE:sFALSE
	if(v instanceof Double){
		// strip trailing zeroes (same as cast sSTR/sDEC path)
		return v.toString().replaceFirst(/(?:\.|(\.\d*?))0+$/,'$1')
	}
	if(v instanceof GString) return v.toString()
	Map rr=dataT(v,sNL)
	String srcDt=sMs(rr,sS)
	def vv=oMv(rr)
	return matchCast(vv,sSTR) ? (String)vv:(String)cast(r9,vv,sSTR,srcDt)
}

@CompileStatic
private static com_cast(ival,String dataType,String srcDt){
	def value=ival
	switch(dataType){
		case sDEC:
			switch(srcDt){
				case sSTR:
					String s=((String)value).replaceAll(/[^-\d.eE]/,sBLK)
					if(s.isDouble() || s.isFloat())
						return s.toDouble()
					if(s.isLong())
						return s.toLong().toDouble()
					if(s.isInteger())
						return s.toInteger().toDouble()
					if(s in trueStrings)
						return d1
					break
				case sBOOLN: return (Double)(value ? d1:dZ)
			}
			Double res
			try{
				res= value as Double
			}catch(ignored){
				res=dZ
			}
			return res
		case sINT:
			switch(srcDt){
				case sSTR:
					String s=((String)value).replaceAll(/[^-\d.eE]/,sBLK)
					if(s.isInteger())
						return s.toInteger()
					if(s.isFloat())
						return Math.floor(s.toDouble()).toInteger()
					if(s in trueStrings)
						return i1
					break
				case sBOOLN: return (Integer)(value ? i1:iZ)
			}
			Integer res
			try{
				res= value as Integer
			}catch(ignored){
				res=iZ
			}
			return res
		case sBOOLN:
			switch(srcDt){
				case sINT:
				case sDEC:
				case sBOOLN:
					return !!value
				case sDEV:
					return listWithSz(value)
			}
			if(value){
				String s= "$value".toLowerCase().trim()
				if(s in falseStrings)return false
				if(s in trueStrings)return true
			}
			return !!value
		case sLONG:
			switch(srcDt){
				case sSTR:
					String s=((String)value).replaceAll(/[^-\d.eE]/,sBLK)
					if(s.isLong() || s.isInteger())
						return s.toLong()
					if(s.isFloat())
						return Math.floor(s.toDouble()).toLong()
					if(s in trueStrings)
						return l1
					break
				case sBOOLN: return (value ? l1:lZ)
			}
			Long res
			try{
				res=value as Long
			}catch(ignored){
				res=lZ
			}
			return res
	}
	return value
}

private Object cast(Map r9,ival,String dataTT,String isrcDT=sNL){
	if(dataTT==sDYN)return ival

	String dataType,srcDt
	dataType=dataTT
	srcDt=isrcDT
	def value
	value=ival

	if(value==null){
		if(dataType==sSTR) return sBLK
		value=sBLK
		srcDt=sSTR
	}
	Map rr=dataT(value,srcDt)
	Boolean isfbd=bIs(rr,sT)
	srcDt=sMs(rr,sS)
	value=oMv(rr)

	switch(dataType){
		case sBOOL: dataType=sBOOLN; break
		case sNUMBER: dataType=sDEC; break
		case sENUM: dataType=sSTR; break
	}
	if(isEric(r9))myDetail r9,"cast src: ${isrcDT} ($ival) as $dataTT --> ${srcDt}${isfbd ? ' bigDF':sBLK} ($value) as $dataType",iN2
	switch(dataType){
		case sSTR:
		case sTEXT:
			switch(srcDt){
				case sBOOLN: return value ? sTRUE:sFALSE
				case sDEC:
					// strip trailing zeroes (e.g. 5.00 to 5 and 5.030 to 5.03)
					return value.toString().replaceFirst(/(?:\.|(\.\d*?))0+$/,'$1')
				case sINT:
				case sLONG: break
				case sTIME: return formatLocalTime(r9,value,'h:mm:ss a z')
				case sDATE: return formatLocalTime(r9,value,'EEE, MMM d yyyy')
				case sDTIME: return formatLocalTime(r9,value)
				case sDEV: return buildDeviceList(r9,value)
			}
			return "$value".toString()
		case sBOOLN:
			return (Boolean)com_cast(value,dataType,srcDt)
		case sINT:
			return (Integer)com_cast(value,dataType,srcDt)
		case sLONG:
			return (Long)com_cast(value,dataType,srcDt)
		case sDEC:
			return (Double)com_cast(value,dataType,srcDt)
		case sTIME:
			Long d
			d=srcDt==sSTR ? stringToTime(r9,value):((Number)value).toLong()
			if(d<lMSDAY)return d
			ZonedDateTime zdt= localDate(r9,d)
			d=Math.round((zdt.getHour()*dSECHR+zdt.getMinute()*d60+zdt.getSecond())*d1000)
			return d
		case sDATE:
		case sDTIME:
			Long d
			if(srcDt in [sTIME,sLONG,sINT,sDEC]){
				d=((Number)value).toLong()
				if(d<lMSDAY) value=gtWCTimeToday(r9,d)
				else value=d
			}
			d=srcDt==sSTR ? stringToTime(r9,value):(Long)value
			if(dataType==sDATE){
				ZonedDateTime zdt,nzdt; zdt= localDate(r9,d)
				nzdt= zdt.withNano(iZ)
				nzdt= nzdt.withSecond(iZ)
				nzdt= nzdt.withMinute(iZ)
				nzdt= nzdt.withHour(iZ)
				d= nzdt.toInstant().toEpochMilli()
			}
			return d
		case sVEC:
			if(srcDt==sSTR) value= fixVector((String)value)
			return value instanceof Map && value!=null && value[sX]!=null && value[sY]!=null && value[sZ]!=null ? value:[(sX):iZ,(sY):iZ,(sZ):iZ]
		case sORIENT:
			return value instanceof Map ? getThreeAxisOrientation(value):value
		case sMS:
		case sS:
		case sM:
		case sH:
		case sD:
		case sW:
		case sN: // months
		case sY: // years
			Long t1
			switch(srcDt){
				case sINT:
				case sLONG:
					t1=((Number)value).toLong(); break
				default:
					t1=lcast(r9,value)
			}
			switch(dataType){
				case sMS: return t1
				case sS: return Math.round(t1*d1000)
				case sM: return Math.round(t1*dMSMINT)
				case sH: return Math.round(t1*dMSECHR)
				case sD: return Math.round(t1*dMSDAY)
				case sW: return Math.round(t1*604800000.0D)
				case sN: return Math.round(t1*2592000000.0D) // 30 days in ms
				case sY: return Math.round(t1*31536000000.0D) // 365 days in ms
			}
			break
		case sDEV:
			//device type is an array of device Ids
			if(value instanceof List){
				((List<String>)value).removeAll{ String it -> !it }
				return (List)value
			}
			String v=scast(r9,value)
			if(v!=sNL)return [v]
			return []
	}
	//anything else
	return value
}

@CompileStatic
private Long elapseT(Long t,Long n=wnow()){ return Math.round(d1*n-t) }

@CompileStatic
private ZonedDateTime utcToLocalDate(Map r9,dateOrTimeOrString=null){
	def mdate=dateOrTimeOrString
	Long ldate
	if(!(mdate instanceof Long)){
		if(mdate instanceof String){
			ldate=stringToTime(r9,(String)mdate)
		}else if(mdate instanceof Date){
			//get unix time
			ldate=((Date)mdate).getTime()
		}
	}else ldate=(Long)mdate
	if(ldate==null || ldate==lZ){
		ldate=wnow()
	}
	return localDate(r9,ldate)
}

@CompileStatic
private ZonedDateTime localDate(Map r9, Long n=wnow()){
	Long t; t= n ?: wnow()
	ZonedDateTime zdt = Instant.ofEpochMilli(t)
			.atZone( mZ(r9) )
	return zdt
}

/**
 * convert to dtime (UTC)
 */
@CompileStatic
private Long stringToTime(Map r9,dateOrTimeOrString){
	Long lnull=(Long)null
	Long res
	res=lnull
	Integer n; n=iZ
	//SimpleDateFormat
	def a=dateOrTimeOrString
	try{
		if("$a".isNumber()){
			Double aa= a as Double
			Long tt= aa.toLong()
			if(tt<lMSDAY){
				res=gtWCTimeToday(r9,tt)
				n=i1
			}else{
// deal with a time in sec (vs. ms)
				Long span=63072000L // Math.round(730*(dMSDAY/d1000)) // 2 years in secs
				Long nowInsecs=Math.round((wnow()/lTHOUS).toDouble())
				if(tt<(nowInsecs+span) && tt>(nowInsecs-span)){
					res=tt*lTHOUS
					n=i2
				}
			}
			if(res==lnull){
				res=tt
				n=i3
			}
		}
	}catch(ignored){}

	if(res==lnull && dateOrTimeOrString instanceof String){
		String sdate=dateOrTimeOrString
		n=i4
		try{
			Date tt1=wtoDateTime(sdate)
			res=tt1.getTime()
		}catch(ignored){ res=lnull }


		// additional ISO 8601 that Hubitat does not parse
		if(res==lnull){
			n=i5
			try{
				String tt=sdate
				def regex1=/Z/
				String tt0=tt.replaceAll(regex1," -0000")
				res=(new Date()).parse("yyyy-MM-dd'T'HH:mm z",tt0).getTime()
			}catch(ignored){ res=lnull }
		}

		// next 3 format local time formatting done by cast
		if(res==lnull){
			n=14
			try{
				res=(new Date()).parse( 'EEE, MMM d yyyy @ h:mm:ss a z',sdate).getTime()
			}catch(ignored){ res=lnull }
		}

		if(res==lnull){
			n=i15
			try{
				res=(new Date()).parse( 'EEE, MMM d yyyy',sdate).getTime()
			}catch(ignored){ res=lnull }
		}

		if(res==lnull){
			n=i16
			try{
				res=(new Date()).parse( 'h:mm:ss a z',sdate).getTime()
			}catch(ignored){ res=lnull }
		}

		if(res==lnull){
			n=i6
			try{
				res=(new Date()).parse(sdate)
			}catch(ignored){ res=lnull }
		}

		if(res==lnull){
			n=i7
			try{
				//get unix time
				//if(!(sdate =~ /(\s[A-Z]{3}([+\-][0-9]{2}:[0-9]{2}|\s[0-9]{4})?$)/)){
				if(!(sdate =~ /(\s[A-Z]{3}([+\-]\d{2}:\d{2}|\s\d{4})?$)/)){
					Long newDate=(new Date()).parse(sdate+sSPC+formatLocalTime(r9,wnow(),'Z'))
					res=newDate
				}
			}catch(ignored){ res=lnull }
		}

		if(res==lnull){
			n=i8
			try{
				TimeZone tz
				tz=rTZ(r9)
				String nsdate,t0
				nsdate=sdate
				if(nsdate =~ /\s[A-Z]{3}$/){ // is not the timezone, strings like CET are not unique.
					try{
						tz=TimeZone.getTimeZone(nsdate[-i3..-i1])
						nsdate=nsdate[iZ..nsdate.size()-i3].trim()
					}catch(ignored){}
				}

				t0=nsdate?.trim() ?: sBLK
				t0=t0.toLowerCase()
				Boolean hasMeridian,hasAM,hasPM
				hasMeridian=false
				hasAM=false
				hasPM=false
				if(t0.endsWith('a.m.')){
					t0=t0.replaceAll('a\\.m\\.','am')
				}
				if(t0.endsWith('p.m.')){
					t0=t0.replaceAll('p\\.m\\.','pm')
				}
				if(t0.endsWith('am')){
					hasMeridian=true
					hasAM=true
				}
				if(t0.endsWith('pm')){
					hasMeridian=true
					hasPM=true
				}
				Long time
				time=lnull
				if(hasMeridian)t0=t0[iZ..-i3].trim()

				try{
					if(t0.length()==i8){
						n=i9
						String tt=t0
						time=(new Date()).parse('HH:mm:ssXX',tt+'-0000').getTime()
						time=gtWCTimeToday(r9,time)
					}else{
						n=i10
						time=wtimeToday(t0,tz).getTime()
					}
				}catch(ignored){}

				if(hasMeridian && time){
					n=i11
					ZonedDateTime zdt,nzdt; zdt= localDate(r9,time)
					Integer hr,nhr; hr=zdt.getHour()
					nhr= hr
					Boolean twelve= hr>=i12
					if(twelve && hasAM)nhr-=i12
					if(!twelve && hasPM)nhr+=i12
					if(hr!=nhr){
						nzdt= zdt.withHour(nhr)
						time= nzdt.toInstant().toEpochMilli()
					}
				}
				res=time ?: lZ
			}catch(ignored){ res=lnull }
		}
	}

	if(res==lnull){
		if(dateOrTimeOrString instanceof Date){
			n=i12
			res=((Date)dateOrTimeOrString).getTime()
		}
	}
	if(res==lnull){
		n=i13
		res=lZ
	}
	//if(eric1() && ((String)gtSetting(sLOGNG))?.toInteger()>i2)
	//	log.warn "stringToTime n is $n"
	//String sv= TZID(rTZ(r9))
	//mTZ()
	return res
}

@CompileStatic
private String formatLocalTime(Map r9,time,String format=sNL){
	def nTime; nTime=time
	Double aa
	Boolean fnd; fnd=false
	try{
		aa= nTime as Double
		fnd=true
	}catch(ignored){}
	if(fnd || time instanceof Long || "${time}".isNumber()){
		Long lt; lt= fnd ? aa.toLong():"${time}".toLong()
		if(lt<lMSDAY)lt=gtWCTimeToday(r9,lt)
// deal with a time in sec (vs. ms)
		if(lt<Math.round((wnow()/d1000+86400.0D*365.0D).toDouble()) )lt=Math.round((lt*d1000).toDouble())
		nTime=new Date(lt)
	}else if(time instanceof String){
		nTime=new Date(stringToTime(r9,(String)time))
	}
	if(!(nTime instanceof Date)){
		return sNL
	}
	String myformat= format!=sNL ? format : 'EEE, MMM d yyyy @ h:mm:ss a z'
	Date d=(Date)nTime
	SimpleDateFormat formatter=new SimpleDateFormat(myformat)
	formatter.setTimeZone(rTZ(r9))
	return formatter.format(d)
}

@CompileStatic
private static Map hexToColor(String hex){
	String mhex
	mhex=hex!=sNL ? hex:sZ6
	String n='#'
	if(mhex.startsWith(n))mhex=mhex.substring(i1)
	if(mhex.size()!=i6)mhex=sZ6
	List<Integer> myHsl=hexToHsl(mhex)
	return [
		(sH): myHsl[iZ],
		(sS): myHsl[i1],
		(sL): myHsl[i2],
		('rgb'): n+mhex
	]
}

@CompileStatic
private static Double _hue2rgb(Double p,Double q,Double ti){
	Double d6=d3*d2
	Double t
	t=ti
	if(t<dZ)t+=d1
	if(t>=d1)t-=d1
	if(t<d1/d6)return p+(q-p)*d6*t
	if(t<d1/d2)return q
	if(t<d2/d3)return (p+(q-p)*(d2/d3-t)*d6).toDouble()
	return p
}

@CompileStatic
private static String hslToHex(Double hue,Double saturation,Double level){
	Double h,s,l
	h=(hue/d360).toDouble()
	s=(saturation/d100).toDouble()
	l=(level/d100).toDouble()
// argument checking
	if(h<dZ)h=dZ
	if(h>d1)h=d1
	if(s<dZ)s=dZ
	if(s>d1)s=d1
	if(l<dZ)l=dZ
	if(l>d1)l=d1

	Double r,g,b
	if(s==dZ){
		r=g=b=l // achromatic
	}else{
		Double q=l<0.5D ? l*(d1+s):l+s-(l*s)
		Double p=d2*l-q
		r=_hue2rgb(p,q,(h+d1/d3).toDouble())
		g=_hue2rgb(p,q,h)
		b=_hue2rgb(p,q,(h-d1/d3).toDouble())
	}
	Double d255=255.0D
	return sprintf('#%02X%02X%02X',Math.round(r*d255),Math.round(g*d255),Math.round(b*d255))
}

@CompileStatic
private static List<Integer> hexToHsl(String hex){
	String mhex
	mhex=hex!=sNL ? hex:sZ6
	if(mhex.startsWith('#'))mhex=mhex.substring(i1)
	if(mhex.size()!=i6)mhex=sZ6
	Double r=Integer.parseInt(mhex.substring(iZ,i2),i16)/255.0D
	Double g=Integer.parseInt(mhex.substring(i2,i4),i16)/255.0D
	Double b=Integer.parseInt(mhex.substring(i4,i6),i16)/255.0D

	Double max=Math.max(Math.max(r,g),b)
	Double min=Math.min(Math.min(r,g),b)
	Double h,s
	h=dZ

	Double l=((max+min)/d2).toDouble()

	if(max==min){
		h=s=dZ // achromatic
	}else{
		Double d6=d3*d2
		Double d=max-min
		s= (l>0.5D ? d/(d2-max-min):d/(max+min)).toDouble()
		switch(max){
			case r: h= ((g-b)/d+(g<b ? d6:dZ)).toDouble(); break
			case g: h= ((b-r)/d+d2).toDouble(); break
			case b: h= ((r-g)/d+d2*d2).toDouble(); break
		}
		h /= d6
	}
	return [Math.round(h*d360).toInteger(),Math.round(s*d100).toInteger(),Math.round(l*d100).toInteger()]
}

/** DEBUG FUNCTIONS					**/

@CompileStatic
private void myDetail(Map r9,String msg,Integer shift=iN1){ log(msg,r9,shift,null,sWARN,true,false) }

@CompileStatic
private Map log(message,Map r9,Integer shift=iN2,Exception err=null,String cmd=sNL,Boolean force=false,Boolean svLog=true){
	if(cmd==sTIMER){
		return [(sM):message.toString(),(sT):wnow(),(sS):shift,(sE):err]
	}
	String myMsg
	Exception merr; merr=err
	Integer mshift; mshift=shift
	if(message instanceof Map){
		mshift=iMsS(message)
		merr=(Exception)message.e
		myMsg=sMs(message,sM)+" (${elapseT(lMt(message))}ms)".toString()
	}else myMsg=message.toString()
	String mcmd=cmd!=sNL ? cmd:sDBG

	if(r9 && lMs(r9,sTMSTMP)){
		//shift is
		// 0 initialize level,level set to 1
		// 1 start of routine,level up
		// -1 end of routine,level down
		// anything else: nothing happens
		Integer level
		level=r9[sDBGLVL]!=null ? iMs(r9,sDBGLVL):iZ
		String ss='╔'
		String sb='║'
		String se='╚'
		String prefix,prefix2
		prefix=sb
		prefix2=sb
		switch(mshift){
			case iZ:
				level=iZ
			case i1:
				level+=i1
				prefix=se
				prefix2=ss
				break
			case iN1:
				level-=i1
				prefix=ss
				prefix2=se
				break
		}
		if(level>iZ){
			prefix=prefix.padLeft(level+(mshift==iN1 ? i1:iZ),sb)
			prefix2=prefix2.padLeft(level+(mshift==iN1 ? i1:iZ),sb)
		}
		r9[sDBGLVL]=level

		Boolean hasErr=(merr!=null && !!merr)
		Boolean didTrunc; didTrunc = false
		if(svLog && r9[sLOGS] instanceof List){
			myMsg=myMsg.replaceAll(/(\r\n|\r|\n|\\r\\n|\\r|\\n)+/,"\r")
			if(myMsg.size()>1024){
				myMsg=myMsg[iZ..1023]+'...[TRUNCATED]'
				didTrunc = true
			}
			List<String> msgs=!hasErr ? myMsg.tokenize("\r"):[myMsg]
			// try to not to use too much runtime memory on logs that will just be truncated anyway
			Integer lim; lim=iMs(r9,sMLOGS)
			lim = Math.max(lim,i50)
			List logs; logs = liMs(r9,sLOGS)
			Integer lsz; lsz=logs.size()
			for(String msg in msgs){
				if(lsz < lim){
					liMs(r9,sLOGS).push([(sO):elapseT(lMs(r9,sTMSTMP)),(sP):prefix2,(sM):msg+(hasErr ? " $merr".toString():sBLK),(sC):mcmd])
					lsz++
				}
			}
		}
		String myPad=sSPC
		if(hasErr) myMsg+="$merr".toString()
		if((mcmd in [sERROR,sWARN]) || hasErr || force || !svLog || bIs(r9,sLOGHE) || isEric(r9)) doLog(mcmd, myPad+prefix+sSPC+myMsg, didTrunc)
	}else doLog(mcmd,myMsg)
	return [:]
}

void doLog(String mcmd, String msg, Boolean didTrunc=false){
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
	String myMsg
	myMsg= msg.replaceAll(sLTH, '&lt;').replaceAll(sGTH, '&gt;')
	if(!didTrunc && myMsg.size()>600) myMsg=myMsg[iZ..599]+'...[TRUNCATED at doLog]'
	log."$mcmd" span(myMsg,clr)
}

private void info(message,Map r9,Integer shift=iN2,Exception err=null){ log(message,r9,shift,err,sINFO) }
private void trace(message,Map r9,Integer shift=iN2,Exception err=null){ log(message,r9,shift,err,sTRC) }
private void debug(message,Map r9,Integer shift=iN2,Exception err=null){ log(message,r9,shift,err,sDBG) }
private void warn(message,Map r9,Integer shift=iN2,Exception err=null){ log(message,r9,shift,err,sWARN) }
private void error(message,Map r9,Integer shift=iN2,Exception err=null){
	String aa,bb
	aa=sNL
	bb=sNL
	try{
		if(err){
			aa=getExceptionMessageWithLine(err)
			bb=getStackTrace(err)
		}
		log(message,r9,shift,err,sERROR)
	}catch(ignored){}
	if(aa||bb)
		doLog(sERROR,"webCoRE exception: "+aa+" \n"+bb)
}
//error "object: ${describeObject(e)}",r9

private Map timer(String message,Map r9,Integer shift=iN2,err=null){
	if(!isInf(r9)) return null
	return log(message,r9,shift,err,sTIMER)
}

//@Field static final String sCLR4D9	= '#2784D9'
@Field static final String sCLRRED	= 'red'
//@Field static final String sCLRRED2	= '#cc2d3b'
@Field static final String sCLRGRY	= 'gray'
//@Field static final String sCLRGRN	= 'green'
//@Field static final String sCLRGRN2	= '#43d843'
@Field static final String sCLRORG	= 'orange'
@Field static final String sLINEBR	= '<br>'
@Field static final String sSPANS	= '<span'
@Field static final String sSTYLE	= 'style='
@Field static final String sCLR		= 'color: '
@Field static final String sFSZ		= 'font-size: '
@Field static final String sFWT		= 'font-weight: bold;'
@Field static final String sIMPT	= 'px !important;'
@CompileStatic
static String span(String str,String clr=sNL,String sz=sNL,Boolean bld=false,Boolean br=false){
	return str ? sSPANS+" ${(clr || sz || bld) ? sSTYLE+"'${clr ? sCLR+"${clr};":sBLK}${sz ? sFSZ+"${sz};":sBLK}${bld ? sFWT:sBLK}'":sBLK}>${str}</span>${br ? sLINEBR:sBLK}": sBLK
}

private static String sectionTitleStr(String title)	{ return '<h3>'+title+'</h3>' }
private static String inputTitleStr(String title)	{ return '<u>'+title+'</u>' }
//private static String pageTitleStr(String title)	{ return '<h1>'+title+'</h1>' }
//private static String paraTitleStr(String title)	{ return '<b>'+title+'</b>' }

@Field static final String sGITP='https://cdn.jsdelivr.net/gh/imnotbob/webCoRE@hubitat-patches/resources/icons/'
private static String gimg(String imgSrc){ return sGITP+imgSrc }

@CompileStatic
private static String imgTitle(String imgSrc,String titleStr,String color=sNL,Integer imgWidth=30,Integer imgHeight=iZ){
	String imgStyle; imgStyle=sBLK
	String myImgSrc=gimg(imgSrc)
	imgStyle+=imgWidth>iZ ? 'width: '+imgWidth.toString()+sIMPT:sBLK
	imgStyle+=imgHeight>iZ ? imgWidth!=iZ ? sSPC:sBLK+'height:'+imgHeight.toString()+sIMPT:sBLK
	String si="""<img style="${imgStyle}" src="${myImgSrc}"> ${titleStr}</img>""".toString()
	if(color!=sNL) return """<div style="${sCLR}${color}; ${sFWT}">${si}</div>""".toString()
	else return si
}

@CompileStatic
private void tracePoint(Map r9,String oId,Long duration,value){
	Map<String,Object> trc=mMs(r9,sTRC)
	Map<String,Map> pt= trc ? msMs(trc,sPTS) : null
	if(oId!=sNL && pt!=null){
		pt[oId]=[(sO):elapseT(lMt(mMs(r9,sTRC)))-duration,(sD):duration,(sV):value]
	}else error "Invalid object ID $oId for trace point",r9
}

@Field static final List<String> weekDaysFLD=[
	'Sunday',
	'Monday',
	'Tuesday',
	'Wednesday',
	'Thursday',
	'Friday',
	'Saturday'
]

@Field static final List<String> yearMonthsFLD=[
	'',
	'January',
	'February',
	'March',
	'April',
	'May',
	'June',
	'July',
	'August',
	'September',
	'October',
	'November',
	'December'
]

@Field static volatile Map<String,Map> svSunTFLD = [:]
@Field static final String sNEXTM='nextM'
@Field static final String sSUNT= 'sunTimes'

/* wrappers */
private Map initSunrSunst(Map r9){
	String ty=sSUNT
	TimeZone mtz=rTZ(r9)
	String k=TZID(mtz)

	Map t0; t0=svSunTFLD[k]
	Long t=wnow()
	Boolean ok; ok=true
	//mTZ()
	if(t0!=null){
		if(t<lMs(t0,sNEXTM)){
			r9[ty]=[:]+t0
		}else{ t0=null; svSunTFLD[k]=null; mb() }
	}
	if(t0==null){
		Map sunTimes=app.getSunriseAndSunset([(sDATE):t])
		if(sunTimes[sSUNRISE]==null){
			warn 'Actual sunrise and sunset times are unavailable; please reset the location for your hub',r9
			Long t1=getMidnightTime(r9)
			sunTimes[sSUNRISE]=new Date(Math.round(t1+7.0D*dMSECHR))
			sunTimes[sSUNSET]=new Date(Math.round(t1+19.0D*dMSECHR))
			ok=false
		}
		Long a=((Date)sunTimes[sSUNRISE]).getTime()
		Long b=((Date)sunTimes[sSUNSET]).getTime()
		Long nmnght=getNextMidnightTime(r9)
		Long c,d,a1,b1; c=d=a1=b1=(Long)null

		Boolean good; good=true
		try{
			Boolean fnd; fnd=false
			try{
				a1=((Date)getTodaysSunrise(mtz)).getTime() // requires FW 2.3.6.118 or later
				b1=((Date)getTodaysSunset(mtz)).getTime()
				c=((Date)getTomorrowsSunrise(mtz)).getTime()
				d=((Date)getTomorrowsSunset(mtz)).getTime()
				fnd=true
				if(eric())debug "updating global sunrise with TZ ${mtz}",null
			}catch(ignored){}
			if(!fnd){
				a1=((Date)getTodaysSunrise()).getTime() // requires FW 2.2.3.132 or later
				b1=((Date)getTodaysSunset()).getTime()
				c=((Date)getTomorrowsSunrise()).getTime()
				d=((Date)getTomorrowsSunset()).getTime()
			}
		}catch(ignored){
			good=false
		}
		t0=[
			(sSUNRISE): a1?:a,
			(sSUNSET): b1?:b,
			('tomorrowssunrise'): c ?: pushTimeAhead(r9,a1?:a,nmnght,false),
			('tomorrowssunset'): d ?: pushTimeAhead(r9,b1?:b,nmnght,false),
			updated: t,
			good: ok&&good,
			(sNEXTM): nmnght
		]
		if(!good || !ok) warn 'Please update HE firmware to improve time handling',r9
		r9[ty]=t0
		if(ok&&good){
			svSunTFLD[k]=t0; mb()
			if(eric())debug "updating global sunrise ${t0}",null
		}
	}
	return mMs(r9,ty)
}

private Long getNextSunriseTime(Map r9){ Map st=initSunrSunst(r9); return lMs(st,'tomorrowssunrise') }
private Long getNextSunsetTime(Map r9){ Map st=initSunrSunst(r9); return lMs(st,'tomorrowssunset') }
private Long getSunriseTime(Map r9,Long dayBasis=null){ commonSunTime(r9,sSUNRISE,dayBasis) }

private Long getSunsetTime(Map r9,Long dayBasis=null){ commonSunTime(r9,sSUNSET,dayBasis) }

@CompileStatic
private Long commonSunTime(Map r9,String tvar,Long dayBasis=null){
//	Boolean lge=isDbg(r9) && isEric(r9)
//	String myS; myS=sNL
//	if(lge){
//		myS='get'+tvar.capitalize()+'Time: '
//		myDetail r9,myS+"${dayBasis} ${dayBasis ? formatLocalTime(r9,dayBasis): ""}",i1
//	}
	ZonedDateTime zdt,nzdt
	Long res
	if(dayBasis){
		res= calcSunTime(r9,tvar,dayBasis)
	}else{
		Map st=initSunrSunst(r9); res= lMs(st,tvar)
	}
//	if(lge)
//		myDetail r9,myS+"${res} ${res ? formatLocalTime(r9,res): ""}"
	return res
}

Long calcSunTime(Map r9,String typ,Long time){
	Long res
	String m; m=sNL
	Long now= wnow()
	Long t; t= time ?: now
	Boolean lge=isDbg(r9) && isEric(r9)
	//if(lge)
	//	myDetail r9,"calcSunTime: ${typ} time: $time now: $now nows: ${formatLocalTime(r9,now)} t: ${formatLocalTime(r9,t)}",iN2
	ZonedDateTime zdt,nzdt
	zdt= localDate(r9,t)
	// strip the time to midnight of the day in question
	nzdt= zdt.withNano(iZ)
	nzdt= nzdt.withSecond(iZ)
	nzdt= nzdt.withMinute(iZ)
	nzdt= nzdt.withHour(iZ)
	Boolean sunset= typ==sSUNSET

	//if(piston location is same as hub location) then
	ZonedDateTime nnzdt; nnzdt= localDate(r9,now)
	if(zdt.getYear()==nnzdt.getYear() && zdt.getMonth().getValue()==nnzdt.getMonth().getValue() && zdt.getDayOfMonth()==nnzdt.getDayOfMonth()){
		if(sunset)
			res= getSunsetTime(r9)
		else
			res= getSunriseTime(r9)
		m= 'getSun'
	}else{
		nnzdt= localDate(r9,now+Math.round(dMSDAY)) //+ 1 day
		if(zdt.getYear()==nnzdt.getYear() && zdt.getMonth().getValue()==nnzdt.getMonth().getValue() && zdt.getDayOfMonth()==nnzdt.getDayOfMonth()){
			if(sunset)
				res = getNextSunsetTime(r9)
			else
				res = getNextSunriseTime(r9)
			m = 'getNextSun'
		}else{
			try{
				Date d= new Date(nzdt.toInstant().toEpochMilli())
				Map sunTimes=app.getSunriseAndSunset([(sDATE):d]) // this only works for hub's location
				if(sunset)
					res=((Date)sunTimes[sSUNSET]).getTime()
				else
					res=((Date)sunTimes[sSUNRISE]).getTime()
				m= 'calculated'
			}catch(ignore){
				t=nzdt.toInstant().toEpochMilli()
				if(sunset)
					res= Math.round(t+19.0D*dMSECHR)
				else
					res= Math.round(t+7.0D*dMSECHR)
				m= 'guessed'
			}
		}
	}
	// else piston location is set different than hub location
/*				Calendar calendar= Calendar.getInstance(rTZ(r9))
				calendar.setTimeInMillis(nzdt.toInstant().toEpochMilli())
				SolarTime solarTime= SolarTime.ofLocation(gtLlat().toDouble(), gtLlong().toDouble()) // this is only hub location
				if(typ==sSUNSET)
					res= solarTime.sunset(calendar)
				else
					res= solarTime.sunrise(calendar)
*/
	if(lge)
		myDetail r9,"calcSunTime: ${typ} ${m} result: $res ${formatLocalTime(r9,res)}",iN2
	return res
}

@CompileStatic
private Boolean loadTZs(Map r9,String id){
	try{
		TimeZone ntz= loadTZ(id)
		ZoneId nid= loadZID(id)
		String tz1= TZID(ntz)
		String zid1= ZID(nid)
		if(tz1!=zid1) warn "tz ($tz1) and zoneid ($zid1) do not match",r9
		else{
			r9[sTZ]= ntz
			r9[sZONEID]= nid
			if(id && id!=TZID(mTZ())){
				assignSt(sTZ,id)
			}else{
				wstateRemove(sTZ)
			}
			return true
		}
	}catch(e){
		wstateRemove(sTZ)
		error "loadTZs failed",r9,iN2,e
	}
	return false
}

private static TimeZone mTZ(){ return TimeZone.getDefault() }
private static TimeZone rTZ(Map r9){ return (TimeZone)r9[sTZ] ?: mTZ() }
private static String TZID(TimeZone tz){ return tz.getID() }
private static TimeZone loadTZ(String tzid){
	TimeZone tz; tz=null
	if(tzid){
		tz= TimeZone.getTimeZone(tzid)
	}
	return tz ?: mTZ()
}

private static ZoneId myZone(){ return ZoneId.systemDefault() }
/**
 * return current ZoneId for piston
 */
private static ZoneId mZ(Map r9){ return (ZoneId)r9[sZONEID] ?: myZone() }
private static String ZID(ZoneId z){ return z.getId() }
private static ZoneId loadZID(String zidS){
	ZoneId zid; zid=null
	if(zidS){
		zid= ZoneId.of(zidS)
	}
	return  zid ?: myZone()
}

@CompileStatic
private Long getMidnightTime(Map r9,Long time=null){
	ZonedDateTime zdt,nzdt
	Long t; t= time ?: wnow()
	zdt= localDate(r9,t)
	// strip the time to midnight of the day in question
	nzdt= zdt.withNano(iZ)
	nzdt= nzdt.withSecond(iZ)
	nzdt= nzdt.withMinute(iZ)
	nzdt= nzdt.withHour(iZ)
	return nzdt.toInstant().toEpochMilli()
}
private Long getNextMidnightTime(Map r9,Long time=null){
	Long t= getMidnightTime(r9,time)
	return pushTimeAhead(r9,t,t+1L)
}

@CompileStatic
private Long getNoonTime(Map r9,Long time=null){
	ZonedDateTime zdt,nzdt
	Long t; t= time ?: wnow()
	zdt= localDate(r9,t)
	// strip the time to noon of the day in question
	nzdt= zdt.withNano(iZ)
	nzdt= nzdt.withSecond(iZ)
	nzdt= nzdt.withMinute(iZ)
	nzdt= nzdt.withHour(12)
	return nzdt.toInstant().toEpochMilli()
}
private Long getNextNoonTime(Map r9,Long time=null){
	Long t= getNoonTime(r9,time)
	return pushTimeAhead(r9,t,t+1L)
}

/**
 * load local variables
 * @param frc - if has initial value, use it
 */
@CompileStatic
private void getLocalVariables(Map r9,Map aS, Boolean frc=true, Boolean proxy=false, List<Map> vars=null){
	/*String myS; myS=sBLK
	Boolean lge=isEric(r9)
	if(lge){
		myS="getLocalVariables:"+sffwdng(r9)
		myDetail r9,myS,i1
	}*/
	r9[sLOCALV]=[:]
	String t
	Map values=mMs(aS,sVARS)
	List<Map>l= proxy && vars ? vars : (List<Map>)oMv(mMs(r9,sPISTN))
	if(!l)return
	Boolean lg=isDbg(r9)
	//if(lge) myDetail r9,"values: $values",iN2
	for(Map var in l){
		t=sMt(var)
		String tn=sanitizeVariableName(sMs(var,sN))
		Map ival= mMv(var) // initialize value for variable
		def v
		v= values ? values[tn]:null // stored value for variable
		//if(lge) myDetail r9,"found variable $tn value: $v",iN2
		Boolean hasival= ival!=null
		Boolean isconst= hasival && sMa(var)==sS && !t.endsWith(sRB)
		Boolean useival= hasival && (v==null || frc || isconst)
		if(!proxy && useival && v!=null){ // clean out any changed value
			clearVariable(r9,tn)
			if(lg)debug 'Reinitializing preset variable: '+tn,r9
		}

		Map variable=[
			(sT):t,
			(sV): useival ? ival : (t.endsWith(sRB) ? (v instanceof Map || v instanceof List ? v:[:]) : (matchCast(v,t) ? v:cast(r9,v,t))),
			(sF): hasival //f means fixed value; ie we can warn they are overwriting it
		]

		if(isconst){  // variable.a sS -> const  sD-> dynamic
			variable[sV]=oMv(evaluateExpression(r9,mevaluateOperand(r9,mMv(var)),t))
			variable[sA]=sS
		}
		((Map)r9[sLOCALV])[tn]=variable
		//if(lge) myDetail r9,"stored variable $tn value: $variable",iN2
	}
	//if(lge)myDetail r9,myS+"result:"
}

/** UI will not display anything that starts with $current or $previous; variables without d:true and non-null value will not display */
@CompileStatic
private Map<String,LinkedHashMap> getSystemVariablesAndValues(Map r9){
	LinkedHashMap<String,LinkedHashMap> result=getSystemVariables()
	LinkedHashMap<String,LinkedHashMap> c=(LinkedHashMap<String,LinkedHashMap>)r9[sPCACHE]
	def res
	for(Map.Entry<String,LinkedHashMap> variable in result){
		String k=(String)variable.key
		// todo special handle $fuel $file
		res=null
		if(bIs(variable.value,sD)) res=gtSysVarVal(r9,k,true)
		if(res==null && c[k]!=null)res=oMv(c[k])
		variable.value[sV]=res
	}
	return result.sort{ Map.Entry<String,LinkedHashMap> it -> it.key }
}

/** define system variables, d:true also means get the variable value dynamically via gtSysVarVal */

// Cached template: structure never changes; callers mutate sV so each call gets shallow-copied entries.
@Field static LinkedHashMap<String,LinkedHashMap> sysVarsTemplateFLD

@CompileStatic
private static LinkedHashMap<String,LinkedHashMap> getSystemVariables(){
	LinkedHashMap<String,LinkedHashMap> tmpl=sysVarsTemplateFLD
	if(tmpl==null){
		tmpl=buildSysVarTemplate()
		sysVarsTemplateFLD=tmpl
	}
	// Return a new outer map with each value entry shallow-copied so sV mutations stay per-execution.
	LinkedHashMap<String,LinkedHashMap> result=new LinkedHashMap<String,LinkedHashMap>(tmpl.size()*2)
	for(Map.Entry<String,LinkedHashMap> e in tmpl.entrySet()){
		result[(String)e.key]=(LinkedHashMap)([:]+e.value)
	}
	return result
}

@CompileStatic
private static LinkedHashMap<String,LinkedHashMap> buildSysVarTemplate(){
	LinkedHashMap dynT=[(sT):sDYN,(sD):true]
	LinkedHashMap strT=[(sT):sSTR,(sD):true]
	//LinkedHashMap strN=[(sT):sSTR,(sV):null]
	LinkedHashMap intT=[(sT):sINT,(sD):true]
	LinkedHashMap boolT=[(sT):sBOOLN,(sD):true]
	LinkedHashMap dtimeT=[(sT):sDTIME,(sD):true]
	LinkedHashMap devT=[(sT):sDEV,(sD):true]
	LinkedHashMap t=[:] as LinkedHashMap
	return [
		(sDLLRDEVICE):rtnMap(sDEV,null),
		(sDLLRDEVS):rtnMap(sDEV,null),
		(sDLLRINDX):rtnMapD(null),
		(sDARGS):t+dynT,
		(sDJSON):t+dynT,
		(sDRESP):t+dynT,
		(sLOCMODE):t+strT,
		(sLOC):rtnMap(sDEV,null),
		(sNOW):t+dtimeT,
		(sLOCNOW):t+dtimeT, // in UI as long
		(sUTC):t+dtimeT, // in UI special display

		(sDLRWEAT):t+dynT,
		(sDLRINCIDENTS):t+dynT,
		(sHSMTRIPPED):t+boolT,
		(sDLRHSMSTS):t+strT,

		(sHTTPCNTN):t+strT,
		(sHTTPCODE):t+intT,
		(sHTTPOK):t+boolT,
		(sIFTTTCODE):t+intT,
		(sIFTTTOK):t+boolT,

		(sCURATTR):t+strT,
		(sCURDESC):t+strT,
		(sCURDATE):t+dtimeT,
		(sCURDELAY):t+intT,
		(sCURDEV):t+devT,
		(sCURDEVINDX):t+intT,
		(sCURPHYS):t+boolT,
		(sCURVALUE):t+dynT,
		(sCURUNIT):t+strT,
//		'$currentState':t+strN,
//		'$currentStateDuration':t+strN,
//		'$currentStateSince':rtnMap(sDTIME,null),
//		'$nextScheduledTime':rtnMap(sDTIME,null),
		'$name':t+strT,
		'$state':t+strT,
		'$hour':t+intT,
		'$hour24':t+intT,
		'$minute':t+intT,
		'$second':t+intT,
		'$lastexecuted': t+dtimeT,
		'$zipCode':t+strT,
		'$latitude':t+strT,
		'$longitude':t+strT,
		'$meridian':t+strT,
		'$meridianWithDots':t+strT,
		'$day':t+intT,
		'$dayOfWeek':t+intT,
		'$dayOfWeekName':t+strT,
		'$month':t+intT,
		'$monthName':t+strT,
		'$year':t+intT,
		'$midnight':t+dtimeT,
		'$noon':t+dtimeT,
		'$sunrise':t+dtimeT,
		'$sunset':t+dtimeT,
		'$nextMidnight':t+dtimeT,
		'$nextNoon':t+dtimeT,
		'$nextSunrise':t+dtimeT,
		'$nextSunset':t+dtimeT,
		'$time':t+strT,
		'$time24':t+strT,

		'$random':[(sT):sDEC,(sD):true],
		'$randomColor':t+strT,
		'$randomColorName':t+strT,
		'$randomLevel':t+intT,
		'$randomSaturation':t+intT,
		'$randomHue':t+intT,
		'$temperatureScale':t+strT,

		(sPLACES):t+dynT,
		'$tzName':t+strT,
		'$tzId':t+strT,
		'$tzOffset':t+intT,
		'$tzInDst':t+boolT,

//		'$zoneName':t+strT,
		'$zoneId': t+strT,
		'$zoneOffset': t+intT,
		'$zoneInDst': t+boolT,

		(sFILE):t+dynT,
		(sFUEL):t+dynT,
		(sROOMS):t+dynT,
		(sROOMIDS):t+dynT,
		(sPEVATTR):t+strT,
		(sPEVDESC):t+strT,
		(sPEVDATE):t+dtimeT,
		(sPEVDELAY):t+intT,
		(sPEVDEV):t+devT,
		(sPEVDEVINDX):t+intT,
		(sPEVPHYS):t+boolT,
		(sPEVVALUE):t+dynT,
		(sPEVUNIT):t+strT,
//		'$previousState':t+strN,
//		'$previousStateDuration':t+strN,
//		'$previousStateSince':rtnMap(sDTIME,null),
		'$mediaId':t+strT,
		'$mediaUrl':t+strT,
		'$mediaType':t+strT,
//		'$mediaSize':t+intT,
		'$version':t+strT,
		'$versionH':t+strT,
		'$nfl':t+dynT
	] as LinkedHashMap<String,LinkedHashMap>
}

@CompileStatic
private static String rtnStr(v,Boolean frcStr=false){
	if(v==null || v==sBLK || v==sNL || v==[:] || v==[]) return sNL
	if(!frcStr && (v instanceof Map || v instanceof List)) return JsonOutput.toJson(v)
	return "${v}".toString()
}

@CompileStatic
private gtSysVarVal(Map r9,String name, Boolean frcStr=false){
	Map<String,Map> sv=msMs(r9,sSYSVARS)
	Map ce=mMs(r9,sCUREVT) ?: [:]
	Map pe=mMs(r9,sPREVEVT) ?: [:]
	switch(name){
		case sDARGS:
		case sDLLRDEVICE:
		case sDLLRDEVS:
		case sDLLRINDX: return oMv(sv[name])
		case sDJSON: return rtnStr(r9[sJSON],frcStr)
		case sDRESP: return rtnStr(r9[sRESP],frcStr)
		case sHTTPCNTN:
		case sHTTPCODE:
		case sHTTPOK:
		case sIFTTTCODE:
		case sIFTTTOK: return oMv(sv[name])
		case sLOCMODE: return gtLMode()
		case sLOCNOW:	// in UI as long
		case sUTC:		// UI special displays with proper locale
		case sNOW:
			return wnow()
		case sCURATTR: return rtnStr(ce[sNM])
		case sCURDESC: return rtnStr(ce[sDESCTXT])
		case sCURDATE: return ce[sT]
		case sCURDELAY: return ce[sDELAY]
		case sCURDEV: return ce[sDEV] ? [sMs(ce,sDEV)]:[]
		case sCURDEVINDX: return ce[sINDX]
		case sCURPHYS: return ce[sPHYS]
		case sCURVALUE: return ce[sVAL]
		case sCURUNIT: return ce[sUNIT]
		case '$lastexecuted': return lMs(r9,sLEXEC)
		case sDLRWEAT: return rtnStr(r9[sWEAT],frcStr)
		case sDLRINCIDENTS: return rtnStr(r9[sINCIDENTS],frcStr)
		case sHSMTRIPPED: return listWithSz(r9[sINCIDENTS])
		case sDLRHSMSTS: return gtLhsmStatus()

		case '$name': return gtAppN()
		case '$state': return (String)((Map)r9[sST])?.new

		case '$hour': Integer h=localDate(r9).getHour(); return (h==iZ ? i12:(h>i12 ? h-i12:h))
		case '$hour24': return localDate(r9).getHour()
		case '$minute': return localDate(r9).getMinute()
		case '$second': return localDate(r9).getSecond()
		case '$zipCode': return gtLzip()
		case '$latitude': return gtLlat()
		case '$longitude': return gtLlong()
		case '$meridian': Integer h=localDate(r9).getHour(); return (h<i12 ? 'AM':'PM')
		case '$meridianWithDots': Integer h=localDate(r9).getHour(); return (h<i12 ? 'A.M.':'P.M.')
		case '$day': return localDate(r9).getDayOfMonth()
		case '$dayOfWeek': return localDate(r9).getDayOfWeek().getValue() % i7
		case '$dayOfWeekName': return weekDaysFLD[localDate(r9).getDayOfWeek().getValue() % i7]
		case '$month': return localDate(r9).getMonth().getValue()
		case '$monthName': return yearMonthsFLD[localDate(r9).getMonth().getValue()]
		case '$year': return localDate(r9).getYear()
		case '$midnight': return getMidnightTime(r9)
		case '$noon': return getNoonTime(r9)
		case '$sunrise': return getSunriseTime(r9)
		case '$sunset': return getSunsetTime(r9)
		case '$nextMidnight': return getNextMidnightTime(r9)
		case '$nextNoon': return getNextNoonTime(r9)
		case '$nextSunrise': return getNextSunriseTime(r9)
		case '$nextSunset': return getNextSunsetTime(r9)
		case '$time': ZonedDateTime t=localDate(r9); Integer h=t.getHour(); Integer m=t.getMinute(); return ((h==iZ ? i12:(h>i12 ? h-i12:h))+sCLN+(m<i10 ? "0$m":"$m")+sSPC+(h<i12 ? 'A.M.':'P.M.')).toString()
		case '$time24': ZonedDateTime t=localDate(r9); Integer h=t.getHour(); Integer m=t.getMinute(); return (h+sCLN+(m<i10 ? "0$m":"$m")).toString()

		case sPLACES: return rtnStr(mMs(r9,sSETTINGS)?.places)
		case '$tzName': return rTZ(r9).displayName
		case '$tzId': return TZID(rTZ(r9))
		case '$tzOffset': return rTZ(r9).getOffset(wnow())
		case '$tzInDst': return rTZ(r9).inDaylightTime(new Date(wnow()))

//		case '$zoneName': return mZ(r9).getDisplayName(TextStyle.FULL_STANDALONE, new Locale.Builder().setLanguage("en").setScript("Latn").setRegion("US").build())
		case '$zoneId': return ZID(mZ(r9))
		case '$zoneOffset': return mZ(r9).getRules().getOffset(Instant.ofEpochMilli(wnow())).getTotalSeconds()*1000L
		case '$zoneInDst': return mZ(r9).getRules().isDaylightSavings(Instant.ofEpochMilli(wnow()))

		case sFILE: String pNm=sMs(r9,snId); return readDataFLD[pNm]
		case sFUEL: String pNm=sMs(r9,snId); return fuelDataFLD[pNm]
		case sROOMS: return rtnStr(gtRooms(r9),frcStr)
		case sROOMIDS:
			List<String> r; r=[]
			Map<String,Map> rms = gtRooms(r9)
			if(rms){
				for(Map.Entry<String,Map> a in rms){
					r.push(a.key)
				}
			}
			return rtnStr(r)
		case sPEVATTR: return rtnStr(pe[sNM])
		case sPEVDESC: return rtnStr(pe[sDESCTXT])
		case sPEVDATE: return pe[sT]
		case sPEVDELAY: return pe[sDELAY]
		case sPEVDEV: return pe[sDEV] ? [sMs(pe,sDEV)]:[]
		case sPEVDEVINDX: return pe[sINDX]
		case sPEVPHYS: return pe[sPHYS]
		case sPEVVALUE: return pe[sVAL]
		case sPEVUNIT: return pe[sUNIT]

		case '$random':
			def tr=getRandomValue(r9,name)
			Double r
			if(tr!=null)r=(Double)tr
			else{
				r=Math.random()
				setRandomValue(r9,name,r)
			}
			return r
		case '$randomColor':
			def tr=getRandomValue(r9,name)
			String r
			if(tr!=null)r=(String)tr
			else{
				r=sMs(getRandomColor(),'rgb')
				setRandomValue(r9,name,r)
			}
			return r
		case '$randomColorName':
			def tr=getRandomValue(r9,name)
			String r
			if(tr!=null)r=(String)tr
			else{
				r=sMs(getRandomColor(),sNM)
				setRandomValue(r9,name,r)
			}
			return r
		case '$randomLevel':
			def tr=getRandomValue(r9,name)
			Integer r
			if(tr!=null)r=(Integer)tr
			else{
				r=Math.round(d100*Math.random()).toInteger()
				setRandomValue(r9,name,r)
			}
			return r
		case '$randomSaturation':
			def tr=getRandomValue(r9,name)
			Integer r
			if(tr!=null)r=(Integer)tr
			else{
				r=Math.round(i50+i50*Math.random()).toInteger()
				setRandomValue(r9,name,r)
			}
			return r
		case '$randomHue':
			def tr=getRandomValue(r9,name)
			Integer r
			if(tr!=null)r=(Integer)tr
			else{
				r=Math.round(d360*Math.random()).toInteger()
				setRandomValue(r9,name,r)
			}
			return r
		case '$temperatureScale':return gtLtScale()

		case '$mediaId': return sMs(r9,sMEDIAID)
		case '$mediaUrl': return sMs(r9,sMEDIAURL)
		case '$mediaType': return sMs(r9,sMEDIATYPE)
//		case '$mediaSize': return (r9[sMEDIADATA]!=null ? ((byte[])r9[sMEDIADATA]).size():null)
		case '$version': return sVER
		case '$versionH': return sHVER
		case '$nfl': return rtnStr(r9.nfl)
	}
	return null
}

@Field static List<String> ListCache=[]
private static void fill_CACH(){
	if(ListCache.size()==iZ)
		ListCache= [sDARGS,sHTTPCNTN,sHTTPCODE,sHTTPOK,sIFTTTCODE,sIFTTTOK]
}

@CompileStatic
private static void stSysVarVal(Map r9,String nm,value/*,Boolean cachePersist=true*/){
	Boolean cachePersist=true
	Map<String,Map>sysV=msMs(r9,sSYSVARS)
	Map var=sysV[nm]
	if(var==null)return
	if(cachePersist && nm in ListCache){
		LinkedHashMap<String,Map> c=(LinkedHashMap<String,Map>)r9[sPCACHE]
		if(value!=null) c[nm]= var+[(sV):value] as LinkedHashMap
		else c.remove(nm)
		r9[sPCACHE]=c
	}
	((Map)((Map)r9[sSYSVARS])[nm])[sV]=value
}

@Field static final String sTEMP='temp'
@Field static final String sRANDS='randoms'

private static getRandomValue(Map r9,String nm){ return msMs(r9,sTEMP)[sRANDS][nm] }
private static void setRandomValue(Map r9,String nm,value){ msMs(r9,sTEMP)[sRANDS][nm]=value }
private static void resetRandomValues(Map r9){ r9[sTEMP]=[(sRANDS):[:]] }

private static Map getColorByName(String nm){
	return getColors().find{ Map it -> sMs(it,sNM)==nm }
}

private static Map getRandomColor(){
	Integer random=Math.round(Math.random()*(getColors().size()-i1)).toInteger()
	return getColors()[random]
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
	else{
	//	if(eric()) log.error "object: ${describeObject(obj)}"
		return 'unknown'
	}
}

/** Returns true if string is encoded device hash  */
@CompileStatic
private static Boolean isWcDev(String dev){ return (dev && dev.size()==i34 && dev.startsWith(sCLN) && dev.endsWith(sCLN)) }

/** Converts v to either webCoRE or hubitat hub variable types and values */
@CompileStatic
Map fixHeGType(Map r9,Boolean toHubV,String typ,v){
	Map ret; ret=[:]
	def myv; myv=v
	String T='T'
	String s9s='9999'
	String format="yyyy-MM-dd'T'HH:mm:ss.sssXX"
	TimeZone tz=rTZ(r9)
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
						Long t0=getMidnightTime(r9)
						Long a1=t0+aaa
						myv=a1+(tz.getOffset(t0)-tz.getOffset(a1))
					}else{
						ZonedDateTime zdt= localDate(r9,aaa)
						Long t2=Math.round((zdt.getHour()*dSECHR+zdt.getMinute()*d60+zdt.getSecond())*d1000)
						myv=t2
					}
				}else if(eric()) warn "trying to convert nonnumber time",null
			case sDATE:
			case sDTIME: //@@
				Date nTime=new Date((Long)myv)
				SimpleDateFormat formatter=new SimpleDateFormat(format)
				formatter.setTimeZone(tz)
				String tt=formatter.format(nTime)
				String[] t1=tt.split(T)

				if(typ==sDATE){
					// comes in long format should be string -> 2021-10-13T99:99:99.999-9999
					String t2=t1[iZ]+'T99:99:99.999-9999'
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
				for(String t in t1){
					// sDEV is a string in hub need to detect if it is really devices :xxxxx:
					if(ok && isWcDev(t))dvL.push(t)
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
					formatter.setTimeZone(tz)
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
					error "datetime parse of hub variable failed",null,iN2,e
				}
				if(tt1!=null){
					lres=tt1.getTime()
					if(mtyp==sTIME){
						ZonedDateTime zdt= localDate(r9,lres)
						Long m2=Math.round((zdt.getHour()*dSECHR+zdt.getMinute()*d60+zdt.getSecond())*d1000)
						lres=m2
					}
				}
				//if(eric())warn "returning $lres"
				ret=[(mtyp):lres]
		}
	}
	return ret
}

private static String md5(String s){
	return MessageDigest.getInstance(sMD5).digest(s.getBytes()).encodeHex().toString()
}

@Field volatile static Map<String,Map<String,String>> theHashMapVFLD=[:]

static void clearHashMap(String wName){
	if(theHashMapVFLD==null) theHashMapVFLD=[:]
	theHashMapVFLD[wName]=[:] as Map<String,String>
	theHashMapVFLD=theHashMapVFLD
	mb()
}

@CompileStatic
private String hashPID(id){
	LinkedHashMap pC=getParentCache()
	if(bIs(pC,sNACCTSID))return hashId3(sMs(pC,sLOCID)+id.toString())
	return hashId3(id)
}

@Field static final String sCR='core.'
@Field static final String sMD5='MD5'

@CompileStatic
private String hashId3(id){
	return hashId2(id,sPAppId())
}

@CompileStatic
private static String hashId(Map r9,id){ return hashId2(id,sMs(r9,spId)) }

@CompileStatic
private static String hashId2(id,String wName){
	String r
	String tId=id.toString()
	Map<String,String> a
	a= theHashMapVFLD ? theHashMapVFLD[wName] : null
	if(a==null){ clearHashMap(wName); a=[:] }
	r=a[tId]
	if(r==sNL){
		r=sCLN+md5(sCR+tId)+sCLN
		theHashMapVFLD[wName][tId]=r
	}
	return r
}

@Field static Semaphore theMBLockFLD=new Semaphore(1)

// Memory Barrier
@CompileStatic
static void mb(String meth=sNL){
	if(theMBLockFLD.tryAcquire()){
		theMBLockFLD.release()
	}
}


/* wrappers */
@Field static Map<String,Map> theAttributesFLD
//uses i,p,t,m
private static Map<String,Map> Attributes(){ return theAttributesFLD }
private Map<String,Map> AttributesF(){
	theAttributesFLD=(Map)parent.getChildAttributes()
	mb()
	return theAttributesFLD
}

@Field static Map<String,Map> theComparisonsFLD
//uses p,t
private static Map<String,Map<String,Map>> Comparisons(){ return theComparisonsFLD }

// Flat merged map: CONDITIONS + TRIGGERS in one lookup; trigger entries tagged with sTRIG:true.
@Field static Map<String,Map> allCmpsFLD
@CompileStatic
private static Map<String,Map> AllComparisons(){ return allCmpsFLD }

private Map<String,Map<String,Map>> ComparisonsF(){
	theComparisonsFLD=(Map)parent.getChildComparisons()
	mb()
	Map<String,Map> flat=[:]
	Map<String,Map> conds=(Map<String,Map>)theComparisonsFLD[sCONDITIONS]
	Map<String,Map> trigs=(Map<String,Map>)theComparisonsFLD[sTRIGGERS]
	if(conds) conds.each{ String k,Map v -> flat[k]=v }
	if(trigs) trigs.each{ String k,Map v -> flat[k]=v+[(sTRIG):true] }
	allCmpsFLD=flat
	return theComparisonsFLD
}

@Field static Map<String,Map> theVirtCommandsFLD
//uses o (override phys command),a (aggregate commands)
private static Map<String,Map> VirtualCommands(){ return theVirtCommandsFLD }
private Map<String,Map> VirtualCommandsF(){
	theVirtCommandsFLD=(Map)parent.getChildVirtCommands()
	mb()
	return theVirtCommandsFLD
}

//uses c and r
// the command r: is replaced with command c.
// If the VirtualCommand c exists and has o: true we will use that virtual command; otherwise it will be replaced with a device command (if one exists)
@Field static final Map<String,Map> CommandsOverrides=[
		push:[c:"push",	s:null,r:"pushMomentary"],
		flash:[c:"flash",	s:null,r:"flashNative"] //flash native command conflicts with flash emulated command. Also needs "o" option on command described later
]

@Field static Map<String,Map> theVirtDevicesFLD
//uses ac,o
private Map<String,Map> VirtualDevices(){ return theVirtDevicesFLD ?: VirtualDevicesF() }
private Map<String,Map> VirtualDevicesF(){
	theVirtDevicesFLD=(Map)parent.getChildVirtDevices()
	mb()
	return theVirtDevicesFLD
}

@Field static Map<String,Map> thePhysCommandsFLD
//uses a,v
private static Map<String,Map> PhysicalCommands(){ return thePhysCommandsFLD }
private Map<String,Map> PhysicalCommandsF(){
	thePhysCommandsFLD=(Map)parent.getChildCommands()
	mb()
	return thePhysCommandsFLD
}

@Field static List<Map> theColorsFLD
private static List<Map> getColors(){ return theColorsFLD }
private List<Map> getColorsF(){
	theColorsFLD=(List)parent.getColors()
	mb()
	return theColorsFLD
}

private Map wgtPdata(){ return (Map)parent.gtPdata() }
private void relaypCall(Map rt){ parent.pCallupdateRunTimeData(rt) }
private List wgetPushDev(){ return (List)parent.getPushDev() }
private Boolean wexecutePiston(String pistonId,Map data,String selfId){ return (Boolean)parent.executePiston(pistonId,data,selfId) }
private Boolean wpausePiston(String pistonId,String selfId){ return (Boolean)parent.pausePiston(pistonId,selfId) }
private Boolean wresumePiston(String pistonId,String selfId){ return (Boolean)parent.resumePiston(pistonId,selfId) }
private Boolean wisPisPaused(String pistonId){ return (Boolean)parent.isPisPaused(pistonId) }
private Map wgetGStore(){ return (Map)parent.getGStore() }
private Map wlistAvailableDevices(Boolean raw){ return parent.listAvailableDevices(raw) }
private Map wgetWData(){ return [:]+(Map)parent.getWData() }
private Map wlistAvailableVariables(){ return (Map)parent.listAvailableVariables() }

private String sPAppId(){ return ((Long)parent.id).toString() }

private String sAppId(){ return ((Long)app.id).toString() }

private Map wgetGlobalVar(String vn){
	def a= getGlobalVar(vn)
	if(a)return [(sTYPE):(String)a[sTYPE],(sVAL): a[sVAL]]
	return null
}

@Field volatile static Map<String,Map<String,List>> globalVarsUseFLD=[:]

private void waddInUseGlobalVar(Map r9,String vn,Boolean heglobal=true,Boolean exists=true){
	String wName=sMs(r9,spId)
	Map<String,List> vars=globalVarsUseFLD[wName] ?: [:]
	String nvn= heglobal ? sAT2+vn : vn
	List<String> pstns
	pstns= vars[nvn] ?: []
	String sa= sAppId()
	if(!(sa in pstns)){
		pstns << sa
		vars[nvn]= pstns
		globalVarsUseFLD[wName]= vars
		globalVarsUseFLD= globalVarsUseFLD
		if(isEric(r9))myDetail r9,"added in use $nvn $wName $sa $pstns $vars",iN2
	}
	if(heglobal && exists) addInUseGlobalVar(vn)
}

Map<String,List> gtGlobalVarsInUse(){
	String wName= sPAppId()
	Map<String,List> vars=globalVarsUseFLD[wName] ?: [:]
	return vars
}

private Boolean wsetGlobalVar(String vn,vl){ setGlobalVar(vn,vl) }

private void wremoveAllInUseGlobalVar(){
	String wName= sPAppId()
	String sa= sAppId()
	Map<String,List> vars=globalVarsUseFLD[wName] ?: [:]
	vars.each{ Map.Entry<String,List>it ->
		String k= it.key
		List<String> pstns
		pstns= it.value //tvars[vn] ?: []
		if(k && sa in pstns){
			pstns= pstns-[sa] as List<String>
			vars[k]= pstns
		}
	}
	globalVarsUseFLD[wName]= vars
	globalVarsUseFLD= globalVarsUseFLD

	removeAllInUseGlobalVar()
}

private Map<String,Map> gtRooms(Map r9){
	List<Map> r=(List<Map>)app.getRooms()
	Map<String,Map> newmap = [:]
	//Boolean lg=isDbg(r9)
	for(Map n in r){
		String k= lMs(n,sID).toString()
		List devs= []
		List missing= []
		for(Long did in (List<Long>)n.deviceIds){
			String h= hashId2(did,sMs(r9,spId))
			if(getDevice(r9,h,false)) devs.push(h)
			else{
				missing.push(did)
				//if(lg)debug "No access to room (${sMs(n,sNM)}) device Id: $did from webCoRE",r9
			}
		}
		newmap[k]=[(sID): n[sID], (sNM): n[sNM], devs: []+devs ] + (missing ? [ (sM): []+missing ] : [])
	}
	newmap
}

private void wappUpdateLabel(String s){ app.updateLabel(s) }
private void wappUpdateSetting(String s,Map m){ app.updateSetting(s,m) }
private void wappRemoveSetting(String s){ app.removeSetting(s) }

private void wunschedule(String m=sNL){ if(m)unschedule(m) else unschedule() }
private void wunsubscribe(){ unsubscribe() }
private void wsubscribe(loc,String attr, handler){ subscribe(loc,attr,handler) }

private static Class HubActionClass(){ return 'hubitat.device.HubAction' as Class }
private static Class HubProtocolClass(){ return 'hubitat.device.Protocol' as Class }
/*private Boolean isHubitat(){ return hubUID!=sNL }*/

private void wpauseExecution(Long t){ pauseExecution(t) }
private void wrunInMillis(Long t,String m,Map d){ runInMillis(t,m,d) }

private Date wtimeToday(String str,TimeZone tz){ return (Date)timeToday(str,tz) }
private Date wtimeTodayAfter(String astr,String tstr,TimeZone tz=null){ return (Date)timeTodayAfter(astr,tstr,tz) }
private Long wnow(){ return (Long)now() }
private Date wtoDateTime(String s){ return (Date)toDateTime(s) }

private String gtAppN(){ return (String)app.label }
private gtSetting(String nm){ return settings.get(nm) }
private gtSt(String nm){ return state.get(nm) }
private gtAS(String nm){ return atomicState.get(nm) }
private void wstateRemove(String nm){ state.remove(nm) }

/** assign to state  */
private void assignSt(String nm,v){ state.put(nm,v) }

/** assign to atomicState  */
private void assignAS(String nm,v){ atomicState.put(nm,v) }

private Map<String,Object> gtState(){ return (Map<String,Object>)state }

private gtLocation(){ return location }
private Map<String,Object> cvtLoc(){ cvtDev(location) }
private String gtLMode(){ return (String)location.getMode() }
private Map fndMode(Map r9,String m){
	def mode= ((List)location.getModes())?.find{ it-> hashId(r9,(Long)it.getId())==m || (String)it.getName()==m }
	return mode ? [(sID): (Long)mode.getId(), (sNM): (String)mode.getName()] :null
}
private Map<String,Object> gtCurrentMode(){
	def a=location.getCurrentMode()
	if(a)return [(sID):(Long)a.getId(),(sNM): (String)a.getName()]
	return null
}
private String gtLtScale(){ return (String)location.getTemperatureScale() }
private String gtLname(){ return (String)location.getName() }

private String gtLzip(){ return (String)location.zipCode }
private String gtLlat(){ return ((BigDecimal)location.latitude).toString() }
private String gtLlong(){ return ((BigDecimal)location.longitude).toString() }

private String gtLhsmStatus(){ return (String)location.hsmStatus }

private String gtLbl(d){ return "${d?.label ?: d?.name ?: gtLname()}".toString() }
//hubitat device ids can be the same as the location id
private Boolean isDeviceLocation(d){
	if(dvStr(d)==dvStr(gtLocation())){
		Integer tt0=d.hubs?.size()
		if((tt0!=null?tt0:iZ)>iZ)return true
	}
	return false
}

Boolean wdeviceHascommand(device, String cmd){ return (Boolean)device?.hasCommand(cmd) }

private static String dvStr(d){ return d.id.toString() }
private static String hashD(Map r9,d){ return hashId2(d.id,sMs(r9,spId)) }
private static Map<String,Object> cvtDev(d){
	Map myDev
	myDev=[:]
	if(d!=null){
		myDev=[(sID):d.id,(sNM):d.name,label:d.label]
		if(d.hubs!=null)myDev.hubs=[(sT):sT]
	}
	return myDev
}
