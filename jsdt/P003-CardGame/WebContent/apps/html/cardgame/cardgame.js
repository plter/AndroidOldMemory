var gameView,jGameView;
var context;
var H_COUNT=5,V_COUNT=6;
var currentNum=1;
var cardArr=[];
var cardMap;
var allPoints=[];


function Card(num,x,y,width,height){
	
	this.width=width;
	this.height=height;
	this.x=x;
	this.y=y;
	this.rectoVisible=true;
	this.num=num;
	
	this.showRecto=function(){
		this.rectoVisible=true;
	};
	this.showVerso=function(){
		this.rectoVisible=false;
	};
	
	this.redraw=function(ctx2d){
		
		ctx2d.save();
		
		ctx2d.translate(this.x,this.y);
				
		if (this.rectoVisible) {
			ctx2d.fillStyle="#FF0000";
			ctx2d.fillRect(0,0,this.width,this.height);
			ctx2d.fillStyle="#FFFFFF";
			ctx2d.font="40px sans-serif";
			ctx2d.fillText(this.num+"",5,40);
		}else{
			ctx2d.fillStyle="#0000FF";
			ctx2d.fillRect(0,0,this.width,this.height);
		}
		
		ctx2d.restore();
	};
}


function reset(){
	currentNum=1;
	cardArr.length=0;
	cardMap=[[],[],[],[],[]];
	
	genAllPoints();
	addCards();
	
	redrawCanvas();
}


function addCards(){
	var card,p,index;
	
	for ( var i = 1; i <= 10; i++) {
		index=Math.floor(allPoints.length*Math.random());
		p=allPoints[index];
		allPoints.splice(index,1);
		
		card=new Card(i, p.i*60, p.j*60, 50, 50);
		cardArr.push(card);
		cardMap[p.i][p.j]=card;
	}
}


function genAllPoints(){
	allPoints.length=0;
	
	for ( var i = 0; i < H_COUNT; i++) {
		for ( var j = 0; j < V_COUNT; j++) {
			allPoints.push({i:i,j:j});
		}
	}
}


function redrawCanvas(){
	context.clearRect(0,0,320,360);
	
	for ( var i = 0; i < cardArr.length; i++) {
		cardArr[i].redraw(context);
	}
}


function removeCardFromArr(card,arr){
	for ( var i = 0; i < arr.length; i++) {
		if (arr[i]==card) {
			arr.splice(i,1);
			break;
		}
	}
}

function turnAllCardOver(){
	for ( var i = 0; i < cardArr.length; i++) {
		cardArr[i].showVerso();
	}
}

function turnAllCardBack(){
	for ( var i = 0; i < cardArr.length; i++) {
		cardArr[i].showRecto();
	}
}


function gameViewClickHandler(event){
	
	var indexI= parseInt(event.pageX/60);
	var indexJ=parseInt(event.pageY/60);
	
	try{
		var clickedCard = cardMap[indexI][indexJ];
		
		if (clickedCard){
			if(clickedCard.num==currentNum) {
				removeCardFromArr(clickedCard, cardArr);
				turnAllCardOver();
				redrawCanvas();
				
				currentNum++;
				
				if (cardArr.length<=0) {
					alert("你真棒！");
					reset();
				}
			}else{
				turnAllCardBack();
				redrawCanvas();
			}
		}
	}catch(e){
//		alert(e);
	}
}

function resetBtnClickHandler(event){
	reset();
}


$(document).ready(function(){
	
	jGameView=$("#gameView");
	jGameView.bind("click",gameViewClickHandler);
	gameView = jGameView[0];
	context = gameView.getContext("2d");
	
	$("#resetBtn").bind("click",resetBtnClickHandler);
	
	reset();
});