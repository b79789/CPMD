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

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    PFObject *testObject = [PFObject objectWithClassName:@"IOS_Object"];
    testObject[@"name"] = @"Brian Stacks";
    [testObject saveInBackground];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end