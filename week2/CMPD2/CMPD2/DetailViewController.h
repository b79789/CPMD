//
//  DetailViewController.h
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>
#import "EnterDataViewController.h"
#import "ViewController.h"


@interface DetailViewController : UIViewController
{
    
}
@property (strong, nonatomic) IBOutlet UILabel *userNameLabel;
@property (strong, nonatomic) IBOutlet UILabel *userColorLabel;
@property (strong, nonatomic) IBOutlet UILabel *userPhoneLabel;
@property (strong, nonatomic) IBOutlet UIButton *editUserButton;
@property (strong, nonatomic) IBOutlet UIButton *userLogoutButton;

-(IBAction)onLogOut:(id)sender;
@end
