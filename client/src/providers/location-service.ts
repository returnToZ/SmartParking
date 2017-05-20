import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Geolocation } from '@ionic-native/geolocation';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';

@Injectable()
export class LocationService {
    constructor(public http: Http) {
        console.log('Hello LocationService Provider');
    }
   
    getLocations(srcsArray,dstsArray,googleObj) {
        return this.http.get('http://localhost:8080/Locations').map(res => res.json()).subscribe(data => {
            Object.keys(data).forEach(function(key){
                    srcsArray.push({title:key,position: new googleObj.maps.LatLng(data[key].lat,data[key].lon)});
                    dstsArray.push({title:key,position: new googleObj.maps.LatLng(data[key].lat,data[key].lon)});
            });
            var geolocation = new Geolocation();
            geolocation.getCurrentPosition().then((position) => {
                srcsArray.push({title:"Current Location",position:new googleObj.maps.LatLng(position.coords.latitude, position.coords.longitude)});
            });
        });
    }
    getParkingAreas(googleObj,mapViewer) {
        
        return this.http.get('http://localhost:8080/ParkingAreas').map(res => res.json()).subscribe(data => {
            var areasArray=[];
            Object.keys(data).forEach(function(key){
                    areasArray.push({color:data[key].color,position : new googleObj.maps.LatLng(data[key].location.lat,data[key].location.lon)});
                    mapViewer.showParkingAreas(areasArray);
            });
            
        });
    }
}