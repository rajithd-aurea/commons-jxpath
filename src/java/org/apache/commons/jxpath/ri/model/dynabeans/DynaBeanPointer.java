/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//jxpath/src/java/org/apache/commons/jxpath/ri/model/dynabeans/DynaBeanPointer.java,v 1.1 2002/10/20 03:44:18 dmitri Exp $
 * $Revision: 1.1 $
 * $Date: 2002/10/20 03:44:18 $
 *
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 1999-2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 2001, Plotnix, Inc,
 * <http://www.plotnix.com/>.
 * For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package org.apache.commons.jxpath.ri.model.dynabeans;

import java.beans.PropertyDescriptor;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.jxpath.ri.model.beans.PropertyOwnerPointer;
import org.apache.commons.jxpath.ri.model.beans.PropertyPointer;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.util.ValueUtils;


/**
 * A Pointer that points to a DynaBean.
 *
 * @author Dmitri Plotnikov
 * @version $Revision: 1.1 $ $Date: 2002/10/20 03:44:18 $
 */
public class DynaBeanPointer extends PropertyOwnerPointer {
    private QName name;
    private DynaBean dynaBean;

    public DynaBeanPointer(QName name, DynaBean dynaBean, Locale locale)
    {
        super(null, locale);
        this.name = name;
        this.dynaBean = dynaBean;
    }

    /**
     * @param name is the name given to the first node
     */
    public DynaBeanPointer(NodePointer parent, QName name, DynaBean dynaBean) {
        super(parent);
        this.name = name;
        this.dynaBean = dynaBean;
    }

    public PropertyPointer getPropertyPointer(){
        return new DynaBeanPropertyPointer(this, dynaBean);
    }

    public QName getName(){
        return name;
    }

    /**
     * Returns the bean itself
     */
    public Object getBaseValue(){
        return dynaBean;
    }

    public Object getNode(){
        return dynaBean;
    }

    public boolean isCollection(){
        return false;
    }

    /**
     * Returns 1.
     */
    public int getLength(){
        return 1;
    }

    public boolean isLeaf() {
        return false;
    }

    public int hashCode(){
        return name == null ? 0 : name.hashCode();
    }

    public boolean equals(Object object){
        if (object == this){
            return true;
        }

        if (!(object instanceof DynaBeanPointer)){
            return false;
        }

        DynaBeanPointer other = (DynaBeanPointer)object;
        if (parent != other.parent){
            if (parent == null || !parent.equals(other.parent)){
                return false;
            }
        }

        if ((name == null && other.name != null) ||
                (name != null && !name.equals(other.name))){
            return false;
        }

        int i_this = (index == WHOLE_COLLECTION ? 0 : index);
        int i_other = (other.index == WHOLE_COLLECTION ? 0 : other.index);
        if (i_this != i_other){
            return false;
        }

        return dynaBean == other.dynaBean;
    }

    /**
     * If there's a parent - parent's path, otherwise "/".
     */
    public String asPath(){
        if (parent != null){
            return super.asPath();
        }
        return "/";
    }
}