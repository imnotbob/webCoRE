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
 *  Last update February 19, 2024 for Hubitat
 */

//file:noinspection SpellCheckingInspection
//file:noinspection unused


import groovy.transform.CompileStatic
import groovy.transform.Field

import java.text.SimpleDateFormat

public static String version() { return "v0.2.103.20240219" }
/*
 *	02/01/2018 >>> v0.2.102.20180201 - BETA M2 - Fixed SmartThings app crash, thanks to @JohnHoke
 *	10/11/2017 >>> v0.2.0fa.20171010 - BETA M2 - Various bug fixes and improvements - fixed the mid() and random() functions
 *	10/07/2017 >>> v0.2.0f9.20171007 - BETA M2 - Added previous location attribute support and methods to calculate distance between places, people, fixed locations...
 *	10/06/2017 >>> v0.2.0f8.20171006 - BETA M2 - Added support for Android geofence filtering depending on horizontal accuracy
 *	10/04/2017 >>> v0.2.0f7.20171004 - BETA M2 - Added speed and bearing support
 *	10/04/2017 >>> v0.2.0f6.20171004 - BETA M2 - Bug fixes for geofencing
 *	10/04/2017 >>> v0.2.0f5.20171003 - BETA M2 - Bug fixes for geofencing
 *	10/04/2017 >>> v0.2.0f4.20171003 - BETA M2 - Bug fixes for geofencing
 *	10/03/2017 >>> v0.2.0f3.20171003 - BETA M2 - Bug fixes for geofencing
 *	10/03/2017 >>> v0.2.0f2.20171003 - BETA M2 - Updated iOS app to add timestamps
 *	10/01/2017 >>> v0.2.0f1.20171001 - BETA M2 - Added debugging options
 *	09/30/2017 >>> v0.2.0f0.20170930 - BETA M2 - Added last update info for both geofences and location updates
 *	09/30/2017 >>> v0.2.0ef.20170930 - BETA M2 - Minor fixes for Android
 *	09/29/2017 >>> v0.2.0ed.20170929 - BETA M2 - Added support for Android presence
 *	09/27/2017 >>> v0.2.0ec.20170927 - BETA M2 - Fixed a problem where the 'was' comparison would fail when the event had no device
 *	09/25/2017 >>> v0.2.0eb.20170925 - BETA M2 - Added Sleep Sensor capability to the webCoRE Presence Sensor, thanks to @Cozdabuch and @bangali
 *	09/24/2017 >>> v0.2.0ea.20170924 - BETA M2 - Fixed a problem where $nfl.schedule.thisWeek would only return one game, it now returns all games for the week. Same for lastWeek and nextWeek.
 *	09/21/2017 >>> v0.2.0e9.20170921 - BETA M2 - Added support for the webCoRE Presence Sensor
 *	09/18/2017 >>> v0.2.0e8.20170918 - BETA M2 - Alpha testing for presence
*/
metadata {
	definition (name: "webCoRE Presence Sensor", namespace: "ady624", author: "Adrian Caramaliu") {
		capability "Presence Sensor"
		capability "Sleep Sensor"
		capability "Sensor"
		capability "Health Check"
		attribute 'places', "String"
		attribute 'previousPlace', "String"
		attribute 'currentPlace', "String"
		attribute 'currentPlaceDisplay', "String"
		attribute "closestPlace", "String"
		attribute "arrivingAtPlace", "String"
		attribute "leavingPlace", "String"
		attribute 'display', "String"
		attribute "distance", "Number"
		attribute "distanceMetric", "Number"
		attribute "distanceDisplay", "String"
		attribute "closestPlaceDistance", "Number"
		attribute "closestPlaceDistanceMetric", "Number"
		attribute "altitude", "Number"
		attribute "altitudeMetric", "Number"
		attribute "floor", "String"
		attribute "floorDisplay", "String"
		attribute "latitude", "Number"
		attribute "longitude", "Number"
		attribute "horizontalAccuracy", "Number"
		attribute "horizontalAccuracyMetric", "Number"
		attribute "verticalAccuracy", "Number"
		attribute "verticalAccuracyMetric", "Number"
		attribute "speedUSC", "Number"
		attribute "speedMetric", "Number"
		attribute "speedDisplay", "String"
		attribute "bearing", "Number"
		attribute sDEBUG, "String"
		attribute 'status', "String"
		attribute 'lastLocationUpdate', "String"
		attribute 'lastGeoFenceUpdate', "String"
		command "asleep"
		command "awake"
		command "toggleSleeping"
	}
/*
	simulator {
		status "present": "presence: 1"
		status "not present": "presence: 0"
	} */

	tiles(scale: 2) {
		multiAttributeTile(name: 'display', type: "generic", width: 2, height: 2, canChangeBackground: true) {
			tileAttribute ("device.display", key: "PRIMARY_CONTROL") {
				attributeState "present, not sleeping", label: 'Home', icon:"st.nest.nest-away", backgroundColor:"#c0ceb9"
				attributeState "present, sleeping", label: 'Home (asleep)', icon:"st.Bedroom.bedroom2", backgroundColor:"#6879a3"
				attributeState "not present", label: 'Away', icon:"st.Office.office5", backgroundColor:"#777777"
			}
			tileAttribute ("device.status", key: "SECONDARY_CONTROL") {
				attributeState "default",
					label:'${currentValue}'
			}

		}
		standardTile('presence', "device.presence", width: 4, height: 2, canChangeBackground: true) {
			state("present", labelIcon:"st.presence.tile.mobile-present", backgroundColor:"#00A0DC")
			state(sNPRES, labelIcon:"st.presence.tile.mobile-not-present", backgroundColor:"#ffffff")
		}
		standardTile('sleeping', "device.sleeping", width: 2, height: 2, canChangeBackground: true) {
			state('sleeping', label:"Asleep", icon: "st.Bedroom.bedroom2", action: "awake", backgroundColor:"#00A0DC")
			state("not sleeping", label:"Awake", icon: "st.Health & Wellness.health12", action: "asleep", backgroundColor:"#ffffff")
		}
		valueTile("currentPlace", "device.currentPlaceDisplay", width: 2, height: 2) {
			state("default", label: '${currentValue}')
		}
		valueTile("distance", "device.distanceDisplay", width: 2, height: 1) {
			state("default", label: '${currentValue}')
		}
		valueTile("speedUSC", "device.speedDisplay", width: 2, height: 1) {
			state("default", label: '${currentValue}')
		}
		valueTile("altitude", "device.altitudeDisplay", width: 2, height: 1) {
			state("default", label: '${currentValue}', icon:"https://dashboard.webcore.co/img/altitude.png")
		}
		valueTile("floor", "device.floorDisplay", width: 2, height: 1) {
			state("default", label: '${currentValue}')
		}
		valueTile("status", "device.status", width: 6, height: 5) {
			state("default", label: '${currentValue}')
		}
		valueTile("lastGeofenceUpdate", "device.lastGeofenceUpdate", width: 3, height: 1) {
			state("default", label: '${currentValue}')
		}
		valueTile("lastLocationUpdate", "device.lastLocationUpdate", width: 3, height: 1) {
			state("default", label: '${currentValue}')
		}

		main('presence')
		details(['display', 'presence', 'sleeping', "currentPlace", "distance", "altitude", "speedUSC", "floor", "status", "lastGeofenceUpdate", "lastLocationUpdate"])
	}

	preferences {
		input 'scale', 'enum', title: 'Distance scale', description: 'Select between imperial (miles) and metric (km)', options: ['Imperial', sMETRIC], defaultValue: 'Imperial', displayDuringSetup: true
		input 'advanced', 'enum', title: 'Show advanced details', description: sBLK, options: [sYES, sNO], defaultValue: sYES, displayDuringSetup: true
		input 'presenceMode', 'enum', title: 'Presence mode', description: sBLK, options: ['Automatic', sFRCPRES, sFRCNPRES], defaultValue: 'Automatic', displayDuringSetup: true
		input 'debugging', 'bool', title: 'Enable debugging', description: sBLK, defaultValue: false, displayDuringSetup: true
	}
}

