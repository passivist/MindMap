/*
* MindMap is a class for drawing MindMaps with SuperCollider.
* It works by generating drawFuncs for use with the Pen object.
*
* Dependencies:
* wslib for Arrow.draw
*/
MindMap {
	var <>funcArray;

	*new {
		^super.new.init;
	}

	init {
		this.funcArray = Array.new(128);
	}
	// add function to queue
	add { arg func;
		this.funcArray.add(func);
	}

	// build drawFunction and draw
	draw { arg view;
		var func = {};

		// stick all the drawFuncs together
		this.funcArray.do{ |inFunc| func = inFunc <> func};

		// add Pen.stroke to draw in the end
		func = { Pen.stroke; } <> func;
		view.drawFunc = func;
	}

}

MindMapArrow {
	var <>from, <>to, <>puffer;

	*new { arg from, to;
		^super.newCopyArgs(from, to).init;
	}

	init {
		this.puffer = 20;
	}

	drawFunc {
		var func, pFrom, pTo,  pointsFrom, pointsTo, bigFrom, bigTo, vectors, distFromTo, distToFrom;

		from	= this.from.rect;
		to 	= this.to.rect;

		// calculate the best connection points for the arrow
		// -> still needs some work
		// works in the moment by finding the smallest distance between the points of two
		// rects which are resised versions of the rects of the from and to objects

		bigFrom	= from.resizeBy(puffer, puffer).center_(from.center);
		bigTo		= to.resizeBy(puffer, puffer).center_(to.center);

		pointsFrom = [
			bigFrom.leftTop,		bigFrom.topMid,		bigFrom.rightTop, 	bigFrom.rightMid,
			bigFrom.rightBottom,	bigFrom.bottomMid,	bigFrom.leftBottom,	bigFrom.bottomMid
		];
		pointsTo = [
			bigTo.leftTop,			bigTo.topMid,			bigTo.rightTop, 		bigTo.rightMid,
			bigTo.rightBottom,	bigTo.bottomMid,		bigTo.leftBottom,		bigTo.bottomMid
		];

		distFromTo = pointsFrom.collect{ |point| point.dist(to.center) };
		distToFrom = pointsTo.collect{ |point| point.dist(from.center) };

		pFrom = pointsFrom[distFromTo.order.first];
		pTo	= pointsTo[distToFrom.order.first];

		// build the function
		func = {
			Pen.arrow(pFrom, pTo);
		};

		^func
	}
}

MindMapTextBox {
	var <>string, <>rect, <>font, <>drawBox;

	*new { arg string, rect, font;
		^super.newCopyArgs(string, rect, font).init;
	}

	init {
		this.drawBox = true;
	}

	// generate a function for drawing later
	drawFunc {
		var func, string, rect, font;

		string	= this.string;
		rect		= this.rect;
		font		= this.font;

		// build the function
		func = {
			Pen.stringCenteredIn(string, rect, font);
			if(drawBox){ Pen.addRect( rect.resizeBy(10, 10).center_(rect.center) ); };
		};

		^func;
	}
}