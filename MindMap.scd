// MindMap Class for generating simple and easy to use mindmaps in supercollider
//
(
{
	var win = Window.new("sketch", 500@350, false).background_(Color.white);
	var view = UserView.new(win, 500@350).clearOnRefresh_(false);
	var titleFont = Font("Droid Sans",  24, true);
	var mainFont = Font("Droid Sans", 16, true);

	// create a MindMap object
	var mindMap = MindMap.new();

	// create MindMap objects such as text boxes and arrows
	var textBoxA = MindMapTextBox.new("Hallo", Rect(30, 30, 70, 40), titleFont);
	var textBoxB = MindMapTextBox.new("Welt", Rect(230, 300, 50, 15), mainFont).drawBox_(false);
	var textBoxC = MindMapTextBox.new("how's", Rect(350, 270, 50, 17), mainFont).drawBox_(false);
	var textBoxD = MindMapTextBox.new("everything?", Rect(300, 50, 150, 30), titleFont);

	var a0 = MindMapArrow.new(textBoxA, textBoxB);
	var a1 = MindMapArrow.new(textBoxB, textBoxC);
	var a2 = MindMapArrow.new(textBoxC, textBoxD);

	mindMap.add(textBoxA.drawFunc);
	mindMap.add(textBoxB.drawFunc);
	mindMap.add(textBoxC.drawFunc);
	mindMap.add(textBoxD.drawFunc);
	mindMap.add(a0.drawFunc);
	mindMap.add(a1.drawFunc);
	mindMap.add(a2.drawFunc);

	mindMap.draw(view);

	win.refresh;

	win.front;
}.value;
)

