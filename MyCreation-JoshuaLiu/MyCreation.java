/*
Joshua Liu
Mr. Afsari-Nejad
October 25, 2019
MyCreation creates a winter scene with many dynamic animated objects
*/
import java.awt.*;
import java.lang.*;
import hsa.Console;

public class MyCreation
{
    Console c;           // The output console
    MyCreation ()       //Constructor
    {
	c = new Console ("My Creation: Winter");
    }


    void initUtil ()    //Init buffer
    {
	DrawUtil.loadBuffer ();
    }


    void background ()      //Draw background
    {
	Background bg = new Background (c);
    }


    void sled ()        //Start sled thread
    {
	Sled sled = new Sled (c);
	sled.start ();
    }


    void cabinSmoke ()  //Start cabin smoke thread
    {
	AnimateCabinSmoke animSmoke = new AnimateCabinSmoke (c);
	animSmoke.start ();
    }


    void bird ()    //Start bird thread
    {
	AnimateBird animBird = new AnimateBird (c);
	animBird.start ();
    }


    void snow ()    //Start snow thread
    {
	AnimateSnow animSnow = new AnimateSnow (c);
	animSnow.start ();
    }


    void star ()    //Start star thread
    {
	AnimateStar animStar = new AnimateStar (c);
	animStar.start ();
    }


    void figureSkater ()    //Start skater thread
    {
	Thread skater = new Thread (new Skater (c));
	skater.start ();
    }


    public static void main (String[] args) //Main
    {
	MyCreation c = new MyCreation ();
	c.initUtil ();
	c.background ();
	c.sled ();
	c.cabinSmoke ();
	c.bird ();
	c.snow ();
	c.star ();
	c.figureSkater ();
    } // main method
} // MyCreation class
