//
//  SignUpViewController.h
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>
#include "DetailViewController.h"

@interface SignUpViewController : UIViewController
{
    
}
@property (strong, nonatomic) IBOutlet UITextField *userName;
@property (strong, nonatomic) IBOutlet UITextField *userEmail;

@property (strong, nonatomic) IBOutlet UITextField *userPass;

@property (strong, nonatomic) IBOutlet UIButton *signUpButton;

-(IBAction)createClick:(id)sender;

@end
