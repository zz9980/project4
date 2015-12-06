//// Project 4, Teng Lin 2015/12/06.


int lin=15;
Ball[] b= new Ball[lin];         //array of ball object 
Ball cue;

int ten=7;
Button [] bt= new Button[ten];   //array of button object 

Bird o, p, q;
////global
float ratx= 40, raty=random(250,350), ratdx=random(2,6);
float cloudx=40, cloudy=30, clouddx=random(1,2) ;                             // cloud variables
int tableRed=70, tableGreen=240, tableBlue=230;                               // pool table color
float left=50, right=450, top=100, bottom=250;                                // Table boundaries
float middle=250;
boolean wall = true;
boolean rat2 = false;
boolean birds = false;
int score;
int n=1;
void setup() {
  size( 700, 500 );                                                           //window size
  left=   50;
  right=  width-50;
  top=    100;
  bottom= height-80;
  middle= left + (right-left) / 2;
  
 //creating 15 balls by array + cue ball
 cue = new Ball();
 b[0]= new Ball( color (int(random(0,255)),int(random(0,255)),int(random(0,255))), random( middle+25, right), random (top, bottom), 0 );
 
 for (int i=1; i<lin; i++) {
 b[i]= new Ball(color (int(random(0,255)),int(random(0,255)),int(random(0,255))), random( middle+25, right), random (top, bottom), i );
 }
 //creating birds
  o = new Bird( 255,0,0, random(50,100), random(20,30),random(1,3));
  p = new Bird( 0,255,0, random(50,100), random(35,50),random(1,3));
  q = new Bird( 0,0,255, random(50,100), random(40,65),random(1,3));
   
  //creating buttons using array
  int mx=70, my=105, l=60, w=30; 
  bt[0]= new Button( mx, my, l, w );
  
  for ( int i=0; i<ten; i++){
  bt[i] = new Button(mx, my, l, w );
  mx+=70;
  }
  
  bt[0].name= "Reset" ;
  bt[1].name= "Wall" ;
  bt[2].name= "Bird" ;
  bt[3].name= "Rat" ;
  bt[4].name= "Closest" ;
  bt[5].name= "List" ;
  bt[6].name= "Sort" ;
  reset();

}


void reset() {
  for (int i=0; i<lin; i++) {
    b[i].reset(); 
  }
  //rat reset.
  rat2=false;
  ratx= 40; 
  raty=random(250,350);
  ratdx=random(2,6);
  score=0;
  //birds reset.
  birds= false;
  o.birdx=random(50,100);  o.birdy=random(20,30);
  p.birdx=random(50,100);  p.birdy=random(35,50);
  q.birdx=random(50,100);  q.birdy=random(40,65);
}
  

    
void draw() {
  background( 250,250,200 );                                                 //background color
  table( left, top, right, bottom );
  grass();
  cloud();
  bird();
  ball();
  rat2();
  buttons();
  score();
  

}


void table( float east, float north, float west, float south ) {
  rectMode( CORNERS );
  fill( tableRed, tableGreen, tableBlue );    // pool table
  strokeWeight(20);
  stroke( 240, 150, 50 );   
  rect( east-20, north-20, west+20, south+20 );
  
  // Start with a WALL down the middle of the table! 
  if (wall) {
    float middle=  (east+west)/2;    
    stroke(0,0,0,30);
    line( middle,north+10, middle,south-10 );
  }
  stroke(0);
  strokeWeight(1);

}


void grass() {
  stroke(0,255,0);                            //grass color green.
  strokeWeight(3);
  
  int gx = 10;
   while( gx < width-20) {
    line( gx, height-30 , gx+15, height);
    line( gx+20, height, gx+30, height-30);
    gx=gx+45;                                //spacing between grass
   }
}


void cloud() {
  noStroke();
  float x;
  
  //construct 7 clouds.
  for ( x = cloudx ; x < width; x++) {
    fill(255);
    ellipse(x, cloudy, 60,40);
    x= x+100;                              //spacing
  }
    cloudx = cloudx + clouddx;             //cloud moving
   
    cloudx %= width+(width/50);            //cloud move back to screen 
    
}


void ball() { 
  // cue ball
  cue.bx = width/5;
  cue.by = height/2;
  fill(255);
  ellipse( cue.bx, cue.by, 30,30);
  textSize(10);
  fill(0);
  text( "Cue", cue.bx-6, cue.by);
  // rest of the balls
  for (int i=0; i<lin; i++) {
    b[i].show();
    b[i].move();
  }
  //// Elastic collisions.
  for(int i=0; i<lin; i++) {
    for( int h=i+1; h<lin; h++) {
     collision( b[i],b[h]);
    }
  }
} 
 

void collision( Ball p, Ball q ) {
   if ( p.hit( q.bx,q.by ) ) {
      float tmp;
      tmp=p.bdx;  p.bdx=q.bdx;  q.bdx=tmp;      // Swap the velocities.
      tmp=p.bdy;  p.bdy=q.bdy;  q.bdy=tmp;
      //p.move();  p.move();   p.move();
      //q.move();  q.move();   q.move();
      score = score+1;
    }
}
  

void bird() { 
  if (birds) {
  o.show();
  p.show();
  q.show();
  
  o.move();
  p.move();
  q.move();
  }
  o.mousePressed();
  p.mousePressed();
  q.mousePressed();
}

