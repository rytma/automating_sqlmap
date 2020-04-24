#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import time
import logging, coloredlogs

from SqlmapSession import SqlmapLogItem, SqlmapSession, SqlmapStatus
from SqlmapManager import SqlmapManager


# create logger
logger = logging.getLogger('SQLMap')
# install handler and set level
coloredlogs.install(level='DEBUG', logger=logger) # passing logger object to evade libraries logs


def main():
    session = SqlmapSession('127.0.0.1', 8775)
    mgr = SqlmapManager(session)

    # new task
    taskid = mgr.new_task()
    logger.debug(f'Created task: {taskid}')

    # get options
    options = mgr.get_options(taskid)
    options['url'] = 'http://localhost:8800/order?item=drill&name=&email&zip&city&submit='
    options['flashSession'] = True

    # start task
    mgr.start_task(taskid, options)
    stat = mgr.get_scan_status(taskid)

    while stat._status != 'terminated':
        time.sleep(5)
        stat = mgr.get_scan_status(taskid)
        logger.debug(f'Status {stat._status}')

    logs = mgr.get_logs(taskid)
    for log in logs:
        if log._level == 'WARNING':
            logger.warning(log._message)
        elif log._level == 'INFO':
            logger.info(log._message)
        elif log._level == 'CRITICAL':
            logger.critical(log._message)
        elif log._level == 'DEBUG':
            logger.debug(log._message)
        else:
            logger.debug(log._level + ' - ' + log._message)

    logger.debug('Deleting task')
    if mgr.delete_task(taskid):
        logger.debug('Deleted successful')
    else:
        logger.error('Cannot delete id ' + taskid)

if __name__ == '__main__':
    main()