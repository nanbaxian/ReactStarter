/*
 * #%L
 * React Site Starter
 * %%
 * Copyright (C) 2009 - 2017 Broadleaf Commerce
 * %%
 * Broadleaf Commerce React Starter
 * 
 * Written in 2017 by Broadleaf Commerce info@broadleafcommerce.com
 * 
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide. This software is distributed without any warranty.
 * You should have received a copy of the CC0 Public Domain Dedication along with this software. If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 * 
 * Please Note - The scope of CC0 Public Domain Dedication extends to Broadleaf Commerce React Starter demo application alone. Linked libraries (including all Broadleaf Commerce Framework libraries) are subject to their respective licenses, including the requirements and restrictions specified therein.
 * #L%
 */
import React from 'react'
import classNames from 'classnames'
import Checkbox from 'material-kit/components/Checkbox'

const AccountField = ({
    addon, autoComplete = 'on', children, inputGroup = true, input, label, meta: { active, touched, error }, type
}) => {
    const formGroup = (
        <div className={classNames({
                'form-group label-floating': true,
                'is-empty': !input.value,
                'is-focused': active,
                'has-error': touched && error
            })}>
                {type !== 'checkbox' && (<label className='control-label'>{label}{touched && error && ` - ${error}`}</label>)}
                {(() => {
                    switch(type) {
                        case 'select':
                        return (
                            <select autoComplete={autoComplete} className='form-control' {...input}>
                                {children}
                            </select>
                        )
                        case 'checkbox':
                        return (
                            <Checkbox autoComplete={autoComplete} {...input}>
                                {label}
                            </Checkbox>
                        )
                        default:
                        return (
                            <input
                                autoComplete={autoComplete}
                                type={type}
                                className='form-control'
                                {...input}
                            />
                        )
                    }
                })()}
        </div>
    )

    return inputGroup ? (
        <div className='input-group'>

            {inputGroup && (
                <span className='input-group-addon'>
                    {addon ? (
                        <i className='material-icons'>{addon}</i>
                    ) : (
                        <i className='material-icons'  style={{ visibility: 'hidden' }}>person</i>
                    )}
                </span>
            )}

            {formGroup}
        </div>
    ) : formGroup
}

export default AccountField
