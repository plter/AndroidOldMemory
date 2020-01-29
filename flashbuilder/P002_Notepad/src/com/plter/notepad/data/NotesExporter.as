package com.plter.notepad.data
{
	import com.plter.notepad.utils.TimeTool;
	
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.utils.ByteArray;

	public class NotesExporter
	{
		public function NotesExporter()
		{
		}
		
		
		public static function browseAndExportNotes():void{
			var f:File=new File();
			var bytes:ByteArray=new ByteArray;
			bytes.writeUTFBytes(JSON.stringify({backupDate:TimeTool.getCurrentTime(),notes:DataManager.getInstance().notesObject}));
			f.save(bytes,TimeTool.getCurrentTime("","","")+".notes");
		}
		
		
		public static function browseAndExportNotesText():void{
			var f:File=new File();
			var bytes:ByteArray=new ByteArray;
			
			var str:String="";
			for each (var o:Object in DataManager.getInstance().notesObject) 
			{
				str+="标题："+o.title+"\n"+o.modified_date+"\n"+o.content+"\n--------------------------------------------------------\n\n";
			}
			bytes.writeUTFBytes(str);
			
			f.save(bytes,TimeTool.getCurrentTime("","","")+".txt");
		}
	}
}