package
{
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	
	import org.aswing.ASFont;
	import org.aswing.AsWingManager;
	import org.aswing.JButton;
	import org.aswing.JTextField;
	
	[SWF(backgroundColor="#FFFFFF",width="300",height="150")]
	public class P002_GenPasswd extends Sprite
	{
		
		private var genPasswdBtn:JButton;
		private var passwdTxt:JTextField;
		private static const CHARS:String="1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM!@#%&";
		
		public function P002_GenPasswd()
		{
			AsWingManager.initAsStandard(this);
			
			genPasswdBtn=new JButton("生成密码");
			genPasswdBtn.setFont(new ASFont(null,18,true));
			genPasswdBtn.setSizeWH(100,30);
			addChild(genPasswdBtn);
			genPasswdBtn.addEventListener(MouseEvent.CLICK,genPasswdBtn_clickHandler);
			
			passwdTxt=new JTextField();
			passwdTxt.setFont(new ASFont(null,18));
			passwdTxt.setSizeWH(200,30);
			passwdTxt.setLocationXY(0,35);
			addChild(passwdTxt);
		}
		
		protected function genPasswdBtn_clickHandler(event:MouseEvent):void
		{
			var str:String="";
			for (var j:int = 0; j < 14; j++) 
			{
				str+=CHARS.charAt(Math.floor(Math.random()*CHARS.length));
			}
			
			passwdTxt.setText(str);
		}
	}
}