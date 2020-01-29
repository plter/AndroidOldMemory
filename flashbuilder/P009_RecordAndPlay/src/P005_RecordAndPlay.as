package
{
	import flash.display.Sprite;
	import flash.errors.EOFError;
	import flash.events.Event;
	import flash.events.SampleDataEvent;
	import flash.events.TimerEvent;
	import flash.media.Microphone;
	import flash.media.Sound;
	import flash.media.SoundChannel;
	import flash.system.Capabilities;
	import flash.text.TextField;
	import flash.utils.ByteArray;
	import flash.utils.Timer;
	
	
	[SWF(width="400",height="300")]
	public class P005_RecordAndPlay extends Sprite
	{
		
		private var mic:Microphone;
		private var alertTxt:TextField;
		private var statusTxt:TextField;
		private const DELAY_LENGTH:int = 10000;
		private var soundBytes:ByteArray = new ByteArray();
		private var isPlaying:Boolean = false;
		private var timer:Timer = new Timer(1000,1);
		private var soundChannel:SoundChannel;
		private var sound:Sound;
		
		public function P005_RecordAndPlay()
		{
			// constructor code
			
			//init
			alertTxt = new TextField;
			alertTxt.width=300;
			alertTxt.height=30;
			alertTxt.textColor=0xFF0000;
			alertTxt.mouseEnabled=false;
			addChild(alertTxt);
			
			statusTxt = new TextField;
			statusTxt.width=300;
			statusTxt.height=30;
			statusTxt.border=true;
			statusTxt.y=50;
			addChild(statusTxt);
			
			var versions:Array = Capabilities.version.split(" ")[1].split(",");
			if (int(versions[0]) < 10 || int(versions[1]) < 1)
			{
				alertTxt.text = "你的FlashPlayer版低，本程序要求10.1以上版本";
				return;
			}
			
			mic = Microphone.getMicrophone();
			if (mic == null)
			{
				alertTxt.text = "找不到音频设备";
				return;
			}
			
			mic.setSilenceLevel(0, DELAY_LENGTH);
			mic.rate = 44;
			mic.setSilenceLevel(35);
			mic.addEventListener(SampleDataEvent.SAMPLE_DATA, mic_sampleDataHandler);
			
			timer.addEventListener(TimerEvent.TIMER,timerHandler);
			
			statusTxt.text = "等待音频输入";
		}
		
		private function mic_sampleDataHandler(event:SampleDataEvent):void
		{
			
			if (! isPlaying)
			{
				while (event.data.bytesAvailable)
				{
					var sample:Number = event.data.readFloat();
					soundBytes.writeFloat(sample);
				}
				
				timer.reset();
				timer.start();
				
				statusTxt.text = "正在录音";
			}
		}
		
		private function timerHandler(event:TimerEvent):void
		{
			
			soundBytes.position = 0;
			
			if (sound != null)
			{
				sound.removeEventListener(SampleDataEvent.SAMPLE_DATA,sound_sampleDataHandler);
			}
			sound = new Sound  ;
			sound.addEventListener(SampleDataEvent.SAMPLE_DATA,sound_sampleDataHandler);
			soundChannel = sound.play();
			soundChannel.addEventListener(Event.SOUND_COMPLETE,soundChannel_soundCompleteHandler);
			isPlaying = true;
			
			statusTxt.text = "正在播放";
		}
		
		private function sound_sampleDataHandler(event:SampleDataEvent):void
		{
			
			try
			{
				
				for (var i:int = 0; i < 4096 && soundBytes.bytesAvailable > 0; i++)
				{
					var sample:Number = soundBytes.readFloat();
					event.data.writeFloat(sample);
					event.data.writeFloat(sample);
					
					soundBytes.position +=  4;
				}
			}
			catch (e:EOFError)
			{
				trace(e.getStackTrace());
			}
		}
		
		private function soundChannel_soundCompleteHandler(event:Event):void
		{
			soundChannel.removeEventListener(Event.SOUND_COMPLETE,soundChannel_soundCompleteHandler);
			isPlaying = false;
			soundBytes.length = 0;
			
			statusTxt.text = "等待音频输入";
		}
		
	}
	
}