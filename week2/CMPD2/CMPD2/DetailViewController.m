//
//  DetailViewController.m
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import "DetailViewController.h"
# import "Reachability.h"
@interface DetailViewController ()
{
    Reachability *internetReachableFoo;
}
@end

@implementation DetailViewController
@synthesize userNameLabel,userPhoneLabel,userColorLabel;

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.hidesBackButton = YES;
    // Do any additional setup after loading the view.
    PFUser *currentUser = [PFUser currentUser];
    BOOL isOnline = [[NSUserDefaults standardUserDefaults] boolForKey:@"isOnline"];
    if (isOnline == TRUE) {
    [[PFUser currentUser] fetchInBackground];
    if (currentUser) {
        // do stuff with the user
        userNameLabel.text= currentUser.username;
        PFQuery *query = [PFQuery queryWithClassName:@"UserObjects"];
        [query getFirstObjectInBackgroundWithBlock:^ (PFObject *userObject, NSError *error){
            // Do something with the returned PFObject in the gameScore variable.
            //NSLog(@"%@", userObject);
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
    }else{
        PFUser *currentUser = [PFUser currentUser];
        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
        userNameLabel.text= currentUser.username;
        userPhoneLabel.text =[defaults stringForKey:@"userNumberOffline"];
        userColorLabel.text=[defaults stringForKey:@"userColorOffline"];
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
    //PFUser *currentUser = [PFUser currentUser];
    [PFUser logOutInBackground];
    //NSLog(@"here %@",currentUser);
    ViewController *VController = [self.storyboard instantiateViewControllerWithIdentifier:@"ViewController"];
    [VController setModalPresentationStyle:UIModalPresentationFullScreen];
    UINavigationController *addViewControl = [[UINavigationController alloc] initWithRootViewController:VController];
    [self presentViewController:addViewControl animated:YES completion:nil];
    
}

-(void)refreshData:(id)sender{
    [self testInternetConnection];
    PFUser *currentUser = [PFUser currentUser];
    BOOL isOnline = [[NSUserDefaults standardUserDefaults] boolForKey:@"isOnline"];
    if (isOnline == TRUE) {
        [[PFUser currentUser] fetchInBackground];
        if (currentUser) {
            // do stuff with the user
            userNameLabel.text= currentUser.username;
            PFQuery *query = [PFQuery queryWithClassName:@"UserObjects"];
            [query getFirstObjectInBackgroundWithBlock:^ (PFObject *userObject, NSError *error){
                // Do something with the returned PFObject in the gameScore variable.
                //NSLog(@"%@", userObject);
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
    }else{
        NSLog(@"Testing ^^^^^^^^^ %@",@"Offline");
            PFUser *currentUser = [PFUser currentUser];
            NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
            userNameLabel.text= currentUser.username;
            userPhoneLabel.text =[defaults stringForKey:@"userNumberOffline"];
            userColorLabel.text=[defaults stringForKey:@"userColorOffline"];
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



@end
