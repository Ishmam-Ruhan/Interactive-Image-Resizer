import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { HomepageComponent } from '../Components/homepage/homepage.component';

export class WebSocketAPI {
    
    webSocketEndPoint: string = 'http://localhost:8900/image-service-websocket';
    topic: string = "/topic/update";
    stompClient: any;
    homeComponent: any;
    
    constructor(homeComponent: HomepageComponent){
        this.homeComponent = homeComponent;
    }
    _connect() {
        console.log("Initialize WebSocket Connection");
        
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;

        _this.stompClient.connect({}, function (frame : any) {
            _this.stompClient.subscribe(_this.topic, function (sdkEvent : any) {
                _this.onMessageReceived(sdkEvent);
            });
            
        });
    };

    onMessageReceived(message : any) {
        console.log("Message Recieved from Server :: " + message);
        this.homeComponent.handleMessage(JSON.stringify(message.body));
    }
}