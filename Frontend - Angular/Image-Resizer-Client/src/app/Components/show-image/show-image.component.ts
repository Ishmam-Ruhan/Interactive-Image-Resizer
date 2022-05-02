import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-show-image',
  templateUrl: './show-image.component.html',
  styleUrls: ['./show-image.component.css']
})
export class ShowImageComponent{

  @Input() open: boolean | undefined;
  @Input() imageURL: string | undefined;

  @Output() close = new EventEmitter();

  constructor() { }

}
