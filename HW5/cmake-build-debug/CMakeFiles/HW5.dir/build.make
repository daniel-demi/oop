# CMAKE generated file: DO NOT EDIT!
# Generated by "NMake Makefiles" Generator, CMake Version 3.19

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


.SUFFIXES: .hpux_make_needs_suffix_list


# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE
NULL=nul
!ENDIF
SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\JetBrains\CLion 2021.1\bin\cmake\win\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\JetBrains\CLion 2021.1\bin\cmake\win\bin\cmake.exe" -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles\HW5.dir\depend.make

# Include the progress variables for this target.
include CMakeFiles\HW5.dir\progress.make

# Include the compile flags for this target's objects.
include CMakeFiles\HW5.dir\flags.make

CMakeFiles\HW5.dir\main.cpp.obj: CMakeFiles\HW5.dir\flags.make
CMakeFiles\HW5.dir\main.cpp.obj: ..\main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/HW5.dir/main.cpp.obj"
	C:\PROGRA~2\MICROS~3\2019\PROFES~1\VC\Tools\MSVC\1428~1.299\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoCMakeFiles\HW5.dir\main.cpp.obj /FdCMakeFiles\HW5.dir\ /FS -c "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\main.cpp"
<<

CMakeFiles\HW5.dir\main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/HW5.dir/main.cpp.i"
	C:\PROGRA~2\MICROS~3\2019\PROFES~1\VC\Tools\MSVC\1428~1.299\bin\Hostx86\x86\cl.exe > CMakeFiles\HW5.dir\main.cpp.i @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\main.cpp"
<<

CMakeFiles\HW5.dir\main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/HW5.dir/main.cpp.s"
	C:\PROGRA~2\MICROS~3\2019\PROFES~1\VC\Tools\MSVC\1428~1.299\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoNUL /FAs /FaCMakeFiles\HW5.dir\main.cpp.s /c "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\main.cpp"
<<

CMakeFiles\HW5.dir\Stream.cpp.obj: CMakeFiles\HW5.dir\flags.make
CMakeFiles\HW5.dir\Stream.cpp.obj: ..\Stream.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/HW5.dir/Stream.cpp.obj"
	C:\PROGRA~2\MICROS~3\2019\PROFES~1\VC\Tools\MSVC\1428~1.299\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoCMakeFiles\HW5.dir\Stream.cpp.obj /FdCMakeFiles\HW5.dir\ /FS -c "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\Stream.cpp"
<<

CMakeFiles\HW5.dir\Stream.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/HW5.dir/Stream.cpp.i"
	C:\PROGRA~2\MICROS~3\2019\PROFES~1\VC\Tools\MSVC\1428~1.299\bin\Hostx86\x86\cl.exe > CMakeFiles\HW5.dir\Stream.cpp.i @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\Stream.cpp"
<<

CMakeFiles\HW5.dir\Stream.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/HW5.dir/Stream.cpp.s"
	C:\PROGRA~2\MICROS~3\2019\PROFES~1\VC\Tools\MSVC\1428~1.299\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoNUL /FAs /FaCMakeFiles\HW5.dir\Stream.cpp.s /c "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\Stream.cpp"
<<

# Object files for target HW5
HW5_OBJECTS = \
"CMakeFiles\HW5.dir\main.cpp.obj" \
"CMakeFiles\HW5.dir\Stream.cpp.obj"

# External object files for target HW5
HW5_EXTERNAL_OBJECTS =

HW5.exe: CMakeFiles\HW5.dir\main.cpp.obj
HW5.exe: CMakeFiles\HW5.dir\Stream.cpp.obj
HW5.exe: CMakeFiles\HW5.dir\build.make
HW5.exe: CMakeFiles\HW5.dir\objects1.rsp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_3) "Linking CXX executable HW5.exe"
	"C:\Program Files\JetBrains\CLion 2021.1\bin\cmake\win\bin\cmake.exe" -E vs_link_exe --intdir=CMakeFiles\HW5.dir --rc=C:\PROGRA~2\WI3CF2~1\10\bin\100190~1.0\x86\rc.exe --mt=C:\PROGRA~2\WI3CF2~1\10\bin\100190~1.0\x86\mt.exe --manifests -- C:\PROGRA~2\MICROS~3\2019\PROFES~1\VC\Tools\MSVC\1428~1.299\bin\Hostx86\x86\link.exe /nologo @CMakeFiles\HW5.dir\objects1.rsp @<<
 /out:HW5.exe /implib:HW5.lib /pdb:"C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\cmake-build-debug\HW5.pdb" /version:0.0 /machine:X86 /debug /INCREMENTAL /subsystem:console  kernel32.lib user32.lib gdi32.lib winspool.lib shell32.lib ole32.lib oleaut32.lib uuid.lib comdlg32.lib advapi32.lib 
<<

# Rule to build all files generated by this target.
CMakeFiles\HW5.dir\build: HW5.exe

.PHONY : CMakeFiles\HW5.dir\build

CMakeFiles\HW5.dir\clean:
	$(CMAKE_COMMAND) -P CMakeFiles\HW5.dir\cmake_clean.cmake
.PHONY : CMakeFiles\HW5.dir\clean

CMakeFiles\HW5.dir\depend:
	$(CMAKE_COMMAND) -E cmake_depends "NMake Makefiles" "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5" "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5" "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\cmake-build-debug" "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\cmake-build-debug" "C:\Users\ddemikho\OneDrive - Technion\technoin-homework\OOP\oop\HW5\cmake-build-debug\CMakeFiles\HW5.dir\DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles\HW5.dir\depend
