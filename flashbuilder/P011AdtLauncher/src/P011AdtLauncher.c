#define MacOSX
//#define Windows

#include <stdio.h>
#include <stdlib.h>
#include <strings.h>

#ifdef Windows
#include <windows.h>
#endif



int main(int argc,char ** argv) {

	char cmdline[1000]="";

	if (argc<2) {
		exit(1000);
	}

	//gen cmd line
	int i=0;
	for (i = 1; i < argc; ++i) {
		strcat(cmdline,"\"");
		strcat(cmdline,argv[i]);
		strcat(cmdline,"\" ");
	}

	printf("%s\n",cmdline);

	//call adt command
#ifdef MacOSX
	int status = system(cmdline);
	exit(WEXITSTATUS(status));
#endif
#ifdef Windows

	PROCESS_INFORMATION pi;
	STARTUPINFO si = {sizeof(si)};
	DWORD dwExitCode;
	BOOL ret = CreateProcess(NULL, cmdline, NULL, NULL, FALSE, 0, NULL, NULL, &si, &pi);
	if (ret) {
		CloseHandle(pi.hThread);
		//wating for child process exit
		WaitForSingleObject(pi.hProcess, INFINITE);
		//get child process exit code
		GetExitCodeProcess(pi.hProcess, &dwExitCode);
		//close child process
		CloseHandle(pi.hProcess);
		exit(dwExitCode);
	}else{
		exit(1001);
	}
#endif
	return 0;
}
