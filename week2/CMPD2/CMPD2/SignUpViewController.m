//
//  SignUpViewController.m
//  CMPD2
//
//  Created by Brian Stacks on 5/7/15.
//  Copyright (c) 2015 Brian Stacks. All rights reserved.
//

#import "SignUpViewController.h"

@interface SignUpViewController ()

@end

@implementation SignUpViewController
@synthesize userPass,userEmail,userName;

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationController.title=@"CMPD";
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)createClick:(id)sender{
    if (userName.text && userPass.text.length&&userEmail.text == 0) {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                       message: @"Do not leave fields blank."
                                                      delegate: self
                                             cancelButtonTitle:@"Cancel"
                                             otherButtonTitles:@"OK",nil];
        [alert show];
        }else{
    
    PFUser *user = [PFUser user];
    user.username = userName.text;
    user.password = userPass.text;
    user.email = userEmail.text;
    [user signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (!error) {
            // Hooray! Let them use the app now.
            // Do stuff after successful login.
            PFObject *privateNote = [PFObject objectWithClassName:@"UserObjects"];
            privateNote[@"favColor"] = @"Enter your favorite color and phone number";
            privateNote[@"phoneNum"] = @0000000000;
            privateNote.ACL = [PFACL ACLWithUser:[PFUser currentUser]];
            [privateNote saveInBackground];
            DetailViewController *detailViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"DetailViewController"];
            [detailViewController setModalPresentationStyle:UIModalPresentationFullScreen];
            UINavigationController *addViewControl = [[UINavigationController alloc] initWithRootViewController:detailViewController];
            [self presentViewController:addViewControl animated:YES completion:nil];

        } else {
            NSString *errorString = [error userInfo][@"error"];
            UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                           message: errorString
                                                          delegate: self
                                                 cancelButtonTitle:nil
                                                 otherButtonTitles:@"OK",nil];
            [alert show];
            
            // Show the errorString somewhere and let the user try again.
        }
    }];
    }

}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    
    // Pass the selected object to the new view controller.
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