@Field static final String sBLK=''
@Field static final String sCOMMA=','
@Field static final String sFRCPRES='Force present'
@Field static final String sFRCNPRES='Force not present'
@Field static final String sYES='Yes'
@Field static final String sNO='No'
@Field static final String sPRES='present'
@Field static final String sNPRES='not present'
@Field static final String sMETRIC='Metric'
@Field static final String sDEBUG='debug'

@CompileStatic
private Boolean isDbg(){ bIs(gtSettings(),'debugging') }
@CompileStatic
private static Boolean bIs(Map m,String v){ (Boolean)m.get(v) }

/** returns m.string */
@CompileStatic
private static List<Map> liMs(Map m,String s){ (List)m.get(s) }

private Map<String,Object> gtState(){ return (Map<String,Object>)state }
private Map<String,Object> gtSettings(){ return (Map<String,Object>)settings }

/** m.string */
@CompileStatic
private static String sMs(Map m,String v){ (String)m.get(v) }


private updated() {
	if (isDbg()) log.debug 'updated'
	updateData(
			liMs(gtState(),'places'),
			(String)device.currentValue('presence'),
			(String)device.currentValue('sleeping'),
			(String)device.currentValue('currentPlace'),
			(String)device.currentValue('closestPlace'),
			(String)device.currentValue('arrivingAtPlace'),
			(String)device.currentValue('leavingPlace'))
}

