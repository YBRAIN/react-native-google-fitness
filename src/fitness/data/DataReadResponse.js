/**
 * Copyright (C) YBrain Inc - All Rights Reserved. 2017.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by sangwoo on 2018. 4. 26..
 */

export type Field_ = {
    field: string,
    value: string,
}

export type DataPoint_ = {
    type: string,
    source: string,
    start: string,
    end: string,
    fields: Field_[],
};

export type DataSet_ = DataPoint_[];

export type Bucket_ = {
    start: number,
    end: number
    type: number,
    activity: string;
    dataSets: DataSet_[];
}

export  type DataReadResponse = {
    status: string,
    dataSets: DataSet_[],
    buckets: Bucket_[],
}