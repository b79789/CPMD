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
    PFQuery *query = [PFQuery queryWithClassName:@"UserObjects"];
    [query getFirstObjectInBackgroundWithBlock:^ (PFObject *userObject, NSError *error){
        // Do something with the returned PFObject in the gameScore variable.
        NSLog(@"%@", userObject);
        int value 
        userObject[@"favColor"]=userColor.text;
        userObject[@"phoneNum"]=usrPhone.text;
        [userObject saveInBackground];
        
    }];
    
    DetailViewController *detailViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"DetailViewController"];
    [detailViewController setModalPresentationStyle:UIModalPresentationFullScreen];
    [self presentViewController:detailViewController animated:YES completion:nil];



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
