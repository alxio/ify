package pl.poznan.put.cs.ify.api.group;

public interface YCommand {
	int SEND_DATA = 0;
	int GET_DATA = -1;
	int GET_USER_LIST = -2;
	int GET_USER_STATE = -3;
}