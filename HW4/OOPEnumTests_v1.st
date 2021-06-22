'From Squeak5.3 of 3 March 2020 [latest update: #19431] on 11 June 2020 at 12:37:18 am'!TestCase subclass: #OOPEnumTests	instanceVariableNames: 'red yellow green check one two three error str'	classVariableNames: ''	poolDictionaries: ''	category: 'OOP4SampleTests'!!OOPEnumTests methodsFor: 'as yet unclassified' stamp: 'a 6/10/2020 16:20'!setUp
	"set up the test - create Enum class"
	
  	error:=[OOPEnum new] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error = 'You can''t create an instance of OOPEnum' ).

	(Smalltalk hasClassNamed: #OOPColor) ifTrue: [ Smalltalk removeClassNamed: #OOPColor. ].
	
	OOPEnum subclass: #OOPColor 
		values: {'RED'. 'YELLOW'. 'GREEN'.} 
		lazyInitialization: true 
		initialize: ('initialize', (String cr), '	^self')
		instanceVariableNames: '' 
		classVariableNames: '' 
		poolDictionaries: '' 
		category: 'OOP4'.
	
	"get the enum's values"
	red := OOPColor RED.
	yellow := OOPColor YELLOW.
	green := OOPColor GREEN.
	check := OOPColor RED.
	self assert:(red == check).
	check := OOPColor GREEN.
	self assert:(check == green). 
	check := OOPColor YELLOW.
	self assert:(check == yellow). 
	
	 Smalltalk removeClassNamed: #OOPColor. 
	 Smalltalk removeClassNamed: #OOPColor_GREEN__.
	 Smalltalk removeClassNamed: #OOPColor_RED__.
	 Smalltalk removeClassNamed: #OOPColor_YELLOW__.

"if it doesn't work, check that you dont add the variables to _OPPColor_GREEN__  etc."
	 OOPEnum subclass: #OOPColor 
		values: {'RED'. 'YELLOW'. 'GREEN'.} 
		lazyInitialization: true 
		initialize: ('initialize', (String cr), '	instvar1:= 1. instvar2:=2. Classvar1:=1. Classvar2:=2')
		instanceVariableNames: 'instvar1 instvar2' 
		classVariableNames: 'Classvar1 Classvar2' 
		poolDictionaries: '' 
		category: 'OOP4'.
	error:=[OOPColor new] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error = 'You can''t create an instance of OOPColor' ).

	error:=[OOPColor subclass: 'b' instanceVariableNames: '' 
			classVariableNames: '' poolDictionaries: ''
			category: ''] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error = 'You must specify b''s values' ).

		error:=[OOPEnum subclass: 'a' instanceVariableNames: '' 
			classVariableNames: '' poolDictionaries: ''
			category: ''] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error = 'You must specify a''s values' ).
	

error:=[OOPColor subclass: 'b' values: '' lazyInitialization: ''
			initialize: '' instanceVariableNames: '' 
			classVariableNames: '' poolDictionaries: ''
			category: ''] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error ='OOPColor is final!! It can''t be inherited' ).
	

	red := OOPColor RED.
	yellow := OOPColor YELLOW.
	green := OOPColor GREEN.
	check := OOPColor RED.
	self assert:(red == check).
	check := OOPColor GREEN.
	self assert:(check == green). 
	check := OOPColor YELLOW.
	self assert:(check == yellow). 


	OOPColor compile:'ChangeInst1:val',(String cr),' instvar1:=val'.
	OOPColor compile:'GetInst1',(String cr),' ^instvar1'.
	self assert:(red GetInst1 = 1).
	red ChangeInst1:2.
	self assert:(red GetInst1 = 2).

	OOPColor compile:'ChangeClass1:val',(String cr),' Classvar1:=val'.
	OOPColor compile:'GetClass1',(String cr),' ^Classvar1'.
	self assert:(red GetClass1 = 1).
	red ChangeClass1:2.
	self assert:(red GetClass1 = 2).

	"Add method to OOPCOLOR_RED__"
	(red class) compile: 'ChangeClass2:val',(String cr),' Classvar2:=val'.
	(red class) compile:'GetClass2',(String cr),' ^Classvar2'.

	"OOPColor should not understand the meesage"
	error:=[OOPColor ChangeClass2:1] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error ='OOPColor class>>ChangeClass2:' ).
	red ChangeClass2:2.
	self assert:(red GetClass2 = 2).





	"new class , withoutlazy"
	(Smalltalk hasClassNamed: #Numbers) ifTrue: [ Smalltalk removeClassNamed: #Numbers ].
	 OOPEnum subclass: #OOPColor2 
		values: {'RED'. 'YELLOW'. 'GREEN'.} 
		lazyInitialization: false 
		initialize: ('initialize', (String cr), '	instvar1:= 1. instvar2:=2. Classvar1:=1. Classvar2:=2')
		instanceVariableNames: 'instvar1 instvar2' 
		classVariableNames: 'Classvar1 Classvar2' 
		poolDictionaries: '' 
		category: 'OOP4'.
error:=[OOPColor2 new] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error = 'You can''t create an instance of OOPColor2' ).

	error:=[OOPColor2 subclass: 'b' instanceVariableNames: '' 
			classVariableNames: '' poolDictionaries: ''
			category: ''] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error = 'You must specify b''s values' ).

		error:=[OOPEnum subclass: 'a' instanceVariableNames: '' 
			classVariableNames: '' poolDictionaries: ''
			category: ''] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error = 'You must specify a''s values' ).
	

