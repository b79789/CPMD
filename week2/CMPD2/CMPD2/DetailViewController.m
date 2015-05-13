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
    self.navigationItem.hidesBackButton = YES;
    // Do any additional setup after loading the view.
    PFUser *currentUser = [PFUser currentUser];
    [[PFUser currentUser] fetchInBackground];
    if (currentUser) {
        // do stuff with the user
        userNameLabel.text= currentUser.username;
        PFQuery *query = [PFQuery queryWithClassName:@"UserObjects"];
        [query getFirstObjectInBackgroundWithBlock:^ (PFObject *userObject, NSError *error){
            // Do something with the returned PFObject in the gameScore variable.
            NSLog(@"%@", userObject);
            NSString *strFromInt = [NSString stringWithFormat:@"%@",[userObject objectForKey:@"phoneNum"]];
            if (strFromInt == nil) {
                userPhoneLabel.text = @"Enter Phone Number";
            }else if ([[userObject objectForKey:@"favColor"] isEqual:@""]){
                userColorLabel.text=@"Enter your favorite color";
            }else{
                userPhoneLabel.text = strFromInt;
                userColorLabel.text=[userObject objectForKey:@"favColor"];
            }
            
            
        }];
    }
}

-(void)viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    PFUser *currentUser = [PFUser currentUser];
    [[PFUser currentUser] fetchInBackground];

    if (currentUser == nil) {
        // show the signup screen here....
        ViewController *VController = [self.storyboard instantiateViewControllerWithIdentifier:@"ViewController"];
        [VController setModalPresentationStyle:UIModalPresentationFullScreen];
        UINavigationController *addViewControl = [[UINavigationController alloc] initWithRootViewController:VController];
        [self presentViewController:addViewControl animated:YES completion:nil];
        
        
    } else {

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
    UINavigationController *addViewControl = [[UINavigationController alloc] initWithRootViewController:VController];
    [self presentViewController:addViewControl animated:YES completion:nil];
    
}




@end
