//
//  EnterDataViewController.m
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import "EnterDataViewController.h"

@interface EnterDataViewController ()

@end

@implementation EnterDataViewController
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
    [[PFUser currentUser]fetchInBackground];
    if (userColor.text && usrPhone.text.intValue ==0) {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                       message: @"Do not leave fields blank."
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

}

-(IBAction)deleteUserClick:(id)sender{
    PFUser *currentUser = [PFUser currentUser];
    [currentUser deleteInBackground];
    ViewController *VController = [self.storyboard instantiateViewControllerWithIdentifier:@"ViewController"];
    [VController setModalPresentationStyle:UIModalPresentationFullScreen];
    UINavigationController *addViewControl = [[UINavigationController alloc] initWithRootViewController:VController];
    [self presentViewController:addViewControl animated:YES completion:nil];


}

@end
