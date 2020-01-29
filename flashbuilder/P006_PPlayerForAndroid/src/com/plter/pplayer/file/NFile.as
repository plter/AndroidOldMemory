package com.plter.pplayer.file
{
	import flash.filesystem.File;
	
	public class NFile
	{
		public function NFile(f:File,isParent:Boolean=false)
		{
			this._file=f;
			this._isParent=isParent;
		}
		
		
		private var _isParent:Boolean=false;

		/**
		 * 指示是否为当前所操作的目录的父目录 
		 */
		public function get isParent():Boolean
		{
			return _isParent;
		}
		
		
		private var _file:File=null;

		public function get file():File
		{
			return _file;
		}
		
		
		public function get url():String{
			return file.url;
		}
		
		
		public function get name():String{
			return file.name;
		}
		
		
		public function get nativePath():String{
			return file.nativePath;
		}
		
		
		public function get isDirectory():Boolean{
			return file.isDirectory;
		}
		
		public function get extension():String{
			return file.extension;
		}
		
		
		public function get exists():Boolean{
			return file.exists;
		}
	}
}