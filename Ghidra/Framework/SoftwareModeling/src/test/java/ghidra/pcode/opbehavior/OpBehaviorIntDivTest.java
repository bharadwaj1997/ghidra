/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.pcode.opbehavior;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import ghidra.pcode.utils.Utils;

public class OpBehaviorIntDivTest extends AbstractOpBehaviorTest {

	public OpBehaviorIntDivTest() {
		super();
	}

	@Test
    public void testEvaluateBinaryLong() {

		OpBehaviorIntDiv op = new OpBehaviorIntDiv();

		Assert.assertEquals(0, op.evaluateBinary(4, 4, 1, 0));// divide by zero

		Assert.assertEquals(13, op.evaluateBinary(4, 4, 65, 5));

		Assert.assertEquals(0, op.evaluateBinary(4, 4, 0, 1));
		Assert.assertEquals(0, op.evaluateBinary(4, 4, 0, 0xffffffffL));
		Assert.assertEquals(0xffffffffL, op.evaluateBinary(4, 4, 0xffffffffL, 1));
		Assert.assertEquals(0, op.evaluateBinary(4, 4, 1, 0xffffffffL));
		Assert.assertEquals(1, op.evaluateBinary(4, 4, 0xffffffffL, 0xffffffffL));
		Assert.assertEquals(0, op.evaluateBinary(4, 4, 0, 0x80000000L));
		Assert.assertEquals(0x80000000L, op.evaluateBinary(4, 4, 0x80000000L, 1));
		Assert.assertEquals(0, op.evaluateBinary(4, 4, 1, 0x80000000L));
		Assert.assertEquals(1, op.evaluateBinary(4, 4, 0x80000000L, 0x7fffffffL));
		Assert.assertEquals(0, op.evaluateBinary(4, 4, 0x7fffffffL, 0x80000000L));
		Assert.assertEquals(1, op.evaluateBinary(4, 4, 0x80000000L, 0x80000000L));
		Assert.assertEquals(1, op.evaluateBinary(4, 4, 0x7fffffffL, 0x7fffffffL));

		Assert.assertEquals(0, op.evaluateBinary(8, 8, 1, 0));// divide by zero

		Assert.assertEquals(0x3E0708EEL, op.evaluateBinary(8, 8, 0x2512345678L, 0x99));

		Assert.assertEquals(0, op.evaluateBinary(8, 8, 0, 1));
		Assert.assertEquals(0, op.evaluateBinary(8, 8, 0, 0xffffffffffffffffL));
		Assert.assertEquals(0xffffffffffffffffL, op.evaluateBinary(8, 8, 0xffffffffffffffffL, 1));
		Assert.assertEquals(0, op.evaluateBinary(8, 8, 1, 0xffffffffffffffffL));
		Assert.assertEquals(1, op.evaluateBinary(8, 8, 0xffffffffffffffffL, 0xffffffffffffffffL));
		Assert.assertEquals(0, op.evaluateBinary(8, 8, 0, Long.MIN_VALUE));
		Assert.assertEquals(Long.MIN_VALUE, op.evaluateBinary(8, 8, Long.MIN_VALUE, 1));
		Assert.assertEquals(0, op.evaluateBinary(8, 8, 1, Long.MIN_VALUE));
		Assert.assertEquals(1, op.evaluateBinary(8, 8, Long.MIN_VALUE, Long.MAX_VALUE));
		Assert.assertEquals(0, op.evaluateBinary(8, 8, Long.MAX_VALUE, Long.MIN_VALUE));
		Assert.assertEquals(1, op.evaluateBinary(8, 8, Long.MIN_VALUE, Long.MIN_VALUE));
		Assert.assertEquals(1, op.evaluateBinary(8, 8, Long.MAX_VALUE, Long.MAX_VALUE));
	}

	@Test
    public void testEvaluateBinaryBigInteger() {

		OpBehaviorIntDiv op = new OpBehaviorIntDiv();

		BigInteger NEGATIVE_ONE = Utils.convertToUnsignedValue(BigInteger.valueOf(-1), 16);
		BigInteger BIG_POSITIVE = new BigInteger("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);
		BigInteger BIG_NEGATIVE = Utils.convertToUnsignedValue(
			new BigInteger("80000000000000000000000000000000", 16), 16);

		assertEquals(BigInteger.ZERO, op.evaluateBinary(1, 16, BigInteger.ONE, BigInteger.ZERO),
			16);

		Assert.assertEquals(BigInteger.valueOf(0x3E0708EEL),
			op.evaluateBinary(8, 8, BigInteger.valueOf(0x2512345678L), BigInteger.valueOf(0x99)));

		assertEquals(BigInteger.ZERO, op.evaluateBinary(1, 16, BigInteger.ZERO, BigInteger.ONE),
			16);
		assertEquals(BigInteger.ZERO, op.evaluateBinary(1, 16, BigInteger.ZERO, NEGATIVE_ONE), 16);
		assertEquals(NEGATIVE_ONE, op.evaluateBinary(1, 16, NEGATIVE_ONE, BigInteger.ONE), 16);
		assertEquals(BigInteger.ZERO, op.evaluateBinary(1, 16, BigInteger.ONE, NEGATIVE_ONE), 16);
		assertEquals(BigInteger.ONE, op.evaluateBinary(1, 16, NEGATIVE_ONE, NEGATIVE_ONE), 16);
		assertEquals(BigInteger.ZERO, op.evaluateBinary(1, 16, BigInteger.ZERO, BIG_NEGATIVE), 16);
		assertEquals(BIG_NEGATIVE, op.evaluateBinary(1, 16, BIG_NEGATIVE, BigInteger.ONE), 16);
		assertEquals(BigInteger.ZERO, op.evaluateBinary(1, 16, BigInteger.ONE, BIG_NEGATIVE), 16);
		assertEquals(BigInteger.ONE, op.evaluateBinary(1, 16, BIG_NEGATIVE, BIG_POSITIVE), 16);
		assertEquals(BigInteger.ZERO, op.evaluateBinary(1, 16, BIG_POSITIVE, BIG_NEGATIVE), 16);
		assertEquals(BigInteger.ONE, op.evaluateBinary(1, 16, BIG_NEGATIVE, BIG_NEGATIVE), 16);// overflow
		assertEquals(BigInteger.ONE, op.evaluateBinary(1, 16, BIG_POSITIVE, BIG_POSITIVE), 16);// overflow

	}
}
