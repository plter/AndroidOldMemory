//
//  AppDelegate.m
//  XTools
//
//  Created by 陈 少佳 on 12-3-28.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "AppDelegate.h"

@implementation AppDelegate

@synthesize window = _window;

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification
{
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(windowCloseHandler) name:NSWindowWillCloseNotification object:self.window];
}


-(void)windowCloseHandler{
   exit(0);
}



-(void)resetLaunchpadHandler:(id)sender{
    system("sqlite3 ~/Library/\"Application Support\"/Dock/*.db 'DELETE FROM apps;' && killall Dock");
    system("rm -r ~/Library/\"Application Support\"/Dock/*.db && killall Dock");
}


-(void)showAllFilesHandler:(id)sender{
    system("defaults write com.apple.finder AppleShowAllFiles -bool true");
    system("killall Finder");
}

-(void)hideFilesHandler:(id)sender{
    system("defaults write com.apple.finder AppleShowAllFiles -bool false");
    system("killall Finder");
}

-(void)saveScreenAsGif:(id)sender{
    system("defaults write com.apple.screencapture type gif");
}

-(void)saveScreenAsJpg:(id)sender{
    system("defaults write com.apple.screencapture type jpg");
}

-(void)saveScreenAsPng:(id)sender{
    system("defaults write com.apple.screencapture type png");
}

@end
