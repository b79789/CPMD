//
//  DetailViewController.m
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import "DetailViewController.h"

@interface DetailViewController ()

@end

@implementation DetailViewController
@synthesize userNameLabel,userPhoneLabel,userColorLabel;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    PFUser *currentUser = [PFUser currentUser];
    
    
    if (currentUser) {
        // do stuff with the user
        userNameLabel.text= currentUser.username;
        PFQuery *query = [PFQuery queryWithClassName:@"UserObjects"];
        [query getFirstObjectInBackgroundWithBlock:^ (PFObject *userObject, NSError *error){
            // Do something with the returned PFObject in the gameScore variable.
            NSLog(@"%@", userObject);
            NSString *strFromInt = [NSString stringWithFormat:@"%@",[userObject objectForKey:@"phoneNum"]];
            userPhoneLabel.text = strFromInt;
            userColorLabel.text=[userObject objectForKey:@"favColor"];
            
        }];
        
    } else {
        // show the signup or login screen
    }
    
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(IBAction)onLogOut:(id)sender{
    PFUser *currentUser = [PFUser currentUser];
    [PFUser logOutInBackground];
    NSLog(@"here %@",currentUser);
    ViewController *VController = [self.storyboard instantiateViewControllerWithIdentifier:@"ViewController"];
    [VController setModalPresentationStyle:UIModalPresentationFullScreen];
    [self presentViewController:VController animated:YES completion:nil];
    
}
/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
