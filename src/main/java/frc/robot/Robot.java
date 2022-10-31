// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;


public class Robot extends TimedRobot {

  CANSparkMax m_intakeUpDown = new CANSparkMax(RobotMap.INTAKEUPDOWNCANID, MotorType.kBrushless);
  TalonSRX m_intakeRun = new TalonSRX(RobotMap.INTAKERUNCANID);

  TalonFX m_indexer = new TalonFX(RobotMap.INDEXERCANID);
  TalonFX m_shooter = new TalonFX(RobotMap.SHOOTCANID);

  CANSparkMax m_hood = new CANSparkMax(RobotMap.HOODCANID, MotorType.kBrushless);

  CANSparkMax m_r1 = new CANSparkMax(RobotMap.R1CANID, MotorType.kBrushless);
  CANSparkMax m_r2 = new CANSparkMax(RobotMap.R2CANID, MotorType.kBrushless);
  CANSparkMax m_r3 = new CANSparkMax(RobotMap.R3CANID, MotorType.kBrushless);
  CANSparkMax m_l1 = new CANSparkMax(RobotMap.L1CANID, MotorType.kBrushless);
  CANSparkMax m_l2 = new CANSparkMax(RobotMap.L2CANID, MotorType.kBrushless);
  CANSparkMax m_l3 = new CANSparkMax(RobotMap.L3CANID, MotorType.kBrushless);

  MotorControllerGroup leftDrive = new MotorControllerGroup(m_l1, m_l2, m_l3);
  MotorControllerGroup rightDrive = new MotorControllerGroup(m_r1, m_r2, m_r3);

  DifferentialDrive dDrive = new DifferentialDrive(leftDrive, rightDrive);

  XboxController driver = new XboxController(0);
  XboxController operator = new XboxController(1);

  Timer timer = new Timer();

  @Override
  public void robotInit() {
    m_l1.setOpenLoopRampRate(RobotMap.RAMPVAL);
    m_l2.setOpenLoopRampRate(RobotMap.RAMPVAL);
    m_l3.setOpenLoopRampRate(RobotMap.RAMPVAL);
    m_r1.setOpenLoopRampRate(RobotMap.RAMPVAL);
    m_r2.setOpenLoopRampRate(RobotMap.RAMPVAL);
    m_r3.setOpenLoopRampRate(RobotMap.RAMPVAL);

    m_intakeUpDown.setOpenLoopRampRate(RobotMap.RAMPVAL);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    speedButtons();
    intakeUpDown();
    intakeRun();

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  public void speedButtons(){
    //slow button for xbox controller
       if(driver.getRawButton(3)){
        dDrive.arcadeDrive(-driver.getRawAxis(0) * 0.2, -driver.getRawAxis(3) * 0.2);
        if(driver.getRawAxis(2) > 0){
        dDrive.arcadeDrive(-driver.getRawAxis(0) * 0.2, driver.getRawAxis(2) * 0.2);
      }
     }
   
     //fast button for xbox controller
     else if(driver.getRawButton(1)){
      dDrive.arcadeDrive(-driver.getRawAxis(0), -driver.getRawAxis(3));
        if(driver.getRawAxis(2) > 0){
          dDrive.arcadeDrive(-driver.getRawAxis(0), driver.getRawAxis(2));
      }
     }

   
     //default condition for neither buttons active
     else if(!driver.getRawButton(3) || !driver.getRawButton(1)){
      dDrive.arcadeDrive(-driver.getRawAxis(0) * 0.8, -driver.getRawAxis(3) * 0.8);
      if(driver.getRawAxis(2) > 0){
        dDrive.arcadeDrive(-driver.getRawAxis(0) * 0.8, driver.getRawAxis(2) * 0.8);
        } 
        }  
      }

  public void intakeUpDown(){
    if(operator.getRawButton(RobotMap.INTAKEUPBUTTON)){
      m_intakeUpDown.set(0.4);
    }
    if(operator.getRawButton(RobotMap.INTAKEDOWNBUTTON)){
      m_intakeUpDown.set(-0.4);
    }
    else{
      m_intakeUpDown.set(0);
    }
  }

  public void intakeRun(){
    if(operator.getRawButton(RobotMap.INTAKERUNBUTTON)){
      m_intakeRun.set(ControlMode.PercentOutput, 0.7);
    }
    if(operator.getRawButton(RobotMap.INTAKESPITBUTTON)){
      m_intakeRun.set(ControlMode.PercentOutput, -0.7);
    }
    else{
      m_intakeRun.set(ControlMode.PercentOutput, 0);
    }
  }

  public void index(){
    if(operator.getRawButton(RobotMap.INDEXERFORBUTTON)){
      m_indexer.set(ControlMode.PercentOutput, 0.4);
    }
    else{
      m_indexer.set(ControlMode.PercentOutput, 0);
    }
  }

  public void shoot(){
    if(operator.getRawButton(RobotMap.SHOOTBUTTON)){
      m_shooter.set(ControlMode.PercentOutput, 0.7);
    }
  }

  public void hoodUpDown(){
    m_hood.set(0.1*operator.getRawAxis(RobotMap.HOODAXIS));
    //maybe soft limits?
  }
}
