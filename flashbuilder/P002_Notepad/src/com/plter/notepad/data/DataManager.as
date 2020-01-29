package com.plter.notepad.data
{
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	
	public class DataManager
	{
		public function DataManager()
		{
		}
		
		private static var __instance:DataManager=null;
		
		public static function getInstance():DataManager{
			if (__instance==null)
			{
				__instance=new DataManager;
			}
			return __instance;
		}
		
		
		private var _dataFile:File=null;
		
		public function getDataFile():File{
			if (_dataFile==null) 
			{
				_dataFile=File.applicationStorageDirectory.resolvePath("notepad.obj");
			}
			return _dataFile;
		}
		
		
		public var notesObject:Array=null;
		
		/**
		 * 取得日志数据对象
		 */
		public function readNotesObject():Array{
			if (getDataFile().exists) 
			{
				var fs:FileStream = new FileStream();
				fs.open(getDataFile(),FileMode.READ);
				notesObject=fs.readObject();
				fs.close();
			}
			
			if (notesObject==null) 
			{
				notesObject=new Array;
			}
			return notesObject;
		}
		
		/**
		 * 保存日志数据对象
		 */
		public function saveNotesObject(obj:Array=null):void{
			if (obj==null) 
			{
				obj=notesObject;
			}
			
			var fs:FileStream=new FileStream;
			fs.open(getDataFile(),FileMode.WRITE);
			fs.writeObject(obj);
			fs.close();
		}
	}
}