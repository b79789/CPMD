//
//  EnterDataViewController.h
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>
#import "DetailViewController.h"

@interface EnterDataViewController : UIViewController
{
    
}
@property (strong, nonatomic) IBOutlet UITextField *userColor;
@property (strong, nonatomic) IBOutlet UITextField *usrPhone;

@property (strong, nonatomic) IBOutlet UIButton *saveButton;

@property (strong, nonatomic) IBOutlet UIButton *deleteButton;

-(IBAction)enterDataClick:(id)sender;

@end