error:=[OOPColor2 subclass: 'b' values: '' lazyInitialization: ''
			initialize: '' instanceVariableNames: '' 
			classVariableNames: '' poolDictionaries: ''
			category: ''] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error ='OOPColor2 is final!! It can''t be inherited' ).
	

	red := OOPColor2 RED.
	yellow := OOPColor2 YELLOW.
	green := OOPColor2 GREEN.
	check := OOPColor2 RED.
	self assert:(red == check).
	check := OOPColor2 GREEN.
	self assert:(check == green). 
	check := OOPColor2 YELLOW.
	self assert:(check == yellow). 


	OOPColor2 compile:'ChangeInst1:val',(String cr),' instvar1:=val'.
	OOPColor2 compile:'GetInst1',(String cr),' ^instvar1'.
	self assert:(red GetInst1 = 1).
	red ChangeInst1:2.
	self assert:(red GetInst1 = 2).

	OOPColor2 compile:'ChangeClass1:val',(String cr),' Classvar1:=val'.
	OOPColor2 compile:'GetClass1',(String cr),' ^Classvar1'.
	self assert:(red GetClass1 = 1).
	red ChangeClass1:2.
	self assert:(red GetClass1 = 2).

	"Add method to OOPCOLOR2_RED__"
	(red class) compile: 'ChangeClass2:val',(String cr),' Classvar2:=val'.
	(red class) compile:'GetClass2',(String cr),' ^Classvar2'.

	"OOPColor2 should not understand the meesage"
	error:=[OOPColor2 ChangeClass2:1] on: Error do: [:ex | ex return: ex messageText].
	self assert: (error ='OOPColor2 class>>ChangeClass2:' ).
	red ChangeClass2:2.
	self assert:(red GetClass2 = 2).


	
		
	

	
