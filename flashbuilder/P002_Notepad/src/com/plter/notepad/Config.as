package com.plter.notepad
{
	import flash.filesystem.File;

	public class Config
	{
		public function Config()
		{
		}
		
		public static const NOTES_TYPE:String="notes";
		
		
		public static function isNotesFile(f:File):Boolean{
			return f.extension.toLowerCase()==NOTES_TYPE;
		}
	}
}