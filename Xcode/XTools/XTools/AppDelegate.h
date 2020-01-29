//
//  AppDelegate.h
//  XTools
//
//  Created by 陈 少佳 on 12-3-28.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface AppDelegate : NSObject <NSApplicationDelegate>{
    IBOutlet NSTextField * label;
}

@property (assign) IBOutlet NSWindow *window;


-(IBAction)resetLaunchpadHandler:(id)sender;
-(IBAction)showAllFilesHandler:(id)sender;
-(IBAction)hideFilesHandler:(id)sender;
-(IBAction)saveScreenAsJpg:(id)sender;
-(IBAction)saveScreenAsPng:(id)sender;
-(IBAction)saveScreenAsGif:(id)sender;

@end
