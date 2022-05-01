import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';


const baseUrl : String = environment.apiUrl;


@Injectable({
  providedIn: 'root'
})



export class ImageServiceService {

  imageData : any = [];
 
  constructor(private httpClient : HttpClient) { }


  uploadImage(data : any){

    this.httpClient.post(baseUrl+"/image/upload/all", data, { observe: 'response', responseType: 'text'})
      .subscribe((response) => {
        console.log(response.status);
      }
    );
  }

  getAllImages(){
    
    this.imageData =  this.httpClient.get(baseUrl+"/image/get/all");

     console.log("Image Data from service; "+this.imageData);
     return this.imageData;
  }

}