//rat
void rat2() { 
  
   if (rat2) {
   fill(0);
   ellipse(ratx, raty, 30,30);
   fill(255,0,0);
   ellipse(ratx+10, raty, 5,5);

    
   //moving rat
    ratx = ratx + ratdx;
    //goes back to screen
    ratx %= width+(width/10);
    
    
   if(ratx>width+50) {
        raty=random(150,350);
   }
 

  //moving legs.
    int k= frameCount/30%2;
    if (k==0) {
     stroke(5);
     fill(0);
     line( ratx, raty, ratx-20, raty+25);
     line( ratx, raty, ratx+20, raty+25);
     raty = raty+0.5;
    }
    
    else {
      stroke(5);
      fill(0);
      line( ratx, raty, ratx-25, raty+25);
      line( ratx, raty, ratx+25, raty+25);
      raty = raty-0.8;
    }
  
      noStroke();
 }
  
  
  
  //if rat collide with ball, ball will stop.
  for(int i=0; i<lin; i++){
  if (  dist( ratx,raty, b[i].bx,b[i].by ) < 30 ){
    b[i].bdx=0;
    b[i].bdy=0;
    score = score-10;
   }
  }
}
//// HANDLERS:  keys, click
void keyPressed() {
  if( key == 'r') {
    reset();
  }


}


void mousePressed() {
  //click on ball to reset the ball.
  for(int i=0; i<lin; i++) {
   b[i].mousePressed();
  }
  /*
  //reset button
  if ( mouseX<b[0].mx && mouseX>b[0].mx-30 && mouseY<b[0].my+15 && mouseY>b[0].my-15) {
   reset();
  }
  //wall button
  if ( mouseX<170 && mouseX>110 && mouseY<120 && mouseY>90) {
    
  for(int i=0; i<lin; i++) {
   b[i].move2();
  }
  wall=false;
  }
  
  //bird button
  if ( mouseX<100 && mouseX>40 && mouseY<160 && mouseY>130) {
   birds=true;

  }
  
  //rat button
  if ( mouseX<170 && mouseX>110 && mouseY<160 && mouseY>130) {
   rat2=true;
   
 
   }
   //catch the rat.
   if ( dist( ratx,raty, mouseX,mouseY ) < 30 ) {
     rat2= false; 
     ratx=40;
     score = score+50;
   }
*/
}


void score() {
  fill(0);
  textSize(10);
  text("SCORE", 600, 80);
  text(score, 600, 90);
  text("Teng Lin", 330, 80);
  text("Project 4", 330, 90);
}

void buttons() {
  
  for (int i=0; i<ten; i++) {
    bt[i].show(); 
  }

 
}



class Button {
  int mx, my, l, w;              //buttons centerx , centery , length , width.
  String name="";
  
 Button( int templ, int tempr, int tempt, int tempb) {
  mx=templ;
  my=tempr;
  l=tempt;
  w=tempb;
 }

 void show() {
  fill(0,0,0,30);
  rectMode(CENTER);
  rect( mx, my, l, w);
  fill(0);
  textSize(14);
  text( name, mx-20, my+5);
  }
  
  /*
 void mousePressed() { 
   if ( mouseX<mx+30 && mouseX>mx-30 && mouseY>my && mouseY< my+15 && mouseY > my-15) {
   }
 }
 */
}

class Ball {
  int c;
  float bx, by;
  float bdx=random(1,1.5), bdy=random(1,1.5);
  int n;
 Ball() {
 }
 Ball(color tempc, float tempx, float tempy, int tempn) {
  c=tempc;
  bx=tempx;
  by=tempy;
  n=tempn;
  
 } 

 void show() {
   fill(c);
   ellipse( bx, by, 30,30);
   fill(0);
   textSize(15);
   text( n, bx-5, by+5); 
 }

 void move() {
  bx = bx + bdx;
  by = by + bdy;
  // bounce off wall
  if ( bx < middle || bx > right )   bdx *= -1;
  if ( by < top || by > bottom )     bdy *= -1;
 }
 void move2() { 
   if (wall=!wall) { 
    middle=left;
   }

 }

 void reset() {
    bx=  random(middle+25, right );
    by=  random( top, bottom );
    bdx=  random( 1,2 );
    bdy=  random( 1,2 );
    wall= true;
    middle= (width/2);
  }
  boolean hit( float x, float y ) {
    if (  dist( x,y, this.bx,this.by ) < 30.5 ) return true;
    else return false;
  }
 
 void mousePressed() {
   if (dist ( bx, by, mouseX, mouseY) <30.5) {
     bx=  random( 390,right );     
     by=  random( top, bottom );
     bdx=  random( 1,2 );    
     bdy=  random( 1,2 );
   }

 }
  
}


class Bird {
  
  float r,g,b, birdx,birdy, bomby=birdy+1, birddx=random(1,3);
  
  
  Bird( int tempr, int tempg, int tempb, float tempx, float tempy, float tempdx) {
  r=tempr;
  g=tempg;
  b=tempb;
  birdx = tempx;
  birdy = tempy;
  birddx = tempdx;
  }
  
  
 void show() { 
  fill(r,g,b);
  ellipse(birdx, birdy, 60, 30);
  fill(0);
  ellipse(birdx+20, birdy-5, 5,5);
  fill(r,g,b);
  int k= frameCount/30%2;
    if (k==0) {
     triangle( birdx-10,birdy, birdx+20, birdy, birdx, birdy-30);
     
    }
    
    else {
     triangle( birdx-10,birdy, birdx+20, birdy, birdx, birdy+30);
     
     
    }
  
 } 
  
  void move() {
    birdx += birddx;
    birdx %= width+(width/10);
  
  
  }
  
  void mousePressed() {
     
   if ( dist( birdx, birdy, mouseX, mouseY) <30) { 
     fill(0);
     ellipse( birdx,bomby, 30, 50);
     bomby = bomby + 1 ;
   
    } 
  }

}
