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
    [userEmail addTarget:self action:@selector(validateEmail:) forControlEvents:UIControlEventEditingDidEnd];
    [userName addTarget:self action:@selector(textFieldShouldEndEditing:) forControlEvents:UIControlEventEditingChanged];
    [userPass addTarget:self action:@selector(textFieldShouldEndEditing2:) forControlEvents:UIControlEventEditingChanged];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)createClick:(id)sender{
    if ([self validateEmail:userEmail]!=true) {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle: @"Error"
                                                       message: @"Use valid email format"
                                                      delegate: self
                                             cancelButtonTitle:@"Cancel"
                                             otherButtonTitles:@"OK",nil];
        [alert show];
    }else if (userName.text && userPass.text.length&&userEmail.text == 0) {
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


-(BOOL) validateEmail: (UITextField *) candidate {
    if (candidate == userEmail) {
        NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        NSRange r = [candidate.text rangeOfString:emailRegex options:NSRegularExpressionSearch];
        if (r.location == NSNotFound) {
            UIAlertView *av = [[UIAlertView alloc] initWithTitle:@"Entry Error"
                                                         message:@"Entered email must use proper email format ie: aa@bb.com"
                                                        delegate:self
                                               cancelButtonTitle:@"OK"
                                               otherButtonTitles:nil];
            [av show];

            
            return NO;
        }
    }
    return YES;
   
}

- (BOOL)textFieldShouldEndEditing:(UITextField *)textField {
    if (textField == userName) {
        NSString *regEx = @"^[a-zA-Z0-9]*$";
        NSRange r = [textField.text rangeOfString:regEx options:NSRegularExpressionSearch];
        if (r.location == NSNotFound) {
            UIAlertView *av = [[UIAlertView alloc] initWithTitle:@"Entry Error"
                                                         message:@"Entered username must use alphanumeric format"
                                                        delegate:self
                                               cancelButtonTitle:@"OK"
                                               otherButtonTitles:nil];
            [av show];
            NSString *newString = [userName.text substringToIndex:[userName.text length]-1];
            
            userName.text=newString;
            return NO;
        }
    }
    return YES;
}

- (BOOL)textFieldShouldEndEditing2:(UITextField *)textField {
    if (textField == userPass) {
        NSString *regEx = @"^[a-zA-Z0-9]*$";
        NSRange r = [textField.text rangeOfString:regEx options:NSRegularExpressionSearch];
        if (r.location == NSNotFound) {
            UIAlertView *av = [[UIAlertView alloc] initWithTitle:@"Entry Error"
                                                         message:@"Entered password must use alphanumeric format"
                                                        delegate:self
                                               cancelButtonTitle:@"OK"
                                               otherButtonTitles:nil];
            [av show];
            NSString *newString = [userPass.text substringToIndex:[userPass.text length]-1];
            
            userPass.text=newString;
            return NO;
        }
    }
    return YES;
}

@end
