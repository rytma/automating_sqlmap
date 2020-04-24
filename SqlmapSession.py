#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import requests

class SqlmapStatus(object):
    _status = None
    _retcode = -1

class SqlmapLogItem(object):
    _message = ''
    _level = ''
    _time = ''

class SqlmapSession(object):
    _host = None
    _port = 8775  # default

    def __init__(self, host, port=8775):
        self._host = host
        self._port = port
    
    def get(self, page):
        '''Execute an HTTP Get request and return a string result.
           `page` string link of the task.'''
        try:
            url = f'http://{self._host}:{self._port}{page}'
            r = requests.get(url)
            return r.json()
        except Exception as ex:
            print(ex)
            return None

    def post(self, page, data):
        '''Execute an HTTP Post request and return a string
        `page` string link of the task.
        `data` post params.'''
        try:
            url = f'http://{self._host}:{self._port}{page}'
            r = requests.post(url, json=data)
            return r.json()
        except Exception as ex:
            print(ex)
            return None