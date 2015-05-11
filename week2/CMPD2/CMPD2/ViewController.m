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
@synthesize userName,userPass,logInButton,createButton;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
}


- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:true];

    PFUser *currentUser = [PFUser currentUser];
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    [[PFUser currentUser] fetch];
    if (currentUser == nil) {
        // show the signup screen here....
        
    } else {
        
        
        DetailViewController *detailViewController = [storyboard instantiateViewControllerWithIdentifier:@"DetailViewController"];
        [detailViewController setModalPresentationStyle:UIModalPresentationFullScreen];
        [self presentViewController:detailViewController animated:YES completion:nil];
        
    }
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
                                                 NSLog(@"LOG: We are here");
                                                 // Do stuff after successful login.
                                                 //UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MainStoryboard" bundle:nil];
                                                 DetailViewController *detailViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"DetailViewController"];
                                                 [detailViewController setModalPresentationStyle:UIModalPresentationFullScreen];
                                                 [self presentViewController:detailViewController animated:YES completion:nil];
                                                 
                                                 
                                             } else {
                                                 // The login failed. Check error to see why.
                                                 UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                                                                message:[error localizedDescription]
                                                                                               delegate: self
                                                                                      cancelButtonTitle:@"Cancel"
                                                                                      otherButtonTitles:@"OK",nil];
                                                 [alert show];
                                             }
                                         }];
     }
    
}
@end