private List<Map> getPlaces(List iplaces) {
	List<Map> places = (List<Map>)iplaces ?: []
	String list; list = sBLK
	List<Map> existingPlaces = liMs(gtState(),'places') ?: []
	Map homePlace; homePlace = null
	for (Map place in places) {
		place.meta = existingPlaces.find{ place.id == it.id }?.meta ?: [:]
		if (place.h) homePlace = place
		list = list + (list.size() ? sCOMMA : sBLK) + sMs(place,'n')
	}
	if (!homePlace && places.size()) places[0].h = true
//	if (list != device.currentValue('places')) {
		sendEvent( name: 'places', value: list /*, displayed: false */ )
//	}
	state.places = places
	return places
}


void doSendEvent(String name, value) {
	/*if (value != device.currentValue(name))*/ sendEvent( name: name, value: value /*, displayed: false */)
}

static String getOrdinalSuffix(ivalue) {
	if (!("$ivalue".isNumber())) return sBLK
	Integer value= "$ivalue".toInteger()
	Integer value100 = value % 100
	Integer value10 = value % 10
	if (((value100 > 3) && (value100 < 21)) || (value10 == 0) || (value10 > 3)) return 'th'
	switch (value10) {
		case 1: return 'st'
		case 2: return 'nd'
		case 3: return 'rd'
	}
	return 'th'
}

Long wnow(){ return (Long)now() }

