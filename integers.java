//// Arrays.

int[] a=  { 11, 22, 33, 7, 99, -42, 166, 0, 88, 3 };
int many=  a.length;

float x,y;

/*
int[] a;
int many=10;
a=  new int[10];
a[0]=  11;
a[1]=  22;
//...
a[9]=  3;
*/


//// SETUP:  size, init.
void setup() {
  size( 800, 600 );
}

//// NEXT:  Sum, display all.
void draw() {
  background( 150, 200, 250 );
  fill(0);
  textSize(15);
  // Tot & avg
  int tot;
  tot=  sum( a, many );
  text( "Total is "+tot, 10,10 );
  float average;
  //--  average=  float( tot ) / float(many);
  average=  float( tot ) / float(many);
  text( "Average is "+average, 10,20 );

  // Display the biggest number.
  int big;
  big=  biggest( a, many );
  text( "The biggest value is "+big, 10,30  );

  //// Show array (BEFORE).
  x=10;
  y=50;
  show( a, many );  
  
  

  //// Show array (BEFORE).
  x=150;
  y=50;
  show( a, many );  

  
}

//// Return INDEX OF biggest # in array.
int whereBig( int a[], int m ) {
  int k=0;

  for (int i=1; i<m; i++) {
    if( a[i] > a[k] )  {
      k=  i;
    }
  }

  return k;
}


//// Return biggest # in array.
int biggest( int a[], int m ) {
  int big;

  big=  a[0];
  for (int i=1; i<m; i++) {
    if( a[i] > big )  {
      big=  a[i];
    }
  }

  return big;
}



//// METHODS ////
// Return the sum total.
int sum( int[] a, int m ) {
  int tot=0;
  for (int i=0; i<m; i++) {
    tot += a[i];
  }
  return tot;
}

// Display the array.
void show(int a[], int m ) {
  for (int i=0; i<m; i++) {
    text( a[i], x, y );
    y += 15;
  }
}  


void keyPressed() {
 if (key == 's') srt( a, many );
 if (key == 'r') ran( a, many );
} 

// Fill array with random #s.
void ran( int[] a, int m ) {
  for (int i=0; i<m; i++) {
    a[i]=  int(  random( -500, 4999 ) );
  }
}  

void srt( int a[], int many ) {
 
  int m=  many;
  int j;
  int k;        // Index of biggest #
 
  for (m=many; m>1; m=m-1 ) { 
    k=  whereBig( a, m );
    j=  m-1;
    swap( a, j, k );
  }

}
void swap( int a[], int j, int k ) {
  int tmp;
  tmp=  a[k];
  a[k]=  a[j];
  a[j]=  tmp;
}
