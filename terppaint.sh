#!/bin/bash
###########################################################################
# terppaint.sh -- run|compile|clean the TerpPaint program.
#
# Copyright (C) 2005 TerpPaint
#
# USAGE: terppaint.sh [run]
#        terppaint.sh compile|clean [file [file file ...]]
#
# TODO: Add option checking and a help output
###########################################################################

# Change the following variables to suite your needs. Comment javapath
# if you want to use the java in your PATH.
mainclass="TerpPaint"
binpath="bin"
srcpath="src/java"
#javapath="/opt/blackdown-jdk-1.4.2.01/bin"
#javacpath="/opt/blackdown-jdk-1.4.2.01/bin"

compile() {
  PATH=$PATH:$javacpath

  if [ ! which javac >& /dev/null ]; then
    echo "You must install javac to compile TerpPaint."\
         "If javac is installed, make sure to add javac to your PATH"\
         "or to the javacpath environment variable." \
	 "You may download javac from java.sun.com or blackdown.org.">&2
    exit 1
  fi

  if [ -z "$files" ]; then
    files="$mainclass.java"
  fi

  echo "compiling..."

  if ! [ -d $projpath/$binpath ]; then
    mkdir -p $projpath/$binpath
  fi

  /bin/cp -rf $projpath/$srcpath/{help,images} $binpath
  /bin/cp -f $projpath/$srcpath/{prefs,windowRegistry}.txt $binpath
  javac -nowarn -d $projpath/$binpath -sourcepath $projpath/$srcpath \
        $projpath/$srcpath/$files

  rc=$?

  if [ $rc == "0" ]; then
      echo "TerpPaint was successfully compiled. Please run ./terppaint.sh"\
     	   "to start TerpPaint"
  else
      echo "Please download the most recent release from"\
	   "terppaint.terpoffice.com"
  fi
}


run() {
  PATH=$PATH:$javapath

  if [ ! which java >& /dev/null ]; then
    echo "You must install java to run TerpPaint."\
  	 "If java is installed, make sure to add java to your PATH"\
	 "or to the javapath environment variable."\
	 "You may download java from java.sun.com or blackdown.org">&2
    exit 1
  fi

  cd $projpath/$binpath
  if [ `uname` == "Darwin" ]; then
    java -Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel $mainclass; 
  else
    java $mainclass;
  fi

  rc=$?
  cd $old_pw

  if [ $rc == "1" ]; then
    echo
    echo "Make sure you have run \`./terppaint compile\` first">&2
  fi
}


clean() {
  /bin/rm -f $projpath/$binpath/*.{class,txt}
  /bin/rm -rf $projpath/$binpath/{help,images}
}

# main()
prg=$0
while [ -h "$prg" ] ; do
  ls=`ls -ld "$prg"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    prg="$link"
  else
    prg="`dirname $prg`/$link"
  fi
done

old_pwd=`pwd`
projpath=`dirname $prg`
files=`expr "$*" : "$1 \(.*\)"`

if [ -n "$1" ]; then
  $1
else
  run
fi

exit $rc
