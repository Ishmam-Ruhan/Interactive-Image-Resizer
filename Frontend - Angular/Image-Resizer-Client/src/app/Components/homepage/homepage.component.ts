import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Data, Router } from '@angular/router';
import { BehaviorSubject, interval, Observable } from 'rxjs';
import { ImageServiceService } from 'src/app/Services/image-service.service';
import { ToastService } from 'src/app/Services/toast.service';
import { WebSocketService } from 'src/app/Services/web-socket.service';
import { WebSocketAPI } from 'src/app/Utilities/WebSocketAPI';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})





export class HomepageComponent implements OnInit {

 
  imageURL : any;;

  viewerOpen = false;

  files : any[]=[];

  error : any;

  notification : any = "Hello!";

  webSocket: any;

  allImages : any = [];

  allData : data[] = [];

  constructor(private imageService : ImageServiceService, private route: Router, private toastService: ToastService) { 
    
  }
  

  ngOnInit(): void {
    interval(200).subscribe(x =>{
      this.getAllImages();
    })

    this.webSocket = new WebSocketAPI(this);
    this.webSocket._connect();
  }



  getAllImages(){
    this.imageService.getAllImages().subscribe((res : any) => this.allImages = res);
    
    this.allData = [];

    console.log("CALLED FUNCTION!!!!!!");

    for(let i = 0; i < this.allImages.length; i++){
      const id = this.allImages[i].id;
      const name = this.allImages[i].imageFileName;
      const fileType = this.allImages[i].fileType;
      const originalImage = this.allImages[i].originalImageData;
      const resizedImage = this.allImages[i].thumbnileImageData;
      const uploadAt = this.allImages[i].uploadedAt;

      const thumbnailData = "data:"+fileType+";base64,"+resizedImage;
      const mainData = "data:"+fileType+";base64,"+originalImage;

      this.allData.push(new data(id,name,fileType,mainData,thumbnailData,uploadAt));
    }
    //console.log(typeof(this.allImages.thumbnileImageData));
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

  deleteImage(id : any){
    this.imageService.deleteImage(id);
    this.getAllImages();
    console.log("Delete photo with id: "+id)
  }

  showImage(image : any){
    this.imageURL = image;
    this.viewerOpen = true;
  }

  handleMessage(message : any){
    const splits = message.split("\\\"",7);
    this.notification = splits[3];
    
    const separateDate = this.notification.split("+");
    const code = separateDate[1];
    const msg = separateDate[0];

    //console.log("Notification value: "+data+"  msg: "+msg);
    // this.showSuccess(message);
    this.handleToast(code,msg);
    this.getAllImages();
  }

  handleToast(code: any, msg: any){
    if(code == 200){
      this.showSuccess(msg);
    }else{
      this.showError(msg);
    }
  }


  showSuccess(msg : any) {
    this.toastService.show(msg, { classname: 'bg-success text-light', delay: 5000 });
  }

  showError(msg: any) {
    this.toastService.show(msg, { classname: 'bg-danger text-light', delay: 5000 });
  }

}

class data{
  id : any;
  name : any;
  fileType: any;
  originalImage : any;
  resizedImage: any;
  upload: any;

  constructor(id: any, name: any, fileType: any,originalImage: any,resizedImage: any,upload: any){
    this.id = id;
    this.name = name;
    this.fileType = fileType;
    this.originalImage = originalImage;
    this.resizedImage = resizedImage;
    this.upload = upload;
  }

}
