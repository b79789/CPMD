//
//  ViewController.m
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import "ViewController.h"
#import "Reachability.h"

@interface ViewController ()
{
    Reachability *internetReachableFoo;
}
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
    
    [userName addTarget:self action:@selector(textFieldShouldEndEditing:) forControlEvents:UIControlEventEditingChanged];
    [userPass addTarget:self action:@selector(textFieldShouldEndEditing2:) forControlEvents:UIControlEventEditingChanged];
}

- (IBAction)btnSwitched:(id)sender {
    if(self.mySwitch.on){
        [[NSUserDefaults standardUserDefaults] setBool:true forKey:@"SyncAtStartup"];
    }else{
        [[NSUserDefaults standardUserDefaults] setBool:false forKey:@"SyncAtStartup"];
    }
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    
    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(IBAction)onClickLogIn:(id)sender{
    [self testInternetConnection];
     BOOL isOnline = [[NSUserDefaults standardUserDefaults] boolForKey:@"isOnline"];
    if (isOnline == TRUE) {
        
    
    
     if (userName.text && userPass.text.length == 0 ) {
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
    }else{
        
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                       message: @"No network connection, neeeded for login"
                                                      delegate: self
                                             cancelButtonTitle:@"Cancel"
                                             otherButtonTitles:@"OK",nil];
        [alert show];
        
    }
    
}


// Checks if we have an internet connection or not
- (void)testInternetConnection
{
    
    // check if we've got network connectivity
    Reachability *myNetwork = [Reachability reachabilityWithHostname:@"google.com"];
    NetworkStatus myStatus = [myNetwork currentReachabilityStatus];
    
    switch (myStatus) {
        case NotReachable:
            NSLog(@"There's no internet connection at all. Display error message now.");
            [[NSUserDefaults standardUserDefaults] setBool:false forKey:@"isOnline"];
            break;
            
        case ReachableViaWWAN:
            NSLog(@"We have a 3G connection");
            [[NSUserDefaults standardUserDefaults] setBool:true forKey:@"isOnline"];
            break;
            
        case ReachableViaWiFi:
            NSLog(@"We have WiFi.");
            [[NSUserDefaults standardUserDefaults] setBool:true forKey:@"isOnline"];
            break;
            
        default:
            break;
    }
    
}



- (BOOL)textFieldShouldEndEditing:(UITextField *)textField {
    if (textField == userName) {
        NSString *regEx = @"^[a-zA-Z0-9]*$";
        NSRange r = [textField.text rangeOfString:regEx options:NSRegularExpressionSearch];
        if (r.location == NSNotFound) {
            UIAlertView *av = [[UIAlertView alloc] initWithTitle:@"Entry Error"
                                                          message:@"Entered username must use alphanumeric format"
                                                         delegate:self
                                                cancelButtonTitle:@"OK"
                                                otherButtonTitles:nil];
            [av show];
            NSString *newString = [userName.text substringToIndex:[userName.text length]-1];
            
            userName.text=newString;
            return NO;
        }
    }
    return YES;
}

- (BOOL)textFieldShouldEndEditing2:(UITextField *)textField {
    if (textField == userPass) {
        NSString *regEx = @"^[a-zA-Z0-9]*$";
        NSRange r = [textField.text rangeOfString:regEx options:NSRegularExpressionSearch];
        if (r.location == NSNotFound) {
            UIAlertView *av = [[UIAlertView alloc] initWithTitle:@"Entry Error"
                                                         message:@"Entered password must use alphanumeric format"
                                                        delegate:self
                                               cancelButtonTitle:@"OK"
                                               otherButtonTitles:nil];
            [av show];
            NSString *newString = [userPass.text substringToIndex:[userPass.text length]-1];
            
            userPass.text=newString;
            return NO;
        }
    }
    return YES;
}
@end