def processEvent(Map event) {
	//log.error "GOT EVENT $event"
	List<Map> places = getPlaces((List)event.places)
	Map loc=(Map)event.location
	Long timestamp = ((Long)loc?.timestamp ?: (Long)event.timestamp) ?: 0L
	Long delay = wnow() - timestamp
	if ((sMs(event,'name') == 'updated') && loc && !loc.error) {
		if (delay > 30000L) {
			if (isDbg()) {
				String info = "Received stale location update with a delay of ${delay}ms"
				log.debug info
				sendEvent( name: sDEBUG, value: info, descriptionText: info /*, isStateChange: true, displayed: true */ )
			}
			return
		}
		if (timestamp < (Long)state.lastTimestamp) {
			if (isDbg()) {
				String info = 'Received location update that is older than the last update'
				log.debug info
				sendEvent( name: sDEBUG, value: info, descriptionText: info /*, isStateChange: true, displayed: true */ )
			}
			return
		}
		Boolean nadvanced = sMs(gtSettings(),'advanced') == sNO
		Boolean metric = sMs(gtSettings(),'scale') == 'Metric'
		state.lastTimestamp = timestamp
		//filter out accuracy
		doSendEvent('latitude', loc.latitude)
		doSendEvent('longitude', loc.longitude)
		doSendEvent('altitude', loc.altitude / 0.3048)
		doSendEvent('altitudeMetric', loc.altitude)
		doSendEvent('altitudeDisplay', nadvanced ? sBLK : (metric ? sprintf('%.1f', loc.altitude) + ' m' : sprintf('%.1f', loc.altitude / 0.3048) + ' ft'))
		doSendEvent('floor', loc.floor)
		doSendEvent('floorDisplay', nadvanced ? sBLK : (loc.floor ? "${loc.floor}${getOrdinalSuffix(loc.floor)} floor" : 'Unknown floor'))
		doSendEvent('horizontalAccuracy', loc.horizontalAccuracy / 0.3048)
		doSendEvent('horizontalAccuracyMetric', loc.horizontalAccuracy)
		doSendEvent('verticalAccuracy', loc.verticalAccuracy / 0.3048)
		doSendEvent('verticalAccuracyMetric', loc.verticalAccuracy)
		Float speed = (Float)loc.speed ?: 0.0F
		doSendEvent('speedUSC', speed / 0.3048F)
		doSendEvent('speedMetric', speed)
		Double bearing = (Double)loc.bearing ?: ((Double)loc.course ?: 0.0D)
		doSendEvent('bearing', bearing)
		doSendEvent('speedDisplay', nadvanced ? sBLK : (speed < 0.0F ? 'Unknown speed' : (speed == 0.0F ? 'Stationary' : (sprintf('%.1f', (metric ? speed * 3.6F : speed * 3.6F / 1.609344F)) + (metric ? ' km/h' : ' mph') + (bearing >= 0.0D ? ' to ' + getBearingName(bearing) : sBLK)))))
		processLocation((Float)loc.latitude, (Float)loc.longitude, places, loc.horizontalAccuracy)
	} else {
		state.lastTimestamp = timestamp > (Long)state.lastTimestamp ? timestamp : (Long)state.lastTimestamp
		String ep=sMs(event,'place')
		if (ep && ep.size() == 71) {
			List<String> parts = ep.tokenize('|')
			if (parts.size() == 3) {
				Map place = places.find{ it.id == parts[1] }
				if (place) {
					processPlace(place, sMs(event,'name'), parts[2], places, (Map)event.location)
				}
			}
		}
	}
}

