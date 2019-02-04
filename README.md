# DriveTrain

[![Build Status](https://travis-ci.com/FRC1257/2019-Robot.svg?branch=DriveTrain)](https://travis-ci.com/FRC1257/2019-Robot)

Code focusing on our DriveTrain.

## WPILib VendorLib Notes

The vendors that write third party software for FRC such as CTRE, REV Robotics, and kauai Labs publish their libraries through the vendorlib system WPILib came up with this season.  Below are URLs to periodically check and compare against our current version.

### Functional

[CTRE Vendorlib JSON](http://devsite.ctr-electronics.com/maven/release/com/ctre/phoenix/Phoenix-latest.json)

[Kauai Labs NavX JSON](https://www.kauailabs.com/dist/frc/2019/navx_frc.json)

[REV Robotics Vendorlib JSON](http://www.revrobotics.com/content/sw/max/sdk/REVRobotics.json)


### Testing Procedure

1. Push from `DriveTrainTest` first. Test basic drive code, like going forward and back with followers.
2. Push from `DriveTrain`. Make sure washout is off. Test basic drive code.
3. Tune PID. Remember to use `SmartDashboard` to help.
4. Enable washout and tune that. You want the v-t graph to look like a trapezoid.
5. Make sure gyro works.
