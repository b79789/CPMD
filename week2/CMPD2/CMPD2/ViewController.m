//
//  ViewController.m
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController
@synthesize userName,userPass,logInButton,createButton,mySwitch;

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.hidesBackButton = YES;
    self.navigationController.navigationBar.topItem.title = @"CMPD";

    
    BOOL test = [[NSUserDefaults standardUserDefaults] boolForKey:@"SyncAtStartup"];

    if (test == true) {
        mySwitch.on=true;
    }else{
        mySwitch.on=false;
    }
    
    if (mySwitch.on) {
        NSString *username = [[NSUserDefaults standardUserDefaults] objectForKey:@"username"];
        userName.text=username;
    }else{
        userName.text=@"";
    }
    
    }

- (IBAction)btnSwitched:(id)sender {
    if(self.mySwitch.on){
        [[NSUserDefaults standardUserDefaults] setBool:true forKey:@"SyncAtStartup"];
    }else{
        [[NSUserDefaults standardUserDefaults] setBool:false forKey:@"SyncAtStartup"];
    }
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:true];

    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(IBAction)onClickSignUp:(id)sender{
    

}


-(IBAction)onClickLogIn:(id)sender{
    
     if (userName.text && userPass.text.length == 0) {
         UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                        message: @"Do not leave fields blank."
                                                       delegate: self
                                              cancelButtonTitle:@"Cancel"
                                              otherButtonTitles:@"OK",nil];
         [alert show];
     }else{
         [PFUser logInWithUsernameInBackground:userName.text password:userPass.text
                                         block:^(PFUser *user, NSError *error) {
                                             if (user) {
                                                 [[NSUserDefaults standardUserDefaults] setObject:userName.text forKey:@"username"];
                                                 [[NSUserDefaults standardUserDefaults] synchronize];
                                                 NSLog(@"LOG: We are here");
                                                 // Do stuff after successful login.
                                                 //UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MainStoryboard" bundle:nil];
                                                 DetailViewController *detailViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"DetailViewController"];
                                                 [detailViewController setModalPresentationStyle:UIModalPresentationFullScreen];
                                                 UINavigationController *addViewControl = [[UINavigationController alloc] initWithRootViewController:detailViewController];
                                                 [self presentViewController:addViewControl animated:YES completion:nil];
                                                 
                                                 
                                             } else {
                                                 // The login failed. Check error to see why.
                                                 UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                                                                message:[error.userInfo valueForKey:@"error"]
                                                                                               delegate: self
                                                                                      cancelButtonTitle:@"Cancel"
                                                                                      otherButtonTitles:@"OK",nil];
                                                 [alert show];
                                             }
                                         }];
     }
    
}




@end