private void processLocation(Float lat, Float lng, List<Map> places, horizontalAccuracy) {
	String presence, closestPlace, currentPlace, arrivingAtPlace, leavingPlace, bearing
	presence = (String)device.currentValue('presence')
	closestPlace = (String)device.currentValue('closestPlace')
	currentPlace = (String)device.currentValue('currentPlace')
	arrivingAtPlace = sBLK
	leavingPlace = sBLK
	bearing = '?'
	Float homeDistance, closestDistance
	homeDistance = -1.0F
	closestDistance = -1.0F
	Integer circles; circles = 0
	String info; info = sBLK
	if (horizontalAccuracy > 100) {
		info = " Low accuracy of ${horizontalAccuracy}m prevented updates to presence."
	} else {
		doSendEvent('lastLocationUpdate', "Last location update on\r\n${formatLocalTime('MM/dd/yyyy @ h:mm:ss a')}")
		for (Map place in places) {
			Float distance = getDistance(lat, lng, (Float)((List)place.p)[0], (Float)((List)place.p)[1])
			String pn = sMs(place,'n')
			if ((closestDistance < 0.0F) || (distance < closestDistance)) {
				closestDistance = distance
				closestPlace = pn
			}
			if (distance <= place.i) {
				info += " Location is inside inner ${pn}.\r\n"
				//we're at this place
				currentPlace = pn
				((Map)place.meta).p = true
				if (place.h) presence = sPRES
				circles += 1
			} else if (distance <= place.o) {
				//we're close to this place
				info += " Location is in the buffer zone of ${pn}.\r\n"
				if (pn == currentPlace) {
					//departing
					arrivingAtPlace = sBLK
					leavingPlace = pn
				} else {
					//arriving
					arrivingAtPlace = pn
					leavingPlace = sBLK
				}
				circles += 1
			} else {
				//we're not at this place
				info += " Location is outside of ${pn}.\r\n"
				((Map)place.meta).p = false
				if (place.h) presence = sNPRES
			}
			((Map)place.meta).d = distance
			if (place.h) {
				homeDistance = distance / 1000.0F
				bearing = getBearing((Float)((List)place.p)[0], (Float)((List)place.p)[1], lat, lng)
			}
		}
		if (!circles) {
			//we found no current circle, so we clear the current place
			info += ' Location is outside of all circles.'
			currentPlace = sBLK
		}
		if ((homeDistance >= 0.0F) /* && homeDistance != device.currentValue('distanceMetric') */ ) {
			sendEvent( name: 'distanceMetric', value: homeDistance /*, isStateChange: true, displayed: false */ )
			sendEvent( name: 'distance', value: homeDistance / 1.609344F /*, isStateChange: true, displayed: false */ )
			sendEvent( name: 'distanceDisplay', value: sMs(gtSettings(),'advanced') == sNO ? sBLK : (sMs(gtSettings(),'scale') == 'Metric' ? sprintf('%.1f', homeDistance) + ' km @ ' + bearing : sprintf('%.1f', homeDistance / 1.609344) + ' mi @ ' + bearing) /*, isStateChange: true, displayed: false */ )
		}

		closestDistance = closestDistance / 1000.0F
		if ((closestDistance >= 0.0F) /* && closestDistance != device.currentValue('closestPlaceDistanceMetric') */ ) {
			sendEvent( name: 'closestPlaceDistanceMetric', value: closestDistance /*, isStateChange: true, displayed: false */ )
			sendEvent( name: 'closestPlaceDistance', value: closestDistance / 1.609344F /*, isStateChange: true, displayed: false */ )
		}
		state.places = places
		updateData(
				places,
				presence,
				(String)device.currentValue('sleeping'),
				currentPlace,
				closestPlace,
				arrivingAtPlace,
				leavingPlace)
	}
	if (isDbg()) {
		info = "Received location update with horizontal accuracy of ${horizontalAccuracy} meters.$info"
		log.debug info
		sendEvent( name: sDEBUG, value: info, descriptionText: info /*, isStateChange: true, displayed: true */ )
	}
}

