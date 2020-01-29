package com.plter.notepad.ui
{
	import com.plter.notepad.data.NotesExporter;
	import com.plter.notepad.data.NotesImporter;
	
	import flash.desktop.NativeApplication;
	import flash.display.NativeMenu;
	import flash.display.NativeMenuItem;
	import flash.events.Event;
	
	import mx.core.FlexGlobals;

	public class MenuBuilder
	{
		
		
		private static var __rootWindowMenu:NativeMenu=null;
		private static var __appMenu:NativeMenu=null;
		private static var __fileMenu:NativeMenu=null;
		
		private static var __aboutItem:NativeMenuItem=null;
		private static var __closeItem:NativeMenuItem=null;
		private static var __quitItem:NativeMenuItem=null;
		
		private static var __importItem:NativeMenuItem=null;
		private static var __exportItem:NativeMenuItem=null;
		private static var __exportAsTextItem:NativeMenuItem=null;
		
		
		public static function getMenu():NativeMenu{
			if (__rootWindowMenu==null) 
			{
				__rootWindowMenu=new NativeMenu;
				
				__appMenu=new NativeMenu;
				__rootWindowMenu.addSubmenu(__appMenu,"记事本");
				__aboutItem=new NativeMenuItem("关于");
				__aboutItem.addEventListener(Event.SELECT,itemSelectHandler);
				__appMenu.addItem(__aboutItem);
				__appMenu.addItem(new NativeMenuItem("",true));
				__closeItem=new NativeMenuItem("关闭");
				__closeItem.addEventListener(Event.SELECT,itemSelectHandler);
				__closeItem.keyEquivalent="w";
				__appMenu.addItem(__closeItem);
				__quitItem=new NativeMenuItem("退出");
				__quitItem.addEventListener(Event.SELECT,itemSelectHandler);
				__quitItem.keyEquivalent="q";
				__appMenu.addItem(__quitItem);
				
				
				__fileMenu=new NativeMenu;
				__rootWindowMenu.addSubmenu(__fileMenu,"文件");
				__importItem=new NativeMenuItem("导入");
				__importItem.addEventListener(Event.SELECT,itemSelectHandler);
				__importItem.keyEquivalent="i";
				__fileMenu.addItem(__importItem);
				__exportItem=new NativeMenuItem("导出");
				__exportItem.addEventListener(Event.SELECT,itemSelectHandler);
				__exportItem.keyEquivalent="e";
				__fileMenu.addItem(__exportItem);
				__fileMenu.addItem(new NativeMenuItem("",true));
				__exportAsTextItem=new NativeMenuItem("导出为文本");
				__exportAsTextItem.addEventListener(Event.SELECT,itemSelectHandler);
				__exportAsTextItem.keyEquivalent="t";
				__fileMenu.addItem(__exportAsTextItem);
			}
			
			return __rootWindowMenu;
			
		}
		
		protected static function itemSelectHandler(event:Event):void
		{
			switch(event.target)
			{
				case __aboutItem:
					AboutWindow.show();
					break;
				case __closeItem:
				case __quitItem:
				{
					NativeApplication.nativeApplication.exit();
					break;
				}
				case __exportItem:
				case __exportAllNotesItem:
					NotesExporter.browseAndExportNotes();
					break;
				case __exportAsTextItem:
					NotesExporter.browseAndExportNotesText();
					break;
				case __importAllNotesItem:
				case __importItem:
					NotesImporter.browseAndImport();
					break;
				case __viewSelectedItem:
					FlexGlobals.topLevelApplication.appMainContainer.viewSelectedNote();
					break;
				case __deleteSelectedItem:
					FlexGlobals.topLevelApplication.appMainContainer.deleteSelectedNote();
					break;
				case __deleteAllItem:
					FlexGlobals.topLevelApplication.appMainContainer.deleteAllNotes();
					break;
				default:
				{
					break;
				}
			}
		}
		
		
		
		private static var __notesListContextRootMenu:NativeMenu=null;
		private static var __viewSelectedItem:NativeMenuItem=null;
		private static var __deleteSelectedItem:NativeMenuItem=null;
		private static var __importAllNotesItem:NativeMenuItem=null;
		private static var __exportAllNotesItem:NativeMenuItem=null;
		private static var __deleteAllItem:NativeMenuItem=null;
		
		public static function getNotesListContextMenu():NativeMenu{
			if (__notesListContextRootMenu==null)
			{
				__notesListContextRootMenu=new NativeMenu;
				
				__viewSelectedItem=new NativeMenuItem("查看选中");
				__viewSelectedItem.addEventListener(Event.SELECT,itemSelectHandler);
				__notesListContextRootMenu.addItem(__viewSelectedItem);
				__deleteSelectedItem=new NativeMenuItem("删除选中");
				__deleteSelectedItem.addEventListener(Event.SELECT,itemSelectHandler);
				__notesListContextRootMenu.addItem(__deleteSelectedItem);
				__importAllNotesItem=new NativeMenuItem("导入");
				__importAllNotesItem.addEventListener(Event.SELECT,itemSelectHandler);
				__notesListContextRootMenu.addItem(__importAllNotesItem);
				__exportAllNotesItem=new NativeMenuItem("导出");
				__exportAllNotesItem.addEventListener(Event.SELECT,itemSelectHandler);
				__notesListContextRootMenu.addItem(__exportAllNotesItem);
				__deleteAllItem=new NativeMenuItem("删除全部");
				__deleteAllItem.addEventListener(Event.SELECT,itemSelectHandler);
				__notesListContextRootMenu.addItem(__deleteAllItem);
			}
			
			return __notesListContextRootMenu;
		}

		public static function get viewSelectedItem():NativeMenuItem
		{
			return __viewSelectedItem;
		}

		public static function get deleteSelectedItem():NativeMenuItem
		{
			return __deleteSelectedItem;
		}


	}
}