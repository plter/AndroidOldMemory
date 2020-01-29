package com.plter.notepad.data
{
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.net.FileFilter;
	
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
	
	public class NotesImporter
	{
		public function NotesImporter()
		{
		}
		
		
		
		private static var __fileTypes:Array=[new FileFilter("日记文件(*.notes)","*.notes")];
		
		/**
		 * 浏览关导入文件
		 */
		public static function browseAndImport():void{
			var f:File=new File;
			f.addEventListener(Event.CANCEL,fileHandler);
			f.addEventListener(Event.SELECT,fileHandler);
			f.browseForOpen("浏览日记文件",__fileTypes);
		}
		
		protected static function fileHandler(event:Event):void
		{
			var f:File=event.target as File;
			f.removeEventListener(Event.CANCEL,fileHandler);
			f.removeEventListener(Event.SELECT,fileHandler);
			
			if (event.type==Event.SELECT) 
			{
				importNotes(f);
			}
		}		
		
		public static function importNotes(f:File):void{
			try{
				var s:FileStream=new FileStream;
				s.open(f,FileMode.READ);
				var obj:Object = JSON.parse(s.readUTFBytes(s.bytesAvailable));
				s.close();
				
				DataManager.getInstance().notesObject=DataManager.getInstance().notesObject.concat(obj.notes);
				DataManager.getInstance().saveNotesObject();
				FlexGlobals.topLevelApplication.appMainContainer.refreshNotesList();
			}catch(e:Error){
				Alert.show("日记导入失败");
			}
		}
	}
}