private void processPlace(Map place, String action, String circle, List<Map> places, Map location) {
	def horizontalAccuracy = location?.horizontalAccuracy ?: 0
	String info; info = sBLK
	String closestPlace = sMs(place,'n')
	if (horizontalAccuracy > 100) {
		info = " Low accuracy of ${horizontalAccuracy}m prevented updates to presence."
	} else {
		doSendEvent('lastGeofenceUpdate', "Last geofence update on\r\n${formatLocalTime('MM/dd/yyyy @ h:mm:ss a')}")
		for (Map p in places) {
			p.meta = p.meta ?: [:]
			if (p != place) {
				((Map)p.meta).p = false
			}
		}
		String presence, currentPlace, arrivingAtPlace, leavingPlace
		presence = (String)device.currentValue('presence')
		currentPlace = (String)device.currentValue('currentPlace')
		arrivingAtPlace = sBLK
		leavingPlace = sBLK
		switch (action) {
			case 'entered':
				switch (circle) {
					case 'i':
						//arrived
						info += " Inner geofence of ${closestPlace} was just entered."
						presence = place.h ? sPRES : sNPRES
						currentPlace = closestPlace
						arrivingAtPlace = sBLK
						leavingPlace = sBLK
						((Map)place.meta).p = true
						break
					case 'o':
						//arriving
						info += " Outter geofence of ${closestPlace} was just entered."
						arrivingAtPlace = currentPlace == sBLK ? closestPlace : sBLK
						leavingPlace = sBLK
						break
				}
				break
			case 'exited':
				switch (circle) {
					case 'i':
						//leaving
						info += " Inner geofence of ${closestPlace} was just exited."
						arrivingAtPlace = sBLK
						leavingPlace = currentPlace == closestPlace ? closestPlace : sBLK
						break
					case 'o':
						//left
						info += " Outer geofence of ${closestPlace} was just exited."
						presence = sNPRES
						currentPlace = sBLK
						arrivingAtPlace = sBLK
						leavingPlace = sBLK
						break
				}
				break
		}
		state.places = places
		updateData(
				places,
				presence,
				(String)device.currentValue('sleeping'),
				currentPlace,
				closestPlace,
				arrivingAtPlace,
				leavingPlace)
	}
	if (isDbg()) {
		info = "Received geofence update for ${circle == 'i' ? 'inner' : 'outer'} circle of ${closestPlace}.$info"
		log.debug info
		sendEvent( name: sDEBUG, value: info, descriptionText: info /*, isStateChange: true, displayed: true */ )
	}
}

private void updateData(List<Map>places, String ipresence, String isleeping, String currentPlace, String closestPlace, String arrivingAtPlace, String leavingPlace) {
	String presence; presence=ipresence
	String sleeping; sleeping=isleeping
	Boolean nadvanced = sMs(gtSettings(),'advanced') == sNO
	String prevPlace = (String)device.currentValue('currentPlace')
//	if (currentPlace != prevPlace) {
		sendEvent( name: 'previousPlace', value: prevPlace /*, isStateChange: true, displayed: false */ )
		sendEvent( name: 'currentPlace', value: currentPlace, /* isStateChange: true, displayed: !nadvanced, */ descriptionText: currentPlace == sBLK ? "Left $prevPlace" : "Arrived at $currentPlace" )
//	}
	doSendEvent('currentPlaceDisplay', nadvanced ? sBLK : (currentPlace ?: 'Away'))
//	if (closestPlace != (String)device.currentValue('closestPlace')) {
		sendEvent( name: 'closestPlace', value: closestPlace /*, displayed: false */)
//	}
//	if (arrivingAtPlace != (String)device.currentValue('arrivingAtPlace')) {
		sendEvent( name: 'arrivingAtPlace', value: arrivingAtPlace /*, isStateChange: true, displayed: false */ )
//	}
//	if (leavingPlace != (String)device.currentValue('leavingPlace')) {
		sendEvent( name: 'leavingPlace', value: leavingPlace /*, isStateChange: true, displayed: false */ )
//	}

	String status; status = sBLK
	if (!nadvanced) {
		Integer count; count = 0
		for (Map place in places.sort{ ((Map)it.meta)?.d }) {
			String pn = sMs(place,'n')
			def metad = ((Map)place.meta)?.d
			String line = ( pn == arrivingAtPlace ? "Arriving at $arrivingAtPlace" : ( leavingPlace == pn ? "Leaving $leavingPlace" : ( currentPlace == pn ? "Currently at $currentPlace" : (metad == null ? sBLK : "~${sMs(gtSettings(),'scale') == sMETRIC ? sprintf('%.2f', metad / 1000) + ' km' : sprintf('%.2f', metad / 1609.344) + ' miles'} from "+pn))))
			if (line) {
				status += (status ? '\r\n' : sBLK) + line
				count += 1
			}
		}
		while (count < 10) {
			status += '\r\n'
			count += 1
		}
	}
//	if (status != device.currentValue('status')) {
		sendEvent( name: 'status', value: status /*, isStateChange: true, displayed: false */ )
//	}
	switch (sMs(gtSettings(),'presenceMode')) {
		case sFRCPRES:
			presence = sPRES
			break
		case sFRCNPRES:
			presence = sNPRES
			break
	}
//	if (presence != device.currentValue('presence')) {
		sendEvent( name: 'presence', value: presence, /* isStateChange: true, displayed: true, */ descriptionText: presence == sPRES ? 'Arrived' : 'Left' )
//	}
	sleeping = sleeping ? (presence == sNPRES ? 'not sleeping' : sleeping) : 'not sleeping'
//	if (sleeping != device.currentValue('sleeping')) {
		sendEvent( name: 'sleeping', value: sleeping, /* isStateChange: true, displayed: true, */ descriptionText: sleeping == 'sleeping' ? 'Sleeping' : 'Awake' )
//	}
	String display = presence + (presence == sPRES ? ', ' + sleeping : sBLK)
//	if (display != device.currentValue('display')) {
		sendEvent( name: 'display', value: display /*, isStateChange: true, displayed: false */ )
//	}
}

