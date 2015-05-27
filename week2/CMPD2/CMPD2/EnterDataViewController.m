//
//  EnterDataViewController.m
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import "EnterDataViewController.h"
#import "Reachability.h"

@interface EnterDataViewController ()

@end

@implementation EnterDataViewController
{
    Reachability *internetReachableFoo;
}
@synthesize userColor,usrPhone;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)enterDataClick:(id)sender{
    [self testInternetConnection];
    BOOL isOnline = [[NSUserDefaults standardUserDefaults] boolForKey:@"isOnline"];
    if (isOnline == TRUE) {
        

    [[PFUser currentUser]fetchInBackground];
    NSMutableArray *myArray = [[NSMutableArray alloc] initWithObjects:@"Red", @"red", @"Blue", @"blue", @"green", @"Green", @"white",@"White", @"black", @"Black",@"brown", @"Brown", @"gray",@"Gray", @"orange", @"Orange",@"pink", @"Pink",@"purple", @"Purple",@"yellow", @"Yellow", nil];
    BOOL isTheObjectThere = [myArray containsObject: userColor.text];
    
    if (userColor.text == 0 || isTheObjectThere==false) {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                       message: @"Please enter a valid color."
                                                      delegate: self
                                             cancelButtonTitle:@"Cancel"
                                             otherButtonTitles:@"OK",nil];
        [alert show];
    }else if (usrPhone.text.length == 0 || usrPhone.text.length < 7) {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                       message: @"Please enter a valid phone number."
                                                      delegate: self
                                             cancelButtonTitle:@"Cancel"
                                             otherButtonTitles:@"OK",nil];
        [alert show];
    }else{
    NSNumberFormatter *f = [[NSNumberFormatter alloc] init];
    f.numberStyle = NSNumberFormatterDecimalStyle;
    NSNumber *myNumber = [f numberFromString:usrPhone.text];
    PFQuery *query = [PFQuery queryWithClassName:@"UserObjects"];
    [query getFirstObjectInBackgroundWithBlock:^(PFObject *pfObject,NSError *nserror){
        pfObject[@"favColor"] = userColor.text;
        pfObject[@"phoneNum"] = myNumber;
        
        pfObject.ACL = [PFACL ACLWithUser:[PFUser currentUser]];
        [pfObject saveInBackground];
    }];
    
    DetailViewController *detailViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"DetailViewController"];
    [detailViewController setModalPresentationStyle:UIModalPresentationFullScreen];
        UINavigationController *addViewControl = [[UINavigationController alloc] initWithRootViewController:detailViewController];
        [self presentViewController:addViewControl animated:YES completion:nil];

    }

    }else{
        NSNumberFormatter *f = [[NSNumberFormatter alloc] init];
        f.numberStyle = NSNumberFormatterDecimalStyle;
        NSNumber *myNumber = [f numberFromString:usrPhone.text];
        PFObject *query = [PFObject objectWithClassName:@"UserObjects"];
        query[@"favColor"] = userColor.text;
        query[@"phoneNum"] = myNumber;
        query.ACL = [PFACL ACLWithUser:[PFUser currentUser]];
        [query saveEventually];
        [[NSUserDefaults standardUserDefaults] setObject:userColor.text forKey:@"userColorOffline"];
        [[NSUserDefaults standardUserDefaults] setObject:myNumber forKey:@"userNumberOffline"];
        [[NSUserDefaults standardUserDefaults] synchronize];
        DetailViewController *detailViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"DetailViewController"];
        [detailViewController setModalPresentationStyle:UIModalPresentationFullScreen];
        UINavigationController *addViewControl = [[UINavigationController alloc] initWithRootViewController:detailViewController];
        [self presentViewController:addViewControl animated:YES completion:nil];
    }
}

-(IBAction)deleteUserClick:(id)sender{
    PFUser *currentUser = [PFUser currentUser];
    [currentUser deleteInBackground];
    ViewController *VController = [self.storyboard instantiateViewControllerWithIdentifier:@"ViewController"];
    [VController setModalPresentationStyle:UIModalPresentationFullScreen];
    UINavigationController *addViewControl = [[UINavigationController alloc] initWithRootViewController:VController];
    [self presentViewController:addViewControl animated:YES completion:nil];


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
