#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from SqlmapSession import SqlmapStatus, SqlmapLogItem

''' SqlmapManager manage sqlmap apis
ref: https://github.com/735tesla/Unofficial-SQLMap-API-Doc/wiki
'''
class SqlmapManager(object):
    _session = None
    def __init__(self, session):
        if session is None:
            raise Exception('session cannot not be empty')
        self._session = session
    
    def new_task(self):
        json_obj = self._session.get('/task/new')
        return json_obj['taskid']
    
    def delete_task(self, taskid):
        '''Delete task with the given `taskid` and return json result'''
        json_obj = self._session.get('/task/' + taskid + '/delete')
        return json_obj['success']
    
    def get_options(self, taskid):
        json_obj = self._session.get('/option/' + taskid + '/list')
        return json_obj['options']
    
    def start_task(self, taskid, options):
        '''Start a task with `taskid` as id and `options` as options'''
        json_obj = self._session.post('/scan/' + taskid + '/start', options)
        return json_obj['success']
    
    def get_scan_status(self, taskid):
        json_obj = self._session.get('/scan/' + taskid + '/status')
        stat = SqlmapStatus()
        stat._status = json_obj['status']
        if json_obj['returncode']:
            stat._retcode = int(json_obj['returncode'])
        return stat
    
    def get_logs(self, taskid):
        json_obj = self._session.get('/scan/' + taskid + '/log')
        log_items = []
        for x in  json_obj['log']:
            item = SqlmapLogItem()
            item._message = x['message']
            item._level = x['level']
            item._time = x['time']
            log_items.append(item)
        return log_items