private static Float getDistance(Float lat1, Float lng1, Float lat2, Float lng2) {
	Double earthRadius = 6371000.0D //meters
	Double dLat = Math.toRadians(lat2-lat1)
	Double dLng = Math.toRadians(lng2-lng1)
	Double a = Math.sin(dLat/2.0D) * Math.sin(dLat/2.0D) +
			Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
			Math.sin(dLng/2.0D) * Math.sin(dLng/2.0D)
	Double c = 2.0D * Math.atan2(Math.sqrt(a), Math.sqrt(1.0D-a))
	Float dist = (Float)(earthRadius * c)
	return dist //meters
}

private static String getBearingName(Double degrees) {
	List<String> bearings = ['N', 'N-NE', 'NE', 'E-NE', 'E', 'E-SE', 'SE', 'S-SE', 'S', 'S-SW', 'SW', 'W-SW', 'W', 'W-NW', 'NW', 'N-NW']
	Integer bearing = Math.floor( ( (degrees + 360.0D + 11.25D).toInteger() % 360 ).toDouble() / 22.5D).toInteger()
	return bearings[bearing]
}

private static String getBearing(Float lat1, Float lon1, Float lat2, Float lon2){
	Double longitude1 = lon1
	Double longitude2 = lon2
	Double latitude1 = Math.toRadians(lat1)
	Double latitude2 = Math.toRadians(lat2)
	Double longDiff= Math.toRadians(longitude2-longitude1)
	Double y= Math.sin(longDiff)*Math.cos(latitude2)
	Double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff)

	return getBearingName(Math.toDegrees(Math.atan2(y, x)))
}

def parse(String description) {
	//not used
}

private toggleSleeping(String isleeping = (String)null) {
	String sleeping = isleeping ?: ((String)device.currentValue('sleeping') == 'not sleeping' ? 'sleeping' : 'not sleeping')
	updateData(
			liMs(gtState(),'places'),
			(String)device.currentValue('presence'),
			sleeping,
			(String)device.currentValue('currentPlace'),
			(String)device.currentValue('closestPlace'),
			(String)device.currentValue('arrivingAtPlace'),
			(String)device.currentValue('leavingPlace'))
}

def asleep() {
	toggleSleeping('sleeping')
}

def awake() {
	toggleSleeping('not sleeping')
}

private static TimeZone mTZ(){ return TimeZone.getDefault() }

private static String formatLocalTime(String format = 'EEE, MMM d yyyy @ h:mm:ss a z', Date time = new Date()) {
	SimpleDateFormat formatter = new SimpleDateFormat(format)
	formatter.setTimeZone(mTZ())
	return formatter.format(time)
}
