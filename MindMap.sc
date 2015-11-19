/*
* MindMap ist ein Container für drawFuncs die dann in eine Andere DrawFunc übergeben werden können
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

		this.funcArray.do{ |inFunc| func = inFunc <> func};

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


		func = {
			/*
			Pen.addRect(bigFrom);
			Pen.addRect(bigTo);
			*/
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

		func = {
			Pen.stringCenteredIn(string, rect, font);
			if(drawBox){ Pen.addRect( rect.resizeBy(10, 10).center_(rect.center) ); };
		};


		^func;
	}
}