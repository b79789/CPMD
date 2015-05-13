//
//  ViewController.h
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>
#import "DetailViewController.h"
#import "SignUpViewController.h"


@interface ViewController : UIViewController
{
    
}
@property (strong, nonatomic) IBOutlet UITextField *userName;
@property (strong, nonatomic) IBOutlet UITextField *userPass;
@property (strong, nonatomic) IBOutlet UIButton *logInButton;
@property (strong, nonatomic) IBOutlet UIButton *createButton;
@property (strong, nonatomic) IBOutlet UISwitch *mySwitch;

-(IBAction)onClickSignUp:(id)sender;
-(IBAction)onClickLogIn:(id)sender;
- (IBAction)btnSwitched:(id)sender;

@end

