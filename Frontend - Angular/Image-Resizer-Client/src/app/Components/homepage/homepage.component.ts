import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ImageServiceService } from 'src/app/Services/image-service.service';
import { WebSocketService } from 'src/app/Services/web-socket.service';
import { WebSocketAPI } from 'src/app/Utilities/WebSocketAPI';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {


  files : any[]=[];

  error : any;

  notification : any = "Hello!";

  webSocket: any;

  allImages : any = [];


  constructor(private imageService : ImageServiceService) { }
  

  ngOnInit(): void {
    this.webSocket = new WebSocketAPI(this);
    this.webSocket._connect();
    this.getAllImages();
  }

  getAllImages(){
    this.imageService.getAllImages().subscribe((res : any) => this.allImages = res);

    
    console.log(this.allImages);
  }


  imageDragDropUpload(images: File[], evt : any){
    
    this.files = Object.keys(images).map(k => images[k]);
    
    if(this.validityCheck()){

      const formData = this.processFiles();

      this.imageService.uploadImage(formData);

      this.getAllImages();
    }else{
      this.error = "Please Upload Valid Image File."
    }

  }

  imageUpload($event : any){
    this.files = $event.srcElement.files;
    
    if(this.validityCheck()){

      const formData = this.processFiles();

      this.imageService.uploadImage(formData);

      this.getAllImages();
    }else{
      this.error = "Please Upload Valid Image File."
    }

  }

  processFiles() : any{
    const data  = new FormData();

    for(let i = 0; i < this.files.length; i++){
      data.append("file",this.files[i]);
    }

    return data;
  }

   validityCheck() : boolean{

    for(let i = 0; i < this.files.length; i++){
      if(this.files[i].name.endsWith(".jpg") || this.files[i].name.endsWith(".png") || this.files[i].name.endsWith(".jpeg") ||this.files[i].name.endsWith(".gif")){
        return true;
      }
    }

    return false;
  }

  handleMessage(message : any){
    const splits = message.split("\\\"",7);
    this.notification = splits[3];
    
    // const separateDate = this.notification.split("+",3);
    // const data = separateDate[1];
    // const msg = separateDate[0];

    //console.log("Notification value: "+data+"  msg: "+msg);
    // this.showSuccess(message);
    // this.handleToast(data,msg);
  }



}