! !!OOPEnumTests methodsFor: 'as yet unclassified' stamp: 'a 6/11/2020 00:21'!testSwitch
	"add switch-case functionality to the enum, and use it"
	OOPColor addSwitch.	error:=[OOPEnum addSwitch] on: Error do: [:ex | ex return: ex messageText].		self assert: (error = 'can''t add switch functionality to non-Enum class OOPEnum' ).		error:=[SmallInteger addSwitch] on: Error do: [:ex | ex return: ex messageText].	self assert: (error = 'can''t add switch functionality to non-Enum class SmallInteger' ).	
	red := OOPColor RED.	yellow := OOPColor YELLOW.	green := OOPColor GREEN.		str:=''.	"red options"
	red switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'].
	self assert:(str = 'red').
	yellow switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'].
	self assert:(str = 'yellow').	green switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'].	self assert:(str = 'green').			red switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] default: [str:='green'].	self assert:(str = 'red').	yellow switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] default: [str:='green'].	self assert:(str = 'yellow').	green switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] default: [str:='green'].	self assert:(str = 'green').		red switch_case_RED: [str:='red' ] case_GREEN: [str:='green'] default: [str:='yellow'].	self assert:(str = 'red').	yellow switch_case_RED: [str:='red' ] case_GREEN: [str:='green'] default: [str:='yellow'].	self assert:(str = 'yellow').	green switch_case_RED: [str:='red' ] case_GREEN: [str:='green'] default: [str:='yellow'].	self assert:(str = 'green').		red switch_case_RED: [str:='red' ] default: [str:='yellow_or_green'].	self assert:(str = 'red').	yellow switch_case_RED: [str:='red' ] default: [str:='yellow_or_green'].	self assert:(str = 'yellow_or_green').	green switch_case_RED: [str:='red' ] default: [str:='yellow_or_green'].	self assert:(str = 'yellow_or_green').		"Yellow options"	red switch_case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'] default: [str:='red'].	self assert:(str = 'red').	yellow switch_case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'] default: [str:='red'].	self assert:(str = 'yellow').	yellow switch_case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'] default: [str:='red'].	self assert:(str = 'yellow').	green switch_case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'] default: [str:='red'].	self assert:(str = 'green').		red switch_case_YELLOW: [str:='yellow'] default: [str:='red_or_green'].	self assert:(str = 'red_or_green').	yellow switch_case_YELLOW: [str:='yellow'] default: [str:='red_or_green'].	self assert:(str = 'yellow').	green switch_case_YELLOW: [str:='yellow'] default: [str:='red_or_green'].	self assert:(str = 'red_or_green').		"GREEN OPTIONS"	red switch_case_GREEN: [str:='green'] default: [str:='red_or_yellow'].	self assert:(str = 'red_or_yellow').	yellow switch_case_GREEN: [str:='green'] default: [str:='red_or_yellow'].	self assert:(str = 'red_or_yellow').	green switch_case_GREEN: [str:='green'] default: [str:='red_or_yellow'].	self assert:(str = 'green').			"WITHOUT LAZY-OOPCOLOR2 CHECK"	OOPColor2 addSwitch.	error:=[OOPEnum addSwitch] on: Error do: [:ex | ex return: ex messageText].		self assert: (error = 'can''t add switch functionality to non-Enum class OOPEnum' ).		error:=[SmallInteger addSwitch] on: Error do: [:ex | ex return: ex messageText].	self assert: (error = 'can''t add switch functionality to non-Enum class SmallInteger' ).		red := OOPColor2 RED.	yellow := OOPColor2 YELLOW.	green := OOPColor2 GREEN.		str:=''.	"red options"	red switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'].	self assert:(str = 'red').	yellow switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'].	self assert:(str = 'yellow').	green switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'].	self assert:(str = 'green').			red switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] default: [str:='green'].	self assert:(str = 'red').	yellow switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] default: [str:='green'].	self assert:(str = 'yellow').	green switch_case_RED: [str:='red' ] case_YELLOW: [str:='yellow'] default: [str:='green'].	self assert:(str = 'green').		red switch_case_RED: [str:='red' ] case_GREEN: [str:='green'] default: [str:='yellow'].	self assert:(str = 'red').	yellow switch_case_RED: [str:='red' ] case_GREEN: [str:='green'] default: [str:='yellow'].	self assert:(str = 'yellow').	green switch_case_RED: [str:='red' ] case_GREEN: [str:='green'] default: [str:='yellow'].	self assert:(str = 'green').		red switch_case_RED: [str:='red' ] default: [str:='yellow_or_green'].	self assert:(str = 'red').	yellow switch_case_RED: [str:='red' ] default: [str:='yellow_or_green'].	self assert:(str = 'yellow_or_green').	green switch_case_RED: [str:='red' ] default: [str:='yellow_or_green'].	self assert:(str = 'yellow_or_green').		"Yellow options"	red switch_case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'] default: [str:='red'].	self assert:(str = 'red').	yellow switch_case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'] default: [str:='red'].	self assert:(str = 'yellow').	yellow switch_case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'] default: [str:='red'].	self assert:(str = 'yellow').	green switch_case_YELLOW: [str:='yellow'] case_GREEN: [str:='green'] default: [str:='red'].	self assert:(str = 'green').		red switch_case_YELLOW: [str:='yellow'] default: [str:='red_or_green'].	self assert:(str = 'red_or_green').	yellow switch_case_YELLOW: [str:='yellow'] default: [str:='red_or_green'].	self assert:(str = 'yellow').	green switch_case_YELLOW: [str:='yellow'] default: [str:='red_or_green'].	self assert:(str = 'red_or_green').		"GREEN OPTIONS"	red switch_case_GREEN: [str:='green'] default: [str:='red_or_yellow'].	self assert:(str = 'red_or_yellow').	yellow switch_case_GREEN: [str:='green'] default: [str:='red_or_yellow'].	self assert:(str = 'red_or_yellow').	green switch_case_GREEN: [str:='green'] default: [str:='red_or_yellow'].	self assert:(str = 'green').						

! !